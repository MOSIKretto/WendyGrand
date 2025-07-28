package src.main.ui.java.components;

import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.awt.*;

public class CustomDivider extends BasicSplitPaneDivider 
{
    public CustomDivider(BasicSplitPaneUI ui) 
    {
        super(ui);
        super.setBackground(Color.GRAY);
    }
    
    @Override
    public void paint(Graphics g) 
    {
        int width = this.getWidth();
        int height = this.getHeight();
        
        g.setColor(this.getBackground());
        g.fillRect(0, 0, width, height);
    }
}
