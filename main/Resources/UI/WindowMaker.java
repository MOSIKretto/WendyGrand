package main.Resources.UI;

import main.Resources.UI.Panels.NavigationPanel;
import main.Resources.UI.Panels.ContentPanel;
import main.Resources.UI.Panels.ControlPanel;
import main.Resources.UI.Panels.MainPanel;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.awt.*;


public class WindowMaker extends JFrame 
{
    
    private JPanel contentPanel;
    private Point startPos;
    private boolean isDragging = false;

    public WindowMaker() 
    {
        configureWindow();
        initUI();
    }

    private void configureWindow() 
    {
        setTitle("Wendy");
        setSize(1000, 700);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    private void initUI() 
    {
        JPanel mainPanel = new MainPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        ControlPanel controlPanel = new ControlPanel(this);
        NavigationPanel navPanel = new NavigationPanel(this);
        contentPanel = new ContentPanel(this);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setupWindowDragHandlers(mainPanel);
        add(mainPanel);
    }

    private void setupWindowDragHandlers(Component dragComponent) 
    {
        dragComponent.addMouseMotionListener(new MouseAdapter() 
        {
            public void mouseDragged(MouseEvent e) 
            {
                if (isDragging) 
                {
                    Point current = e.getLocationOnScreen();
                    setLocation(current.x - startPos.x, current.y - startPos.y);
                }
            }
        });

        dragComponent.addMouseListener(new MouseAdapter() 
        {
            public void mousePressed(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e)) 
                {
                    isDragging = true;
                    startPos = e.getPoint();
                }
            }
            
            public void mouseReleased(MouseEvent e) 
            {
                if (SwingUtilities.isLeftMouseButton(e))
                    isDragging = false;
            }
        });
    }

    public void navigateTo(int index) 
    {
        ((CardLayout) contentPanel.getLayout()).show(contentPanel, String.valueOf(index));
    }

    public static void startWindow() 
    {
        SwingUtilities.invokeLater(() -> new WindowMaker().setVisible(true));
    }
}