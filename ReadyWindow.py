import sys
import subprocess
from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QLabel, QPushButton, QDesktopWidget
from PyQt5.QtCore import Qt
import GitRemuver

class Ready(QWidget):

    def __init__(self):
        super().__init__()
        self.initGui()

    def initGui(self):
        self.setFixedSize(290, 125)
        self.setWindowTitle("Wendy's Installer")
        self.setStyleSheet("background-color: black;")
        layout = QVBoxLayout()
        self.label = QLabel("Всё готово для установки Wendy!", self)
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.label.setStyleSheet("color: white;")
        self.button = QPushButton("Начать установку Wendy")
        self.button.setStyleSheet("background-color: white;")
        self.button.clicked.connect(self.install)
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

    def install(self):
        #Сделать установку венди
        subprocess.run(["java", "Translator.java", "EndingWindow.py"])
        sys.exit(0)
    
app = QApplication(sys.argv)
window = Ready()
window.show()
sys.exit(app.exec())
