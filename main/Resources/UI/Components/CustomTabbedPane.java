package main.Resources.UI.Components;


import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class CustomTabbedPane extends BasicTabbedPaneUI {
    // Цвета вкладок
    private final Color selectedColor = new Color(59, 30, 84);
    private final Color selectedBorderColor = new Color(100, 65, 120);
    private final Color unselectedColor = new Color(50, 50, 50);
    private final Color unselectedBorderColor = new Color(80, 80, 80);
    
    // Цвета панели вкладок
    private final Color tabAreaBackground = new Color(35, 35, 35);
    private final Color tabAreaBorder = new Color(70, 70, 70);
    
    // Размеры
    private final int TAB_HEIGHT = 40;
    private final int BORDER_THICKNESS = 2;
    private final Font tabFont = new Font("Segoe UI", Font.PLAIN, 14);

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabAreaInsets = new Insets(0, 0, 0, 0);
        contentBorderInsets = new Insets(0, 0, 0, 0);
        tabInsets = new Insets(8, 25, 8, 25);
        tabPane.setFont(tabFont);
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        // Заливка фона
        g2d.setColor(tabAreaBackground);
        g2d.fillRect(0, 0, tabPane.getWidth(), calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight));
        
        // Граница снизу
        g2d.setColor(tabAreaBorder);
        g2d.setStroke(new BasicStroke(1));
        int y = calculateTabAreaHeight(tabPlacement, runCount, maxTabHeight) - 1;
        g2d.drawLine(0, y, tabPane.getWidth(), y);
        
        g2d.dispose();
        
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        return TAB_HEIGHT;
    }

    @Override
    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects, 
                          int tabIndex, Rectangle iconRect, Rectangle textRect) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        Rectangle tabRect = rects[tabIndex];
        boolean isSelected = tabPane.getSelectedIndex() == tabIndex;

        // Основная заливка вкладки (с учетом обводки)
        g2d.setColor(isSelected ? selectedColor : unselectedColor);
        g2d.fillRect(
            tabRect.x + BORDER_THICKNESS, 
            tabRect.y + BORDER_THICKNESS, 
            tabRect.width - BORDER_THICKNESS*2, 
            tabRect.height - BORDER_THICKNESS*2
        );

        // Полная обводка со всех сторон
        g2d.setColor(isSelected ? selectedBorderColor : unselectedBorderColor);
        g2d.setStroke(new BasicStroke(BORDER_THICKNESS));
        g2d.drawRect(
            tabRect.x + BORDER_THICKNESS/2, 
            tabRect.y + BORDER_THICKNESS/2, 
            tabRect.width - BORDER_THICKNESS, 
            tabRect.height - BORDER_THICKNESS
        );

        // Текст
        g2d.setColor(Color.WHITE);
        String title = tabPane.getTitleAt(tabIndex);
        FontMetrics fm = g2d.getFontMetrics();
        int textX = tabRect.x + (tabRect.width - fm.stringWidth(title)) / 2;
        int textY = tabRect.y + (tabRect.height + fm.getAscent() - fm.getDescent()) / 2;
        g2d.drawString(title, textX, textY);

        g2d.dispose();
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, 
                               int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        // Не используется, рисуем обводку в paintTab
    }

    @Override
    protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
        // Без границы контента
    }
    
    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement,
                                    Rectangle[] rects, int tabIndex,
                                    Rectangle iconRect, Rectangle textRect,
                                    boolean isSelected) {
        // Без индикатора фокуса
    }
}