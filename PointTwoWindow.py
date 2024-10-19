
# Окно по пункту 2 из ТЗ на 19.10.2024

import sys
from PyQt5.QtWidgets import QApplication, QWidget, QDesktopWidget, QLabel, QPushButton, QVBoxLayout
from PyQt5.QtCore import Qt

class CuteLabel(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Wendy's installer")
        self.setStyleSheet("background-color: black;")
        self.setFixedSize(300, 150)
        self.startPos = None
        self.isDragging = False
        layout = QVBoxLayout()
        self.label = QLabel('', self)
        self.label.setStyleSheet("color: white;")
        self.label.setAlignment(Qt.AlignmentFlag.AlignHCenter)
        self.button = QPushButton('Какая-то кнопка', self)
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
        # здесь действия при нажатии кнопки
        print('Кнопка нажата')
    
    def changeText(self, text): # этот метод отвечает за смену текста в строке
        self.label.setText(text)

app = QApplication(sys.argv)
window = CuteLabel()
window.show()
sys.exit(app.exec())
