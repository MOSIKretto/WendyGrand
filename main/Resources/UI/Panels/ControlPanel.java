package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.*;


public class ControlPanel extends JPanel 
{
    
    public ControlPanel(JFrame frame) 
    {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 10, 10));
        
        add(createControlButton("minimize", e -> frame.setState(Frame.ICONIFIED)));
        add(createControlButton("close", e -> System.exit(0)));
    }

    private JButton createControlButton(String type, ActionListener action) 
    {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) 
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) 
                {
                    g2.setColor(type.equals("close") ? new Color(232, 17, 35, 150) : new Color(100, 100, 100, 100));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                }
                
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                if (type.equals("minimize")) 
                    g2.drawLine(centerX - 6, centerY, centerX + 6, centerY);
                else 
                {
                    g2.drawLine(centerX - 5, centerY - 5, centerX + 5, centerY + 5);
                    g2.drawLine(centerX + 5, centerY - 5, centerX - 5, centerY + 5);
                }
                g2.dispose();
            }
        };
        
        btn.addActionListener(action);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(30, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return btn;
    }
}