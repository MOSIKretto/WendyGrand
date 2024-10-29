import sys
import subprocess
from PyQt5.QtWidgets import QApplication, QWidget, QDesktopWidget, QLabel, QPushButton, QVBoxLayout
from PyQt5.QtCore import Qt

class CuteLabel(QWidget): 

    def __init__(self):

        super().__init__()
        self.setWindowTitle("Wendy's installer")
        self.setStyleSheet("background-color: black;")
        self.setFixedSize(450, 75)
        self.startPos = None
        self.isDragging = False
        layout = QVBoxLayout()
        f = list(open("error.txt", "r"))
        self.label = QLabel(f[0], self)
        self.label.setStyleSheet("color: white;")
        self.label.setAlignment(Qt.AlignmentFlag.AlignHCenter)
        self.button = QPushButton('<-- Назад', self)
        self.button.setStyleSheet("background-color: white;")
        self.button.clicked.connect(self.function)
        layout.addWidget(self.label)
        layout.addWidget(self.button)
        self.setLayout(layout)
        self.center()

    def center(self):

        qr = self.frameGeometry()
        cp = QDesktopWidget().availableGeometry().center()
        qr.moveCenter(cp)
        self.move(qr.topLeft())

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

    def function(self):
        
        import threading
        def run_checkwindow():
            subprocess.call(["python3", "CheckWindow.py"])
        thread = threading.Thread(target=run_checkwindow)
        thread.start()
        self.close()


app = QApplication(sys.argv)
window = CuteLabel()
window.show()
sys.exit(app.exec())
