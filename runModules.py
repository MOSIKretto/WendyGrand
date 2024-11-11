import subprocess
import sys
import os


def slice_until_period(input_string):
    period_index = input_string.rfind('.')
    if period_index != -1:
        return input_string[period_index + 1:]
    else:
        print('К сожалению, я не могу найти точку в строке')
        return None

# Для C/C++
def compile_and_run_cpp(source_file):
    base_name = os.path.splitext(os.path.basename(source_file))[0]
    directory = os.path.dirname(source_file)
    executable_path = os.path.join(directory, base_name)
    subprocess.run(["g++", source_file, "-o", executable_path])
    subprocess.run([f"./{executable_path}"])


res = slice_until_period(str(sys.argv[1]))

if res == "py":
    subprocess.run(["python3", "../WendyGrand/Modules/YourModules/" + sys.argv[1]])
elif res == "cpp" or res == "c":
    compile_and_run_cpp("../WendyGrand/Modules/YourModules/" + sys.argv[1])
elif res == "java":
    subprocess.run([res, "../WendyGrand/Modules/YourModules/" + sys.argv[1]])
