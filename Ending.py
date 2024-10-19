
# Окно из пунка 3 ТЗ на 19.10.2024
# Локальная проверка --- ГОТОВО
# Внешняя проверка --- ОЖИДАЕТСЯ
# СТАТУС --- ОЖИДАЕТ ПРОВЕРКИ

import sys
from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QLabel, QVBoxLayout, QDesktopWidget
from PyQt5.QtCore import Qt

class End(QWidget):
    def __init__(self):
        super().__init__()
        self.setFixedSize(450,125)
        self.setWindowTitle("Wendy's installer")
        self.setStyleSheet("background-color: black;")
        self.startPos = None
        self.isDragging = False
        layout = QVBoxLayout()
        self.label = QLabel("Wendy успешно установлена, можете начать использование", self)
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.label.setStyleSheet("color: white;")
        self.start_btn = QPushButton("Запустить Wendy", self)
        self.start_btn.setStyleSheet("background-color: white;")
        self.close_btn = QPushButton("Выйти из установщика", self)
        self.close_btn.setStyleSheet("background-color: white;")
        self.start_btn.clicked.connect(self.start)
        self.close_btn.clicked.connect(self.closing)
        layout.addWidget(self.label)
        layout.addWidget(self.start_btn)
        layout.addWidget(self.close_btn)
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

    def start(self):
        #  прописываем запуск венди
        print('Wendy запускается')

    def closing(self):
        sys.exit(0)

app = QApplication(sys.argv)
window = End()
window.show()
sys.exit(app.exec())
