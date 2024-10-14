
#ГОТОВО!!!

import subprocess

def RemuverGit(manager):
    command_map = {
        'pacman': ["pacman", "-R", "--noconfirm", "git"],
        'apt': ["apt", "remove", "-y", "git"],
        'dnf': ["dnf", "remove", "-y", "git"],
        'yum': ["yum", "remove", "-y", "git"]
    }
    command = command_map.get(manager[0])
    if command:
        try:
            subprocess.run(["pkexec"] + command, check=True)
            print("Git успешно установлен.")
        except subprocess.CalledProcessError as e:
            print(f"Ошибка установки Git: {e}")
