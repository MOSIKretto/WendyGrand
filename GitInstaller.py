
#ГОТОВО!!!

import subprocess

def InstallGit(manager):
    command_map = {
        'pacman': ["pacman", "-S", "--noconfirm", "git"],
        'apt': ["apt", "install", "-y", "git"],
        'dnf': ["dnf", "install", "-y", "git"],
        'yum': ["yum", "install", "-y", "git"]
    }
    command = command_map.get(manager[0])
    if command:
        try:
            subprocess.run(["pkexec"] + command, check=True)
            print("Git успешно установлен.")
            ReadyWindow()
        except subprocess.CalledProcessError as e:
            print(f"Ошибка установки Git: {e}")

def ReadyWindow():
    subprocess.run(["java", "Translator.java", "ReadyWindow.py"])