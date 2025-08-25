package src.main.UI.main.core;

import src.main.UI.main.panels.pagesPanel.DictionariesPanel;
import src.main.UI.main.panels.basePanel.NavigationPanel;
import src.main.UI.main.panels.pagesPanel.SettingsPanel;
import src.main.UI.main.panels.pagesPanel.ModulesPanel;
import src.main.UI.main.panels.basePanel.ContentPanel;
import src.main.UI.main.panels.basePanel.ControlPanel;
import src.main.UI.main.components.CustomTabbedPane;
import src.main.UI.main.panels.basePanel.MainPanel;
import src.main.UI.helpers.enums.FocusState;
import src.main.UI.assistant.dialogs.ErrorHandler;

import javax.swing.border.EmptyBorder;
import java.awt.geom.RoundRectangle2D;
import java.awt.KeyboardFocusManager;
import java.util.prefs.Preferences;
import java.awt.event.MouseAdapter;
import javax.swing.SwingUtilities;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Point;

public class WindowMaker extends JFrame 
{
    public static final Preferences PREFS = Preferences.userNodeForPackage(WindowMaker.class);
    private static ErrorHandler errorHandler;
    
    private FocusState currentFocusState = FocusState.MAIN_MENU;
    private JPanel contentPanel;
    private NavigationPanel navPanel;
    private Point startPos;
    private boolean isDragging = false;
    private boolean spacePressed = false; 

    public WindowMaker() 
    {
        try {
            configureWindow();
            initUI();
            setInitialFocus();
            setFocus(FocusState.MAIN_MENU);
            restoreLastState();
        } catch (Exception e) {
            getErrorHandler().handleFatalError("Ошибка инициализации приложения", e);
            throw new RuntimeException("Failed to initialize WindowMaker", e);
        }
    }

    // Метод для получения обработчика ошибок
    private static ErrorHandler getErrorHandler() {
        if (errorHandler == null) {
            // Создаем стандартный обработчик, если не установлен
            errorHandler = new src.main.UI.assistant.dialogs.SwingErrorHandler();
        }
        return errorHandler;
    }
    
    // Метод для установки кастомного обработчика ошибок
    public static void setErrorHandler(ErrorHandler handler) {
        errorHandler = handler;
    }

    private void configureWindow() 
    {
        try {
            setTitle("Wendy");
            setSize(1280, 840);
            setUndecorated(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
        } catch (Exception e) {
            getErrorHandler().handleFatalError("Ошибка конфигурации окна", e);
            throw e;
        }
    }

    private void initUI() {
        try {
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
        } catch (Exception e) {
            getErrorHandler().handleFatalError("Ошибка инициализации UI", e);
            throw e;
        }
    }

    private void restoreLastState() 
    {
        try {
            int lastMenu = PREFS.getInt("last_menu_item", 0);
            navPanel.getMenuList().setSelectedIndex(lastMenu);

            switch (lastMenu) 
            {
                case 1: 
                    focusOnPanel("Settings");
                    break;

                case 2: 
                    focusOnPanel("Modules");
                    int lastModule = PREFS.getInt("last_module", 0);
                    ((ModulesPanel)contentPanel.getComponent(2)).restoreLastModule(lastModule);
                    break;

                case 3: 
                    focusOnPanel("Dictioraries");
                    int lastDictTab = PREFS.getInt("last_dict_tab", 0);
                    ((DictionariesPanel)contentPanel.getComponent(3)).getTabs().setSelectedIndex(lastDictTab);
                    break;
            }
        } catch (Exception e) {
            getErrorHandler().showWarning("Восстановление состояния", 
                "Не удалось восстановить предыдущее состояние приложения: " + e.getMessage());
        }
    }

    public void focusOnPanel(String target)
    {
        try {
            switch (target) 
            {
                case "MainMenu":
                    PREFS.putInt("last_menu_item", navPanel.getMenuList().getSelectedIndex());
                    navPanel.focusOnList();
                    setCurrentFocusState(FocusState.MAIN_MENU); 
                    break;
                
                case "Settings":
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
                    break;

                case "Modules":
                    ((CardLayout)contentPanel.getLayout()).show(contentPanel, "2");
                    setCurrentFocusState(FocusState.MODULES_LIST);
                    
                    for (Component modComp : contentPanel.getComponents()) 
                    {
                        if (modComp instanceof ModulesPanel) 
                        {
                            ((ModulesPanel)modComp).focusOnList();
                            break;
                        }
                    }
                    break;
                
                case "Dictionaries":
                    ((CardLayout)contentPanel.getLayout()).show(contentPanel, "3");
                    setCurrentFocusState(FocusState.DICTIONARIES);

                    Component dictComp = contentPanel.getComponent(3);
                    if (dictComp instanceof DictionariesPanel) 
                    {
                        CustomTabbedPane tabs = ((DictionariesPanel)dictComp).getTabs();
                        if (tabs != null && tabs.getTabCount() > 0) 
                        {
                            tabs.setSelectedIndex(0);
                            tabs.requestFocusInWindow();
                        }
                    }
                    break;
            }
        } catch (Exception e) {
            getErrorHandler().showWarning("Навигация", 
                "Ошибка при переходе к панели " + target + ": " + e.getMessage());
        }
    }

    private void handleKeyPress(KeyEvent e) 
    {
        if (e.getID() != KeyEvent.KEY_PRESSED) return;

        try {
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
                            focusOnPanel("Modules");
                        } 
                        else if ("Словари".equals(selected)) 
                        {
                            focusOnPanel("Dictionaries");
                        }
                        else if ("Настройки".equals(selected))
                        {
                            focusOnPanel("Settings");
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
                            focusOnPanel("MainMenu");
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
                            focusOnPanel("MainMenu");
                            e.consume();
                        }
                    }
                }
                break;
            }
        } catch (Exception ex) {
            getErrorHandler().showWarning("Обработка клавиш", 
                "Ошибка при обработке нажатия клавиши: " + ex.getMessage());
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
        try {
            ((CardLayout) contentPanel.getLayout()).show(contentPanel, String.valueOf(index));
        } catch (Exception e) {
            getErrorHandler().showWarning("Навигация", 
                "Ошибка при переходе к индексу " + index + ": " + e.getMessage());
        }
    }

    private static void safeStart() {
        try {
            SwingUtilities.invokeLater(() -> {
                try {
                    WindowMaker window = new WindowMaker();
                    window.setVisible(true);
                } catch (Exception e) {
                    getErrorHandler().handleFatalError("Ошибка создания окна", e);
                }
            });
        } catch (Exception e) {
            getErrorHandler().handleFatalError("Ошибка в потоке EDT", e);
        }
    }

    public static void startWindow() {
        safeStart();
    }

    public void setInitialFocus()
    {
        try {
            SwingUtilities.invokeLater(() -> 
            {
                navPanel.getMenuList().requestFocusInWindow();
                navPanel.getMenuList().setSelectedIndex(0);
            });
        } catch (Exception e) {
            getErrorHandler().showWarning("Установка фокуса", 
                "Ошибка при установке начального фокуса: " + e.getMessage());
        }
    }

    public void setCurrentFocusState(FocusState state)
    {
        this.currentFocusState = state;
    }

    public FocusState getCurrentFocusState()
    {
        return currentFocusState;
    }
    
    // Вспомогательные методы для доступа к обработчику ошибок из других классов
    public static void showWarning(String title, String message) {
        getErrorHandler().showWarning(title, message);
    }
    
    public static void showInfo(String title, String message) {
        getErrorHandler().showInfo(title, message);
    }
}