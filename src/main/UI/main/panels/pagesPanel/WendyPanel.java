package src.main.UI.main.panels.pagesPanel;

import src.main.UI.main.utils.UIUtils;

import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Color;
import java.io.File;


public class WendyPanel extends JPanel 
{
    private static final String CONTENT_FILE = "../WendyGrand/src/main/UI/resources/content.html";
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
        try { editorPane.setPage(new File(CONTENT_FILE).toURI().toURL()); } 
        catch (Exception e) { editorPane.setText("<html><body><h1>Ошибка</h1><p>Ошибка загрузки</p></body></html>"); }
    }
    
}