package Actions;

import java.io.IOException;

public class BrowserManager {
    public static void startBrowser() {
        String[] browsers = {"firefox"};
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        if (os.contains("nux")) {
            for (String browser : browsers) {
                try {
                    runtime.exec(browser + " ");
                } catch (IOException ignored) {

                }
            }
        }
    }
}
