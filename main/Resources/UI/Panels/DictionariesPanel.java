package main.Resources.UI.Panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;

import main.Resources.UI.Components.CustomTabbedPane;

public class DictionariesPanel extends JPanel
{
    public DictionariesPanel()
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(new Color(50, 50, 50));

        JTabbedPane tabs = new JTabbedPane();
        tabs.setUI(new CustomTabbedPane());

        tabs.addTab("Основной словарь", new DictionaryPanel());
        tabs.addTab("Словарь модулей", new ModulesDictPanel());
        tabs.addTab("Словарь озвучки", new VoiceOverDict());

        add(tabs, BorderLayout.CENTER);
    }
}
