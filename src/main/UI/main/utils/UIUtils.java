package src.main.UI.main.utils;

import src.main.UI.main.components.CustomButton;

import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.event.ActionListener;
import javax.swing.text.DefaultCaret;
import javax.swing.BorderFactory;
import java.awt.RenderingHints;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JTextArea;
import java.awt.BasicStroke;
import javax.swing.JButton;
import java.awt.Graphics2D;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Cursor;
import java.awt.Color;
import java.awt.Font;

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

    public static JButton createControlButton(String type, ActionListener action) 
    {
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) 
            {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (getModel().isRollover()) 
                {
                    g2.setColor(type.equals("close") ? new Color(232, 17, 35, 150) : new Color(100, 100, 100, 100));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 5, 5);
                }
                
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(2f));
                
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                
                if (type.equals("minimize")) 
                    g2.drawLine(centerX - 6, centerY, centerX + 6, centerY);
                else 
                {
                    g2.drawLine(centerX - 5, centerY - 5, centerX + 5, centerY + 5);
                    g2.drawLine(centerX + 5, centerY - 5, centerX - 5, centerY + 5);
                }
                g2.dispose();
            }
        };
        
        btn.addActionListener(action);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(30, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        return btn;
    }

    public static void styleScrollPane(JScrollPane scrollPane) 
    {
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        
        styleScrollBar(verticalScrollBar);
        styleScrollBar(horizontalScrollBar);
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
            textArea.addCaretListener(_ -> highlightCurrentLine(textArea, lineColor));
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
}