import subprocess

# Включение ночного света через gsettings (Только для GNOME)
subprocess.run(
    ["gsettings", "set", "org.gnome.settings-daemon.plugins.color", "night-light-enabled", "true"], 
    check=True
)