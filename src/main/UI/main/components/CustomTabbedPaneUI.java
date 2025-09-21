package src.main.UI.main.components;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.RenderingHints;
import java.awt.FontMetrics;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


public class CustomTabbedPaneUI extends BasicTabbedPaneUI 
{
    private final Color selectedColor = new Color(59, 30, 84);
    private final Color selectedBorderColor = new Color(100, 65, 120);
    private final Color unselectedColor = new Color(40, 40, 40);
    private final Color unselectedBorderColor = new Color(80, 80, 80);
    private final float BORDER_STROKE_WIDTH = 2.0f;
    
    private final Color hoverColor = new Color(70, 70, 70); 
    private final Color tabAreaBackground = new Color(35, 35, 35);
    private final Color tabAreaBorder = new Color(70, 70, 70);
    
    private final int TAB_HEIGHT = 40;
    private final int BORDER_THICKNESS = 2;
    private final Font tabFont = new Font("Courier", Font.BOLD, 18);
    
    private int hoveredTabIndex = -1; 

    @Override
    protected void installDefaults() 
    {
        super.installDefaults();
        tabAreaInsets = new Insets(0, 0, 0, 0);
        contentBorderInsets = new Insets(0, 0, 0, 0);
        tabInsets = new Insets(8, 25, 8, 25);
        tabPane.setFont(tabFont);
    }

    @Override
    protected void installListeners() {
        super.installListeners();
        
        // Следим за движением мыши для hover-эффекта
        tabPane.addMouseMotionListener(new MouseMotionAdapter() 
        {
            @Override
            public void mouseMoved(MouseEvent e) 
            {
                int tabIndex = tabForCoordinate(tabPane, e.getX(), e.getY());
                if (tabIndex != hoveredTabIndex) 
                {
                    hoveredTabIndex = tabIndex;
                    tabPane.repaint(); 
                }
            }
        });
        
        tabPane.addMouseListener(new MouseAdapter() 
        {
            @Override
            public void mouseExited(MouseEvent e) 
            {
                if (hoveredTabIndex != -1) 
                {
                    hoveredTabIndex = -1;
                    tabPane.repaint();
                }
            }
        });
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) 
    {
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setColor(tabAreaBackground);
        g2d.fillRect(0, 0, tabPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));
        
        g2d.setColor(tabAreaBorder);
        g2d.setStroke(new BasicStroke(1));
        int y = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - 1;
        g2d.drawLine(0, y, tabPane.getWidth(), y);
        
        g2d.dispose();
        
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) { return TAB_HEIGHT; }

    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect) 
    {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Rectangle tabRect = rects[tabIndex];
        boolean isHovered = hoveredTabIndex == tabIndex;
        boolean isActive = (tabPane.getSelectedIndex() == tabIndex) && tabPane.hasFocus();

        if (isActive) g2d.setColor(selectedColor);
        else if (isHovered) g2d.setColor(hoverColor);
        else g2d.setColor(unselectedColor);
        
        g2d.fillRect(
            tabRect.x + BORDER_THICKNESS, 
            tabRect.y + BORDER_THICKNESS, 
            tabRect.width - BORDER_THICKNESS * 2, 
            tabRect.height - BORDER_THICKNESS * 2
        );

        g2d.setColor(isActive ? selectedBorderColor : unselectedBorderColor);
        g2d.setStroke(new BasicStroke(BORDER_STROKE_WIDTH));
        g2d.drawRect(
            (int)(tabRect.x + BORDER_STROKE_WIDTH / 2),  
            (int)(tabRect.y + BORDER_STROKE_WIDTH / 2),
            (int)(tabRect.width - BORDER_STROKE_WIDTH),
            (int)(tabRect.height - BORDER_STROKE_WIDTH)
        );

        g2d.setColor(isActive ? Color.WHITE : new Color(150, 150, 150));
        String title = tabPane.getTitleAt(tabIndex);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = tabRect.x + (tabRect.width - fm.stringWidth(title)) / 2;
        int textY = tabRect.y + (tabRect.height + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(title, textX, textY);

        g2d.dispose();
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex,Rectangle iconRect, Rectangle textRect,boolean isSelected)
    {
        if (isSelected && tabPane.hasFocus()) 
        {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(new Color(100, 150, 255));
            g2d.setStroke(new BasicStroke(2));
            Rectangle tabRect = rects[tabIndex];
            g2d.drawRect(
                tabRect.x + 2, 
                tabRect.y + 2, 
                tabRect.width - 4, 
                tabRect.height - 4
            );
            g2d.dispose();
        }
    }
}