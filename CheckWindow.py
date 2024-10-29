from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QCheckBox, QDesktopWidget, QHBoxLayout
from PyQt5.QtCore import Qt
import SmartManager
import GitChecker
import subprocess
import sys
import os
        
class Check(QWidget):

    def __init__(self):
        super().__init__()
        self.initGui()

    #Отрисовка окна
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
        layout.addLayout(button_layout)
        self.setLayout(layout)
        self.center()  
        
    #Централизуем окно
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

    #Запуск установки
    def install(self):

        is_checked = self.checkbox.isChecked()

        if is_checked:
            print("Git будет удалён после установки")
            self.Flag()
            SmartManager.SmartManager()
            sys.exit(0)

        else:
            print("Git не будет удалён")
            self.Flag()
            os.remove('const.txt')
            SmartManager.SmartManager()
            sys.exit(0)

    #вывод состояния чекбокса (необязательно)
    def git_select(self):

        if self.checkbox.isChecked():
            print("Чекбокс отмечен")

        else:
            print("Чекбокс не отмечен")
    
    #Проверка git в системе
    def has_git(self):

        if GitChecker.is_git_installed() == True:
            self.Flag()
            os.remove('const.txt') 

            from InternetCheck import InternetCheck

            if InternetCheck():

                import threading
                def window():
                    subprocess.run(["python3", "ReadyWindow.py"])
                thread = threading.Thread(target=window)
                thread.start()
                self.close()
                
            else:
                with open("error.txt", "w") as file:
                    print("Дисконект! Проверьте подключение к интернету :(", file=file)

                import threading
                def window():
                    subprocess.run(["python3", "PointTwoWindow.py"])
                thread = threading.Thread(target=window)
                thread.start()
                self.close()

        else:
            with open("error.txt", "w") as file:
                print("У вас нет Git в системе :( Пожалуйста, установите его.", file=file)
                
            import threading
            def window():
                subprocess.run(["python3", "PointTwoWindow.py"])
            thread = threading.Thread(target=window)
            thread.start()
            self.close()

    #Создание флага
    def Flag(self):

        with open("const.txt", "w") as file:
            print(1, file=file)


app = QApplication(sys.argv)
window = Check()
window.show()
sys.exit(app.exec())
