import sounddevice as sd
import subprocess
import vosk
import time
import os


class Recognizer:

    def __init__(self):
        self.lastCommandTime = 0
        self.model = vosk.Model(os.path.abspath("../WendyGrand/src/main/recognizer/resources/model_small"))
        self.samplerate = int(sd.query_devices(sd.default.device[0], 'input')['default_samplerate'])

        self.dictionaryHandler = os.path.abspath("../WendyGrand/src/main/logic/main/DictionaryHandler.java")

        self.helloSTART = os.path.abspath("../WendyGrand/src/main/recognizer/helpers/voiceover/helloSTART.java")
        self.byeFINISH = os.path.abspath("../WendyGrand/src/main/recognizer/helpers/voiceover/byeFINISH.java")
        self.hello = os.path.abspath("../WendyGrand/src/main/recognizer/helpers/voiceover/hello.java")

        self.names = {"венди", "вэнди", "среда"}
        self.exitPhrases = self.exit()
        self.running = True
        self.recognizer = None

    def exit(self):
        commands = {"пока", "закройся", "выход", "закрывайся"}
        return commands | {f"{name} {cmd}" for name in self.names for cmd in commands}

    def start(self):
        subprocess.run(["java", self.helloSTART])
        self.mainLoop()

    def processCommand(self, text):
        print(f"Распознано: {text}")
        with open('../WendyGrand/settings/configs/History.conf', 'a') as f:
            f.write(f"{text}\n")

        if text in self.exitPhrases:
            subprocess.run(["java", self.byeFINISH])
            self.running = False
            return

        for name in self.names:
            if text.startswith(name):
                command = text[len(name):].strip()
                if command:
                    subprocess.run(["java", self.dictionaryHandler, command])
                else:
                    self.lastCommandTime = time.time()
                    subprocess.run(["java", self.hello])
                return

        current_time = time.time()
        if current_time - self.lastCommandTime <= 10:
            subprocess.run(["java", self.dictionaryHandler, text])
            self.lastCommandTime = 0

    def callback(self, indata, frames, time_info, status):
        if self.recognizer.AcceptWaveform(bytes(indata)):
            result = self.recognizer.Result()
            text = result[14:-3].strip()
            if not text:
                return
                
            current_time = time.time()
            time_valid = current_time - self.lastCommandTime <= 10
            if text in self.exitPhrases or any(text.startswith(name) for name in self.names) or time_valid:
                self.processCommand(text)

    def mainLoop(self):
        self.recognizer = vosk.KaldiRecognizer(self.model, self.samplerate)
        
        with sd.RawInputStream(
            samplerate=self.samplerate,
            blocksize=8000,
            dtype='int16',
            channels=1,
            callback=self.callback
        ):
            while self.running:
                time.sleep(0.1)


if __name__ == "__main__":
    Recognizer().start()