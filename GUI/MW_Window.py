from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton, QTextEdit, QLabel, QDesktopWidget, QLineEdit
from PyQt5.QtGui import QIcon
from PyQt5.QtCore import Qt
import sys


browser_text = 'Select a browser:'
app_store = 'Select the app store'

ascii_art = r""" 
   ;dl      ;xkdooooooooookOc       
   'dk;     'xk;         ,xk,       
    :ko      :Oo'        cOl        
    .ok;     'dko;      ,xx,        
     ;kd'     lKKo      cOc         
     .oO:    'xNNO;    ,xx'         
      ;kd'   cOxoOd'   lOc.         
      .lOc. ,kx,'oO: .,kd'          
       ,kx'.lOc. ;kd'.oO:           
       .lOc;kd'  .lOc;kd'           
        ,xkxO:    ,kkxk:            
        .c0Xd'    .lKXo.            
         'ld;      'lo,             
"""



class Main_Window(QWidget):
    
    def __init__(self):
        super().__init__()
        self.setWindowTitle('Wendy_Grand')
        self.setFixedSize(380, 568)
        self.setStyleSheet("background-color: #3B1E54;")
        self.startPos = None
        self.isDragging = False

        layout = QVBoxLayout()
        self.label = QLabel(self)
        self.label.setText(f"<pre style='font-family:Courier; font-size:14.5pt; color: #E1D7C6;'>{ascii_art}</pre>")
        self.label.setStyleSheet("background-color: #1A1A1D;")
        self.label.setAlignment(Qt.AlignmentFlag.AlignVCenter)

        self.chat_area = QTextEdit(self)
        self.chat_area.setReadOnly(True)
        self.chat_area.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")

        self.button = QPushButton("SETTINGS                                               ⚙️", self)
        self.button.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")
        self.button.clicked.connect(self.settings)

        layout.addWidget(self.label)
        layout.addWidget(self.chat_area)
        layout.addWidget(self.button)

        self.setLayout(layout)
        self.center()

        self.settings_window = None

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

    def settings(self):
        self.settings_window = Settings_Window(self)
        self.settings_window.show()

    def closeEvent(self, event):
        if self.settings_window is not None:
            self.settings_window = None
        event.accept()
        
    def wendy_output(self, text):
        self.chat_area.append(f"Wendy: {text}.")

    def user_input(self, text):
        self.chat_area.append(f"You: {text}.")



class Settings_Window(QWidget):

    def __init__(self, SeparateWindow, parent=None):
        super().__init__(parent)

        self.setWindowTitle("Wendy's settings")
        self.setStyleSheet("background-color: #BEBEBE")
        self.setFixedSize(400, 300)
        self.setWindowIcon(QIcon('WGui.ico'))

        self.layout = QVBoxLayout(self)

        self.text_browser = QLabel(self)
        self.text_browser.setText(f"<pre style='font-family:Courier; font-size:12pt; color: black;'>{browser_text}</pre>")
        self.layout.addWidget(self.text_browser, alignment=Qt.AlignmentFlag.AlignCenter)

        # Создание и настройка поля ввода для данных браузера, которые пойдут в конфиг
        self.name_browser_input = QLineEdit(self)
        self.name_browser_input.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")

        self.layout.addWidget(self.name_browser_input)

        # Добавьте кнопку для сохранения настроек
        self.save_button = QPushButton("Save", self)
        self.save_button.setStyleSheet("font-size:12pt; font-family:Courier; color: black")
        self.save_button.clicked.connect(self.save_settings)
        self.layout.addWidget(self.save_button)

        # Добавьте кнопку для закрытия окна
        self.close_button = QPushButton("Close", self)
        self.close_button.clicked.connect(self.close)
        self.layout.addWidget(self.close_button)

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

    def save_settings(self):  # Функция для обработки сохранения настроек
        browser_choice = self.name_browser_input.text()
        if browser_choice:
            print(f"Browser selection saved: {browser_choice}")  # Замена с фактической логикой сохранения настроек



class VenvEditor_Window(QWidget):

    def __init__(self, SeparateWindow, parent=None):
        super().__init__(parent)

        self.setWindowTitle('Text Input Window')
        self.setFixedSize(400, 300)  # Увеличиваем высоту окна

        layout = QVBoxLayout()

        self.label = QLabel('Введите текст:')
        layout.addWidget(self.label)

        # Многострочное поле для ввода текста
        self.text_input = QTextEdit(self)
        layout.addWidget(self.text_input)

        # Кнопка для подтверждения ввода
        self.submit_button = QPushButton('Подтвердить', self)
        self.submit_button.clicked.connect(self.display_text)  # Подключаем к функции
        layout.addWidget(self.submit_button)

        # Метка для отображения введенного текста
        self.output_label = QLabel('', self)
        layout.addWidget(self.output_label)

        self.setLayout(layout)

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

    def display_text(self):
        # Получаем текст из многострочного поля ввода и отображаем его в метке
        entered_text = self.text_input.toPlainText()  # Используем toPlainText для получения текста
        self.output_label.setText(f'Вы ввели:{entered_text}')



if __name__ == '__main__':
    app = QApplication(sys.argv)
    first_window = Main_Window()
    first_window.show()
    sys.exit(app.exec_())
