# Developer: Lazaretto (Wendy`s primary developer)
# Assistant: VenTurn
# Date: 18.11.2024
# Task: AI_Chat - Artificial Intelligence Window

# Безлимитный, бесплатный ChatGPT.

LIBS_FOR_WENDY = """
PyQt6-WebEngine, 
Pygments,
PyQt6, 
g4f
"""

from PyQt6.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget, QPushButton, QPlainTextEdit, QHBoxLayout, QFrame, QSizePolicy, QListWidget, QListWidgetItem
from PyQt6.QtCore import QThread, pyqtSignal, Qt, QTimer, QEvent, QSize
from PyQt6.QtWebEngineWidgets import QWebEngineView
from PyQt6.QtWebEngineCore import QWebEngineSettings
from pygments.lexers import get_lexer_by_name
from pygments.formatters import HtmlFormatter
from pygments import highlight
from PyQt6.QtGui import QFont, QBrush, QColor, QKeySequence, QShortcut
import g4f
import sys
import re
import json

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

    def __init__(self, user_input, chat_id):
        super().__init__()
        self.user_input = user_input
        self.chat_id = chat_id

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

        # Main layout
        self.layout = QHBoxLayout()

        # Initialize chat data
        self.chats = []
        self.current_chat_id = 0
        self.first_message_sent = False

        # Chat list layout
        self.chat_list_layout = QVBoxLayout()

        # Button to create a new chat
        self.create_chat_button = QPushButton("Создать чат", self)
        self.create_chat_button.clicked.connect(self.create_new_chat)
        self.create_chat_button.setStyleSheet("""
            background-color: #1A1A1D; 
            color: #E1D7C6; 
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 15px 20px; 
            font-size: 16px;
        """)
        self.chat_list_layout.addWidget(self.create_chat_button)

        # Chat list widget
        self.chat_list = QListWidget(self)
        self.chat_list.setStyleSheet("""
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
        self.chat_list.setFixedWidth(200)
        self.chat_list.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAlwaysOff)  # Убираем скроллбар
        self.chat_list_layout.addWidget(self.chat_list)

        # Button to delete the current chat
        self.delete_chat_button = QPushButton("Удалить чат", self)
        self.delete_chat_button.clicked.connect(self.delete_current_chat)
        self.delete_chat_button.setStyleSheet("""
            background-color: #1A1A1D; 
            color: #E1D7C6; 
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 15px 20px; 
            font-size: 16px;
        """)
        self.chat_list_layout.addWidget(self.delete_chat_button)

        self.layout.addLayout(self.chat_list_layout)

        # Chat layout
        self.chat_layout = QVBoxLayout()

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
        self.chat_layout.addWidget(self.frame)

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
        self.chat_layout.addWidget(self.input_field)

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

        self.chat_layout.addLayout(button_layout)

        self.layout.addLayout(self.chat_layout)

        container = QWidget()
        container.setLayout(self.layout)
        self.setCentralWidget(container)

        self.worker = None
        self.messages = []

        self.text_area.settings().setAttribute(QWebEngineSettings.WebAttribute.JavascriptEnabled, True)
        self.text_area.settings().setAttribute(QWebEngineSettings.WebAttribute.JavascriptCanAccessClipboard, True)
        self.text_area.page().loadFinished.connect(self.inject_javascript)

        self.set_initial_html_content()

        # Connect signals after all initializations
        self.chat_list.currentItemChanged.connect(self.load_chat)
        self.chat_list.itemChanged.connect(self.update_chat_name)

        # Add shortcuts for switching chats
        self.shortcut_pgup = QShortcut(QKeySequence("Ctrl+PgUp"), self)
        self.shortcut_pgup.activated.connect(lambda: self.switch_chat(-1))
        self.shortcut_pgdn = QShortcut(QKeySequence("Ctrl+PgDown"), self)
        self.shortcut_pgdn.activated.connect(lambda: self.switch_chat(1))

        # Load chats from file
        self.load_chats()

        # Check if there are any chats and load the first one if available
        if self.chats:
            self.chat_list.setCurrentRow(0)
            self.load_chat(self.chat_list.currentItem(), None)
            self.text_area.page().runJavaScript("document.getElementById('splash-screen').style.display = 'none';")

        # Enable drag and drop for chat list
        self.chat_list.setDragEnabled(True)
        self.chat_list.setAcceptDrops(True)
        self.chat_list.setDragDropMode(QListWidget.DragDropMode.InternalMove)

    def create_new_chat(self):
        self.current_chat_id = len(self.chats)
        self.chats.append({"name": f"Чат {self.current_chat_id + 1}", "messages": []})
        item = QListWidgetItem(self.chats[self.current_chat_id]["name"])
        item.setBackground(QBrush(QColor("#1A1A1D")))
        item.setForeground(QBrush(QColor("#E1D7C6")))
        item.setFlags(item.flags() | Qt.ItemFlag.ItemIsEditable | Qt.ItemFlag.ItemIsDragEnabled)
        item.setSizeHint(QSize(0, 50))
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        self.chat_list.addItem(item)
        self.chat_list.setCurrentRow(self.current_chat_id)
        self.messages = self.chats[self.current_chat_id]["messages"]
        self.set_initial_html_content()

    def load_chat(self, current, previous):
        if current:
            chat_id = self.chat_list.row(current)
            if 0 <= chat_id < len(self.chats):  # Проверка на допустимость индекса
                self.current_chat_id = chat_id
                self.messages = self.chats[self.current_chat_id]["messages"]
                self.update_chat_window()
            else:
                print(f"Error: Invalid chat_id {chat_id}")

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
                        if (notification) {
                            notification.classList.add('show');
                            setTimeout(() => {
                                notification.classList.remove('show');
                            }, 5000);
                        }
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
            if not self.chats:
                self.create_new_chat()

            if user_input.startswith('```'):
                formatted_user_message = self.format_message(f'<div class="user-message"><strong>Вы:</strong> {user_input}</div>')
            else:
                user_input = re.sub(' +', ' ', user_input)
                formatted_user_message = self.format_message(f'<div class="user-message"><strong>Вы:</strong> {user_input}</div>')
            self.chats[self.current_chat_id]["messages"].append(formatted_user_message)
            ai_placeholder = '<div style="color: #E1D7C6; padding: 10px;"><strong>AI готовит ответ...</strong><br></div>'
            self.chats[self.current_chat_id]["messages"].append(ai_placeholder)
            self.update_chat_window()
            self.input_field.setPlainText("")
            self.input_field.ensureCursorVisible()
            self.input_field.setFocus()
            self.start_worker(user_input, self.current_chat_id)

            if not self.first_message_sent:
                self.first_message_sent = True
                self.text_area.page().runJavaScript("""
                    const splashScreen = document.getElementById('splash-screen');
                    if (splashScreen) {
                        splashScreen.style.display = 'none';
                    }
                """)

    def start_worker(self, user_input, chat_id):
        if self.worker and self.worker.isRunning():
            self.worker.quit()
            self.worker.wait()
        self.worker = Worker(user_input, chat_id)
        self.worker.update_text.connect(self.receive_ai_response)
        self.worker.start()

    def format_message(self, message):
        message_with_code = format_code_blocks(message)
        return f'<div style="color: #E1D7C6; padding: 10px;">{message_with_code}</div>'

    def receive_ai_response(self, response_message):
        chat_id = self.worker.chat_id
        if 0 <= chat_id < len(self.chats):  # Проверка на допустимость индекса
            if self.chats[chat_id]["messages"]:
                if self.chats[chat_id]["messages"][-1].startswith('<div'):
                    self.chats[chat_id]["messages"].pop()
            formatted_response = self.format_message(f'<div class="ai-response"><strong>AI:</strong> {response_message}</div>')
            self.chats[chat_id]["messages"].append(formatted_response)
            hr_tag = '<hr style="border: 1px solid #E1D7C6; margin: 10px 0;">'
            self.chats[chat_id]["messages"].append(hr_tag)
            if chat_id == self.current_chat_id:
                self.update_chat_window()
        else:
            print(f"Error: Invalid chat_id {chat_id}")

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
        if not self.chats[self.current_chat_id]["messages"]:
            chat_content += '<div class="splash-screen" id="splash-screen">Начните общение с AI</div>'
        else:
            for message in self.chats[self.current_chat_id]["messages"]:
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
        self.save_chats()
        event.accept()

    def switch_chat(self, direction):
        current_row = self.chat_list.currentRow()
        new_row = current_row + direction
        if 0 <= new_row < self.chat_list.count():
            self.chat_list.setCurrentRow(new_row)
            self.load_chat(self.chat_list.currentItem(), None)

    def save_chats(self):
        with open("chats.json", "w", encoding="utf-8") as f:
            json.dump(self.chats, f, ensure_ascii=False, indent=4)

    def load_chats(self):
        try:
            with open("chats.json", "r", encoding="utf-8") as f:
                self.chats = json.load(f)
                # Remove empty chats
                self.chats = [chat for chat in self.chats if chat["messages"]]
                for chat in self.chats:
                    item = QListWidgetItem(chat["name"])
                    item.setBackground(QBrush(QColor("#1A1A1D")))
                    item.setForeground(QBrush(QColor("#E1D7C6")))
                    item.setFlags(item.flags() | Qt.ItemFlag.ItemIsEditable | Qt.ItemFlag.ItemIsDragEnabled)
                    item.setSizeHint(QSize(0, 50))
                    item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                    self.chat_list.addItem(item)
        except FileNotFoundError:
            self.chats = []

    def update_chat_name(self, item):
        chat_id = self.chat_list.row(item)
        self.chats[chat_id]["name"] = item.text()

    def delete_current_chat(self):
        if self.chats:
            self.chats.pop(self.current_chat_id)
            self.chat_list.takeItem(self.current_chat_id)
            
            # Если чаты остались, переходим к предыдущему или следующему чату
            if self.chats:
                self.current_chat_id = min(self.current_chat_id, len(self.chats) - 1)
                self.chat_list.setCurrentRow(self.current_chat_id)
                self.load_chat(self.chat_list.currentItem(), None)
            else:
                self.current_chat_id = 0
                self.messages = []
                self.set_initial_html_content()
                self.text_area.page().runJavaScript("""
                    const splashScreen = document.getElementById('splash-screen');
                    if (splashScreen) {
                        splashScreen.style.display = 'block';
                    }
                """)

if __name__ == "__main__":
    app = QApplication(sys.argv)
    app.setFont(QFont("Cantarell", 11))
    window = ChatWindow()
    window.show()
    sys.exit(app.exec())
