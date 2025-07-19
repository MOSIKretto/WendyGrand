package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import main.Resources.UI.UIUtils;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import javax.swing.*;
import java.awt.*;

public class VoiceOverDict extends JPanel 
{
    
    private JTextArea voiceOverDictArea;
    private static final String VOICEOVER_DICT_CONFIG = "../WendyGrand/Configs/Voiceover.conf";

    public VoiceOverDict() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initUI();
        loadVoiceOverDict();
    }

    private void initUI() 
    {
        voiceOverDictArea = new JTextArea();
        voiceOverDictArea.setBackground(new Color(60, 60, 60));
        voiceOverDictArea.setForeground(Color.WHITE);

        JScrollPane scroll = new JScrollPane(voiceOverDictArea);
        JButton saveBtn = new JButton("Сохранить словарь");

        saveBtn.addActionListener(this::saveDictionary);
        
        UIUtils.styleScrollPane(scroll);
        UIUtils.styleButton(saveBtn);
        
        add(scroll, BorderLayout.CENTER);
        add(saveBtn, BorderLayout.SOUTH);
    }

    private void loadVoiceOverDict() 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(VOICEOVER_DICT_CONFIG))) 
        {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null)
                content.append(line).append("\n");

            voiceOverDictArea.setText(content.toString());
        } 
        catch (IOException e) { voiceOverDictArea.setText("Ошибка загрузки: " + e.getMessage()); }
    }

    private void saveDictionary(ActionEvent e) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VOICEOVER_DICT_CONFIG))) 
        {
            writer.write(voiceOverDictArea.getText());
            JOptionPane.showMessageDialog(this, "Словарь сохранен");
        } 
        catch (IOException ex) { JOptionPane.showMessageDialog(this, "Ошибка сохранения: " + ex.getMessage()); }
    }
}
