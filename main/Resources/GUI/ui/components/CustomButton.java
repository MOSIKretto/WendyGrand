package ui.components;

import java.awt.geom.RoundRectangle2D;
import utils.ColorPalette;
import javax.swing.*;
import java.awt.*;



public class CustomButton extends JButton 
{
    private static Color backgroundColor = ColorPalette.BTN_BG;
    private static Color hoverColor = ColorPalette.BTN_HOVER_COLOR;
    private static Color pressedColor = ColorPalette.BTN_PRESSED_COLOR;
    private static int cornerRadius = 15;

    public CustomButton(String text,int weidht, int height) 
    {
        super(text);
        setupButton(weidht, height);
    }

    private void setupButton(int button_weidht, int button_height) 
    {
        // Убираем стандартное оформление
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        
        // Настраиваем шрифт и цвет текста
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        
        // Фиксируем предпочтительный размер
        setPreferredSize(new Dimension(button_weidht, button_height));
        
        // Меняем курсор при наведении
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g.create();
        
        // Включаем сглаживание
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Определяем цвет в зависимости от состояния
        Color color = backgroundColor;
        if (getModel().isPressed()) 
            color = pressedColor;

        else if (getModel().isRollover()) 
            color = hoverColor;
        
        // Рисуем скругленный прямоугольник
        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        
        // Рисуем текст
        super.paintComponent(g2);
        g2.dispose();
    }

    // Методы для изменения стиля
    public void setBackgroundColor(Color color) 
    {
        CustomButton.backgroundColor = color;
        repaint();
    }
    
    public void setHoverColor(Color color) 
    {
        CustomButton.hoverColor = color;
        repaint();
    }
    
    public void setPressedColor(Color color)
    {
        CustomButton.pressedColor = color;
        repaint();
    }
    
    public void setCornerRadius(int radius) 
    {
        CustomButton.cornerRadius = radius;
        repaint();
    }
}