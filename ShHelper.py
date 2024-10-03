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


class SH():

    @staticmethod
    def ShStart():
        """Запускает скрипты Recognizer.sh и Glava.sh в параллельных процессах."""
        def run_script(script_path):
            """Запускает один скрипт."""
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
