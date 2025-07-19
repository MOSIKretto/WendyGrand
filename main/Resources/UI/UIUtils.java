package main.Resources.UI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

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

    public static void styleScrollPane(JScrollPane scrollPane) {

        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        
        styleScrollBar(verticalScrollBar);
        styleScrollBar(horizontalScrollBar);
    }

    private static void styleScrollBar(JScrollBar scrollBar) {
        scrollBar.setUnitIncrement(16); 
        scrollBar.setPreferredSize(new Dimension(10, 10)); 
        
        
        scrollBar.setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(200, 200, 200); 
                this.trackColor = new Color(40, 40, 40);    
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createInvisibleButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createInvisibleButton();
            }

            private JButton createInvisibleButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                button.setMinimumSize(new Dimension(0, 0));
                button.setMaximumSize(new Dimension(0, 0));
                return button;
            }
        });
        
        scrollBar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                scrollBar.setBackground(new Color(80, 80, 80));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                scrollBar.setBackground(new Color(40, 40, 40));
            }
        });
    }
}