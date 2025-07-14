package main.Resources.UI.Panels;

import main.Resources.UI.MenuListRenderer;
import main.Resources.UI.WindowMaker;
import javax.swing.*;
import java.awt.*;


public class NavigationPanel extends JPanel 
{

    public NavigationPanel(WindowMaker frame) 
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(30, 30, 30));
        
        String[] menuItems = {"Wendy", "Настройки", "Модули", "Словари"};
        JList<String> menuList = new JList<>(menuItems);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setSelectedIndex(0);
        menuList.setFixedCellHeight(40);
        menuList.setBackground(new Color(30, 30, 30));
        menuList.setForeground(new Color(225, 215, 198));
        menuList.setCellRenderer(new MenuListRenderer());
        
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting())
                frame.navigateTo(menuList.getSelectedIndex());
        });
        
        add(menuList);
    }
}