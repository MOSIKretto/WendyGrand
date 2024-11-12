import subprocess
import time

constTime = 1

subprocess.Popen(["flatpak", "run", "md.obsidian.Obsidian"])
time.sleep(constTime)
subprocess.Popen(["telegram-desktop"])
time.sleep(constTime)
subprocess.Popen(["firefox"])
time.sleep(constTime)
subprocess.Popen(["code"])
time.sleep(constTime)
subprocess.Popen(["AmneziaVPN"])

# В данном случае хорошо бы использовать import time, в противном случае приложения могут не успевать запускаться
# Чем слабее компьютер, тем больше времени надо выставить значение в "constTime" (в идеале)