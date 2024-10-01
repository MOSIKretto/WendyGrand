'''
* *ShHelper*
*
*RU Запуск Sh скриптов
*----------------------------------------------
*En Running Sh Scripts
'''
def ShStart():

    import subprocess

    subprocess.call(['./Glava.sh'], cwd = "../WendyGrand/Sh/")

ShStart()