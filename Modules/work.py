import subprocess

# Добавьте или измените список запускаемых приложений
programs = [
    "firefox", 
    "code",
    "telegram-desktop", 
    "md.obsidian.Obsidian"
]

for program in programs:
    subprocess.Popen(program)
