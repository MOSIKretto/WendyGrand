package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import main.Resources.UI.WindowMaker;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import main.Resources.FocusState;
import main.Resources.UI.UIUtils;
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

    private WindowMaker window;
    private JTextArea configArea;
    private JList<String> modulesList;
    private DefaultListModel<String> listModel;
    private static final String MODULES_DIR = "../WendyGrand/Modules/";

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

            @Override
            public void focusLost(FocusEvent e)
            {

            }
        });
    }

    private void initUI() 
    {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setBackground(new Color(50, 50, 50));
        splitPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
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
        
        JScrollPane scroll = new JScrollPane(modulesList);
        UIUtils.styleScrollPane(scroll);
        panel.add(scroll, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(50, 50, 50));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(59, 30, 84), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15) 
        ));
        
        JButton refreshBtn = new JButton("Обновить");
        refreshBtn.addActionListener(e -> refreshModulesList());
        UIUtils.styleButton(refreshBtn);
        
        JButton openBtn = new JButton("Открыть папку");
        openBtn.addActionListener(e -> openModulesFolder());
        UIUtils.styleButton(openBtn);
        
        buttonPanel.add(refreshBtn);
        buttonPanel.add(openBtn);
        
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createEditorPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 50, 50));
        
        configArea = new JTextArea();
        configArea.setCaretPosition(0);
        UIUtils.styleTextArea(configArea, Color.WHITE, 500, 5, false, null);

        configArea.setBackground(new Color(60, 60, 60));
        configArea.setForeground(Color.WHITE);
        configArea.setFont(new Font("Courier", Font.BOLD, 14));
        JScrollPane scroll = new JScrollPane(configArea);
        UIUtils.styleScrollPane(scroll);
        
        JButton saveBtn = new JButton("Сохранить");
        saveBtn.addActionListener(this::saveModuleConfig);
        UIUtils.styleButton(saveBtn);
        
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(saveBtn, BorderLayout.SOUTH);
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

    private void setupKeyBindings()
    {
        InputMap im = modulesList.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = modulesList.getActionMap();

        Action returnAction = new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                modulesList.clearSelection();
                window.focusOnMainMenu();
            }
        };

        im.put(KeyStroke.getKeyStroke("LEFT"), "returnAction");
        im.put(KeyStroke.getKeyStroke("ESCAPE"), "returnAction");
        am.put("returnAction", returnAction);

        im.put(KeyStroke.getKeyStroke("RIGHT"), "none");
    }

    private void returnFocusTomainMenu()
    {
        modulesList.clearSelection();
        window.focusOnMainMenu();
    }

    public void focusOnList() {
        modulesList.requestFocusInWindow();
        if (modulesList.getModel().getSize() > 0) 
        {
            modulesList.setSelectedIndex(0); 
        }
        
        InputMap im = modulesList.getInputMap(JComponent.WHEN_FOCUSED);
        im.put(KeyStroke.getKeyStroke("RIGHT"), "none");
    }
}