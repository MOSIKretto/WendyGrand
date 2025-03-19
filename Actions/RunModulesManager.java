package Actions;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.File;


public class RunModulesManager 
{
    // Начало активации модуля
    public static void run(String arg) throws IOException, InterruptedException 
    {
        executeModule(
            sliceUntilPeriod(arg), arg, "../WendyGrand/Modules/");
    }

    // Определение языка по расширению файла
    private static String sliceUntilPeriod(String inputString) 
    {
        int periodIndex = inputString.lastIndexOf('.');
        return periodIndex != -1 ? inputString.substring(periodIndex + 1) : "";
    }

    // Проверка на язык и выполнение соответствующего модуля
    private static void executeModule(String extension, String arg, String currentDirectory) throws IOException, InterruptedException 
    {
        String modulePath = Paths.get(currentDirectory, arg).toString();

        switch (extension) 
        {
            case "py":
                handlePythonModule(modulePath, currentDirectory);
                break;

            case "cpp": case "c":
                handleRCModule("g++", modulePath, currentDirectory);
                break;

            case "rs":
                handleRCModule("rustc", modulePath, currentDirectory);
                break;

            case "js":
                handleJavaScriptModule(modulePath, currentDirectory);
                break;

            case "go":
                startModules("go", "run", modulePath);
                break;

            case "rb":
                startModules("ruby", modulePath);
                break;

            case "":
                startModules("./" + modulePath);
                break;
            
            default:
                startModules(extension, modulePath);
                break;
        }
    }

    // Запуск модуля
    private static void startModules(String... command) throws IOException, InterruptedException 
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }



    //ФУНКЦИИ ДЛЯ ЯЗЫКОВ


    //ОБРАБОТЧИКИ ----------------------------------------------------------------------------------------------------------------------

    // Обработка Python модуля
    private static void handlePythonModule(String modulePath, String currentDirectory) throws IOException, InterruptedException 
    {
        String venvPath = Paths.get(currentDirectory, "venv").toString();
        File venvDir = new File(venvPath);

        // Создание venv, если его нет
        if (!venvDir.exists()) 
        {
            System.out.println("Виртуальное окружение не найдено. Создание...");
            new ProcessBuilder("python3", "-m", "venv", "venv")
            .directory(new File(currentDirectory))
            .start()
            .waitFor();
        }

        // Чтение модуля и поиск библиотек
        List<String> libraries = extractLibFromPy(modulePath);
        if (!libraries.isEmpty()) 
        {
            System.out.println("Найдены библиотеки для установки: " + libraries);
            installLibsForPy(venvPath, libraries);
        }

        // Запуск модуля через venv
        String pythonExecutable = Paths.get(venvPath, "bin", "python").toString();
        startModules(pythonExecutable, modulePath);
    }

    // Обработка Rust, C и C++ модуля
    private static void handleRCModule(String compiler, String modulePath, String currentDirectory) throws IOException, InterruptedException 
    {
        String baseName = new File(modulePath).getName().replaceFirst("[.][^.]+$", "");
        String executablePath = Paths.get(currentDirectory, baseName).toString();

        new ProcessBuilder(compiler, modulePath, "-o", executablePath)
        .start()
        .waitFor();

        startModules("./" + executablePath);
    }

    // Обработка JavaScript модуля
    private static void handleJavaScriptModule(String modulePath, String currentDirectory) throws IOException, InterruptedException 
    {
        if (checkingTheInterpreter("node")) 
        {
            System.out.println("Запуск через Node.js...");
            startModules("node", modulePath);
        } 
        else if (checkingTheInterpreter("deno")) 
        {
            System.out.println("Запуск через Deno...");
            startModules("deno", "run", modulePath);
        } 
        else System.err.println("Не удалось запустить файл. Убедитесь, что установлен Node.js или Deno.");
    }
    //----------------------------------------------------------------------------------------------------------------------------------


    //УЛУЧШЕНИЕ РАБОТЫ С ЯЗЫКАМИ -------------------------------------------------------------------------------------------------------

    // Чтение модуля и извлечение библиотек из переменной (LIBS_FOR_WENDY, libs_for_wendy, LFW и т.д.)
    private static List<String> extractLibFromPy(String modulePath) throws IOException 
    {
        String content = new String(Files.readAllBytes(Paths.get(modulePath)));
        Pattern pattern = Pattern.compile(
            "(?i)(LIBS_FOR_WENDY|libs_for_wendy|Libs_For_Wendy|LFW|lfw|L_F_W|l_f_w)\\s*=\\s*\"\"\"([\\s\\S]*?)\"\"\"");
        Matcher matcher = pattern.matcher(content);

        if (matcher.find()) 
        {
            String libsString = matcher.group(2).trim();
            return Arrays.asList(libsString.split(",\\s*"));
        }

        return List.of();
    }

    // Установка библиотек через pip в venv (с проверкой наличия)
    private static void installLibsForPy(String venvPath, List<String> libraries) throws IOException, InterruptedException 
    {
        String pipExecutable = Paths.get(venvPath, "bin", "pip").toString();

        for (String lib : libraries) 
        {
            if (isLibInstalledInVenv(venvPath, lib)) continue;
            
            else 
            {
                System.out.println("Установка библиотеки: " + lib);
                int exitCode = new ProcessBuilder(pipExecutable, "install", lib)
                .inheritIO()
                .start()
                .waitFor();

                if (exitCode != 0) throw new IOException("Не удалось установить библиотеку: " + lib);
            }
        }

        System.out.println("Все библиотеки присутствуют.");
    }

    // Проверка, установлена ли библиотека в venv
    private static boolean isLibInstalledInVenv(String venvPath, String library) throws IOException, InterruptedException 
    {
        String pipExecutable = Paths.get(venvPath, "bin", "pip").toString();

        int exitCode = new ProcessBuilder(pipExecutable, "show", library)
        .redirectErrorStream(true)
        .start()
        .waitFor();

        return exitCode == 0;
    }

    // Проверка на наличие интерпретатора
    private static boolean checkingTheInterpreter(String command) 
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
    //----------------------------------------------------------------------------------------------------------------------------------
}