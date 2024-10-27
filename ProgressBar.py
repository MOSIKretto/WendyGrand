from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QDesktopWidget
from PyQt5.QtCore import Qt, QThread, pyqtSignal
import subprocess
import sys
import ProgressHandler
import time  # Временная хрень

ascii_picture_one = r"""
∩~~~
~  ~
~@~~
"""
ascii_picture_two = r"""
~~~~
∩  ~
~~@~
"""
ascii_picture_three = r"""
~~~~
~  ~
C~~@
"""
ascii_picture_four = r"""
~~~~
~  @
~C~~
"""
ascii_picture_five = r"""
~~~@
~  ~
~~C~
"""
ascii_picture_six = r"""
~~@~
~  ~
~~~U
"""
ascii_picture_seven = r"""
~@~~
~  U
~~~~
"""
ascii_picture_eight = r"""
@~~Ɔ
~  ~
~~~~
"""
ascii_picture_ten = r"""
~~Ɔ~
@  ~
~~~~
"""
ascii_picture_eleven = r"""
~Ɔ~~
~  ~
@~~~
"""

class CloneThread(QThread):
    finished = pyqtSignal()

    def run(self):
        self.Clone()
        self.finished.emit()

    def Clone(self):
        time.sleep(20)  # Временная хрень
        subprocess.run(["java", "Translator.java", "EndingWindow.py"])

class Progress(QWidget):

    # Отрисовка окна
    def __init__(self):

        super().__init__()
        self.setWindowTitle("Wendy's cloning...")
        self.setFixedSize(250, 200)
        self.startPos = None
        self.isDragging = False
        self.layout = QVBoxLayout(self)
        self.setStyleSheet("background-color: black;")
        self.art = QLabel('', self)
        self.art.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.art.setStyleSheet("color: white;")
        self.label = QLabel(self)
        self.label.setText("Копирование репозитория...")
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.layout.addWidget(self.label)
        self.label.setStyleSheet("color: white;")
        self.layout.addWidget(self.art)
        self.center()

        # Запуск потока
        self.clone_thread = CloneThread()
        self.handler = ProgressHandler.ProgressHandler()
        self.handler.signal.connect(self.start_animation)
        self.clone_thread.finished.connect(self.close)
        self.clone_thread.start()
        self.handler.start()


    def start_animation(self, step):
        match step:
            case 1:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_one}</pre")
            case 2:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_two}</pre")
            case 3:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_three}</pre")
            case 4:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_four}</pre")
            case 5:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_five}</pre")
            case 6:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_six}</pre")
            case 7:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_seven}</pre")
            case 8:
                self.art.setText(f"<pre style='font-family:Courier; background-color: black; font-size:12pt; color: #FFFFFF;'>{ascii_picture_eight}</pre")
            case other:
                self.art.setText("OUTPUT ART ERROR!!!")

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
