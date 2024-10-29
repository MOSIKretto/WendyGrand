import subprocess
import threading

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
            # Запускаем ReadyWindow.py в отдельном потоке
            def run_readywindow():
                subprocess.run(["python3", "ReadyWindow.py"])
            thread = threading.Thread(target=run_readywindow)
            thread.start()
            thread.close()
            
        except subprocess.CalledProcessError as e:
            print(f"Ошибка установки Git: {e}")
