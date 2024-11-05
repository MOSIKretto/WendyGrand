'''
* *ShHelper*
*
*RU Запуск Sh скриптов необходимых для пересборки venv и запуска
*----------------------------------------------------------------
*En Running Sh scripts required to rebuild venv and run
'''

from config import  SOURCE_DIR, SH_START_DIR
import multiprocessing
import subprocess
import sys
import os


class SH():

    @staticmethod
    def run_dir():
        
        current_file_path = os.path.abspath(__file__)
        now_dir = os.path.dirname(current_file_path)

        try:
            with open("path.conf", 'r') as f:
                last_path = f.read()
        except FileNotFoundError:
            last_path = now_dir

        with open("path.conf", 'w') as f:
            f.write(now_dir)

        if now_dir != last_path:
            subprocess.run(['./Rebuild_Libs.sh'], cwd=SOURCE_DIR)
            subprocess.run(['./Start.sh'], cwd=SOURCE_DIR)

        SH.ShStart()

    @staticmethod
    def ShStart():
        #Запускает скрипты Recognizer.sh и Glava.sh в параллельных процессах.
        def run_script(name_script):
            
            subprocess.call(['./' + name_script], cwd=SH_START_DIR)

        process_recognizer = multiprocessing.Process(target=run_script, args=("Recognizer.sh",))
        process_glava = multiprocessing.Process(target=run_script, args=("Glava.sh",))

        process_recognizer.start()
        process_glava.start()

        process_recognizer.join()
        process_glava.join()


if len(sys.argv) > 1:
    print("SH." + sys.argv[1] + "()")
    eval("SH." + sys.argv[1] + "()")
