from PyQt6.QtWidgets import QApplication, QHBoxLayout, QVBoxLayout, QLabel, QWidget, QPushButton, QCheckBox, QListWidget, QFrame, QSystemTrayIcon, QMenu
from PyQt6.QtCore import Qt, QPoint
from PyQt6.QtGui import QPixmap, QIcon
import sys
import os

list_item = ['Начало', 'Зависимоти', 'Установка', 'Конец']

class Installer(QWidget):
    def __init__(self):
        super().__init__()
        self.initGui()
        self.center() 
    
    def initGui(self):
        # Системные настройки
        self.startPos = QPoint(0, 0)
        self.isDragging = False

        # Косметические настройки
        self.setWindowTitle("Wendy")
        self.setFixedSize(700, 400)
        self.setStyleSheet('background-color: #241B2C;')
        self.setWindowIcon(QIcon('../WendyGrand/GUI/icon.ico'))

        # Основной layout окна
        main_layout = QHBoxLayout()

        #-------------------------------------------------------------------------------------------------------
        # Настройка фреймов для этапов

        # Начало
        begin_frame = QFrame(self)
        begin_frame.setFrameShape(QFrame.Shape.Box)
        begin_frame.setFrameShadow(QFrame.Shadow.Sunken)
        begin_frame.setStyleSheet("""
            QFrame {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
            }
        """)
        begin_frame.setVisible(True)

        # Зависимости
        adiction_frame = QFrame(self)
        adiction_frame.setFrameShape(QFrame.Shape.Box)
        adiction_frame.setFrameShadow(QFrame.Shadow.Sunken)
        adiction_frame.setStyleSheet("""
            QFrame {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
            }
        """)
        adiction_frame.setVisible(False)

        # Установка
        install_frame = QFrame(self)
        install_frame.setFrameShape(QFrame.Shape.Box)
        install_frame.setFrameShadow(QFrame.Shadow.Sunken)
        install_frame.setStyleSheet("""
            QFrame {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
            }
        """)
        install_frame.setVisible(False)

        # Конец
        final_frame = QFrame(self)
        final_frame.setFrameShape(QFrame.Shape.Box)
        final_frame.setFrameShadow(QFrame.Shadow.Sunken)
        final_frame.setStyleSheet("""
            QFrame {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
            }
        """)
        final_frame.setVisible(False)

        




        # Настройка списка этапов
        self.stage_list_layout = QVBoxLayout(self)
        self.stage_list = QListWidget(self)
        self.stage_list.setStyleSheet("""
            QListWidget {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
            }
            QListWidget::item {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
                margin: 5px 0;
            }
            QListWidget::item:selected {
                background-color: #3B1E54;
            }
        """)
        self.stage_list.setFixedWidth(200)
        self.stage_list.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)  # Убираем скроллбар
        self.stage_list_layout.addWidget(self.stage_list)
        for item in list_item:
            self.stage_list.addItem(item)
        self.stage_list.setCurrentRow(0)
        self.stage_list.itemClicked.connect(self.select_stage)
        main_layout.addLayout(self.stage_list_layout)


        
        
        
        
        self.setLayout(main_layout)
        


    def select_stage(self, item):
        pass

    #Централизуем окно
    def center(self):
        qr = self.frameGeometry()
        screen = QApplication.primaryScreen()
        cp = screen.availableGeometry().center()
        qr.moveCenter(cp)
        self.move(qr.topLeft())

    #Передвижение окна
    def mousePressEvent(self, event):

        if event.button() == Qt.MouseButton.LeftButton:
            self.isDragging = True
            self.startPos = event.position().toPoint()  

    def mouseMoveEvent(self, event):

        if self.isDragging:
            delta = event.position().toPoint() - self.startPos  
            self.move(self.pos() + delta)  

    def mouseReleaseEvent(self, event):

        if event.button() == Qt.MouseButton.LeftButton:
            self.isDragging = False
            self.startPos = None

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = Installer()
    window.show()

    tray_icon = QSystemTrayIcon(QIcon('../WendyGrand/GUI/icon.ico'), parent=app)
    menu = QMenu()

    exit_action = menu.addAction("Закрыть Wendy")
    exit_action.triggered.connect(app.quit)

    settings_action = menu.addAction("Открыть Настройки")
    #settings_action.triggered.connect(window.settings)

    settings_action = menu.addAction("Открыть Venv creator")
    #settings_action.triggered.connect(window.edit_venv)
    
    tray_icon.setContextMenu(menu)
    tray_icon.setVisible(True)

    sys.exit(app.exec())