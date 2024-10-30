from PyQt5.QtWidgets import QApplication, QWidget, QPushButton, QLabel, QHBoxLayout, QVBoxLayout, QDesktopWidget
from PyQt5.QtCore import Qt
import subprocess
import GitRemuver
import sys

class End(QWidget):

    #Отрисовка окна
    def __init__(self):
        super().__init__()
        self.setFixedSize(650, 250)
        self.setWindowTitle("Wendy's installer")
        self.setStyleSheet("background-color: black;")
        self.startPos = None
        self.isDragging = False

        button_layout = QHBoxLayout()
        self.retry_btn = QPushButton("Попробовать ещё раз", self)
        self.retry_btn.setStyleSheet("background-color: white;")
        self.retry_btn.clicked.connect(self.retry_install)
        button_layout.addWidget(self.retry_btn)

        self.close_btn = QPushButton("Выйти из установщика", self)
        self.close_btn.setStyleSheet("background-color: white;")
        self.close_btn.clicked.connect(self.closing)
        button_layout.addWidget(self.close_btn)

        layout = QVBoxLayout()
        self.label = QLabel("Wendy успешно установлена, можете начать использование!\n \n \n Warning!!!\n Если это окно появилось моментально или папка ''WendyGrand'' не была создана,\n возможно, у вас проблемы с интернетом или не удалось подключиться к GitHub.\n\n Попробуйте еще раз!", self)
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.label.setStyleSheet("color: white;")

        layout.addWidget(self.label)
        layout.addLayout(button_layout)
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

    def check_and_remove_git(self):

        package_managers = ["pacman", "apt", "dnf", "yum"]
        installed_managers = []

        for manager in package_managers:
            if manager:
                installed_managers.append(manager)

        if installed_managers:
            GitRemuver.RemuverGit(installed_managers)

        if installed_managers:
            try:
                subprocess.run([installed_managers[0], '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
                return True

            except subprocess.CalledProcessError:
                return False

            except FileNotFoundError:
                return False

    def retry_install(self):
        
        import threading
        def run_checkwindow():
            subprocess.call(["python3", "ProgressBar.py"])
        thread = threading.Thread(target=run_checkwindow)
        thread.start()
        self.close()

app = QApplication(sys.argv)
window = End()
window.show()
sys.exit(app.exec())
