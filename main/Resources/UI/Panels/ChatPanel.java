package main.Resources.UI.Panels;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import main.Resources.UI.UIUtils;
import javax.swing.*;
import java.awt.*;


public class ChatPanel extends JPanel 
{

    private JTextArea chatArea;
    private JTextField messageField;

    public ChatPanel() 
    {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(new Color(50, 50, 50));
        
        initChatArea();
        initInputPanel();
        addWelcomeMessage();
    }

    private void initChatArea() 
    {
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(new Color(60, 60, 60));
        chatArea.setForeground(Color.WHITE);
        chatArea.setFont(new Font("Arial", Font.PLAIN, 14));
        
        JScrollPane scroll = new JScrollPane(chatArea);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);
    }

    private void initInputPanel() 
    {
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(new EmptyBorder(10, 0, 0, 0));
        inputPanel.setBackground(new Color(50, 50, 50));
        
        messageField = new JTextField();
        messageField.setBackground(new Color(70, 70, 70));
        messageField.setForeground(Color.WHITE);
        messageField.addActionListener(this::processMessage);
        
        JButton sendBtn = new JButton("Отправить");
        sendBtn.addActionListener(this::processMessage);
        UIUtils.styleButton(sendBtn);
        
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);
    }

    private void addWelcomeMessage() 
    {
        addMessage("Wendy", "Привет! Я Wendy, твой помощник. Чем могу помочь?", false);
    }

    private void processMessage(ActionEvent e) 
    {
        String msg = messageField.getText().trim();
        if (!msg.isEmpty()) 
        {
            addMessage("Вы", msg, true);
            messageField.setText("");
        }
    }

    private void addMessage(String sender, String text, boolean isUser) 
    {
        SwingUtilities.invokeLater(() -> {
            chatArea.append((isUser ? "[Вы]: " : "[" + sender + "]: ") + text + "\n\n");
            chatArea.setCaretPosition(chatArea.getDocument().getLength());
        });
    }
}