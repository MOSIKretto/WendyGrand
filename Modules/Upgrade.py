import subprocess
import sys
import os

def update_system():
    if os.system("command -v apt >/dev/null 2>&1") == 0:
        print("Обновление системы с использованием apt...")
        subprocess.run(["apt", "update"])
        subprocess.run(["apt", "upgrade", "-y"])
        return True

    elif os.system("command -v dnf >/dev/null 2>&1") == 0:
        print("Обновление системы с использованием dnf...")
        subprocess.run(["dnf", "upgrade", "-y"])
        return True

    elif os.system("command -v yum >/dev/null 2>&1") == 0:
        print("Обновление системы с использованием yum...")
        subprocess.run(["yum", "upgrade", "-y"])
        return True

    elif os.system("command -v pacman >/dev/null 2>&1") == 0:
        print("Обновление системы с использованием pacman...")
        subprocess.run(["pacman", "-Syu", "--noconfirm"])
        subprocess.run(["yay", "-Syu", "--noconfirm"])
        return True

    elif os.system("command -v xbps-install >/dev/null 2>&1") == 0:
        print("Обновление системы с использованием xbps-install...")
        subprocess.run(["xbps-install", "-Suvy"])
        return True

    elif os.system("command -v nix >/dev/null 2>&1") == 0:
        print("Обновление системы с использованием nix...")
        subprocess.run(["nixpkgs.stable", "--no-outdated", "--no-reorder", "nixos-rebuild", "switch", "-y"])
        return True

    else:
        print("Неизвестный дистрибутив. Пожалуйста, обновите систему вручную.")
        return False

password_entered = False

while not password_entered:
    if os.geteuid() != 0:
        subprocess.run(["pkexec", "python3", __file__] + sys.argv[1:])
        break

    if os.getenv("PKEXEC_UID") is None:
        subprocess.run(["pkexec", "python3", __file__] + sys.argv[1:])
    else:
        password_entered = update_system()