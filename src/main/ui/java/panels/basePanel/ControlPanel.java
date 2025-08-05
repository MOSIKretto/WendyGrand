package src.main.ui.java.panels.basePanel;

import src.main.ui.java.UIUtils;

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
        
        add(UIUtils.createControlButton("minimize", e -> frame.setState(JFrame.ICONIFIED)));
        add(UIUtils.createControlButton("close", e -> System.exit(0)));
    }
    
}