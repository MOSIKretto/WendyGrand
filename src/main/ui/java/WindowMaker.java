package src.main.ui.java;

import src.main.ui.java.panels.pagesPanel.DictionariesPanel;
import src.main.ui.java.panels.basePanel.NavigationPanel;
import src.main.ui.java.panels.pagesPanel.SettingsPanel;
import src.main.ui.java.panels.pagesPanel.ModulesPanel;
import src.main.ui.java.panels.basePanel.ContentPanel;
import src.main.ui.java.panels.basePanel.ControlPanel;
import src.main.ui.java.components.CustomTabbedPane;
import src.main.ui.java.panels.basePanel.MainPanel;
import src.main.helpers.java.enums.FocusState;

import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;
import java.util.prefs.Preferences;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;


public class WindowMaker extends JFrame 
{
    public static final Preferences PREFS = Preferences.userNodeForPackage(WindowMaker.class);
    private FocusState currentFocusState = FocusState.MAIN_MENU;
    private JPanel contentPanel;
    private NavigationPanel navPanel;
    private Point startPos;
    private boolean isDragging = false;
    private boolean spacePressed = false; 

    public WindowMaker() 
    {
        configureWindow();
        initUI();
        setInitialFocus();
        setFocus(FocusState.MAIN_MENU);
        restoreLastState();
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
        
        mainPanel.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                {
                    spacePressed = true;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) 
            {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                {
                    spacePressed = false;
                }
            }
        });
        
        add(mainPanel);

        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(e -> 
        {
            if (e.getID() == KeyEvent.KEY_PRESSED) 
            {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                {
                    spacePressed = true;
                }
                handleKeyPress(e);
            } else if (e.getID() == KeyEvent.KEY_RELEASED) 
            {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) 
                {
                    spacePressed = false;
                }
            }
            return false;
        });
    }

    private void restoreLastState() 
    {
        int lastMenu = PREFS.getInt("last_menu_item", 0);
        navPanel.getMenuList().setSelectedIndex(lastMenu);

        switch (lastMenu) 
        {
            case 1: 
                focusOnSettings();
                break;

            case 2: 
                focusOnModulesList();
                int lastModule = PREFS.getInt("last_module", 0);
                ((ModulesPanel)contentPanel.getComponent(2)).restoreLastModule(lastModule);
                break;

            case 3: 
                focusOnDictionaries();
                int lastDictTab = PREFS.getInt("last_dict_tab", 0);
                ((DictionariesPanel)contentPanel.getComponent(3)).getTabs().setSelectedIndex(lastDictTab);
                break;
        }
    }

    public void focusOnMainMenu() 
    {
        PREFS.putInt("last_menu_item", navPanel.getMenuList().getSelectedIndex());
        navPanel.focusOnList();
        setCurrentFocusState(FocusState.MAIN_MENU); 
    }

    public void focusOnModulesList() 
    {
        ((CardLayout)contentPanel.getLayout()).show(contentPanel, "2");
        setCurrentFocusState(FocusState.MODULES_LIST);
        
        for (Component comp : contentPanel.getComponents()) 
        {
            if (comp instanceof ModulesPanel) 
            {
                ((ModulesPanel)comp).focusOnList();
                break;
            }
        }
    }

    public void focusOnDictionaries() 
    {
        ((CardLayout)contentPanel.getLayout()).show(contentPanel, "3");
        setCurrentFocusState(FocusState.DICTIONARIES);

        Component comp = contentPanel.getComponent(3);
        if (comp instanceof DictionariesPanel) 
        {
            CustomTabbedPane tabs = ((DictionariesPanel)comp).getTabs();
            if (tabs != null && tabs.getTabCount() > 0) 
            {
                tabs.setSelectedIndex(0);
                tabs.requestFocusInWindow();
            }
        }
    }

    public void focusOnSettings() 
    {
        ((CardLayout)contentPanel.getLayout()).show(contentPanel, "1");
        setCurrentFocusState(FocusState.SETTINGS);

        Component comp = contentPanel.getComponent(1);
        if (comp instanceof SettingsPanel) 
        {
            CustomTabbedPane tabs = ((SettingsPanel)comp).getTabs();
            if (tabs != null && tabs.getTabCount() > 0) 
            {
                tabs.setSelectedIndex(0);
                tabs.requestFocusInWindow();
            }
        }
    }

    private void handleKeyPress(KeyEvent e) 
    {
        if (e.getID() != KeyEvent.KEY_PRESSED) return;

        if ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) 
        {
            if (e.getKeyCode() == KeyEvent.VK_Q) 
            {
                dispose();
            }
            return;
        }

        if (e.getKeyCode() != KeyEvent.VK_SPACE) 
        {
            spacePressed = false;
        }

        switch (currentFocusState) 
        {
            case MAIN_MENU:
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) 
                {
                    String selected = navPanel.getMenuList().getSelectedValue();
                    if ("Модули".equals(selected)) 
                    {
                        focusOnModulesList();
                    } 
                    else if ("Словари".equals(selected)) 
                    {
                        focusOnDictionaries();
                    }
                    else if ("Настройки".equals(selected))
                    {
                        focusOnSettings();
                    }
                }
                break;
                
            case MODULES_LIST:
                break;

            case DICTIONARIES:
            if (e.getKeyCode() == KeyEvent.VK_LEFT) 
            {
                Component comp = contentPanel.getComponent(3);
                if (comp instanceof DictionariesPanel) 
                {
                    DictionariesPanel dictPanel = (DictionariesPanel) comp;
                    CustomTabbedPane tabs = dictPanel.getTabs();
                    if ("Основной словарь".equals(tabs.getTitleAt(tabs.getSelectedIndex()))) 
                    {
                        focusOnMainMenu();
                        e.consume();
                    }
                }
            }
            break;
            
        case SETTINGS:
            if (e.getKeyCode() == KeyEvent.VK_LEFT) 
            {
                Component comp = contentPanel.getComponent(1);
                if (comp instanceof SettingsPanel) 
                {
                    SettingsPanel settingsPanel = (SettingsPanel) comp;
                    CustomTabbedPane tabs = settingsPanel.getTabs();
                    if ("Окно".equals(tabs.getTitleAt(tabs.getSelectedIndex()))) 
                    {
                        focusOnMainMenu();
                        e.consume();
                    }
                }
            }
            break;
        }
    }

    private void setFocus(FocusState newState) 
    {
        currentFocusState = newState;
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
        SwingUtilities.invokeLater(() -> 
        {
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