#!/bin/bash

echo "Очистка пользовательских данных в Arch Linux"
echo "-------------------------------------------"

# 1. Очистка кеша thumbnails
echo "[1/4] Удаление кеша thumbnails..."
find "$HOME" -type d -path '*/cache/thumbnails' -exec rm -rf {} + 2>/dev/null

# 2. Очистка пользовательских кешей
echo "[2/4] Очистка кешей пользователя..."
find "$HOME/.cache" -type f -atime +30 -delete 2>/dev/null

# 3. Очистка временных файлов пользователя
echo "[3/4] Очистка временных файлов..."
rm -rf "$HOME"/.tmp/* 2>/dev/null
rm -rf "$HOME"/tmp/* 2>/dev/null
rm -rf "$HOME"/*~ 2>/dev/null

# 4. Очистка кеша браузеров (опционально)
echo "[4/4] Очистка кеша браузеров..."
for browser in chrome chromium firefox opera brave; do
    cache_dir="$HOME/.cache/$browser"
    if [ -d "$cache_dir" ]; then
        find "$cache_dir" -type f -atime +7 -delete 2>/dev/null
    fi
done

echo "-------------------------------------------"
echo "Очистка завершена!"
df -h "$HOME" | awk 'NR==2 {print "Свободное место в домашней директории:", $4}'