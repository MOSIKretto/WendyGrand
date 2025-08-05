package src.main.ui.java.panels.pagesPanel;

import javax.swing.plaf.basic.BasicSplitPaneDivider;
import src.main.ui.java.components.CustomDivider;
import src.main.ui.java.components.CustomButton;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import src.main.helpers.java.enums.FocusState;
import javax.swing.border.EmptyBorder;
import src.main.ui.java.WindowMaker;
import java.awt.event.FocusAdapter;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import src.main.ui.java.UIUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.io.File;
import java.awt.*;

public class ModulesPanel extends JPanel 
{
    private static final String MODULES_DIR = "../WendyGrand/modules/";
    private DefaultListModel<String> listModel;
    private JList<String> modulesList;
    private JTextArea configArea;
    private WindowMaker window;
    
    public ModulesPanel(WindowMaker window) 
    {
        this.window = window;

        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(new Color(50, 50, 50));

        initUI();
        setupKeyBindings();

        modulesList.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                window.setCurrentFocusState(FocusState.MODULES_LIST);
            }
        });
    }

    private void initUI() 
    {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(250);
        splitPane.setBackground(new Color(50, 50, 50));
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        splitPane.setDividerSize(4);

        splitPane.setUI(new BasicSplitPaneUI() 
        {
            @Override
            public BasicSplitPaneDivider createDefaultDivider()
            {
                return new CustomDivider(this);
            }
        });
        
        splitPane.setLeftComponent(createModulesListPanel());
        splitPane.setRightComponent(createEditorPanel());
        
        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createModulesListPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        
        listModel = new DefaultListModel<>();
        refreshModulesList();
        
        modulesList = new JList<>(listModel);
        modulesList.setFont(new Font("Courier", Font.BOLD, 16));
        modulesList.setBackground(new Color(30, 30, 30));
        modulesList.setForeground(new Color(225, 215, 198));
        modulesList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                loadModuleConfig();
        });

        modulesList.setCellRenderer(new DefaultListCellRenderer() 
        {
            @Override
            public Component getListCellRendererComponent(
                JList<?> list, 
                Object value, 
                int index, 
                boolean isSelected, 
                boolean cellHasFocus
            ) {
                Component c = super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus
                );
                
                if (isSelected) 
                {
                    if (list.hasFocus()) 
                    {
                        c.setBackground(new Color(59, 30, 84));
                        c.setForeground(Color.WHITE);
                    } else 
                    {
                        c.setBackground(new Color(80, 40, 100));
                        c.setForeground(Color.LIGHT_GRAY);
                    }
                } else 
                {
                    c.setBackground(new Color(30, 30, 30));
                    c.setForeground(new Color(225, 215, 198));
                }
                return c;
            }
        });
        
        JScrollPane scroll = new JScrollPane(modulesList);
        UIUtils.styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(30, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        CustomButton refreshBtn = new CustomButton("Обновить", 0, 0);
        refreshBtn.addActionListener(e -> refreshModulesList());
        refreshBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        refreshBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        
        CustomButton openBtn = new CustomButton("Открыть папку", 0, 0);
        openBtn.addActionListener(e -> openModulesFolder());
        openBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        openBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(openBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createEditorPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        
        configArea = new JTextArea();
        UIUtils.styleTextArea(configArea, Color.WHITE, 500, 5, false, null);

        configArea.setBackground(new Color(60, 60, 60));
        configArea.setForeground(Color.WHITE);
        configArea.setFont(new Font("Courier", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(configArea);
        UIUtils.styleScrollPane(scroll);
        
        CustomButton saveBtn = UIUtils.createSaveButton("Сохранить", getWidth(), getHeight());
        saveBtn.addActionListener(this::saveModuleConfig);
        
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(saveBtn, BorderLayout.SOUTH);
        setupEditorKeyBindings(configArea);
        return panel;
    }

    private void refreshModulesList() 
    {
        listModel.clear();
        File modulesDir = new File(MODULES_DIR);

        if (modulesDir.exists() && modulesDir.isDirectory()) 
        {
            File[] files = modulesDir.listFiles();

            if (files != null)
                for (File f : files)
                    if (f.isFile())
                        listModel.addElement(f.getName());
        }
        if (listModel.isEmpty())
            listModel.addElement("Нет модулей");
    }

    private void loadModuleConfig() 
    {
        String name = modulesList.getSelectedValue();
        if (name == null || name.equals("Нет модулей")) 
        {
            configArea.setText("");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(MODULES_DIR + File.separator + name)))
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");

            configArea.setText(content.toString());
        }
        catch (IOException e) { configArea.setText("Ошибка загрузки: " + e.getMessage()); }
        configArea.setCaretPosition(0);

        if (modulesList.getSelectedIndex() != -1)
        {
            WindowMaker.PREFS.putInt("last_module", modulesList.getSelectedIndex());
        }
    }

    private void saveModuleConfig(ActionEvent e) 
    {
        String name = modulesList.getSelectedValue();
        if (name == null || name.equals("Нет модулей")) return;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(MODULES_DIR + File.separator + name))) 
        {
            writer.write(configArea.getText());
            JOptionPane.showMessageDialog(this, "Модуль сохранен");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }

    private void openModulesFolder() 
    {
        try { java.awt.Desktop.getDesktop().open(new File(MODULES_DIR)); } 
        catch (Exception ex) { JOptionPane.showMessageDialog(this, "Ошибка открытия папки: " + ex.getMessage()); }
    }

    private void setupEditorKeyBindings(JTextArea editor) 
    {
        InputMap im = editor.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = editor.getActionMap();

        im.put(KeyStroke.getKeyStroke("ESCAPE"), "returnToList");
        im.put(KeyStroke.getKeyStroke("shift pressed TAB"), "returnToList");
        im.put(KeyStroke.getKeyStroke("ctrl LEFT"), "returnToMenu");

        am.put("returnToList", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                modulesList.requestFocusInWindow();
            }
        });
        
        am.put("returnToMenu", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                window.focusOnPanel("MainMenu");;
            }
        });
    }

    private void setupKeyBindings()
    {
        InputMap im = modulesList.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = modulesList.getActionMap();

        im.put(KeyStroke.getKeyStroke("LEFT"), "returnToMenu");
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "returnToMenu");
        
        im.put(KeyStroke.getKeyStroke("DOWN"), "selectNext");
        im.put(KeyStroke.getKeyStroke("UP"), "selectPrevious");
        
        im.put(KeyStroke.getKeyStroke("TAB"), "focusEditor");
        im.put(KeyStroke.getKeyStroke("ENTER"), "focusEditor");

        am.put("returnToMenu", new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                modulesList.clearSelection();
                window.focusOnPanel("MainMenu");;
            }
        });

        am.put("selectNext", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int next = Math.min(modulesList.getSelectedIndex() + 1, modulesList.getModel().getSize() - 1);
                modulesList.setSelectedIndex(next);
            }
        });
        
        am.put("selectPrevious", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int prev = Math.max(modulesList.getSelectedIndex() - 1, 0);
                modulesList.setSelectedIndex(prev);
            }
        });
        
        am.put("focusEditor", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                configArea.requestFocusInWindow();
            }
        });
    }

    

    public void restoreLastModule(int index) 
    {
        if (modulesList != null && index >= 0 && index < modulesList.getModel().getSize()) 
        {
            modulesList.setSelectedIndex(index);
            loadModuleConfig();
            WindowMaker.PREFS.putInt("last_module", index);
        }
    }

    public void focusOnList() {
        if (modulesList != null) 
        {
            modulesList.requestFocusInWindow();
            if (modulesList.getSelectedIndex() == -1 && modulesList.getModel().getSize() > 0) 
            {
                modulesList.setSelectedIndex(0);
            }
        }
    }
}