# Developer: Lazaretto (Wendy`s primary developer)
# Date: 18.11.2024
# Task: AI_Chat - Artificial Intelligence Window

from PyQt6.QtWidgets import QApplication, QMainWindow, QPushButton, QTextEdit, QVBoxLayout, QWidget
from PyQt6.QtCore import QThread, pyqtSignal
import g4f
import sys

class Worker(QThread):
    update_text = pyqtSignal(str)

    def __init__(self, user_input):
        super().__init__()
        self.user_input = user_input

    def run(self):
        try:
            response = g4f.ChatCompletion.create(
                model="gpt-4o", #Здесь можно изменить модель, почитайте доку g4f
                messages=[{"role": "user", "content": self.user_input}]
            )
            ai_response = response['choices'][0]['message']['content'] if isinstance(response, dict) else response
            self.update_text.emit("AI: " + ai_response + "\n\n")
        except Exception as e:
            self.update_text.emit(f"AI: Произошла ошибка: {str(e)}. Попробуйте позже.\n\n")

class ChatWindow(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle("AI_Chat")
        self.setGeometry(100, 100, 400, 500)
        self.setStyleSheet("background-color: #3B1E54;")

        self.layout = QVBoxLayout()

        self.text_area = QTextEdit(self)
        self.text_area.setReadOnly(True)
        self.text_area.setStyleSheet("font-size: 15px; background-color: #1A1A1D; color: #E1D7C6;")
        self.layout.addWidget(self.text_area)

        self.input_field = QTextEdit(self)
        self.input_field.setPlaceholderText("Введите ваше сообщение...")
        self.input_field.setFixedHeight(6 * 25)
        self.input_field.setStyleSheet("font-size: 15px; background-color: #1A1A1D; color: #E1D7C6;")
        self.layout.addWidget(self.input_field)

        self.send_button = QPushButton("Отправить", self)
        self.send_button.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")
        self.send_button.clicked.connect(self.send_message)
        self.layout.addWidget(self.send_button)

        self.exit_button = QPushButton("Выход", self)
        self.exit_button.setStyleSheet("background-color: #1A1A1D; color: #E1D7C6;")
        self.exit_button.clicked.connect(self.exit_application)
        self.layout.addWidget(self.exit_button)

        container = QWidget()
        container.setLayout(self.layout)
        self.setCentralWidget(container)

    def send_message(self):
        user_input = self.input_field.toPlainText().strip()
        if user_input:
            self.display_user_message(user_input)
            self.input_field.clear()
            self.text_area.append("AI готовит ответ...\n\n")
            self.start_worker(user_input)

    def display_user_message(self, user_input):
        self.text_area.append("Вы: " + user_input + "\n")

    def start_worker(self, user_input):
        self.worker = Worker(user_input)
        self.worker.update_text.connect(self.update_chat)
        self.worker.start()

    def highlight_code(self, code):
        return f'<pre style="background-color: black; color: green; padding: 10px;">{code}</pre>'

    def update_chat(self, message):
        formatted_message = self.format_message(message)
        self.text_area.insertHtml(formatted_message + "<br>")

    def format_message(self, message):
        if '``' in message:
            parts = message.split('``')
            formatted_message = ""
            for i, part in enumerate(parts):
                if i % 2 == 0:
                    formatted_message += part
                else:
                    highlighted_code = self.highlight_code(part.strip())
                    formatted_message += highlighted_code
            return formatted_message
        else:
            return message

    def exit_application(self):
        self.close()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    window = ChatWindow()
    window.show()
    sys.exit(app.exec())



# Безлимитный, бесплатный ChatGPT.
# Может быть как модулем для Wendy, так и обычной программой
# Поддерживает легкую подсветку синтаксиса для кода от ИИ, от пользователья - нет
# Для работы нужны библиотеки g4f и PyQt6
#
# Установка:
#
# pip install PyQt6
# pip install g4f
#
# Если необходимо, создайте вирутальное окружение:
# python -m venv venv
#
# И активируйте его:
# source venv/bin/activate
#
# После чего устновите библиотеки как указано в пункте "Установка" и запустите командой python AI_Chat.py
#
# Wendy на ветке main не поддерживает сейчас модули с venv, но на ветке unstable-main уже может...
# Этот модуль можно использовать в Wendy с поправкой на то, что Wendy приостанавливает процессы на момент его использования
