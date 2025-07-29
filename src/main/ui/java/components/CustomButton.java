package src.main.ui.java.components;

import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton
{
    private Color backgroundColor = new Color(40, 40, 40);
    private Color hoverColor = new Color(60, 60, 60);
    private Color pressedColor = new Color(90, 90, 90);
    private int cornerRadius = 15;

    public CustomButton(String text,int weidht, int height) 
    {
        super(text);
        setupButton(weidht, height);
    }

    private void setupButton(int button_weidht, int button_height) 
    {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);
        
        if (button_height > 0 && button_weidht > 0) 
        { setPreferredSize(new Dimension(button_weidht, button_height));}
        
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        Graphics2D g2 = (Graphics2D) g.create();
        
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Color color = backgroundColor;
        if (getModel().isPressed()) 
            color = pressedColor;

        else if (getModel().isRollover()) 
            color = hoverColor;
        
        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));
        
        super.paintComponent(g2);
        g2.dispose();
    }


    public void setBackgroundColor(Color color) 
    {
        this.backgroundColor = color;
        repaint();
    }
    
    public void setHoverColor(Color color) 
    {
        this.hoverColor = color;
        repaint();
    }
    
    public void setPressedColor(Color color)
    {
        this.pressedColor = color;
        repaint();
    }
    
    public void setCornerRadius(int radius) 
    {
        this.cornerRadius = radius;
        repaint();
    }
}
