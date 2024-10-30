'''
* *ShHelper*
*
*RU Запуск Sh скриптов
*----------------------------------------------
*En Running Sh Scripts
'''
import subprocess
import multiprocessing
import sys
import os


class SH():

    @staticmethod
    def run_dir():
        import subprocess
        current_file_path = os.path.abspath(__file__)
        now_dir = os.path.dirname(current_file_path)

        try:
            with open("path.txt", 'r') as f:
                last_path = f.read()
        except FileNotFoundError:
            last_path = now_dir

        with open("path.txt", 'w') as f:
            f.write(now_dir)

        if now_dir != last_path:
        
            subprocess.run(['./Libs.sh'], cwd="../WendyGrand/Sh/")
            subprocess.run(['./Start.sh'], cwd="../WendyGrand/")
            
        SH.ShStart()

    @staticmethod
    def ShStart():

        #Запускает скрипты Recognizer.sh и Glava.sh в параллельных процессах."""
        def run_script(script_path):
            
            subprocess.call(['./' + script_path], cwd = "../WendyGrand/Sh/")

        # Создаем два процесса
        process_recognizer = multiprocessing.Process(target=run_script, args=("Recognizer.sh",))
        process_glava = multiprocessing.Process(target=run_script, args=("Glava.sh",))

        # Запускаем процессы
        process_recognizer.start()
        process_glava.start()

        # Ожидаем завершения процессов
        process_recognizer.join()
        process_glava.join()


if len(sys.argv) > 1:
    print("SH." + sys.argv[1] + "()")
    eval("SH." + sys.argv[1] + "()")