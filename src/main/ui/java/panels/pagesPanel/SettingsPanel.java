package src.main.ui.java.panels.pagesPanel;

import src.main.ui.java.panels.tabPanel.settings.WindowSettingsPanel;
import src.main.ui.java.panels.tabPanel.settings.AppsSettingsPanel;
import src.main.ui.java.components.UniversalTabPanel;

public class SettingsPanel extends UniversalTabPanel
{
    public SettingsPanel()
    {
        super();

        addTab("Окно", new WindowSettingsPanel());
        addTab("Приложения", new AppsSettingsPanel());

        setDefaultSelectedTab(0);
    }
}