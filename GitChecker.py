
#ГОТОВО!!!

import subprocess

def is_git_installed():
    try:
        # Выполняем команду git --version
        result = subprocess.run(['git', '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        
        # Проверяем, был ли Git установлен
        if result.returncode == 0:
            print("Git установлен:", result.stdout.strip())
            return True
        else:
            print("Git не установлен.")
            return False
    except FileNotFoundError:
        print("Git не установлен.")
        return False