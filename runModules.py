from config import MODULES_PATH
import subprocess
import sys
import os


def slice_until_period(input_string):
    period_index = input_string.rfind('.')
    if period_index != -1:
        return input_string[period_index + 1:]
    else:
        print('К сожалению, я не могу запустить ваш файл')
        return None

# Для C/C++
def compile_and_run_cpp(source_file):
    base_name = os.path.splitext(os.path.basename(source_file))[0]
    directory = os.path.dirname(source_file)
    executable_path = os.path.join(directory, base_name)
    subprocess.run(["g++", source_file, "-o", executable_path])
    subprocess.run([f"./{executable_path}"])


arg = sys.argv[1]
res = slice_until_period(str(arg))

if res == "py":
    subprocess.run(["python3", MODULES_PATH + arg])
elif res == "cpp" or res == "c":
    compile_and_run_cpp(MODULES_PATH + arg)
elif res:
    subprocess.run([res, MODULES_PATH + arg])
