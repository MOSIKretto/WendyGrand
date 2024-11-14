import sys
from PyQt5.QtGui import QIcon
from PyQt5.QtWidgets import QApplication, QWidget, QLineEdit, QLabel, QVBoxLayout, QPushButton, QLabel, QDesktopWidget, QHBoxLayout
from PyQt5.QtCore import Qt

browser_text = 'Выберете браузер:'
app_store = 'Выберете магазин приложений:'

class Settings_Win(QWidget):
    def __init__(self):
        super().__init__()

        # Настройки окна
        self.setWindowTitle("Wendy's settings")
        self.setStyleSheet("background-color: #BEBEBE")
        # Задаем фиксированный размер окна
        self.setFixedSize(400, 300)  # Ширина 400, высота 300
        self.startPos = None
        self.isDragging = False

        self.setWindowIcon(QIcon('WGui.ico'))

        # Создание разметки окна
        layout = QVBoxLayout()
        button_layout = QHBoxLayout()

        #Создание и настройка строки
        self.text_browser = QLabel(self)
        self.text_browser.setText(f"<pre style='font-family:Courier; font-size:12pt; color: black;'>{browser_text}</pre>")
        layout.addWidget(self.text_browser, alignment=Qt.AlignmentFlag.AlignCenter)

        # Создание и настройка поля ввода для данных браузера, которые пойдут в конфиг
        self.name_browser_input = QLineEdit(self)
        self.name_browser_input.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        layout.addWidget(self.name_browser_input)

        # Создание и настройка кнопки сохранения и отправки данных браузера в конфиг
        self.browser_Button = QPushButton('Save and apply', self)
        self.browser_Button.setStyleSheet("font-size:12pt; font-family:Courier; color: black")
        self.browser_Button.clicked.connect(self.browser_select)
        layout.addWidget(self.browser_Button)

        # Создание и настройка строки
        self.text_app_store = QLabel(self)
        self.text_app_store.setText(f"<pre style='font-family:Courier; font-size:12pt; color: black;'>{app_store}</pre>")
        layout.addWidget(self.text_app_store, alignment=Qt.AlignmentFlag.AlignCenter)

        # Создание и настройка поля ввода для данных магазина приложения, которые пойдут в конфиг
        self.name_app_store_input = QLineEdit(self)
        self.name_app_store_input.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        layout.addWidget(self.name_app_store_input)

        # Создание и настройка кнопки сохранения и отправки данных магазина приложений в конфиг
        self.app_store_button = QPushButton('Save and apply', self)
        self.app_store_button.setStyleSheet("font-size:12pt; font-family:Courier; color: black")
        self.app_store_button.clicked.connect(self.app_store_select)
        button_layout.addWidget(self.app_store_button)

        # Создание и настройка кнопки выхода
        self.close_button = QPushButton('EXIT', self)
        self.close_button.setStyleSheet("font-size:12pt; font-family:Courier; color: black")
        self.close_button.clicked.connect(self.exit)
        button_layout.addWidget(self.close_button)
        
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

    def exit(self): # Закрытие окна настроек
        sys.exit(app.exec())

    def browser_select(self): # передает данные в конфиг
        send = self.name_browser_input.text()
        if send:
            pass # передача данных в конфиг

    def app_store_select(self): # передает данные в конфиг
        send = self.name_app_store_input.text()
        if send:
            pass # передача данных в конфиг


app = QApplication(sys.argv)
settings_window = Settings_Win()
settings_window.show()
sys.exit(app.exec())