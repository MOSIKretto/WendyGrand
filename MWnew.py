from PyQt5.QtWidgets import QApplication, QWidget, QVBoxLayout, QPushButton, QTextEdit, QLabel, QDesktopWidget
from PyQt5.QtCore import Qt
import subprocess
import sys

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

class GUI(QWidget):
    def __init__(self):
        super().__init__()
        self.setWindowTitle('Wendy')
        self.setFixedSize(380, 568)
        #self.setWindowFlags(self.windowFlags() | Qt.FramelessWindowHint)
        self.setStyleSheet("background-color: #BEBEBE;")
        self.startPos = None
        self.isDragging = False

        layout = QVBoxLayout()
        self.label = QLabel(self)
        self.label.setText(f"<pre style='font-family:Courier; font-size:12pt; color: #FFFFFF;'>{ascii_art}</pre>")
        self.label.setStyleSheet("background-color: #404040;")
        self.label.setAlignment(Qt.AlignmentFlag.AlignVCenter)

        self.chat_area = QTextEdit(self)
        self.chat_area.setReadOnly(True)
        self.chat_area.setStyleSheet("background-color: #404040; color: white;")

        self.button = QPushButton("SETTINGS                                               ⚙️", self)
        self.button.setStyleSheet("background-color: #404040; color: white;")
        self.button.clicked.connect(self.settings)

        layout.addWidget(self.label)
        layout.addWidget(self.chat_area)
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

    def settings(self):
        self.button.setText('Я пока не работаю')  # Обратная связь о состоянии кнопки
        self.button.setEnabled(False)  # Отключение кнопки
        subprocess.run(['python', 'settings.py'])  # Открытие другого скрипта

    def wendy_output(self, text):
        self.chat_area.append(f"Wendy: {text}.")

    def user_input(self, text):
        self.chat_area.append(f"Вы: {text}.")

if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = GUI()
    window.show()
    sys.exit(app.exec())
