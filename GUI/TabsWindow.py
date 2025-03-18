from PyQt6.QtWidgets import QApplication, QHBoxLayout, QVBoxLayout, QLabel, QWidget, QPushButton, QCheckBox, QListWidget, QFrame, QLineEdit
from PyQt6.QtCore import Qt, QPoint
from PyQt6.QtGui import QPixmap
import sys
import os

list_item = ['Wendy', 'Настройка', 'Создание модуля', 'Техничка']

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
        self.setWindowTitle("Wendy's Installer")
        self.setFixedSize(700, 400)
        self.setStyleSheet('background-color: #241B2C;')

        # Основной layout окна
        main_layout = QHBoxLayout()

        # Настройка списка этапов
        self.stage_list_layout = QVBoxLayout()
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

        # Настройка фреймов для этапов
        self.frames = []
        self.frames.append(self.create_frame("Wendy"))
        self.frames.append(self.create_frame("Настройки"))
        self.frames.append(self.create_frame("Создание модуля"))
        self.frames.append(self.create_frame("Техничка"))

        # Добавляем фреймы в основной layout
        self.frame_layout = QVBoxLayout()
        for frame in self.frames:
            self.frame_layout.addWidget(frame)
        main_layout.addLayout(self.frame_layout)

        self.setLayout(main_layout)

        # Показываем первый фрейм по умолчанию
        self.frames[0].setVisible(True)

    def create_frame(self, title):
        frame = QFrame(self)
        frame.setFrameShape(QFrame.Shape.Box)
        frame.setFrameShadow(QFrame.Shadow.Sunken)
        frame.setStyleSheet("""
            QFrame {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 10px;
            }
        """)
        frame.setVisible(False)

        label = QLabel(title, frame)
        label.setStyleSheet("""
            QLabel {
                color: #E1D7C6;
            }
        """)
        # Добавляем дополнительные виджеты в каждый фрейм
        if title == "Начало":
            

            description_label = QLabel("""
            Спасибо, что решили установить Wendy!
            """,frame)
            description_label.setStyleSheet("""
                QLabel {
                   color: #E1D7C6;
                }
            """)

        
        if title == "Установка":
            input_line_edit = QLineEdit(frame)
            input_line_edit.setStyleSheet("""
                QLineEdit {
                    background-color: #1A1A1D; 
                    color: #E1D7C6; 
                    border: 2px solid #E1D7C6; 
                    border-radius: 5px; 
                    padding: 5px;
                }
            """)
            layout = QVBoxLayout()
            layout.addWidget(label)
            #layout.addWidget(description_label)
            layout.addWidget(input_line_edit)
        else:
            layout = QVBoxLayout()
            layout.addWidget(label)
          #  layout.addWidget(description_label)

        frame.setLayout(layout)

        return frame

    def select_stage(self, item):
        # Скрываем все фреймы
        for frame in self.frames:
            frame.setVisible(False)

        # Показываем фрейм, соответствующий выбранному элементу
        index = self.stage_list.row(item)
        self.frames[index].setVisible(True)

    # Централизуем окно
    def center(self):
        qr = self.frameGeometry()
        screen = QApplication.primaryScreen()
        cp = screen.availableGeometry().center()
        qr.moveCenter(cp)
        self.move(qr.topLeft())

    # Передвижение окна
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
    sys.exit(app.exec())