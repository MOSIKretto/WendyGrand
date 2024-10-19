import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QCheckBox, QDesktopWidget
from PyQt5.QtCore import Qt

class CheckAdiction(QWidget):
    def __init__(self):
        super().__init__()
        self.initGUI()
    
    def initGUI(self):
        self.setWindowTitle("Wendy's installer")
        self.setStyleSheet("background-color: black;")
        self.setFixedSize(400, 150)
        layout = QVBoxLayout()
        self.startPos = None
        self.isDragging = False
        self.label = QLabel('Проверка зависимостей...', self)
        self.label.setStyleSheet("color: white;")
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.checkbox = QCheckBox("Java", self)
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
            "   background-color: #33FF00;"
            "}"
        )
        self.checkbox.setChecked(False)
        self.checkbox.setEnabled(False)
        self.button = QPushButton('Начать проверку', self)
        self.button.setStyleSheet("background-color: white;")
        self.button.clicked.connect(self.forth)
        layout.addWidget(self.label)
        layout.addWidget(self.checkbox)
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

    def forth(self):
        if self.checkbox.isChecked() == False:
            self.button.setText('Подождите, идёт проверка...')
            # короче работу этого метода тебе прям нужно понять, потому что тут должна быть как раз проверка на наличие Java, случай того, что java - нет, я думаю ты сам напишешь
            # а так вот окно готово
            self.setEnabled(False)
            print('идет а-ля поиск java')
            self.checkbox.setChecked(True)
            self.label.setText('Зависимости найдены!')
            self.label.adjustSize()
            self.button.setText('Дальше')
            self.button.setEnabled(True)
            self.forth()

        else:
            print('переход на следующее окно')

app = QApplication(sys.argv)
window = CheckAdiction()
window.show()
sys.exit(app.exec())


        