import subprocess
import GitInstaller

def check_package_manager(manager):
    try:
        # Выполняем команду для проверки пакетного менеджера
        subprocess.run([manager, '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
        return True
    except subprocess.CalledProcessError:
        return False
    except FileNotFoundError:
        return False

def SmartManager():
    package_managers = ["pacman", "apt", "dnf"]
    installed_managers = []

    for manager in package_managers:
        if check_package_manager(manager):
            installed_managers.append(manager)

    if installed_managers:
        GitInstaller.InstallGit(installed_managers)
    else:
        #Если не найдены выводить в окно
        print("Пакетные менеджеры не найдены")