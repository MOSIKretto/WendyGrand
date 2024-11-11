import subprocess
import sys

def slice_until_period(input_string):
    period_index = input_string.rfind('.')
    if period_index != -1:
        return input_string[period_index + 1:]
    else:
        print('К сожалению, я не могу найти точку в строке')
        return None

res = slice_until_period(str(sys.argv[1]))

if res == "py":
    subprocess.run(["python3", f"../WendyGrand/Modules/YourModules/{str(sys.argv[1])}"])
else:
    subprocess.run([slice_until_period(str(sys.argv[1])), f"../WendyGrand/Modules/YourModules/{str(sys.argv[1])}"])
