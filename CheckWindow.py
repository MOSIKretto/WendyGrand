import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QCheckBox, QDesktopWidget
from PyQt5.QtCore import Qt

class Check(QWidget):

    def __init__(self):  # Исправлено на __init__
        super().__init__()  # Исправлено на __init__
        self.initGui()

    def initGui(self):
        self.setFixedSize(400, 150)
        self.setStyleSheet("background-color: black;")

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
        # Получаем состояние чекбокса
        is_checked = self.checkbox.isChecked()
        if is_checked:
            print("Git будет удалён после установки.")
            # Здесь пишите алгоритм, если галка поставлена
        else:
            print("Git не будет удалён.")
            # Здесь пишите алгоритм, если галки нет

    def git_select(self):
        # обновляем метод на простой вывод состояния чекбокса (необязательно)
        if self.checkbox.isChecked():
            print("Чекбокс отмечен.")
        else:
            print("Чекбокс не отмечен.")

app = QApplication(sys.argv)
window = Check()
window.show()
sys.exit(app.exec())
