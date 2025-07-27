package main.Resources.UI.Components;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CustomTabbedPane extends JTabbedPane 
{
    private final CustomTabbedPaneUI customUI = new CustomTabbedPaneUI();
    
    public CustomTabbedPane() 
    {
        setUI(customUI);
        initKeyboardNavigation();
        setFocusCycleRoot(true);
    }

    private void initKeyboardNavigation() 
    {
        setFocusable(true);
        
        InputMap im = getInputMap(JComponent.WHEN_FOCUSED);
        ActionMap am = getActionMap();

        im.put(KeyStroke.getKeyStroke("RIGHT"), "nextTab");
        im.put(KeyStroke.getKeyStroke("LEFT"), "prevTab");
        im.put(KeyStroke.getKeyStroke("SPACE"), "selectTab");
        im.put(KeyStroke.getKeyStroke("ENTER"), "selectTab");

        am.put("nextTab", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                navigate(1);
            }
        });

        am.put("prevTab", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                navigate(-1);
            }
        });

        am.put("selectTab", new AbstractAction() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {

            }
        });
    }

    private void navigate(int direction) 
    {
        int count = getTabCount();
        if (count == 0) return;
        
        int current = getSelectedIndex();
        int newIndex;
        
        if (current == -1) 
        {
            newIndex = (direction > 0) ? 0 : count - 1;
        } else {
            newIndex = current + direction;
            
            if (newIndex < 0) newIndex = count - 1;
            if (newIndex >= count) newIndex = 0;
        }
        
        setSelectedIndex(newIndex);
        
        requestFocusInWindow();
    }
}