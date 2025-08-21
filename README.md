
# Wendy - Голосовой помощник для Linux

<p align="center">
  <img src="https://github.com/MOSIKretto/WendyGrand/blob/develop/backend/__refactoring__/src/main/UI/resources/logo.png" alt="WendyGrand Logo" width="150" height="150">
</p>

## Добро пожаловать в репозиторий Wendy! Это интеллектуальный голосовой помощник для Linux, выполняющий задачи по вашим командам.

## ⚙️ Зависимости:
**Изменение яркости экрана: brightnessctl**
**Изменение громкости: pactl (PulseAudio)**

## 📚 Описание

**WendyGrand** - инновационный проект, разработанный для помощи новичкам в мире Linux и автоматизации обсалютно любых задач. WendyGrand предоставляет базовый набор функций, расширяемый с помощью модулей. Основной код проекта написан на **Java** и **Python**.

## 📜 Лицензия

Проект распространяется под лицензией [GPLv3](https://github.com/MOSIKretto/WendyGrand/blob/develop/LICENSE).

## 👨‍💻 Разработчики

- **Max** [(MOSIKretto)](https://github.com/MOSIKretto)
- **Viacheslav** [(VenTurchik)](https://github.com/VenTurchik)

## 🎉 Благодарности

- **Bugakov Ivan** [(PajiloyLis)](https://github.com/PajiloyLis)

## 🚀 Запуск
Просто запустить бинарник **"WendyGrand"**

## ⚙️ Настройка

Вы можете настроить практически всё: от запускаемых программ до слов-активаторов.

### Инструкция:
1. Перейдите в папку `configs`
2. Выберите нужный конфигурационный файл
3. Откройте файл в текстовом редакторе
4. Измените значения параметров по схеме: `ключ = значение`

### Важно:
- Сохраняйте формат `ключ = значение`
- Не изменяйте названия ключей
- Избегайте синтаксических ошибок

## 📁 Модули

### Стандартные модули:

#### 1. **Обновление системы** (apt, dnf, yum, pacman, xbps, nix) (Python)
- Автоматическое обновление через пакетные менеджеры
- [Исходный код](https://github.com/MOSIKretto/WendyGrand/blob/develop/Modules/Upgrade.py)

#### 2. **Стандартный протокол** (Python)
- Запуск настроенного набора программ
- [Исходный код](https://github.com/MOSIKretto/WendyGrand/blob/develop/Modules/work.py)

#### 3. **AI_Chat** (Python)
- Графический интерфейс с ChatGPT 
- Имеет возможность создания нескольких чатов и сохренения их историй
- [Исходный код](https://github.com/MOSIKretto/WendyGrand/blob/develop/Modules/AI_Chat.py)

#### 4. **Пасхалка** (Python)
- Специальный скрытый функционал
- [Исходный код](https://github.com/MOSIKretto/WendyGrand/blob/develop/Modules/creator.py)

## 🛠️ Вклад | 📢 Поддержка

Хотите предложить улучшение или столкнулись с проблемой? 
[Свяжитесь с нами](https://t.me/TheElexum)

# Спасибо за интерес к WendyGrand!

<p align="center">
  <a href="https://github.com/MOSIKretto/WendyGrand">GitHub репозиторий</a>
</p>
