import subprocess
import GitInstaller
from InternetCheck import InternetCheck
import sys

# Выполняем проверку пакетного менеджера
def SmartManager():

    if InternetCheck():

        package_managers = ["pacman", "apt", "dnf", "yum"]
        installed_managers = []

        for manager in package_managers:
            if manager:
                installed_managers.append(manager)

        if installed_managers:
            GitInstaller.InstallGit(installed_managers)

        else:
            with open("error.txt", "w") as file:
                print("Не удалось найти ваш пакетный менаджер :(", file=file)
            import threading
            def window():
                subprocess.run(["python3", "PointTwoWindow.py"])
            thread = threading.Thread(target=window)
            thread.start()
            thread.close()


        try:
            subprocess.run([manager, '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
            return True
        
        except subprocess.CalledProcessError:
            return False
        
        except FileNotFoundError:
            return False
        
    else:
        with open("error.txt", "w") as file:
            print("Дисконект! Проверьте подключение к интернету :(", file=file)
        import threading
        def window():
            subprocess.run(["python3", "PointTwoWindow.py"])
        thread = threading.Thread(target=window)
        thread.start()
        thread.close()