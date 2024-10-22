import subprocess
import GitInstaller

# Выполняем проверку пакетного менеджера
def SmartManager():

    package_managers = ["pacman", "apt", "dnf", "yum"]
    installed_managers = []

    for manager in package_managers:
        if manager:
            installed_managers.append(manager)

    if installed_managers:
        GitInstaller.InstallGit(installed_managers)

    else:
        from PointTwoWindow import CuteLabel as CL
        CL.changeText("Упс, похоже, мой создатель забыл включить Ваш пакетный менаджер в установщик")

    try:
        subprocess.run([manager, '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, check=True)
        return True
    
    except subprocess.CalledProcessError:
        return False
    
    except FileNotFoundError:
        return False