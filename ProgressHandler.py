from PyQt5 import QtCore
from time import sleep

class ProgressHandler(QtCore.QThread): # Для анимации загрузки
    signal = QtCore.pyqtSignal(int)
    def run(self):
        for step in range(1,9):
            self.signal.emit(step)
            sleep(0.1)
        self.run()