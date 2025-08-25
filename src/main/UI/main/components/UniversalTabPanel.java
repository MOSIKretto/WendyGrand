package src.main.UI.main.components;

import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;

public class UniversalTabPanel extends JPanel 
{
    protected CustomTabbedPane tabs;

    public UniversalTabPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        setBackground(new Color(50, 50, 50));
        
        tabs = new CustomTabbedPane();
        add(tabs, BorderLayout.CENTER);
    }

    public void addTab(String title, JPanel panel) 
    {
        tabs.addTab(title, panel);
    }

    public void setDefaultSelectedTab(int index) 
    {
        if (tabs.getTabCount() > index) {
            tabs.setSelectedIndex(index);
        }
    }

    public CustomTabbedPane getTabs() 
    {
        return tabs;
    }
}