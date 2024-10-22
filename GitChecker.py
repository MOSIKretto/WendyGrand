import subprocess

def is_git_installed():
    
    try:
        result = subprocess.run(['git', '--version'], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
        
        if result.returncode == 0:
            print("Git установлен:", result.stdout.strip())
            return True
        
        else:
            print("Git не установлен.")
            return False
        
    except FileNotFoundError:
        print("Git не установлен.")
        return False