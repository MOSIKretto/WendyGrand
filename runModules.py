from config import MODULES_PATH
import subprocess
import sys
import os

def listFoldersInCurrentDirectory(path):
    try:
        items = os.listdir(path)
        folders = [item for item in items if os.path.isdir(os.path.join(path, item))]
        return folders
    except Exception:
        return []
    
def sliceUntilPeriod(input_string):
    period_index = input_string.rfind('.')
    if period_index != -1:
        return input_string[period_index + 1:]
    else:
        print('К сожалению, я не могу запустить ваш файл')
        return None

arg = sys.argv[1]
res = sliceUntilPeriod(arg)

current_directory = MODULES_PATH
current_folders = listFoldersInCurrentDirectory(current_directory)

if res == "py":
    if "venv" in current_folders:
        venv_activate_script = os.path.join(current_directory, "venv", "bin", "activate")
        subprocess.run(f"source {venv_activate_script} && python3 {os.path.join(current_directory, arg)}", shell=True, executable="/bin/bash")
    else:
        try:
            subprocess.run(["python3", os.path.join(current_directory, arg)])
        except:
            print("У вас нет виртуального окружения.")
elif res in ["cpp", "c"]:
    base_name = os.path.splitext(os.path.basename(arg))[0]
    executable_path = os.path.join(current_directory, base_name)
    subprocess.run(["g++", os.path.join(current_directory, arg), "-o", executable_path])
    subprocess.run([f"./{executable_path}"])
elif res:
    subprocess.run([res, os.path.join(current_directory, arg)])