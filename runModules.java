import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class runModules 
{
    //для Python на наличие venv
    private static List<String> listFoldersInCurrentDirectory(String path) 
    {
        List<String> folders = new ArrayList<>();
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) 
        {
            File[] items = directory.listFiles();
            if (items != null)
            {
                for (File item : items) 
                {
                    if (item.isDirectory())
                    {
                        folders.add(item.getName());
                    }
                }
            }
        }
        return folders;
    }

    //определение языка
    private static String sliceUntilPeriod(String inputString) 
    {
        int periodIndex = inputString.lastIndexOf('.');
        if (periodIndex != -1) 
        {
            return inputString.substring(periodIndex + 1);
        }
        else{return null;}
    }

    //запуск модуля
    private static void startModules(String... command) throws IOException, InterruptedException 
    {
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        pb.start();
    }

    //проверка на язык
    private static void executeModule(String extension, String arg, String currentDirectory) throws IOException, InterruptedException 
    {
        if ("py".equals(extension)) 
        {
            List<String> currentFolders = listFoldersInCurrentDirectory(currentDirectory);

            if (currentFolders.contains("venv")) 
            {
                String venvActivateScript = Paths.get(currentDirectory, "venv", "bin", "activate").toString();
                String command = String.format("source %s && python3 %s", venvActivateScript, Paths.get(currentDirectory, arg).toString());
                startModules("bash", "-c", command);
            } 
            else 
            {
                startModules("python3", Paths.get(currentDirectory, arg).toString());
            }
        } 
        else if ("cpp".equals(extension) || "c".equals(extension)) 
            {
                String baseName = new File(arg).getName().replaceFirst("[.][^.]+$", "");
                String executablePath = Paths.get(currentDirectory, baseName).toString();
                ProcessBuilder compilePb = new ProcessBuilder("g++", Paths.get(currentDirectory, arg).toString(), "-o", executablePath);
                compilePb.inheritIO();
                compilePb.start().waitFor();

                startModules("./" + executablePath);
            }
        else if ("go".equals(extension)) 
        {
            startModules("go", "run", Paths.get(currentDirectory, arg).toString());
        }
        else if (extension != null) 
        {
            startModules(extension, Paths.get(currentDirectory, arg).toString());
        }
        else if (new File(Paths.get(currentDirectory, arg).toString()).canExecute() && extension == null) 
        {   
            startModules("./" + Paths.get(currentDirectory, arg).toString());
        }
    }

    public static void run(String arg) throws IOException, InterruptedException
    {
        String extension = sliceUntilPeriod(arg);
        String currentDirectory = "../WendyGrand/Modules/YourModules/";

        executeModule(extension, arg, currentDirectory);
    }
}