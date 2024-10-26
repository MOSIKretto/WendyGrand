from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QDesktopWidget
from PyQt5.QtCore import Qt, QThread, pyqtSignal
import subprocess
import sys
import time  # Временная хрень


class CloneThread(QThread):
    finished = pyqtSignal()

    def run(self):
        self.Clone()
        self.finished.emit()

    def Clone(self):
        time.sleep(5)  # Временная хрень
        subprocess.run(["java", "Translator.java", "EndingWindow.py"])


class Progress(QWidget):

    # Отрисовка окна
    def __init__(self):

        super().__init__()
        self.setWindowTitle("Wendy's cloning...")
        self.setFixedSize(350, 75)
        self.startPos = None
        self.isDragging = False
        self.layout = QVBoxLayout(self)
        self.setStyleSheet("background-color: black;")
        self.label = QLabel(self)
        self.label.setText("Копирование репозитория...")
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.layout.addWidget(self.label)
        self.label.setStyleSheet("color: white;")
        self.center()

        # Запуск потока
        self.clone_thread = CloneThread()
        self.clone_thread.finished.connect(self.close)
        self.clone_thread.start()

    # Централизовать окно
    def center(self):

        qr = self.frameGeometry()
        cp = QDesktopWidget().availableGeometry().center()
        qr.moveCenter(cp)
        self.move(qr.topLeft())

    # Передвижение окна
    def mousePressEvent(self, event):

        if event.button() == Qt.LeftButton:
            self.isDragging = True
            self.startPos = event.pos()

    def mouseMoveEvent(self, event):

        if self.isDragging:
            delta = event.pos() - self.startPos
            self.move(self.pos() + delta)

    def mouseReleaseEvent(self, event):

        if event.button() == Qt.LeftButton:
            self.isDragging = False
            self.startPos = None


app = QApplication(sys.argv)
window = Progress()
window.show()
sys.exit(app.exec())
