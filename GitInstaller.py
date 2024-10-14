import subprocess

def InstallGit(manager):
    print(manager[0], type(manager[0]))
    if manager[0] == 'pacman':
        try:
            # Запуск команды pacman для установки git
            subprocess.run(["sudo", "pacman", "-S", "git"], check=True)
        except subprocess.CalledProcessError as e:
            print(f"Ошибка установки Git: {e}")


