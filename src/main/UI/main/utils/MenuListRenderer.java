package src.main.UI.main.utils;

import javax.swing.DefaultListCellRenderer;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import java.awt.Color;
import java.awt.Font;

public class MenuListRenderer extends DefaultListCellRenderer
{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) 
    {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        label.setFont(new Font("Courier", Font.BOLD, 29));
        label.setBorder(new EmptyBorder(5, 18, 5, 18));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        label.setOpaque(true);
        
        if (isSelected) 
        {
            if (list.hasFocus()) 
            {
                label.setBackground(new Color(59, 30, 84));
                label.setForeground(Color.WHITE);
            } 
            else 
            {
                label.setBackground(new Color(59, 30, 84).darker());
                label.setForeground(new Color(150, 150, 150));
            }
        } 
        else 
        {
            label.setBackground(new Color(30, 30, 30));
            label.setForeground(new Color(225, 215, 198));
        }
        
        return label;
    }
}