# Developer: Lazaretto (Wendy`s primary developer)
# Assistant: VenTurn
# Date: 18.11.2024
# Task: AI_Chat - Artificial Intelligence Window

# Developer: Lazaretto (Wendy`s primary developer)
# Assistant: VenTurn
# Date: 18.11.2024
# Task: AI_Chat - Artificial Intelligence Window

# Developer: Lazaretto (Wendy`s primary developer)
# Assistant: VenTurn
# Date: 18.11.2024
# Task: AI_Chat - Artificial Intelligence Window

import sys
import os
import re
import json
import tempfile
import logging
import shutil
from typing import List, Dict, Any, Optional
from pathlib import Path

# Настройка логирования
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

# Создаем временную директорию для хранения данных g4f
temp_dir = tempfile.mkdtemp()
os.environ["G4F_DATA_DIR"] = temp_dir

# Импорт библиотек с обработкой ошибок
try:
    from PyQt6.QtWidgets import (QApplication, QMainWindow, QVBoxLayout, QWidget, QPushButton, 
                                QPlainTextEdit, QHBoxLayout, QFrame, QSizePolicy, QListWidget, 
                                QListWidgetItem, QMessageBox)
    from PyQt6.QtCore import QThread, pyqtSignal, Qt, QTimer, QEvent, QSize, QMutex
    from PyQt6.QtWebEngineWidgets import QWebEngineView
    from PyQt6.QtWebEngineCore import QWebEngineSettings
    from PyQt6.QtGui import QFont, QBrush, QColor, QKeySequence, QShortcut, QTextOption
    from pygments.lexers import get_lexer_by_name
    from pygments.formatters import HtmlFormatter
    from pygments import highlight
    import g4f
    from g4f.Provider import __providers__
except ImportError as e:
    logger.error(f"Ошибка импорта библиотек: {e}")
    print("Пожалуйста, установите необходимые библиотеки:")
    print("pip install PyQt6 PyQt6-WebEngine Pygments g4f")
    sys.exit(1)

code_block_pattern = re.compile(r'```(\w+)?\n?(.*?)```', re.DOTALL)

def format_code_blocks(message: str) -> str:
    """Форматирование блоков кода с подсветкой синтаксиса"""
    def replace_code_block(match: re.Match) -> str:
        language = match.group(1) or 'python'
        code = match.group(2).strip()
        try:
            lexer = get_lexer_by_name(language, stripall=True)
            formatter = HtmlFormatter(style='monokai', noclasses=True)
            highlighted_code = highlight(code, lexer, formatter)
            return f'<br><div class="code-block"><pre style="background-color: #272822; color: #f8f8f2; padding: 10px; border-radius: 5px; overflow-x: auto; margin: 10px 0;">{highlighted_code}</pre></div>'
        except Exception as e:
            logger.warning(f"Ошибка форматирования кода: {e}")
            return f'<br><div class="code-block"><pre style="background-color: #272822; color: #f8f8f2; padding: 10px; border-radius: 5px; overflow-x: auto; margin: 10px 0;">{code}</pre></div>'
    
    return code_block_pattern.sub(replace_code_block, message)

class Worker(QThread):
    """Класс для выполнения запросов к AI в отдельном потоке"""
    update_text = pyqtSignal(str, int)
    error_occurred = pyqtSignal(str)
    finished = pyqtSignal()

    def __init__(self, user_input: str, chat_id: int):
        super().__init__()
        self.user_input = user_input
        self.chat_id = chat_id
        self.mutex = QMutex()
        self._is_running = True

    def run(self):
        try:
            # Получаем список доступных провайдеров
            available_providers = [
                provider for provider in __providers__
                if provider.working and not provider.needs_auth and provider.supports_stream
            ]
            
            if not available_providers:
                self.error_occurred.emit("Нет доступных провайдеров")
                return
                
            response = None
            # Пробуем использовать доступные провайдеры по очереди
            for provider in available_providers:
                if not self._is_running:
                    return
                    
                try:
                    logger.info(f"Попытка использования провайдера: {provider.__name__}")
                    response = g4f.ChatCompletion.create(
                        model="claude-sonnet-4",
                        messages=[{"role": "user", "content": self.user_input}],
                        provider=provider,
                        stream=True
                    )
                    
                    # Собираем ответ по частям
                    ai_response = ""
                    for chunk in response:
                        if not self._is_running:
                            return
                        if isinstance(chunk, str):
                            ai_response += chunk
                        elif hasattr(chunk, 'content'):
                            ai_response += chunk.content
                        # Небольшая задержка для обработки событий
                        self.msleep(10)
                    
                    self.update_text.emit(ai_response, self.chat_id)
                    return  # Успешно, выходим из цикла
                    
                except Exception as e:
                    logger.warning(f"Провайдер {provider.__name__} не сработал: {str(e)}")
                    continue
            
            # Если ни один провайдер не сработал
            self.error_occurred.emit("Все провайдеры недоступны. Попробуйте позже.")
            
        except Exception as e:
            logger.error(f"Ошибка в worker: {str(e)}")
            self.error_occurred.emit(f"Произошла ошибка: {str(e)}")
        finally:
            self.finished.emit()

    def stop(self):
        self.mutex.lock()
        self._is_running = False
        self.mutex.unlock()

