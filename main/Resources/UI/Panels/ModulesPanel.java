package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
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

    private JTextArea configArea;
    private JList<String> modulesList;
    private DefaultListModel<String> listModel;
    private static final String MODULES_DIR = "../WendyGrand/Modules/";

    public ModulesPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initUI();
    }

    private void initUI() 
    {
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(300);
        splitPane.setBackground(new Color(50, 50, 50));
        
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
        configArea.setBackground(new Color(60, 60, 60));
        configArea.setForeground(Color.WHITE);
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
}