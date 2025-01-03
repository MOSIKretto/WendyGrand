import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class runModules 
{
    // Начало активации модуля
    public static void run(String arg) throws IOException, InterruptedException
    {
        executeModule(
            sliceUntilPeriod(arg), arg, "../WendyGrand/Modules/YourModules/");
    }

    // Определение языка
    private static String sliceUntilPeriod(String inputString) 
    {
        int periodIndex = inputString.lastIndexOf('.');

        if (periodIndex != -1){
            return inputString.substring(periodIndex + 1);}
        else{
            return null;}
    }

    // Проверка на язык
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
            else{
                startModules("python3", Paths.get(currentDirectory, arg).toString());}
        }
        //--------------------------------------------------------------------------------------------------------------

        else if ("cpp".equals(extension) || "c".equals(extension)) 
        {
            String baseName = new File(arg).getName().replaceFirst("[.][^.]+$", "");
            String executablePath = Paths.get(currentDirectory, baseName).toString();

            new ProcessBuilder("g++", Paths.get(currentDirectory, arg).toString(), "-o", executablePath)
            .start()
            .waitFor();

            startModules("./" + executablePath);
        }
        //--------------------------------------------------------------------------------------------------------------

        else if ("rs".equals(extension)) 
        {
            String baseName = new File(arg).getName().replaceFirst("[.][^.]+$", "");
            String executablePath = Paths.get(currentDirectory, baseName).toString();

            new ProcessBuilder("rustc", Paths.get(currentDirectory, arg).toString(), "-o", executablePath)
            .start()
            .waitFor();

            startModules("./" + executablePath);
        }
        //--------------------------------------------------------------------------------------------------------------

        else if ("js".equals(extension)) 
        {
            if (checkingTheInterpreterForJS("node")) 
            {
                System.out.println("Запуск через Node.js...");
                startModules("node", Paths.get(currentDirectory, arg).toString());
            } 
            else if (checkingTheInterpreterForJS("deno")) 
            {
                System.out.println("Запуск через Deno...");
                startModules("deno", "run", Paths.get(currentDirectory, arg).toString());
            }
            else{
                System.err.println("Не удалось запустить файл. Убедитесь, что установлен Node.js или Deno.");}
        }
        //--------------------------------------------------------------------------------------------------------------

        else if ("go".equals(extension)){
            startModules("go", "run", Paths.get(currentDirectory, arg).toString());}
        //--------------------------------------------------------------------------------------------------------------

        else if ("rb".equals(extension)){
            startModules("ruby", Paths.get(currentDirectory, arg).toString());}
        //--------------------------------------------------------------------------------------------------------------

        else if (extension != null){
            startModules(extension, Paths.get(currentDirectory, arg).toString());}
        //--------------------------------------------------------------------------------------------------------------

        else if (new File(Paths.get(currentDirectory, arg).toString()).canExecute() && extension == null){
            startModules("./" + Paths.get(currentDirectory, arg).toString());}
        //--------------------------------------------------------------------------------------------------------------
        else{
            //Озвучка отсутствия технологии запуска (Добавиться с появлением Voiceover.java)
        }
        //--------------------------------------------------------------------------------------------------------------
    }

    // Для Python на наличие venv
    private static List<String> listFoldersInCurrentDirectory(String path) 
    {
        List<String> folders = new ArrayList<>();
        File directory = new File(path);

        if (directory.exists() && directory.isDirectory()) 
        {
            File[] items = directory.listFiles(File::isDirectory);

            if (items != null) 
            {
                for (File item : items){
                    folders.add(item.getName());}
            }
        }

        return folders;
    }

    // Проверка интерпритатора для JS
    private static boolean checkingTheInterpreterForJS(String command) 
    {
        try 
        {
            Process process = new ProcessBuilder("command", "-v", command).start();

            process.waitFor(1, TimeUnit.SECONDS);
            
            return process.exitValue() == 0;
        } 
        catch (IOException | InterruptedException e){
            return false;}
    }

    // Запуск модуля
    private static void startModules(String... command) throws IOException, InterruptedException
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }
}