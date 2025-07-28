from Voiceover import ActionsVoiceover
import sounddevice as sd
import subprocess
import queue
import vosk
import time


class Recognizer:

    def __init__(self):
        self.lastCommandTime = 0
        self.model = vosk.Model("../WendyGrand/src/main/resources/python/model_small")
        self.samplerate = int(sd.query_devices(sd.default.device[0], 'input')['default_samplerate'])
        self.wordHandler = "../WendyGrand/src/main/java/core/WordHandler.java"
        self.names = {"венди", "вэнди", "среда"}
        self.exitPhrases = self.exit()
        self.running = True
        self.commandQueue = queue.Queue()
        self.recognizer = None

    def exit(self):
        commands = {"пока", "закройся", "выход", "закрывайся"}
        return commands | {f"{name} {cmd}" for name in self.names for cmd in commands}

    def start(self):
        ActionsVoiceover.HelloVoiceover()
        self.mainLoop()

    def processCommand(self, text):
        print(f"Распознано: {text}")
        with open('../WendyGrand/configs/History.conf', 'a') as f:
            f.write(f"{text}\n")

        if text in self.exitPhrases:
            ActionsVoiceover.ByeVoiceover()
            self.running = False
            return

        for name in self.names:
            if text.startswith(name):
                command = text[len(name):].strip()
                if command:
                    subprocess.run(["java", self.wordHandler, command])
                else:
                    self.lastCommandTime = time.time()
                    ActionsVoiceover.hello()
                return

        current_time = time.time()
        if current_time - self.lastCommandTime <= 10:
            subprocess.run(["java", self.wordHandler, text])
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
                self.commandQueue.put(text)

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
                try:
                    text = self.commandQueue.get(timeout=0.1)
                    self.processCommand(text)
                except queue.Empty:
                    continue

if __name__ == "__main__":
    Recognizer().start()