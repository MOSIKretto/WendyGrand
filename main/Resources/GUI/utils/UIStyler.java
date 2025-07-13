package utils;

import java.awt.*;
import javax.swing.BorderFactory;
import ui.components.CustomButton;

public class UIStyler
{
    public static void styleExitButton(CustomButton exitButton)
    {
        exitButton.setPressedColor(Color.RED);
        exitButton.setHoverColor(new Color(150, 0, 0));
    }

    public static void styleExitMenuButton(CustomButton exitMenuButton)
    {
        exitMenuButton.setPressedColor(Color.RED);
        exitMenuButton.setHoverColor(new Color(150, 0, 0));
    }

    public static CustomButton createExitButton(String text)
    {
        CustomButton exitButton = new CustomButton(text, 30, 35);
        exitButton.setFont(new Font("Arial", Font.BOLD, 10)); 
        exitButton.setMargin(new Insets(0, 0, 0, 0));
        exitButton.setBorder(BorderFactory.createEmptyBorder());
        styleExitButton(exitButton);
        
        return exitButton;
    }

    public static CustomButton createWrapButton(String text)
    {
        CustomButton wrapButton = new CustomButton(text, 30, 35);
        wrapButton.setMargin(new Insets(0, 0, 0, 0));
        wrapButton.setBorder(BorderFactory.createEmptyBorder());
        wrapButton.setFont(new Font("Arial", Font.BOLD, 10));

        return wrapButton;
    }

    public static CustomButton createMenuButton(String text, int weidht, String target)
    {
        CustomButton menuButton = new CustomButton(text, weidht, 60);
        menuButton.setMargin(new Insets(5, 0, 0, 0));
        menuButton.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.BLACK));
        menuButton.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 18));
        menuButton.setCornerRadius(0);
        menuButton.setBackground(ColorPalette.MENU_BACKGROUND);

        if (target == "exit")
            styleExitMenuButton(menuButton);
        else
        {
            // Ну тут короче это, да... ниче нет:)
        }

        return menuButton;
    }
}