class ChatWindow(QMainWindow):
    def __init__(self):
        super().__init__()
        self.setup_ui()
        self.setup_connections()
        self.load_chats()

    def setup_ui(self):
        """Настройка пользовательского интерфейса"""
        self.setWindowTitle("AI_Chat - Бесплатный ChatGPT")
        self.setGeometry(100, 100, 1000, 800)
        self.setStyleSheet("background-color: #3B1E54;")

        # Main layout
        self.main_widget = QWidget()
        self.layout = QHBoxLayout(self.main_widget)
        self.layout.setSpacing(10)
        self.layout.setContentsMargins(10, 10, 10, 10)

        # Initialize chat data
        self.chats: List[Dict[str, Any]] = []
        self.current_chat_id = 0
        self.first_message_sent = False
        self.workers: List[Worker] = []

        # Chat list layout
        self.setup_chat_list()
        
        # Chat layout
        self.setup_chat_area()

        self.setCentralWidget(self.main_widget)

    def setup_chat_list(self):
        """Настройка списка чатов"""
        self.chat_list_layout = QVBoxLayout()
        self.chat_list_layout.setSpacing(10)

        # Button to create a new chat
        self.create_chat_button = QPushButton("Создать чат")
        self.create_chat_button.setStyleSheet(self.get_button_style())
        self.chat_list_layout.addWidget(self.create_chat_button)

        # Chat list widget
        self.chat_list = QListWidget()
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
        self.chat_list.setVerticalScrollBarPolicy(Qt.ScrollBarPolicy.ScrollBarAsNeeded)
        self.chat_list_layout.addWidget(self.chat_list)

        # Button to delete the current chat
        self.delete_chat_button = QPushButton("Удалить чат")
        self.delete_chat_button.setStyleSheet(self.get_button_style())
        self.chat_list_layout.addWidget(self.delete_chat_button)

        self.layout.addLayout(self.chat_list_layout)

    def setup_chat_area(self):
        """Настройка области чата"""
        self.chat_layout = QVBoxLayout()
        self.chat_layout.setSpacing(10)

        self.frame = QFrame()
        self.frame.setStyleSheet("""
            border: 2px solid #E1D7C6; 
            border-radius: 10px; 
            padding: 10px;
        """)
        self.frame_layout = QVBoxLayout()
        self.frame_layout.setContentsMargins(0, 0, 0, 0)
        self.frame.setLayout(self.frame_layout)

        self.text_area = QWebEngineView()
        self.text_area.setStyleSheet("background-color: #1A1A1D;")
        self.text_area.setSizePolicy(QSizePolicy.Policy.Expanding, QSizePolicy.Policy.Expanding)
        self.frame_layout.addWidget(self.text_area)
        self.chat_layout.addWidget(self.frame)

        self.input_field = QPlainTextEdit()
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
        self.input_field.setWordWrapMode(QTextOption.WrapMode.WrapAtWordBoundaryOrAnywhere)
        self.input_field.installEventFilter(self)
        self.chat_layout.addWidget(self.input_field)

        button_layout = QHBoxLayout()
        button_layout.setSpacing(10)

        self.send_button = QPushButton("Отправить")
        self.send_button.setStyleSheet(self.get_button_style())
        button_layout.addWidget(self.send_button)

        self.exit_button = QPushButton("Выход")
        self.exit_button.setStyleSheet(self.get_button_style())
        button_layout.addWidget(self.exit_button)

        self.chat_layout.addLayout(button_layout)

        self.layout.addLayout(self.chat_layout)

        # Настройка WebEngine
        self.text_area.settings().setAttribute(QWebEngineSettings.WebAttribute.JavascriptEnabled, True)
        self.text_area.settings().setAttribute(QWebEngineSettings.WebAttribute.JavascriptCanAccessClipboard, True)
        self.text_area.page().loadFinished.connect(self.inject_javascript)

    def setup_connections(self):
        """Настройка сигналов и слотов"""
        self.create_chat_button.clicked.connect(self.create_new_chat)
        self.delete_chat_button.clicked.connect(self.delete_current_chat)
        self.send_button.clicked.connect(self.send_message)
        self.exit_button.clicked.connect(self.close)
        
        self.chat_list.currentItemChanged.connect(self.load_chat)
        self.chat_list.itemChanged.connect(self.update_chat_name)

        # Add shortcuts for switching chats
        self.shortcut_pgup = QShortcut(QKeySequence("Ctrl+PgUp"), self)
        self.shortcut_pgup.activated.connect(lambda: self.switch_chat(-1))
        self.shortcut_pgdn = QShortcut(QKeySequence("Ctrl+PgDown"), self)
        self.shortcut_pgdn.activated.connect(lambda: self.switch_chat(1))
        
        # Shortcut for sending message
        self.send_shortcut = QShortcut(QKeySequence("Ctrl+Return"), self)
        self.send_shortcut.activated.connect(self.send_message)

    def get_button_style(self):
        """Возвращает стандартный стиль для кнопок"""
        return """
            QPushButton {
                background-color: #1A1A1D; 
                color: #E1D7C6; 
                border: 2px solid #E1D7C6; 
                border-radius: 10px; 
                padding: 15px 20px; 
                font-size: 16px;
            }
            QPushButton:hover {
                background-color: #2A2A2D;
            }
            QPushButton:pressed {
                background-color: #3B1E54;
            }
        """

    def create_new_chat(self):
        """Создание нового чата"""
        self.current_chat_id = len(self.chats)
        self.chats.append({"name": f"Чат {self.current_chat_id + 1}", "messages": []})
        item = QListWidgetItem(self.chats[self.current_chat_id]["name"])
        item.setBackground(QBrush(QColor("#1A1A1D")))
        item.setForeground(QBrush(QColor("#E1D7C6")))
        item.setFlags(item.flags() | Qt.ItemFlag.ItemIsEditable)
        item.setSizeHint(QSize(0, 50))
        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
        self.chat_list.addItem(item)
        self.chat_list.setCurrentRow(self.current_chat_id)
        self.set_initial_html_content()

    def load_chat(self, current, previous):
        """Загрузка выбранного чата"""
        if current:
            chat_id = self.chat_list.row(current)
            if 0 <= chat_id < len(self.chats):
                self.current_chat_id = chat_id
                self.update_chat_window()
            else:
                logger.error(f"Invalid chat_id {chat_id}")

    def set_initial_html_content(self):
        """Установка начального HTML контента"""
        initial_content = '''
        <html>
            <head>
                <style>
                    body { 
                        background-color: #1A1A1D; 
                        color: #E1D7C6; 
                        font-size: 16px; 
                        font-family: 'Cantarell', sans-serif; 
                        margin: 0;
                        padding: 10px;
                    }
                    .code-block * { line-height: normal !important; }
                    .code-block { 
                        position: relative; 
                        margin: 10px 0; 
                        cursor: pointer; 
                        width: 100%; 
                    }
                    .code-block pre { 
                        background-color: #272822; 
                        color: #f8f8f2; 
                        padding: 10px; 
                        border-radius: 5px; 
                        overflow-x: auto; 
                        margin: 10px 0;
                    }
                    .copy-notification { 
                        position: fixed; 
                        bottom: 20px; 
                        right: 20px; 
                        background-color: #1A1A1D; 
                        color: #E1D7C6; 
                        padding: 10px; 
                        border: 1px solid #E1D7C6; 
                        border-radius: 5px; 
                        display: block; 
                        z-index: 1000; 
                        opacity: 0; 
                        transition: opacity 0.5s ease-in-out; 
                    }
                    .copy-notification.show { opacity: 1; }
                    .user-message { 
                        background-color: #3B1E54; 
                        color: #E1D7C6; 
                        border-radius: 10px; 
                        padding: 10px; 
                        margin: 10px 0; 
                    }
                    .ai-response { 
                        background-color: #1A1A1D; 
                        color: #E1D7C6; 
                        border: 2px solid #E1D7C6; 
                        border-radius: 10px; 
                        padding: 10px; 
                        margin: 10px 0; 
                    }
                    .message-header {
                        font-weight: bold;
                        margin-bottom: 5px;
                        color: #E1D7C6;
                    }
                    .message-content {
                        margin: 5px 0;
                    }
                    ::-webkit-scrollbar { width: 10px; }
                    ::-webkit-scrollbar-track { background: transparent; }
                    ::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.2); border-radius: 5px; }
                    ::-webkit-scrollbar-thumb:hover { background: rgba(255, 255, 255, 0.3); }
                    ::selection { background-color: rgba(255, 255, 255, 0.5); color: #FFFFFF; }
                    .splash-screen { 
                        position: absolute; 
                        top: 50%; 
                        left: 50%; 
                        transform: translate(-50%, -50%); 
                        width: 80%; 
                        font-size: 24px; 
                        color: #E1D7C6; 
                        text-align: center; 
                        padding: 20px; 
                        background-color: #3B1E54; 
                        animation: blink 1s infinite; 
                        z-index: 1000; 
                        display: flex; 
                        justify-content: center; 
                        align-items: center; 
                        font-weight: bold; 
                        border-radius: 10px;
                        border: 2px solid #E1D7C6;
                    }
                    @keyframes blink { 
                        0% { opacity: 1; } 
                        50% { opacity: 0.5; } 
                        100% { opacity: 1; } 
                    }
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
        """Внедрение JavaScript для обработки копирования кода"""
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
        """Обработка событий клавиатуры"""
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
        """Отправка сообщения"""
        user_input = self.input_field.toPlainText().strip()
        if not user_input:
            return
            
        if not self.chats:
            self.create_new_chat()

        # Останавливаем все предыдущие worker'ы
        self.stop_all_workers()

        # Обработка пользовательского ввода
        user_input = re.sub(r'\s+', ' ', user_input)
        formatted_user_message = self.format_message(f'<div class="message-header">Вы:</div><div class="message-content">{user_input}</div>', "user")
        self.chats[self.current_chat_id]["messages"].append(formatted_user_message)
        
        # Добавляем плейсхолдер для ответа AI
        ai_placeholder = '<div style="color: #E1D7C6; padding: 10px;"><strong>AI готовит ответ...</strong><br></div>'
        self.chats[self.current_chat_id]["messages"].append(ai_placeholder)
        
        self.update_chat_window()
        self.input_field.clear()
        self.input_field.setFocus()
        
        # Запускаем worker в отдельном потоке
        self.start_worker(user_input, self.current_chat_id)

        if not self.first_message_sent:
            self.first_message_sent = True
            self.text_area.page().runJavaScript("""
                const splashScreen = document.getElementById('splash-screen');
                if (splashScreen) {
                    splashScreen.style.display = 'none';
                }
            """)

    def start_worker(self, user_input: str, chat_id: int):
        """Запуск worker'а для обработки запроса"""
        worker = Worker(user_input, chat_id)
        worker.update_text.connect(self.receive_ai_response)
        worker.error_occurred.connect(self.handle_error)
        worker.finished.connect(lambda: self.workers.remove(worker))
        self.workers.append(worker)
        worker.start()

    def stop_all_workers(self):
        """Остановка всех worker'ов"""
        for worker in self.workers:
            if worker.isRunning():
                worker.stop()
                worker.quit()
                worker.wait(1000)  # Ждем завершения до 1 секунды
        self.workers.clear()

    def format_message(self, message: str, message_type: str = "ai") -> str:
        """Форматирование сообщения с обработкой блоков кода"""
        message_with_code = format_code_blocks(message)
        message_class = "ai-response" if message_type == "ai" else "user-message"
        return f'<div class="{message_class}">{message_with_code}</div>'

    def receive_ai_response(self, response_message: str, chat_id: int):
        """Обработка ответа от AI"""
        if 0 <= chat_id < len(self.chats):
            # Удаляем плейсхолдер "AI готовит ответ..."
            if (self.chats[chat_id]["messages"] and 
                self.chats[chat_id]["messages"][-1].startswith('<div style="color: #E1D7C6; padding: 10px;"><strong>AI готовит ответ...</strong>')):
                self.chats[chat_id]["messages"].pop()
                
            formatted_response = self.format_message(f'<div class="message-header">AI:</div><div class="message-content">{response_message}</div>', "ai")
            self.chats[chat_id]["messages"].append(formatted_response)
                
            if chat_id == self.current_chat_id:
                self.update_chat_window()

    def handle_error(self, error_message: str):
        """Обработка ошибок"""
        if 0 <= self.current_chat_id < len(self.chats):
            # Удаляем плейсхолдер "AI готовит ответ..."
            if (self.chats[self.current_chat_id]["messages"] and 
                self.chats[self.current_chat_id]["messages"][-1].startswith('<div style="color: #E1D7C6; padding: 10px;"><strong>AI готовит ответ...</strong>')):
                self.chats[self.current_chat_id]["messages"].pop()
                
            error_html = f'<div style="color: #ff6b6b; padding: 10px;"><strong>Ошибка:</strong> {error_message}</div>'
            self.chats[self.current_chat_id]["messages"].append(error_html)
            self.update_chat_window()

    def update_chat_window(self):
        """Обновление окна чата"""
        if not self.chats or self.current_chat_id >= len(self.chats):
            return
            
        chat_content = '''
        <html>
            <head>
                <style>
                    body { 
                        background-color: #1A1A1D; 
                        color: #E1D7C6; 
                        font-size: 16px; 
                        font-family: 'Cantarell', sans-serif; 
                        margin: 0;
                        padding: 10px;
                    }
                    .code-block * { line-height: normal !important; }
                    .code-block { 
                        position: relative; 
                        margin: 10px 0; 
                        cursor: pointer; 
                        width: 100%; 
                    }
                    .code-block pre { 
                        background-color: #272822; 
                        color: #f8f8f2; 
                        padding: 10px; 
                        border-radius: 5px; 
                        overflow-x: auto; 
                        margin: 10px 0;
                    }
                    .copy-notification { 
                        position: fixed; 
                        bottom: 20px; 
                        right: 20px; 
                        background-color: #1A1A1D; 
                        color: #E1D7C6; 
                        padding: 10px; 
                        border: 1px solid #E1D7C6; 
                        border-radius: 5px; 
                        display: block; 
                        z-index: 1000; 
                        opacity: 0; 
                        transition: opacity 0.5s ease-in-out; 
                    }
                    .copy-notification.show { opacity: 1; }
                    .user-message { 
                        background-color: #3B1E54; 
                        color: #E1D7C6; 
                        border-radius: 10px; 
                        padding: 10px; 
                        margin: 10px 0; 
                    }
                    .ai-response { 
                        background-color: #1A1A1D; 
                        color: #E1D7C6; 
                        border: 2px solid #E1D7C6; 
                        border-radius: 10px; 
                        padding: 10px; 
                        margin: 10px 0; 
                    }
                    .message-header {
                        font-weight: bold;
                        margin-bottom: 5px;
                        color: #E1D7C6;
                    }
                    .message-content {
                        margin: 5px 0;
                    }
                    ::-webkit-scrollbar { width: 10px; }
                    ::-webkit-scrollbar-track { background: transparent; }
                    ::-webkit-scrollbar-thumb { background: rgba(255, 255, 255, 0.2); border-radius: 5px; }
                    ::-webkit-scrollbar-thumb:hover { background: rgba(255, 255, 255, 0.3); }
                    ::selection { background-color: rgba(255, 255, 255, 0.5); color: #FFFFFF; }
                    .splash-screen { 
                        position: absolute; 
                        top: 50%; 
                        left: 50%; 
                        transform: translate(-50%, -50%); 
                        width: 80%; 
                        font-size: 24px; 
                        color: #E1D7C6; 
                        text-align: center; 
                        padding: 20px; 
                        background-color: #3B1E54; 
                        animation: blink 1s infinite; 
                        z-index: 1000; 
                        display: flex; 
                        justify-content: center; 
                        align-items: center; 
                        font-weight: bold; 
                        border-radius: 10px;
                        border: 2px solid #E1D7C6;
                    }
                    @keyframes blink { 
                        0% { opacity: 1; } 
                        50% { opacity: 0.5; } 
                        100% { opacity: 1; } 
                    }
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
        """Прокрутка вниз"""
        self.text_area.page().runJavaScript("window.scrollTo(0, document.body.scrollHeight);")

    def closeEvent(self, event):
        """Обработка события закрытия приложения"""
        self.stop_all_workers()
        self.save_chats()
        
        # Очищаем временную директорию
        try:
            shutil.rmtree(temp_dir)
        except Exception as e:
            logger.error(f"Ошибка при удалении временной директории: {e}")
            
        event.accept()

    def switch_chat(self, direction):
        """Переключение между чатами"""
        current_row = self.chat_list.currentRow()
        new_row = current_row + direction
        if 0 <= new_row < self.chat_list.count():
            self.chat_list.setCurrentRow(new_row)

    def save_chats(self):
        """Сохранение чатов в файл"""
        try:
            with open("chats.json", "w", encoding="utf-8") as f:
                json.dump(self.chats, f, ensure_ascii=False, indent=4)
        except Exception as e:
            logger.error(f"Ошибка при сохранении чатов: {str(e)}")
            QMessageBox.warning(self, "Ошибка", f"Не удалось сохранить чаты: {str(e)}")

    def load_chats(self):
        """Загрузка чатов из файла"""
        try:
            if os.path.exists("chats.json"):
                with open("chats.json", "r", encoding="utf-8") as f:
                    self.chats = json.load(f)
                    # Удаляем пустые чаты
                    self.chats = [chat for chat in self.chats if chat.get("messages")]
                    
                    for chat in self.chats:
                        item = QListWidgetItem(chat.get("name", "Безымянный чат"))
                        item.setBackground(QBrush(QColor("#1A1A1D")))
                        item.setForeground(QBrush(QColor("#E1D7C6")))
                        item.setFlags(item.flags() | Qt.ItemFlag.ItemIsEditable)
                        item.setSizeHint(QSize(0, 50))
                        item.setTextAlignment(Qt.AlignmentFlag.AlignCenter)
                        self.chat_list.addItem(item)
        except Exception as e:
            logger.error(f"Ошибка при загрузке чатов: {str(e)}")
            self.chats = []

    def update_chat_name(self, item):
        """Обновление имени чата"""
        chat_id = self.chat_list.row(item)
        if 0 <= chat_id < len(self.chats):
            self.chats[chat_id]["name"] = item.text()

    def delete_current_chat(self):
        """Удаление текущего чата"""
        if not self.chats:
            return
            
        # Подтверждение удаления
        reply = QMessageBox.question(self, 'Подтверждение', 
                                    'Вы уверены, что хотите удалить этот чат?',
                                    QMessageBox.StandardButton.Yes | QMessageBox.StandardButton.No)
        
        if reply != QMessageBox.StandardButton.Yes:
            return
            
        # Останавливаем все worker'ы для этого чата
        self.stop_all_workers()
        
        self.chats.pop(self.current_chat_id)
        self.chat_list.takeItem(self.current_chat_id)
        
        # Если чаты остались, переходим к предыдущему или следующему чату
        if self.chats:
            self.current_chat_id = min(self.current_chat_id, len(self.chats) - 1)
            self.chat_list.setCurrentRow(self.current_chat_id)
            self.load_chat(self.chat_list.currentItem(), None)
        else:
            self.current_chat_id = 0
            self.set_initial_html_content()
            self.text_area.page().runJavaScript("""
                const splashScreen = document.getElementById('splash-screen');
                if (splashScreen) {
                    splashScreen.style.display = 'block';
                }
            """)

def main():
    """Основная функция приложения"""
    # Отключаем аппаратное ускорение для избежания проблем с рендерингом
    os.environ["QTWEBENGINE_DISABLE_GPU"] = "1"
    os.environ["QT_QUICK_BACKEND"] = "software"
    os.environ["QTWEBENGINE_CHROMIUM_FLAGS"] = "--disable-gpu"
    
    app = QApplication(sys.argv)
    app.setFont(QFont("Cantarell", 11))
    
    # Обработка непредвиденных исключений
    def excepthook(type, value, traceback):
        logger.error(f"Необработанное исключение: {type.__name__}: {value}")
        QMessageBox.critical(None, "Ошибка", f"Произошла непредвиденная ошибка:\n{type.__name__}: {value}")
    
    sys.excepthook = excepthook
    
    window = ChatWindow()
    window.show()
    sys.exit(app.exec())

if __name__ == "__main__":
    main()