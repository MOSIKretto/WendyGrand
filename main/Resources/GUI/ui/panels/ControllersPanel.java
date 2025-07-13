package ui.panels;

// Готово

import ui.components.CustomButton;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import utils.*;

public class ControllersPanel extends JPanel
{
    public ControllersPanel()
    {
        setLayout(new BorderLayout());
        setBackground(ColorPalette.BACKGROUND);
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));

        CustomButton exitButton = UIStyler.createExitButton("X");
        CustomButton wrapButton = UIStyler.createWrapButton("−");
        JLabel title = new JLabel("Wendy");

        title.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 24));
        title.setForeground(Color.WHITE);
                
        JPanel overlay = new JPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));

        overlay.setLayout(new OverlayLayout(overlay));
        overlay.setBackground(new Color(0, 0, 0, 0));
       
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setOpaque(false);
        buttonPanel.add(wrapButton);
        buttonPanel.add(exitButton);

        titlePanel.setBackground(ColorPalette.BACKGROUND);
        titlePanel.setOpaque(true);
        titlePanel.add(title);

        wrapButton.addActionListener(e -> ((JFrame)SwingUtilities.getWindowAncestor(wrapButton)).setState(Frame.ICONIFIED));
        exitButton.addActionListener(e -> closeWindow());

        final Point[] dragStart = new Point[1];

        overlay.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                dragStart[0] = e.getPoint();
            }
        });

        overlay.addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseDragged(MouseEvent e) 
            {
                if (dragStart[0] != null)
                {
                    Window window = SwingUtilities.getWindowAncestor(overlay);

                    if (window != null)
                    {
                        Point current = window.getLocation();
                        window.setLocation(
                        current.x + e.getX() - dragStart[0].x,
                        current.y + e.getY() - dragStart[0].y);
                    }
                }
                
            }
        });

        overlay.add(buttonPanel);
        overlay.add(titlePanel);
        add(overlay, BorderLayout.CENTER);
    }

    private void closeWindow() 
    {
        Window window = SwingUtilities.getWindowAncestor(this);
        
        if (window != null)
            window.dispose();
    }

    public static JPanel createControllerPanel()
    {
        return new ControllersPanel();
    }
}