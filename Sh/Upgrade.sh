#!/bin/bash

#Вызов окна подтверждения
if [ "$(id -u)" -ne 0 ]; then
    echo "\"Wendy will upgrade your system\"" 
    exec pkexec "$0"
fi


#Обновление системы
update_system() {
    # (Debian/Ubuntu)
    if command -v apt >/dev/null 2>&1; then
        echo "Обновление системы с использованием apt..."
        apt update && apt upgrade -y

    # (Fedora/RHEL)
    elif command -v dnf >/dev/null 2>&1; then
        echo "Обновление системы с использованием dnf..."
        dnf upgrade -y

    # (CentOS/RHEL 7)
    elif command -v yum >/dev/null 2>&1; then
        echo "Обновление системы с использованием yum..."
        yum upgrade -y

    # (Arch Linux)
    elif command -v pacman >/dev/null 2>&1; then
        echo "Обновление системы с использованием pacman..."
        pacman -Syu --noconfirm

    else
        echo "Неизвестный дистрибутив. Пожалуйста, обновите систему вручную."
        exit 1
    fi
}


update_system

