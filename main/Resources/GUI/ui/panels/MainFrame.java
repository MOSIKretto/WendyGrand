package ui.panels;
// Оставлено до лучших времен
//import java.awt.geom.RoundRectangle2D;

import utils.ColorPalette;
import utils.UIConstants;
import javax.swing.*;
import java.awt.*;


public class MainFrame 
{
    private JFrame frame;
    
    public static void createMainFrame()
    {
        SwingUtilities.invokeLater(() -> 
        {
            JFrame frame = new JFrame();
            ImageIcon icon = new ImageIcon("ui/components/icon.ico");
            frame.setUndecorated(true);
            frame.setMinimumSize(new Dimension(UIConstants.MIN_WIDTH, UIConstants.MIN_HEIGHT));
            frame.setMaximumSize(new Dimension(UIConstants.MAX_WIDTH, UIConstants.MAX_HEIGHT));
            //frame.setShape(new RoundRectangle2D.Double(0, 0, UIConstants.LAUNCH_WIDTH, UIConstants.LAUNCH_HEIGHT, 30, 30));
            frame.setBackground(ColorPalette.BACKGROUND);
            frame.setIconImage(icon.getImage());
            frame.setLocationRelativeTo(null);
            
            frame.setResizable(true);

            frame.add(MainPanel.mainPanel(), BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }

    public void closeWindow()
    {
        frame.dispose();
    }
}

