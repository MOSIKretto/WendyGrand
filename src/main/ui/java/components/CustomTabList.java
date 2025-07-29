package src.main.ui.java.components;

import src.main.ui.java.WindowMaker;
import main.Resources.UI.Renders.TabListRenderer;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class CustomTabList extends JPanel {
    public final JList<String> tabList;
    private final JPanel contentPanel;
    private final WindowMaker frame;
    private final DefaultListModel<String> listModel;
    private final List<Component> tabContents = new ArrayList<>();
    private int lastSelectedIndex = -1;
    private boolean isActive = false;

    public CustomTabList(WindowMaker frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        setOpaque(false);
        
        // Инициализация модели списка
        listModel = new DefaultListModel<>();
        
        // Настройка списка вкладок
        tabList = new JList<>(listModel);
        tabList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        tabList.setVisibleRowCount(1);
        tabList.setFixedCellHeight(40);
        tabList.setFixedCellWidth(200);
        tabList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabList.setBackground(new Color(35, 35, 35));
        tabList.setForeground(new Color(150, 150, 150));
        tabList.setCellRenderer(new TabListRenderer());
        
        // Панель для содержимого
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(50, 50, 50));
        contentPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        // Добавляем компоненты
        add(tabList, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
        
        // Обработка выбора вкладки
        tabList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int index = tabList.getSelectedIndex();
                if (index != -1 && index != lastSelectedIndex && isActive) {
                    showTabContent(index);
                }
            }
        });
        
        // Навигация с клавиатуры
        tabList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }
        });
        
        // Обработка клика мышью
        tabList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int index = tabList.locationToIndex(evt.getPoint());
                if (index != -1 && isActive) {
                    showTabContent(index);
                }
            }
        });
    }
    
    public void addTab(String title, Component component) {
        listModel.addElement(title);
        tabContents.add(component);
    }
    
    public void showTabContent(int index) {
        if (index < 0 || index >= tabContents.size()) return;
        
        // Обновляем контент
        contentPanel.removeAll();
        contentPanel.add(tabContents.get(index), BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
        
        lastSelectedIndex = index;
    }
    
    public void setSelectedIndex(int index) {
        if (index >= 0 && index < listModel.size()) {
            tabList.setSelectedIndex(index);
            tabList.ensureIndexIsVisible(index);
            if (isActive) {
                showTabContent(index);
            }
        }
    }
    
    public void activate() {
        isActive = true;
        tabList.setBackground(new Color(35, 35, 35));
        tabList.setForeground(new Color(200, 200, 200));
        
        if (tabList.getSelectedIndex() != -1) {
            showTabContent(tabList.getSelectedIndex());
        } else if (listModel.size() > 0) {
            setSelectedIndex(0);
        }
        
        // Перерисовываем компонент
        revalidate();
        repaint();
    }

    public void deactivate() {
        isActive = false;
        contentPanel.removeAll();
        contentPanel.revalidate();
        contentPanel.repaint();
        tabList.setBackground(new Color(35, 35, 35));
        tabList.setForeground(new Color(100, 100, 100));
        tabList.clearSelection();
        lastSelectedIndex = -1;
        
        // Перерисовываем компонент
        revalidate();
        repaint();
    }
    
     private void handleKeyPress(KeyEvent e) {
        int keyCode = e.getKeyCode();
        int current = tabList.getSelectedIndex();
        int count = listModel.size();
        
        if (count == 0) return;
        
        if (keyCode == KeyEvent.VK_RIGHT) {
            int next = (current == -1) ? 0 : (current + 1) % count;
            setSelectedIndex(next);
            e.consume();
        } 
        else if (keyCode == KeyEvent.VK_LEFT) {
            if (current == 0) {
                String title = listModel.getElementAt(0);
                if ("Окно".equals(title) || "Основной словарь".equals(title)) {
                    frame.focusOnMainMenu();
                    e.consume();
                    return;
                }
            }
            
            int prev = (current == -1) ? count - 1 : (current - 1 + count) % count;
            setSelectedIndex(prev);
            e.consume();
        }
        else if (keyCode == KeyEvent.VK_ENTER) {
            if (current != -1) {
                showTabContent(current);
                e.consume();
            }
        }
    }
    
    public void requestTabFocus() {
        tabList.requestFocusInWindow();
    }
    
    public int getSelectedIndex() {
        return tabList.getSelectedIndex();
    }
    
    public boolean isActive() {
        return isActive;
    }
}