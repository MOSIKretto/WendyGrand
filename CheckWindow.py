import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QCheckBox, QDesktopWidget, QHBoxLayout
from PyQt5.QtCore import Qt
import SmartManager
import GitChecker
import subprocess
import os
        
class Check(QWidget):

    def __init__(self):  # Исправлено на __init__
        super().__init__()  # Исправлено на __init__
        self.initGui()

    def initGui(self):
        self.setFixedSize(400, 150)
        self.setStyleSheet("background-color: black;")
        self.setWindowTitle("Wendy's Installer")

        layout = QVBoxLayout()

        self.label = QLabel("Для установки Wendy требуется Git", self)
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.label.setStyleSheet("color: white;")

        self.checkbox = QCheckBox("Удалить Git после установки", self)
        self.checkbox.setStyleSheet(
            "QCheckBox {"
            "   border: 2px solid #2a2a2a;"
            "   border-radius: 10px;"
            "   padding: 5px;"
            "   font-size: 12px;" 
            "   color: white;"
            "}"
            "QCheckBox:indicator {"
            "   border: 2px solid #2a2a2a;"
            "   width: 20px;"
            "   height: 20px;"
            "   border-radius: 10px;"
            "}"
            "QCheckBox:indicator:checked {"
            "   background-color: #FFFFFF;"
            "}"
        )
        self.checkbox.stateChanged.connect(self.git_select)

        # Создаем горизонтальный layout для кнопок
        button_layout = QHBoxLayout()

        self.install_button = QPushButton("Установить Git", self)
        self.install_button.setStyleSheet("background-color: white;")
        self.install_button.clicked.connect(self.install)
        
        self.has_git_button = QPushButton("У меня есть Git", self)
        self.has_git_button.setStyleSheet("background-color: white;")
        self.has_git_button.clicked.connect(self.has_git)

        button_layout.addWidget(self.install_button)
        button_layout.addWidget(self.has_git_button)

        layout.addWidget(self.label)
        layout.addWidget(self.checkbox)
        layout.addLayout(button_layout)  # Добавляем горизонтальный layout с кнопками

        self.setLayout(layout)
        self.center()  # Централизуем окно

    def center(self):
        """Централизует окно на экране."""
        qr = self.frameGeometry()  # Получаем геометрию окна
        cp = QDesktopWidget().availableGeometry().center()  # Получаем центр экрана
        qr.moveCenter(cp)  # Перемещаем окно к центру
        self.move(qr.topLeft())  # Устанавливаем новое положение окна

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
        # Получаем состояние чекбокса
        is_checked = self.checkbox.isChecked()
        if is_checked:
            # Здесь пишите алгоритм, если галка поставлена 
            print("Git будет удалён после установки")
            with open("const.txt", "w") as file:
                print(1, file=file)
            SmartManager.SmartManager()
            sys.exit(0)
        else:
            # Здесь пишите алгоритм, если галки нет
            print("Git не будет удалён")
            with open("const.txt", "w") as file:
                print(1, file=file)
            os.remove('const.txt')
            SmartManager.SmartManager()
            sys.exit(0)
            
    def git_select(self):
        # обновляем метод на простой вывод состояния чекбокса (необязательно)
        if self.checkbox.isChecked():
            print("Чекбокс отмечен")
        else:
            print("Чекбокс не отмечен")
    
    def has_git(self):
        if GitChecker.is_git_installed() == True:
            with open("const.txt", "w") as file:
                print(1, file=file)
            os.remove('const.txt') 
            subprocess.run(["java", "Translator.java", "ReadyWindow.py"])
            sys.exit(0)
        else:
            from PointTwoWindow import CuteLabel as CL
            CL.changeText("У вас нет git в системе")

app = QApplication(sys.argv)
window = Check()
window.show()
sys.exit(app.exec())
