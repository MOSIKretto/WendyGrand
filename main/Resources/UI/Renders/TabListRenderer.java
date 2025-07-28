package main.Resources.UI.Renders;

import main.Resources.UI.Components.CustomTabList;
import javax.swing.*;
import java.awt.*;

public class TabListRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                 boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(
            list, value, index, isSelected, cellHasFocus
        );
        
        // Получаем родительский контейнер вкладок
        Container parent = list.getParent();
        if (parent == null) return label;
        
        CustomTabList tabList = null;
        while (parent != null) {
            if (parent instanceof CustomTabList) {
                tabList = (CustomTabList) parent;
                break;
            }
            parent = parent.getParent();
        }
        
        boolean isActive = tabList != null && tabList.isActive();
        
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        if (isSelected && isActive) {
            label.setBackground(new Color(70, 130, 180));
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(isActive ? new Color(60, 60, 60) : new Color(40, 40, 40));
            label.setForeground(isActive ? new Color(200, 200, 200) : new Color(100, 100, 100));
        }
        
        return label;
    }
}