package main.Resources.UI;

import main.Resources.UI.Panels.BasePanel.ContentPanel;
import main.Resources.UI.Panels.BasePanel.ControlPanel;
import main.Resources.UI.Panels.BasePanel.MainPanel;
import main.Resources.UI.Panels.BasePanel.NavigationPanel;
import main.Resources.UI.Panels.PagesPanel.DictionariesPanel;
import main.Resources.UI.Panels.PagesPanel.ModulesPanel;
import main.Resources.enums.FocusState;
import main.Resources.UI.Components.CustomTabbedPane;

import java.awt.geom.RoundRectangle2D;
import java.util.prefs.Preferences;
import main.Resources.UI.Panels.PagesPanel.SettingsPanel;
import javax.swing.border.EmptyBorder;
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
    private boolean spacePressed = false; // Добавлено для отслеживания пробела

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
        
        // Добавление слушателей для пробела
        mainPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    spacePressed = true;
                }
            }
            
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
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

    private void restoreLastState() {
        int lastMenu = PREFS.getInt("last_menu_item", 0);
        navPanel.getMenuList().setSelectedIndex(lastMenu);

        switch (lastMenu) 
        {
            case 1: // Настройки
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

        // Глобальные горячие клавиши
        if ((e.getModifiersEx() & KeyEvent.CTRL_DOWN_MASK) != 0) 
        {
            if (e.getKeyCode() == KeyEvent.VK_Q) 
            {
                dispose();
            }
            return;
        }

        // Возврат в главное меню по Space+Left
        if (e.getKeyCode() == KeyEvent.VK_LEFT && spacePressed) {
            focusOnMainMenu();
            e.consume();
            return;
        }

        // Сброс пробела при других нажатиях
        if (e.getKeyCode() != KeyEvent.VK_SPACE) {
            spacePressed = false;
        }

        // Навигация между основными разделами
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
                if (e.getKeyCode() == KeyEvent.VK_LEFT && spacePressed) 
                {
                    focusOnMainMenu();
                }
                break;

            case DICTIONARIES:
                if (e.getKeyCode() == KeyEvent.VK_LEFT && spacePressed) 
                {
                    focusOnMainMenu();
                }
                break;
                
            case SETTINGS:
                if (e.getKeyCode() == KeyEvent.VK_LEFT && spacePressed) 
                {
                    focusOnMainMenu();
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

    private boolean isFocusInTabbedPane(Component comp)
    {
        while (comp != null) 
        {
            if (comp instanceof CustomTabbedPane)
            {
                return true;
            }
            comp = comp.getParent();
        }
        return false;
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