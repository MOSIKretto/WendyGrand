import sys
from PyQt5.QtWidgets import QApplication, QWidget, QLabel, QVBoxLayout, QPushButton, QDesktopWidget
from PyQt5.QtCore import Qt

class Installer(QWidget):
    # Настройка окна, тут уже НЕ ТРОГАТЬ!!!!
    def __init__(self):
        super().__init__()
        self.setWindowTitle("Wendy's Installer")
        self.setFixedSize(350, 75)
        self.startPos = None  # Начальная позиция мыши
        self.isDragging = False  # Флаг для отслеживания состояния перетаскивания

        self.layout = QVBoxLayout(self)
        # Установка цвета фона окна на черный
        self.setStyleSheet("background-color: black;")

        self.label = QLabel(self)
        self.label.setText("Нажмите на кнопку и Wendy начнет установку")
        self.label.setAlignment(Qt.AlignmentFlag.AlignCenter)
        self.layout.addWidget(self.label)
        
        # Установка цвета текста на белый
        self.label.setStyleSheet("color: white;")

        self.install_button = QPushButton("Установить", self)
        # Установка цвета кнопки на белый
        self.install_button.setStyleSheet("background-color: white;") 
        self.install_button.clicked.connect(self.install)
        self.layout.addWidget(self.install_button)

        self.center()  # Централизуем окно

    def center(self):
        """Централизует окно на экране."""
        qr = self.frameGeometry()  # Получаем геометрию окна
        cp = QDesktopWidget().availableGeometry().center()  # Получаем центр экрана
        qr.moveCenter(cp)  # Перемещаем окно к центру
        self.move(qr.topLeft())  # Устанавливаем новое положение окна

    def mousePressEvent(self, event):
        if event.button() == Qt.LeftButton:
            self.isDragging = True
            self.startPos = event.pos()  # Сохраняем начальную позицию мыши

    def mouseMoveEvent(self, event):
        if self.isDragging:
            # Вычисляем смещение мыши
            delta = event.pos() - self.startPos
            self.move(self.pos() + delta)  # Перемещаем окно

    def mouseReleaseEvent(self, event):
        if event.button() == Qt.LeftButton:
            self.isDragging = False  # Завершаем перетаскивание
            self.startPos = None

    # Здесь уже можешь делать всё что хочешь, print поставил, чтобы спокойно тесты проводить
    def install(self):
        print('Начинается аля установка')

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = Installer()
    window.show()
    sys.exit(app.exec())
