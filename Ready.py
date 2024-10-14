
#ГОТОВО!!!

import sys
import subprocess
from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QLabel, QPushButton, QDesktopWidget
from PyQt5.QtCore import Qt
from CheckWindow import Flag
import GitRemuver

class Ready(QWidget):

    def __init__(self):
        super().__init__()
        self.setFixedSize(290, 125)
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

    def install(self):
        subprocess.call(['./WendyClone.sh'])
        if Flag == True:

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

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = Ready()
    window.show()
    exit(app.exec())