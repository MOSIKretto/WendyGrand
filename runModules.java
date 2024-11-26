import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class runModules 
{
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

    private static String sliceUntilPeriod(String inputString) 
    {
        int periodIndex = inputString.lastIndexOf('.');
        if (periodIndex != -1) 
        {
            return inputString.substring(periodIndex + 1);
        }
        else 
        {
            System.out.println("К сожалению, я не могу запустить ваш файл");
            return null;
        }
    }

    public static void run(String arg) 
    {
        String extension = sliceUntilPeriod(arg);
        String currentDirectory = "../WendyGrand/Modules/YourModules/";
        List<String> currentFolders = listFoldersInCurrentDirectory(currentDirectory);

        try 
        {
            if ("py".equals(extension)) 
            {
                if (currentFolders.contains("venv")) 
                {
                    String venvActivateScript = Paths.get(currentDirectory, "venv", "bin", "activate").toString();
                    String command = String.format("source %s && python3 %s", venvActivateScript, Paths.get(currentDirectory, arg).toString());
                    ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
                    pb.inheritIO();
                    pb.start().waitFor();
                } 
                else 
                {
                    ProcessBuilder pb = new ProcessBuilder("python3", Paths.get(currentDirectory, arg).toString());
                    pb.inheritIO();
                    pb.start().waitFor();
                }
            } 
            else if ("cpp".equals(extension) || "c".equals(extension)) 
            {
                String baseName = new File(arg).getName().replaceFirst("[.][^.]+$", "");
                String executablePath = Paths.get(currentDirectory, baseName).toString();
                ProcessBuilder compilePb = new ProcessBuilder("g++", Paths.get(currentDirectory, arg).toString(), "-o", executablePath);
                compilePb.inheritIO();
                compilePb.start().waitFor();

                ProcessBuilder runPb = new ProcessBuilder("./" + executablePath);
                runPb.inheritIO();
                runPb.start().waitFor();
            } 
            else if (extension != null) 
            {
                ProcessBuilder pb = new ProcessBuilder(extension, Paths.get(currentDirectory, arg).toString());
                pb.inheritIO();
                pb.start().waitFor();
            }
        } 
        catch (IOException | InterruptedException e){}
    }
}
