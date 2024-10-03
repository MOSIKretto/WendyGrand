'''
* *ShHelper*
*
*RU Запуск Sh скриптов
*----------------------------------------------
*En Running Sh Scripts
'''

import sys

class SH():

    def ShStart():
        import subprocess
        subprocess.call(['./Glava.sh'], "../WendyGrand/Sh/")

if len(sys.argv) > 1:
    print("SH." + sys.argv[1] + "()")
    eval("SH." + sys.argv[1] + "()")