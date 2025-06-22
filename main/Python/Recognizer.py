from Voiceover import ActionsVoiceover
import sounddevice as sd
import subprocess
import vosk
import time
import sys


class VoiceAssistant:
    def __init__(self):
        self.last_command_time = 0
        self.model = vosk.Model("../WendyGrand/main/Resources/model_small")
        self.samplerate = int(sd.query_devices(sd.default.device[0], 'input')['default_samplerate'])
        self.word_handler = "../WendyGrand/main/Java/Handlers/WordHandler.java"
        self.stop_commands = {"пока", "закройся", "выход"}
        self.names = {"венди", "вэнди", "среда"}
        self.exit_flag = False

    def start(self):
        ActionsVoiceover.HelloVoiceover()
        self.main_loop()

    def should_exit(self, text):
        return any(text == cmd or text.startswith(f"{name} {cmd}") for name in self.names for cmd in self.stop_commands)

    def should_print(self, text):
        return (any(text.startswith(name) for name in self.names) or (time.time() - self.last_command_time <= 10))

    def process_command(self, text):
        if self.should_exit(text):
            if not self.exit_flag:
                print(f"Распознано: {text}")
                ActionsVoiceover.ByeVoiceover()
                self.exit_flag = True
            return False

        if self.should_print(text):
            print(f"Распознано: {text}")

        for name in self.names:
            if text.startswith(name):
                command = text[len(name):].strip()
                if command:
                    subprocess.run(["java", self.word_handler, command])
                else:
                    self.last_command_time = time.time()
                    ActionsVoiceover.hello()
                return True

        if time.time() - self.last_command_time <= 10:
            subprocess.run(["java", self.word_handler, text])
            self.last_command_time = 0
        return True

    def audio_callback(self, indata, frames, time, status):
        if self.recognizer.AcceptWaveform(bytes(indata)):
            if text := self.recognizer.Result()[14:-3]:
                self.process_command(text.lower())

    def main_loop(self):
        self.recognizer = vosk.KaldiRecognizer(self.model, self.samplerate)
        
        with sd.RawInputStream(
            samplerate=self.samplerate,
            blocksize=8000,
            device=sd.default.device[0],
            dtype='int16',
            channels=1,
            callback=self.audio_callback
        ):
            while not self.exit_flag:
                time.sleep(0.1)
            
        sys.exit(0)

if __name__ == "__main__":
    VoiceAssistant().start()