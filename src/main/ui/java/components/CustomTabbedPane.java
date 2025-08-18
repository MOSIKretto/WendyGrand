package src.main.ui.java.components;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JTabbedPane;
import javax.swing.JComponent;
import javax.swing.ActionMap;
import javax.swing.KeyStroke;
import javax.swing.InputMap;

public class CustomTabbedPane extends JTabbedPane 
{
    private final CustomTabbedPaneUI customUI = new CustomTabbedPaneUI();
    
    public CustomTabbedPane() 
    {
        setUI(customUI);
        setSelectedIndex(-1);
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