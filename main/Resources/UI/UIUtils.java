package main.Resources.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;


public class UIUtils 
{
    
    public static void styleButton(JButton btn) 
    {
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btn.addMouseListener(new MouseAdapter() 
        {
            public void mouseEntered(MouseEvent e) 
            {
                btn.setForeground(new Color(200, 200, 200));
            }

            public void mouseExited(MouseEvent e)
            {
                btn.setForeground(Color.WHITE);
            }
        });
    }
}