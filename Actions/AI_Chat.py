# Developer: Lazaretto (Wendy`s primary developer)
# Assistant: VenTurn
# Date: 18.11.2024
# Task: AI_Chat - Artificial Intelligence Window

from PyQt6.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget, QPushButton, QPlainTextEdit, QHBoxLayout, QFrame, QSizePolicy
from PyQt6.QtCore import QThread, pyqtSignal, Qt, QTimer, QEvent
from PyQt6.QtWebEngineWidgets import QWebEngineView
from PyQt6.QtWebEngineCore import QWebEngineSettings
from pygments.lexers import get_lexer_by_name
from pygments.formatters import HtmlFormatter
from pygments import highlight
from PyQt6.QtGui import QFont
import g4f
import sys
import re

code_block_pattern = re.compile(r'```(\w+)?\n?(.*?)```', re.DOTALL)

def format_code_blocks(message):
    def replace_code_block(match):
        language = match.group(1) or 'python'
        code = match.group(2)
        lexer = get_lexer_by_name(language, stripall=True)
        formatter = HtmlFormatter(style='monokai', noclasses=True)
        highlighted_code = highlight(code, lexer, formatter)
        return f'<br><br><div class="code-block"><pre style="background-color: transparent; line-height: 1; font-size: 16px; margin: 20px 0 0 0; padding: 0; display: inline-block; width: 100%;">{highlighted_code}</pre></div>'
    return code_block_pattern.sub(replace_code_block, message)

class Worker(QThread):
    update_text = pyqtSignal(str)

    def __init__(self, user_input):
        super().__init__()
        self.user_input = user_input

    def run(self):
        try:
            response = g4f.ChatCompletion.create(
                model="gpt-4o",  # Здесь можно изменить модель, почитайте доку g4f
                messages=[{"role": "user", "content": self.user_input}]
            )
            ai_response = response['choices'][0]['message']['content'] if isinstance(response, dict) else response
            self.update_text.emit(ai_response)
        except Exception as e:
            self.update_text.emit(f"AI: Произошла ошибка: {str(e)}. Попробуйте позже.\n\n")

class ChatWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setWindowTitle("AI_Chat")
        self.setGeometry(100, 100, 800, 800)
        self.setStyleSheet("background-color: #3B1E54;")

        self.layout = QVBoxLayout()

        self.frame = QFrame()
        self.frame.setStyleSheet("""
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 10px;
        """)
        self.frame_layout = QVBoxLayout()
        self.frame_layout.setContentsMargins(0, 0, 0, 0)  # Убираем отступы
        self.frame.setLayout(self.frame_layout)

        self.text_area = QWebEngineView(self)
        self.text_area.setStyleSheet("""
            background-color: #1A1A1D; 
            font-size: 16px; 
            color: #E1D7C6; 
        """)
        self.text_area.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)  # Растягиваем виджет
        self.frame_layout.addWidget(self.text_area)
        self.layout.addWidget(self.frame)

        self.input_field = QPlainTextEdit(self)
        self.input_field.setPlaceholderText("Введите ваше сообщение...")
        self.input_field.setFixedHeight(6 * 25)
        self.input_field.setStyleSheet("""
            background-color: #1A1A1D; 
            color: #E1D7C6; 
            selection-background-color: rgba(255, 255, 255, 0.5); 
            selection-color: #FFFFFF;
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 10px;
        """)
        self.input_field.setFocusPolicy(Qt.FocusPolicy.StrongFocus)
        self.input_field.setContextMenuPolicy(Qt.ContextMenuPolicy.DefaultContextMenu)
        self.input_field.installEventFilter(self)
        self.layout.addWidget(self.input_field)

        button_layout = QHBoxLayout()

        self.send_button = QPushButton("Отправить", self)
        self.send_button.clicked.connect(self.send_message)
        self.send_button.setStyleSheet("""
            background-color: #1A1A1D; 
            color: #E1D7C6; 
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 15px 20px; 
            font-size: 16px;
        """)
        button_layout.addWidget(self.send_button)

        self.exit_button = QPushButton("Выход", self)
        self.exit_button.clicked.connect(self.close)
        self.exit_button.setStyleSheet("""
            background-color: #1A1A1D; 
            color: #E1D7C6; 
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 15px 20px; 
            font-size: 16px;
        """)
        button_layout.addWidget(self.exit_button)

        self.layout.addLayout(button_layout)

        container = QWidget()
        container.setLayout(self.layout)
        self.setCentralWidget(container)

        self.worker = None
        self.messages = []
        self.first_message_sent = False

        self.text_area.settings().setAttribute(QWebEngineSettings.WebAttribute.JavascriptEnabled, True)
        self.text_area.settings().setAttribute(QWebEngineSettings.WebAttribute.JavascriptCanAccessClipboard, True)
        self.text_area.page().loadFinished.connect(self.inject_javascript)

        self.set_initial_html_content()

    def set_initial_html_content(self):
        initial_content = '''
        <html>
            <head>
                <style>
                    body { background-color: #1A1A1D; color: #E1D7C6; font-size: 16px; font-family: 'Cantarell', sans-serif; }
                    .code-block * { line-height: normal !important; }
                    .code-block { position: relative; margin-bottom: 10px; cursor: pointer; width: 100%; padding: 10px; }
                    .code-block pre { background-color: transparent; line-height: 1; font-size: 16px; margin: 0; padding: 0; display: inline-block; width: 100%; }
                    .copy-notification { position: fixed; bottom: 20px; right: 20px; background-color: #1A1A1D; color: #E1D7C6; padding: 10px; border: 1px solid #E1D7C6; border-radius: 5px; display: block; z-index: 1000; opacity: 0; transition: opacity 0.5s ease-in-out; }
                    .copy-notification.show { opacity: 1; }
                    .user-message { background-color: #3B1E54; color: #E1D7C6; border-radius: 10px; padding: 10px; margin-bottom: 10px; position: relative; }
                    .user-message::before { content: ''; position: absolute; top: 10px; left: -10px; width: 0; height: 0; border-top: 10px solid transparent; border-bottom: 10px solid transparent; border-right: 10px solid #3B1E54; }
                    ::-webkit-scrollbar { width: 10px; }
                    ::-webkit-scrollbar-track { background: transparent; }
                    ::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.2); border-radius: 5px; }
                    ::-webkit-scrollbar-thumb:hover { background: rgba(255, 255, 255, 0.3); }
                    ::selection { background-color: rgba(255, 255, 255, 0.5); color: #FFFFFF; }
                    .ai-response { border: 2px solid #E1D7C6; border-radius: 10px; padding: 10px; margin-bottom: 10px; }
                    .splash-screen { position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 62.5%; height: 50%; font-size: 30px; color: #E1D7C6; border: 2px solid #E1D7C6; border-radius: 10px; padding: 10px; background-color: #3B1E54; animation: blink 1s infinite; z-index: 1000; display: flex; justify-content: center; align-items: center; font-weight: bold; }
                    @keyframes blink { 0% { opacity: 1; } 50% { opacity: 0.5; } 100% { opacity: 1; } }
                </style>
            </head>
            <body>
                <div class="copy-notification" id="copy-notification">Скопировано</div>
                <div class="splash-screen" id="splash-screen">Начните общение с AI</div>
            </body>
        </html>
        '''
        self.text_area.setHtml(initial_content)

    def inject_javascript(self, loaded):
        if loaded:
            self.text_area.page().runJavaScript("""
                function copyToClipboard(pre) {
                    const textArea = document.createElement("textarea");
                    textArea.value = pre.innerText;
                    document.body.appendChild(textArea);
                    textArea.select();
                    try {
                        document.execCommand('copy');
                        const notification = document.getElementById('copy-notification');
                        notification.classList.add('show');
                        setTimeout(() => {
                            notification.classList.remove('show');
                        }, 5000);
                    } catch (err) {}
                    document.body.removeChild(textArea);
                }
                document.addEventListener('click', (event) => {
                    if (event.target.closest('.code-block')) {
                        copyToClipboard(event.target.closest('.code-block').querySelector('pre'));
                    }
                });
            """)

    def eventFilter(self, obj, event):
        if obj == self.input_field and event.type() == QEvent.Type.KeyPress:
            if event.key() == Qt.Key.Key_Return:
                if event.modifiers() & Qt.KeyboardModifier.ShiftModifier:
                    cursor = self.input_field.textCursor()
                    cursor.insertText('\n')
                    self.input_field.ensureCursorVisible()
                    return True
                else:
                    QTimer.singleShot(0, self.send_message)
                    return True
            elif event.key() == Qt.Key.Key_Tab:
                self.input_field.insertPlainText('    ')
                return True
        return super().eventFilter(obj, event)

    def send_message(self):
        user_input = self.input_field.toPlainText().strip()
        if user_input:
            if user_input.startswith('```'):
                formatted_user_message = self.format_message(f'<div class="user-message"><strong>Вы:</strong> {user_input}</div>')
            else:
                user_input = re.sub(' +', ' ', user_input)
                formatted_user_message = self.format_message(f'<div class="user-message"><strong>Вы:</strong> {user_input}</div>')
            self.messages.append(formatted_user_message)
            ai_placeholder = '<div style="color: #E1D7C6; padding: 10px;"><strong>AI готовит ответ...</strong><br></div>'
            self.messages.append(ai_placeholder)
            self.update_chat_window()
            self.input_field.setPlainText("")
            self.input_field.ensureCursorVisible()
            self.input_field.setFocus()
            self.start_worker(user_input)

            if not self.first_message_sent:
                self.first_message_sent = True
                self.text_area.page().runJavaScript("document.getElementById('splash-screen').style.display = 'none';")

    def start_worker(self, user_input):
        if self.worker and self.worker.isRunning():
            self.worker.quit()
            self.worker.wait()
        self.worker = Worker(user_input)
        self.worker.update_text.connect(self.receive_ai_response)
        self.worker.start()

    def format_message(self, message):
        message_with_code = format_code_blocks(message)
        return f'<div style="color: #E1D7C6; padding: 10px;">{message_with_code}</div>'

    def receive_ai_response(self, response_message):
        if self.messages and self.messages[-1].startswith('<div'):
            self.messages.pop()
        formatted_response = self.format_message(f'<div class="ai-response"><strong>AI:</strong> {response_message}</div>')
        self.messages.append(formatted_response)
        hr_tag = '<hr style="border: 1px solid #E1D7C6; margin: 10px 0;">'
        self.messages.append(hr_tag)
        self.update_chat_window()

    def update_chat_window(self):
        chat_content = f'''
        <html>
            <head>
                <style>
                    body {{ background-color: #1A1A1D; color: #E1D7C6; font-size: 16px; font-family: 'Cantarell', sans-serif; }}
                    .code-block * {{ line-height: normal !important; }}
                    .code-block {{ position: relative; margin-bottom: 10px; cursor: pointer; width: 100%; padding: 10px; }}
                    .code-block pre {{ background-color: transparent; line-height: 1; font-size: 16px; margin: 0; padding: 0; display: inline-block; width: 100%; }}
                    .copy-notification {{ position: fixed; bottom: 20px; right: 20px; background-color: #1A1A1D; color: #E1D7C6; padding: 10px; border: 1px solid #E1D7C6; border-radius: 5px; display: block; z-index: 1000; opacity: 0; transition: opacity 0.5s ease-in-out; }}
                    .copy-notification.show {{ opacity: 1; }}
                    .user-message {{ background-color: #3B1E54; color: #E1D7C6; border-radius: 10px; padding: 10px; margin-bottom: 10px; position: relative; }}
                    .user-message::before {{ content: ''; position: absolute; top: 10px; left: -10px; width: 0; height: 0; border-top: 10px solid transparent; border-bottom: 10px solid transparent; border-right: 10px solid #3B1E54; }}
                    /* Custom scrollbar styles */
                    ::-webkit-scrollbar {{ width: 10px; }}
                    ::-webkit-scrollbar-track {{ background: transparent; }}
                    ::-webkit-scrollbar-thumb {{ background: rgba(255, 255, 255, 0.2); border-radius: 5px; }}
                    ::-webkit-scrollbar-thumb:hover {{ background: rgba(255, 255, 255, 0.3); }}
                    /* Selection styles */
                    ::selection {{ background-color: rgba(255, 255, 255, 0.5); color: #FFFFFF; }}
                    .ai-response {{ border: 2px solid #E1D7C6; border-radius: 10px; padding: 10px; margin-bottom: 10px; }}
                    .splash-screen {{ position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); width: 62.5%; height: 50%; font-size: 30px; color: #E1D7C6; border: 2px solid #E1D7C6; border-radius: 10px; padding: 10px; background-color: #3B1E54; animation: blink 1s infinite; z-index: 1000; display: flex; justify-content: center; align-items: center; font-weight: bold; }}
                    @keyframes blink {{ 0% {{ opacity: 1; }} 50% {{ opacity: 0.5; }} 100% {{ opacity: 1; }} }}
                </style>
            </head>
            <body>
                <div class="copy-notification" id="copy-notification">Скопировано</div>
        '''
        for message in self.messages:
            chat_content += message
        chat_content += '</body></html>'
        self.text_area.setHtml(chat_content)
        QTimer.singleShot(100, self.scroll_to_bottom)

    def scroll_to_bottom(self):
        self.text_area.page().runJavaScript("window.scrollTo(0, document.body.scrollHeight);")

    def closeEvent(self, event):
        if self.worker and self.worker.isRunning():
            self.worker.quit()
            self.worker.wait()
        event.accept()

if __name__ == "__main__":
    app = QApplication(sys.argv)
    app.setFont(QFont("Cantarell", 11))
    window = ChatWindow()
    window.show()
    sys.exit(app.exec())


# Безлимитный, бесплатный ChatGPT.
# Может быть как модулем для Wendy, так и обычной программой
# 
#
# Установка:
#
# pip install PyQt6-WebEngine
# pip install Pygments
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