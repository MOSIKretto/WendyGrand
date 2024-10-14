'''
* *Check_window*
*
*RU Второе окно после инсталлера, настройка параметров установки
*-------------------------------------------------------------
*En The second window after the installer, setting the installation parameters
*
'''


import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QCheckBox, QDesktopWidget
from PyQt5.QtCore import Qt

class Check(QWidget):
    def __init__(self):
        super().__init__()
        self.initGui()


    def initGui(self):
        self.setFixedSize(400, 150)
        self.setStyleSheet("background-color: black;")

        layout = QVBoxLayout()

        self.label = QLabel("Для установки Wendy требуется Git", self)
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.label.setStyleSheet("color: white;")

        self.checkbox = QCheckBox("Удалить Git после установки",self)
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
            "   background-color: #4caf50;"
            "}"
        )
        self.checkbox.stateChanged.connect(self.git_select)

        self.button = QPushButton("Установить Wendy", self)
        self.button.setStyleSheet("background-color: white;")
        self.button.clicked.connect(self.install)

        layout.addWidget(self.label)
        layout.addWidget(self.checkbox)
        layout.addWidget(self.button)
        
        
        self.setLayout(layout)
        self.center()  # Централизуем окно



    def center(self):
        """Централизует окно на экране."""
        qr = self.frameGeometry()  # Получаем геометрию окна
        cp = QDesktopWidget().availableGeometry().center()  # Получаем центр экрана
        qr.moveCenter(cp)  # Перемещаем окно к центру
        self.move(qr.topLeft())  # Устанавливаем новое положение окна

    def install(self):
        if self.git_select == True:
            print(1)
            # здесь пишешь алгоритм, если галка поставлена
            pass
        else:
            print(0)
            # здесь пишешь алгоритм, если галки нет, жаль галку:(....
            pass

    def git_select(self):
        if self.checkbox.isChecked:
            # здесь прописываем, что делать при помеченном чек боксе 
            return True
        else:
            # здесь прописываем при НЕ помеченном чек боксе
            return False

app = QApplication(sys.argv)
window = Check()
window.show()
sys.exit(app.exec())


