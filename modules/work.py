import subprocess

# Добавьте или измените список запускаемых приложений
programs = [
    "firefox", 
    "code",
    "Telegram", 
    "md.obsidian.Obsidian"
]

for program in programs:
    subprocess.Popen(program)
