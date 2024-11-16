from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QHBoxLayout, QPushButton, QTextEdit, QLabel, QDesktopWidget, QLineEdit # type: ignore
from PyQt5.QtGui import QIcon # type: ignore
from PyQt5.QtCore import Qt # type: ignore
import sys


browser_text = 'Браузер: '
app_store = 'Стор: '
themes = 'Тема: '

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
        self.setWindowIcon(QIcon('icon.png'))
        layout = QVBoxLayout()
        button_layout = QHBoxLayout()
        self.label = QLabel(self)
        self.label.setText(f"<pre style='font-family:Courier; font-size:14.5pt; color: #E1D7C6;'>{ascii_art}</pre>")
        self.label.setStyleSheet("background-color: #1A1A1D;")
        self.label.setAlignment(Qt.AlignmentFlag.AlignVCenter)

        self.chat_area = QTextEdit(self)
        self.chat_area.setReadOnly(True)
        self.chat_area.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")

        self.button = QPushButton("SETTINGS               ⚙️", self)
        self.button.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")
        self.button.clicked.connect(self.settings)

        self.edit_button = QPushButton('VENV MODULES 🐍', self)
        self.edit_button.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")
        self.edit_button.clicked.connect(self.edit_venv)

        button_layout.addWidget(self.edit_button)
        button_layout.addWidget(self.button)

        layout.addWidget(self.label)
        layout.addWidget(self.chat_area)
        layout.addLayout(button_layout)

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

    def edit_venv(self):
        self.venv_editor = VenvEditor_Window(self)
        self.venv_editor.show()

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
        self.startPos = None
        self.isDragging = False
        self.setWindowIcon(QIcon('icon.png'))
        main_layout = QVBoxLayout()
        level1_layout = QHBoxLayout()
        level2_layout = QHBoxLayout()
        level3_layout = QHBoxLayout()
        level4_layout = QHBoxLayout()

        self.label1 = QLabel(self)
        self.label1.setText(f"<pre style='font-family:Courier; font-size:12pt; color: black;'>{browser_text}</pre>")
        self.browser_input = QLineEdit(self)
        self.browser_input.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        self.browser_Button = QPushButton('Сохранить', self)
        self.browser_Button.setStyleSheet("font-size:12pt; font-family:Courier; color: black")
        self.browser_Button.clicked.connect(self.browser_select)
        level1_layout.addWidget(self.label1)
        level1_layout.addWidget(self.browser_input)
        level1_layout.addWidget(self.browser_Button)

        self.label2 = QLabel(self)
        self.label2.setText(f"<pre style='font-family:Courier; font-size:12pt; color: black;'>{app_store}</pre>")
        self.app_input = QLineEdit(self)
        self.app_input.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        self.app_button = QPushButton('Сохранить', self)
        self.app_button.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        self.app_button.clicked.connect(self.app_store_select)
        level2_layout.addWidget(self.label2)
        level2_layout.addWidget(self.app_input)
        level2_layout.addWidget(self.app_button)

        self.label3 = QLabel(self)
        self.label3.setText(f"<pre style='font-family:Courier; font-size:12pt; color: black;'>{themes}</pre>")
        self.themes_input = QLineEdit(self)
        self.themes_input.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        self.themes_button = QPushButton('Сохранить',self)
        self.themes_button.setStyleSheet("font-size:12pt; font-family:Courier; color: black")
        self.themes_button.clicked.connect(self.themes_select)
        level3_layout.addWidget(self.label3)
        level3_layout.addWidget(self.themes_input)
        level3_layout.addWidget(self.themes_button)

        self.close_button = QPushButton('Выйти из настроек', self)
        self.close_button.setStyleSheet("font-size:12pt;  font-family:Courier; color: black")
        self.close_button.clicked.connect(self.close)
        level4_layout.addWidget(self.close_button)

        main_layout.addLayout(level1_layout)
        main_layout.addLayout(level2_layout)
        main_layout.addLayout(level3_layout)
        main_layout.addLayout(level4_layout)
        self.setLayout(main_layout)
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
        send = self.browser_input.text()
        if send:
            pass # передача данных в конфиг

    def app_store_select(self): # передает данные в конфиг
        send = self.app_input.text()
        if send:
            pass # передача данных в конфиг
    
    def themes_select(self):
        send = self.themes_input.text()
        if send:
            pass # передача данных в конфиг

    def save_settings(self):  # Функция для обработки сохранения настроек
        browser_choice = self.name_browser_input.text()
        if browser_choice:
            print(f"Browser selection saved: {browser_choice}")  # Замена с фактической логикой сохранения настроек



class VenvEditor_Window(QWidget):

    def __init__(self, SeparateWindow, parent=None):
        super().__init__(parent)

        self.setWindowTitle('Text Input Window')
        self.setFixedSize(400, 300)  # Увеличиваем высоту окна
        self.setWindowIcon(QIcon('icon.png'))

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
