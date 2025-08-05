package src.main.ui.java;

import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.DefaultCaret;

import src.main.ui.java.components.CustomButton;
import javax.swing.*;
import java.awt.*;

public class UIUtils 
{
    public static CustomButton createSaveButton(String text, int width, int height)
    {
        CustomButton saveButton = new CustomButton(text, width, height);
        saveButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveButton.setPressedColor(new Color(55, 168, 78));
        saveButton.setHoverColor(new Color(50, 105, 60));
        saveButton.setCornerRadius(0);
        return saveButton;
    }

    public static void styleScrollPane(JScrollPane scrollPane) 
    {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        
        styleScrollBar(verticalScrollBar);
        styleScrollBar(horizontalScrollBar);
    }

    private static void styleScrollBar(JScrollBar scrollBar) 
    {
        scrollBar.setUnitIncrement(16); 
        scrollBar.setPreferredSize(new Dimension(10, 10)); 
        
        scrollBar.setUI(new BasicScrollBarUI() 
        {
            @Override
            protected void configureScrollBarColors() 
            {
                this.thumbColor = new Color(200, 200, 200); 
                this.trackColor = new Color(40, 40, 40);    
            }

        });
        
        scrollBar.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                scrollBar.setBackground(new Color(80, 80, 80));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                scrollBar.setBackground(new Color(40, 40, 40));
            }
        });
    }

    public static void styleTextArea(
        JTextArea textArea,
        Color caretColor,
        int blinkRate,
        int caretWidth,
        boolean highlightLine,
        Color highlightColor
    ) 
    {
        textArea.setCaretColor(caretColor != null ? caretColor : Color.RED);
        textArea.getCaret().setBlinkRate(blinkRate);

        textArea.setCaret(new DefaultCaret() 
        {
            @Override
            protected synchronized void damage(Rectangle r) {
                if (r == null) return;
                r.width = caretWidth > 0 ? caretWidth : 2; 
                super.damage(r);
            }
        });

        ((DefaultCaret) textArea.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        if (highlightLine) 
        {
            Color lineColor = highlightColor != null 
                ? highlightColor 
                : new Color(100, 100, 255, 50); 
            textArea.addCaretListener(e -> highlightCurrentLine(textArea, lineColor));
        }
    }

    private static void highlightCurrentLine(JTextArea textArea, Color color) 
    {
        try 
        {
            int caretPos = textArea.getCaretPosition();
            int line = textArea.getLineOfOffset(caretPos);
            textArea.setSelectionStart(textArea.getLineStartOffset(line));
            textArea.setSelectionEnd(textArea.getLineEndOffset(line));
            textArea.setSelectionColor(color);
        } 
        catch (Exception ignored) {}
    }
}