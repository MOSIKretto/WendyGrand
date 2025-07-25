package main.Resources.UI;

import main.Resources.UI.Panels.NavigationPanel;
import main.Resources.UI.Panels.ContentPanel;
import main.Resources.UI.Panels.ControlPanel;
import main.Resources.UI.Panels.MainPanel;
import main.Resources.UI.Panels.ModulesPanel;
import java.awt.geom.RoundRectangle2D;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.awt.*;


public class WindowMaker extends JFrame 
{
    private FocusState currentFocusState = FocusState.MAIN_MENU;
    private JPanel contentPanel;
    private NavigationPanel navPanel;
    private Point startPos;
    private boolean isDragging = false;

    public WindowMaker() 
    {
        configureWindow();
        initUI();
        setInitialFocus();
        setFocus(FocusState.MAIN_MENU);
    }

    private void configureWindow() 
    {
        setTitle("Wendy");
        setSize(1280, 840);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    private void initUI() {
        JPanel mainPanel = new MainPanel();
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        ControlPanel controlPanel = new ControlPanel(this);
        navPanel = new NavigationPanel(this); 
        contentPanel = new ContentPanel(this);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setupWindowDragHandlers(mainPanel);
        add(mainPanel);

        KeyboardFocusManager.getCurrentKeyboardFocusManager()
            .addKeyEventDispatcher(e -> 
            {
                if (e.getID() == KeyEvent.KEY_PRESSED) 
                {
                    if (e.getKeyCode() == KeyEvent.VK_RIGHT && navPanel.getMenuList().getSelectedValue().equals("Модули")) 
                    {
                        focusOnModulesList();
                    } 
                    else if (e.getKeyCode() == KeyEvent.VK_LEFT) 
                    {
                        focusOnMainMenu();
                    }
                }
                return false;
            });
    }

    public void focusOnMainMenu() 
    {
        navPanel.focusOnList();
        navPanel.getMenuList().setSelectedIndex(2); 
    }

    public void focusOnModulesList() 
    {
        ((CardLayout)contentPanel.getLayout()).show(contentPanel, "2");
        for (Component comp : contentPanel.getComponents()) 
        {
            if (comp instanceof ModulesPanel) 
            {
                ((ModulesPanel)comp).focusOnList();
                break;
            }
        }
    }

    private void handleKeyPress(KeyEvent e)
    {
        if (e.getID() != KeyEvent.KEY_PRESSED) return;
    
        switch (currentFocusState) 
        {
            case MAIN_MENU:
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && navPanel != null && "Модули".equals(navPanel.getMenuList().getSelectedValue())) 
                {
                    focusOnModulesList();
                }
                break;
                
            case MODULES_LIST:
                if (e.getKeyCode() == KeyEvent.VK_LEFT) 
                {
                    focusOnMainMenu();
                }
                break;
        }
    }

    private void setFocus(FocusState newState) 
    {
        currentFocusState = newState;
    
        SwingUtilities.invokeLater(() -> 
        {
            switch (newState) 
            {
                case MAIN_MENU:
                    navPanel.getMenuList().requestFocusInWindow();
                    if (navPanel.getMenuList().getSelectedIndex() == -1) 
                    {
                        navPanel.getMenuList().setSelectedIndex(2);
                    }
                    break;
                
                case MODULES_LIST:
                    ((CardLayout)contentPanel.getLayout()).show(contentPanel, "2");
                    for (Component comp : contentPanel.getComponents()) 
                    {
                        if (comp instanceof ModulesPanel) 
                        {
                            ((ModulesPanel)comp).focusOnList();
                            break;
                        }
                    }
                    break;
        }
    });
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
        SwingUtilities.invokeLater(() -> {
            new WindowMaker().setVisible(true);
        });
    }

    public void setInitialFocus()
    {
        SwingUtilities.invokeLater(() -> 
        {
            navPanel.getMenuList().requestFocusInWindow();
            navPanel.getMenuList().setSelectedIndex(0);
        });
    }

    

    public void setCurrentFocusState(FocusState state)
    {
        this.currentFocusState = state;
    }

    public FocusState getCurrentFocusState()
    {
        return currentFocusState;
    }
}