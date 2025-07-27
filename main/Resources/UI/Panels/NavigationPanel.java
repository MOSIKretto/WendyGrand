package main.Resources.UI.Panels;

import main.Resources.enums.FocusState;
import main.Resources.UI.MenuListRenderer;
import main.Resources.UI.WindowMaker;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.*;
import java.awt.*;


public class NavigationPanel extends JPanel 
{
    private JList<String> menuList;
    private WindowMaker frame;

    public NavigationPanel(WindowMaker frame) 
    {
        this.frame = frame;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 0));
        setBackground(new Color(30, 30, 30));
        
        String[] menuItems = {"Wendy", "Настройки", "Модули", "Словари", "История"};
        menuList = new JList<>(menuItems);
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

        menuList.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("RIGHT"), "focusModules");
        menuList.getActionMap().put("focusModules", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                if (menuList.getSelectedValue().equals("Модули")) 
                {
                    frame.focusOnModulesList();
                }
            }
        });

        menuList.addFocusListener(new FocusAdapter() 
        {
            @Override
            public void focusGained(FocusEvent e)
            {
               frame.setCurrentFocusState(FocusState.MAIN_MENU);
            }    
        });
        
        add(menuList);
    }

    public void focusOnList()
    {
        if (menuList != null)
        {
            menuList.requestFocusInWindow();
            if (menuList.getModel().getSize() > 0 && menuList.isSelectionEmpty())
            {
                menuList.setSelectedIndex(0);
            }
        }
    }

    public JList<String> getMenuList()
    {
        return menuList;
    }
}