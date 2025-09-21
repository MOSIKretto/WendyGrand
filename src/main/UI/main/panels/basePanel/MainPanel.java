package src.main.UI.main.panels.basePanel;

import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;


public class MainPanel extends JPanel 
{
    public MainPanel() 
    {
        super(new BorderLayout());
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(40, 40, 40));
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
        g2d.dispose();
    }
}