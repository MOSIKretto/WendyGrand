from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QLabel, QVBoxLayout, QDesktopWidget
from PyQt5.QtCore import Qt
import subprocess
import GitRemuver
import sys

class End(QWidget):

    #Отрисовка окна
    def __init__(self):

        super().__init__()
        self.setFixedSize(450,100)
        self.setWindowTitle("Wendy's installer")
        self.setStyleSheet("background-color: black;")
        self.startPos = None
        self.isDragging = False
        layout = QVBoxLayout()
        self.label = QLabel("Wendy успешно установлена, можете начать использование!", self)
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.label.setStyleSheet("color: white;")
        self.close_btn = QPushButton("Выйти из установщика", self)
        self.close_btn.setStyleSheet("background-color: white;")
        self.close_btn.clicked.connect(self.closing)
        layout.addWidget(self.label)
        layout.addWidget(self.close_btn)
        self.setLayout(layout)
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

    #Закрываем установщик
    def closing(self):

        try:
            file = open("const.txt", "r")

            if file:
                print("Удалить Git!")
                self.check_and_remove_git()
                sys.exit(0)

        except:        
                print("Git не будет удален.")
                sys.exit(0)

    #Проверка и удаление git в системе
    def check_and_remove_git(self):

        package_managers = ["pacman", "apt", "dnf", "yum"]
        installed_managers = []

        for manager in package_managers:
            if manager:
                installed_managers.append(manager)

        if installed_managers:
            GitRemuver.RemuverGit(installed_managers)

        try:
            subprocess.run([manager, '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
            return True
        
        except subprocess.CalledProcessError:
            return False
        
        except FileNotFoundError:
            return False


app = QApplication(sys.argv)
window = End()
window.show()
sys.exit(app.exec())
