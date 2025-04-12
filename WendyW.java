import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class WendyW extends JFrame {
    // Константы путей
    private static final String CONFIG_DIR = "Configs";
    private static final String MODULES_DIR = "Modules";
    private static final String APPS_CONFIG = CONFIG_DIR + File.separator + "Apps.conf";
    private static final String DICT_CONFIG = CONFIG_DIR + File.separator + "Dictionary.conf"; 
    private static final String WINDOW_CONFIG = CONFIG_DIR + File.separator + "Window.conf";

    // Компоненты UI
    private JTextArea chatArea;
    private JTextField messageField;
    private JList<String> menuList;
    private JTextArea moduleConfigArea;
    private JTextArea dictionaryArea;
    private Point startPos;
    private boolean isDragging = false;

    public WendyW() {
        configureWindow();
        initUI();
    }

    private void configureWindow() {
        setTitle("Wendy");
        setSize(1000, 700);
        setUndecorated(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 20, 20));
    }

    private void initUI() {
        // Главная панель с темным фоном
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(40, 40, 40));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Панель управления (кнопки закрытия)
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setOpaque(false);
        
        JButton minimizeBtn = new JButton("—");
        minimizeBtn.addActionListener(e -> setState(Frame.ICONIFIED));
        styleControlButton(minimizeBtn);
        
        JButton closeBtn = new JButton("×");
        closeBtn.addActionListener(e -> System.exit(0));
        styleControlButton(closeBtn);
        
        controlPanel.add(minimizeBtn);
        controlPanel.add(closeBtn);

        // Меню навигации
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.Y_AXIS));
        navPanel.setPreferredSize(new Dimension(200, 0));
        navPanel.setBackground(new Color(30, 30, 30));
        
        String[] menuItems = {"Wendy", "Настройки", "Модули", "Словари"};
        menuList = new JList<>(menuItems);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setSelectedIndex(0);
        menuList.setFixedCellHeight(40);
        menuList.setBackground(new Color(30, 30, 30));
        menuList.setForeground(new Color(225, 215, 198));
        menuList.setCellRenderer(new MenuListRenderer());
        
        navPanel.add(menuList);

        // Контентная панель
        JPanel contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(new Color(50, 50, 50));
        
        // Добавляем экраны
        contentPanel.add(createChatScreen(), "0");
        contentPanel.add(createSettingsScreen(), "1");
        contentPanel.add(createModulesScreen(), "2");
        contentPanel.add(createDictionaryScreen(), "3");

        // Обработчик выбора меню
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                CardLayout cl = (CardLayout)(contentPanel.getLayout());
                cl.show(contentPanel, String.valueOf(menuList.getSelectedIndex()));
            }
        });

        // Сборка интерфейса
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(navPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Обработчики перемещения окна
        addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if (isDragging) {
                    Point current = e.getLocationOnScreen();
                    setLocation(current.x - startPos.x, current.y - startPos.y);
                }
            }
        });

        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    isDragging = true;
                    startPos = e.getPoint();
                }
            }
            
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    isDragging = false;
                }
            }
        });

        add(mainPanel);
    }

    private JPanel createChatScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));
        
        // Область чата
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(60, 60, 60));
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scroll = new JScrollPane(chatArea);
        panel.add(scroll, BorderLayout.CENTER);
        
        // Панель ввода
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        inputPanel.setBackground(new Color(50, 50, 50));
        
        messageField = new JTextField();
        messageField.setBackground(new Color(70, 70, 70));
        messageField.setForeground(Color.WHITE);
        messageField.addActionListener(e -> processMessage());
        
        JButton sendBtn = new JButton("Отправить");
        sendBtn.addActionListener(e -> processMessage());
        styleButton(sendBtn);
        
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        panel.add(inputPanel, BorderLayout.SOUTH);
        
        // Тестовое сообщение
        addMessage("Wendy", "Привет! Я Wendy, твой помощник. Чем могу помочь?", false);
        
        return panel;
    }

    private JPanel createSettingsScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));
        
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(50, 50, 50));
        tabs.setForeground(new Color(225, 215, 198));
        
        // Вкладка настроек окна
        JPanel windowPanel = new JPanel(new BorderLayout());
        windowPanel.setBackground(new Color(50, 50, 50));
        
        // Заглушка для смены темы (в разработке)
        JPanel themePanel = new JPanel();
        themePanel.setLayout(new BoxLayout(themePanel, BoxLayout.Y_AXIS));
        themePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        themePanel.setBackground(new Color(50, 50, 50));
        
        JLabel infoLabel = new JLabel("Смена темы в разработке");
        infoLabel.setForeground(new Color(225, 215, 198));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel comingSoonLabel = new JLabel("Доступно в следующих версиях");
        comingSoonLabel.setForeground(new Color(180, 180, 180));
        comingSoonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        themePanel.add(infoLabel);
        themePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        themePanel.add(comingSoonLabel);
        
        windowPanel.add(themePanel, BorderLayout.CENTER);
        
        // Вкладка настроек программ
        JPanel appsPanel = createAppsSettingsPanel();
        
        tabs.addTab("Окно", windowPanel);
        tabs.addTab("Программы", appsPanel);
        
        panel.add(tabs, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createAppsSettingsPanel() {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBackground(new Color(50, 50, 50));
    
    // Загрузка текущих настроек
    Map<String, String> config = new HashMap<>();
    try {
        config = loadConfig(APPS_CONFIG);
    } catch (IOException e) {
        System.err.println("Ошибка загрузки конфига: " + e.getMessage());
    }
    
    String[] programs = {
        "browser", "conductor", "terminal", "store", 
        "office", "messenger", "socialnetwork", "notes", 
        "codeeditor", "websearch"
    };
    
    String[] labels = {
        "Браузер", "Проводник", "Терминал", "Менеджер пакетов", 
        "Офис", "Мессенджер", "Соц. сети", "Заметки", 
        "Редактор кода", "Поисковая система"
    };
    
    JPanel settingsPanel = new JPanel();
    settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));
    settingsPanel.setBackground(new Color(50, 50, 50));
    
    // Максимальный размер для текстовых полей
    Dimension fieldSize = new Dimension(300, 30);
    JTextField[] fields = new JTextField[programs.length];
    
    for (int i = 0; i < programs.length; i++) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBorder(new EmptyBorder(5, 0, 5, 0));
        row.setBackground(new Color(50, 50, 50));
        
        JLabel label = new JLabel(labels[i]);
        label.setForeground(new Color(225, 215, 198));
        label.setPreferredSize(new Dimension(150, 30));
        
        JPanel fieldWrapper = new JPanel(new BorderLayout());
        fieldWrapper.setBackground(new Color(50, 50, 50));
        
        fields[i] = new JTextField(config.getOrDefault(programs[i], ""));
        fields[i].setBackground(new Color(70, 70, 70));
        fields[i].setForeground(Color.WHITE);
        fields[i].setPreferredSize(fieldSize);
        fields[i].setMaximumSize(fieldSize);
        
        fieldWrapper.add(fields[i], BorderLayout.WEST);
        
        row.add(label, BorderLayout.WEST);
        row.add(fieldWrapper, BorderLayout.CENTER);
        
        settingsPanel.add(row);
    }
    
    JButton saveBtn = new JButton("Сохранить");
    saveBtn.addActionListener(e -> {
        try {
            // Читаем исходный файл полностью
            List<String> originalLines = Files.readAllLines(Paths.get(APPS_CONFIG), StandardCharsets.UTF_8);
            Map<String, String> newValues = new HashMap<>();
            
            // Собираем новые значения из полей ввода
            for (int i = 0; i < programs.length; i++) {
                newValues.put(programs[i], fields[i].getText().trim());
            }
            
            // Обрабатываем каждую строку оригинального файла
            List<String> newLines = new ArrayList<>();
            Set<String> processedKeys = new HashSet<>();
            
            for (String line : originalLines) {
                String trimmedLine = line.trim();
                
                // Сохраняем пустые строки и комментарии как есть
                if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                    newLines.add(line);
                    continue;
                }
                
                // Разбираем строки конфига
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    if (newValues.containsKey(key)) {
                        // Обновляем значение, сохраняя оригинальный формат
                        String newLine = parts[0] + "=" + newValues.get(key);
                        newLines.add(newLine);
                        processedKeys.add(key);
                        continue;
                    }
                }
                
                // Если строка не содержит параметр из нашего списка, оставляем как есть
                newLines.add(line);
            }
            
            // Добавляем новые параметры, которых не было в файле
            if (newValues.size() > processedKeys.size()) {
                newLines.add("");
                newLines.add("# Добавленные параметры");
                for (Map.Entry<String, String> entry : newValues.entrySet()) {
                    if (!processedKeys.contains(entry.getKey())) {
                        newLines.add(entry.getKey() + "=" + entry.getValue());
                    }
                }
            }
            
            // Записываем обновленный файл
            Files.write(Paths.get(APPS_CONFIG), newLines, StandardCharsets.UTF_8);
            JOptionPane.showMessageDialog(this, "Настройки сохранены с сохранением структуры файла!");
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка сохранения: " + ex.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    });
    styleButton(saveBtn);
    
    JScrollPane scrollPane = new JScrollPane(settingsPanel);
    scrollPane.setBorder(null);
    
    panel.add(scrollPane, BorderLayout.CENTER);
    
    JPanel buttonPanel = new JPanel();
    buttonPanel.setBackground(new Color(50, 50, 50));
    buttonPanel.add(saveBtn);
    
    panel.add(buttonPanel, BorderLayout.SOUTH);
    
    return panel;
}

    private JPanel createModulesScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setBackground(new Color(50, 50, 50));
        
        // Список модулей
        DefaultListModel<String> modulesModel = new DefaultListModel<>();
        JList<String> modulesList = new JList<>(modulesModel);
        refreshModulesList(modulesModel);
        modulesList.setBackground(new Color(30, 30, 30));
        modulesList.setForeground(new Color(225, 215, 198));
        
        JScrollPane listScroll = new JScrollPane(modulesList);
        
        // Кнопки управления
        JPanel listButtons = new JPanel();
        listButtons.setBackground(new Color(50, 50, 50));
        
        JButton refreshBtn = new JButton("Обновить");
        refreshBtn.addActionListener(e -> refreshModulesList(modulesModel));
        styleButton(refreshBtn);
        
        JButton openBtn = new JButton("Открыть папку");
        openBtn.addActionListener(e -> openModulesFolder());
        styleButton(openBtn);
        
        listButtons.add(refreshBtn);
        listButtons.add(openBtn);
        
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(50, 50, 50));
        leftPanel.add(listScroll, BorderLayout.CENTER);
        leftPanel.add(listButtons, BorderLayout.SOUTH);
        
        // Редактор модуля
        moduleConfigArea = new JTextArea();
        moduleConfigArea.setBackground(new Color(60, 60, 60));
        moduleConfigArea.setForeground(Color.WHITE);
        JScrollPane editorScroll = new JScrollPane(moduleConfigArea);
        
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.addActionListener(e -> saveModuleConfig(modulesList.getSelectedValue()));
        styleButton(saveBtn);
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(new Color(50, 50, 50));
        rightPanel.add(editorScroll, BorderLayout.CENTER);
        rightPanel.add(saveBtn, BorderLayout.SOUTH);
        
        // Обработчик выбора модуля
        modulesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadModuleConfig(modulesList.getSelectedValue());
            }
        });
        
        splitPane.setLeftComponent(leftPanel);
        splitPane.setRightComponent(rightPanel);
        
        panel.add(splitPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDictionaryScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setBackground(new Color(50, 50, 50));
        
        dictionaryArea = new JTextArea();
        dictionaryArea.setBackground(new Color(60, 60, 60));
        dictionaryArea.setForeground(Color.WHITE);
        JScrollPane scroll = new JScrollPane(dictionaryArea);
        
        JButton saveBtn = new JButton("Сохранить словарь");
        saveBtn.addActionListener(e -> saveDictionary());
        styleButton(saveBtn);
        
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(saveBtn, BorderLayout.SOUTH);
        
        loadDictionary();
        
        return panel;
    }

    private void styleControlButton(JButton btn) {
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(200, 200, 200));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });
    }

    private void styleButton(JButton btn) {
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setForeground(new Color(200, 200, 200));
            }
            public void mouseExited(MouseEvent e) {
                btn.setForeground(Color.WHITE);
            }
        });
    }

    private void processMessage() {
        String msg = messageField.getText().trim();
        if (!msg.isEmpty()) {
            addMessage("Вы", msg, true);
            messageField.setText("");
        }
    }

    private void addMessage(String sender, String text, boolean isUser) {
        SwingUtilities.invokeLater(() -> {
            chatArea.append((isUser ? "[Вы]: " : "[" + sender + "]: ") + text + "\n\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }

    private Map<String, String> loadConfig(String path) throws IOException {
        Map<String, String> config = new HashMap<>();
        File configFile = new File(path);
        if (!configFile.exists()) {
            return config;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(configFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    config.put(parts[0].trim(), parts[1].trim());
                }
            }
        }
        return config;
    }

    private void refreshModulesList(DefaultListModel<String> model) {
        model.clear();
        File modulesDir = new File(MODULES_DIR);
        if (modulesDir.exists() && modulesDir.isDirectory()) {
            File[] files = modulesDir.listFiles();
            if (files != null) {
                for (File f : files) {
                    if (f.isFile()) {
                        model.addElement(f.getName());
                    }
                }
            }
        }
        if (model.isEmpty()) {
            model.addElement("Нет модулей");
        }
    }

    private void loadModuleConfig(String name) {
        if (name == null || name.equals("Нет модулей")) {
            moduleConfigArea.setText("");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(MODULES_DIR + File.separator + name))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            moduleConfigArea.setText(content.toString());
        } catch (IOException e) {
            moduleConfigArea.setText("Ошибка загрузки: " + e.getMessage());
        }
    }

    private void saveModuleConfig(String name) {
        if (name == null || name.equals("Нет модулей")) return;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MODULES_DIR + File.separator + name))) {
            writer.write(moduleConfigArea.getText());
            JOptionPane.showMessageDialog(this, "Модуль сохранен");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка сохранения: " + e.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openModulesFolder() {
        try {
            Desktop.getDesktop().open(new File(MODULES_DIR));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка открытия папки: " + e.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadDictionary() {
        try (BufferedReader reader = new BufferedReader(new FileReader(DICT_CONFIG))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            dictionaryArea.setText(content.toString());
        } catch (IOException e) {
            dictionaryArea.setText("Ошибка загрузки словаря: " + e.getMessage());
        }
    }

    private void saveDictionary() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DICT_CONFIG))) {
            writer.write(dictionaryArea.getText());
            JOptionPane.showMessageDialog(this, "Словарь сохранен");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Ошибка сохранения: " + e.getMessage(), 
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    static class MenuListRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                    boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBorder(new EmptyBorder(5, 10, 5, 10));
            label.setHorizontalAlignment(SwingConstants.LEFT);
            label.setOpaque(true);
            
            if (isSelected) {
                label.setBackground(new Color(59, 30, 84));
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(new Color(30, 30, 30));
                label.setForeground(new Color(225, 215, 198));
            }
            
            return label;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            WendyW app = new WendyW();
            app.setVisible(true);
        });
    }
}