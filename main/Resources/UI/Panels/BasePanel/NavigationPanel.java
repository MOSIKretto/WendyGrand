package main.Resources.UI.Panels.BasePanel;

import main.Resources.FocusState;
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
        
        menuList.addListSelectionListener(e -> 
        {
            if (!e.getValueIsAdjusting())
                frame.navigateTo(menuList.getSelectedIndex());
        });

        // Настройка клавиатурных команд
        InputMap im = menuList.getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = menuList.getActionMap();

        // Навигация вверх/вниз по меню
        im.put(KeyStroke.getKeyStroke("DOWN"), "selectNext");
        im.put(KeyStroke.getKeyStroke("UP"), "selectPrevious");
        
        // Переход в выбранный раздел
        im.put(KeyStroke.getKeyStroke("RIGHT"), "enterSelected");


        am.put("enterSelected", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String selected = menuList.getSelectedValue();
                if ("Модули".equals(selected)) 
                {
                    frame.focusOnModulesList();
                } 
                else if ("Словари".equals(selected)) 
                {
                    frame.focusOnDictionaries();
                }
                else if ("Настройки".equals(selected)) 
                {
                    frame.focusOnSettings();
                }
            }
        });
        
        am.put("selectNext", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int next = Math.min(menuList.getSelectedIndex() + 1, menuList.getModel().getSize() - 1);
                menuList.setSelectedIndex(next);
            }
        });
        
        am.put("selectPrevious", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                int prev = Math.max(menuList.getSelectedIndex() - 1, 0);
                menuList.setSelectedIndex(prev);
            }
        });
        
        am.put("enterSelected", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String selected = menuList.getSelectedValue();
                if ("Модули".equals(selected)) 
                {
                    frame.focusOnModulesList();
                } 
                else if ("Словари".equals(selected)) 
                {
                    frame.focusOnDictionaries();
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
            if (menuList.getSelectedIndex() == -1 && menuList.getModel().getSize() > 0) 
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