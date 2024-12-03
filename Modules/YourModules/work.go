package main

import (
    "os/exec"
)

func main() {
    
    //добавьте или измените список запускаемых приложений
    programs := []string{"firefox", "code", "AmneziaVPN", "telegram-desktop", "md.obsidian.Obsidian"} 

    for _, program := range programs {

        cmd := exec.Command(program)
        cmd.Start()

    }

}
