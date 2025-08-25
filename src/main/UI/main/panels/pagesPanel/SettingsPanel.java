package src.main.UI.main.panels.pagesPanel;

import src.main.UI.main.panels.tabPanel.settings.WindowSettingsPanel;
import src.main.UI.main.panels.tabPanel.settings.AppsSettingsPanel;
import src.main.UI.main.components.UniversalTabPanel;

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