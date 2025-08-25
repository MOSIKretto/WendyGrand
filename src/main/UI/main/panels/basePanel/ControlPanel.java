package src.main.UI.main.panels.basePanel;

import src.main.UI.main.utils.UIUtils;

import javax.swing.border.EmptyBorder;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JFrame;

public class ControlPanel extends JPanel 
{
    public ControlPanel(JFrame frame) 
    {
        setLayout(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        setOpaque(false);
        setBorder(new EmptyBorder(0, 0, 10, 10));
        
        add(UIUtils.createControlButton("minimize", _ -> frame.setState(JFrame.ICONIFIED)));
        add(UIUtils.createControlButton("close", _ -> System.exit(0)));
    }
    
}