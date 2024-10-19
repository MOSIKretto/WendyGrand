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
        #Прописать установку с гита
        file = open("const.txt", "r")
        if bool(file): # Проверяем наличие файла const.txt
            print("Удалить Git!")
            self.check_and_remove_git() # Вызываем функцию проверки и удаления Git
        else:
            print("Git не будет удален.")

    def check_and_remove_git(self):
        def check_package_manager(manager):
            try:
                subprocess.run([manager, '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
                return True
            except subprocess.CalledProcessError:
                return False
            except FileNotFoundError:
                return False

        package_managers = ["pacman", "apt", "dnf", "yum"]
        installed_managers = []
        for manager in package_managers:
            if check_package_manager(manager):
                installed_managers.append(manager)
        if installed_managers:
            GitRemuver.RemuverGit(installed_managers)

    
app = QApplication(sys.argv)
window = Ready()
window.show()
sys.exit(app.exec())
