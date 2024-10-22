from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QDesktopWidget
from PyQt5.QtCore import Qt
import subprocess
import sys

class Installer(QWidget):

    #Отрисовка окна
    def __init__(self):

        super().__init__()
        self.setWindowTitle("Wendy's Installer")
        self.setFixedSize(350, 75)
        self.startPos = None
        self.isDragging = False
        self.layout = QVBoxLayout(self)
        self.setStyleSheet("background-color: black;")
        self.label = QLabel(self)
        self.label.setText("Нажмите на кнопку и Wendy начнет установку")
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.layout.addWidget(self.label)
        self.label.setStyleSheet("color: white;")
        self.install_button = QPushButton("Установить", self)
        self.install_button.setStyleSheet("background-color: white;") 
        self.install_button.clicked.connect(self.install)
        self.layout.addWidget(self.install_button)
        self.center()

    #Централизовать окно
    def center(self):

        qr = self.frameGeometry()
        cp = QDesktopWidget().availableGeometry().center()
        qr.moveCenter(cp)
        self.move(qr.topLeft())

    #Передвижение окна
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

    #Запуск следущего окна 
    def install(self):

        subprocess.run(["java", "Translator.java", "CheckWindow.py"])
        sys.exit(0)


app = QApplication(sys.argv)
window = Installer()
window.show()
sys.exit(app.exec())