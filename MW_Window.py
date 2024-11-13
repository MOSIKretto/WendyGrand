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
        self.setWindowTitle('Wendy_Grand')
        self.setFixedSize(380, 568)
        #self.setWindowFlags(self.windowFlags() | Qt.FramelessWindowHint)
        self.setStyleSheet("background-color: #3B1E54;")
        self.startPos = None
        self.isDragging = False

        layout = QVBoxLayout()
        self.label = QLabel(self)
        self.label.setText(f"<pre style='font-family:Courier; font-size:12pt; color: #E1D7C6;'>{ascii_art}</pre>")
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
        subprocess.run(['python', 'SW_Window.py'])  # Открытие другого скрипта

    def wendy_output(self, text):
        self.chat_area.append(f"Wendy: {text}.")

    def user_input(self, text):
        self.chat_area.append(f"Вы: {text}.")


app = QApplication(sys.argv)
window = GUI()
window.show()
sys.exit(app.exec())
