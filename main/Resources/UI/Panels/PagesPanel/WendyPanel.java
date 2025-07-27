package main.Resources.UI.Panels.PagesPanel;

import javax.swing.border.EmptyBorder;
import main.Resources.UI.UIUtils;
import javax.swing.*;
import java.io.File;
import java.awt.*;



public class WendyPanel extends JPanel 
{

    private static final String CONTENT_FILE = "../WendyGrand/main/Resources/UI/content.html";
    private JEditorPane editorPane;
    
    public WendyPanel() 
    {   
        setBackground(new Color(50, 50, 50));
        
        initContent();  
        loadPage(); 
    }

    private void initContent() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0, 0, 0, 0));
        
        editorPane = new JEditorPane();
        editorPane.setBorder(new EmptyBorder(0, 0, 0, 0));
        editorPane.setContentType("text/html");
        editorPane.setEditable(false);

        JScrollPane scroll = new JScrollPane(editorPane);
        UIUtils.styleScrollPane(scroll);
        add(scroll, BorderLayout.CENTER);
    }

    private void loadPage()
    {
        try 
        {
            editorPane.setPage(new File(CONTENT_FILE).toURI().toURL());  
        } 
        catch (Exception e) 
        {
            editorPane.setText("<html><body><h1>Ошибка</h1><p>Ошибка загрузки</p></body></html>");
        }
    }
    
}