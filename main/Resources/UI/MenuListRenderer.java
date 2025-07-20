package main.Resources.UI;

import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;


public class MenuListRenderer extends DefaultListCellRenderer 
{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        label.setFont(new Font("Arial", Font.BOLD, 18));

        label.setBorder(new EmptyBorder(5, 10, 5, 10));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setOpaque(true);
        
        if (isSelected) 
        {
            label.setBackground(new Color(59, 30, 84));
            label.setForeground(Color.WHITE);
        } 
        else 
        {
            label.setBackground(new Color(30, 30, 30));
            label.setForeground(new Color(225, 215, 198));
        }
        
        return label;
    }
}