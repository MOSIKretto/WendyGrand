package main.Resources.Managers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.Resources.GeneralHelper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.File;


public class RunModuleManager 
{
    // Начало активации модуля
    public static void run(String arg) throws 
    InterruptedException, 
    IOException
    {
        executeModule(sliceUntilPeriod(arg), arg, "../WendyGrand/Modules/");
    }

    // Определение языка по расширению файла
    private static String sliceUntilPeriod(String inputString) 
    {
        int periodIndex = inputString.lastIndexOf('.');
        return periodIndex != -1 ? inputString.substring(periodIndex + 1) : "";
    }

    // Проверка на язык и выполнение соответствующего модуля
    private static void executeModule(String extension, String arg, String currentDirectory) throws 
    InterruptedException, 
    IOException
    {
        String modulePath = Paths.get(currentDirectory, arg).toString();

        switch (extension) 
        {
            case "py":
                handlePythonModule(modulePath, currentDirectory);
                break;

            case "":
                startModules("./" + modulePath);
                break;
            
            default:
                startModules(extension, modulePath);
                break;
        }
    }

    
    // Python-специфичные методы --------------------------------------------------------
    
    private static void handlePythonModule(String modulePath, String currentDirectory) throws 
    InterruptedException, 
    IOException 
    {
        String venvPath = setupVenv(currentDirectory);
        String pipPath = Paths.get(venvPath, "bin", "pip").toString();
        
        // Обновляем pip перед установкой библиотек
        upgradePip(pipPath);
        
        installRequiredLibraries(modulePath, pipPath);
        GeneralHelper.Performer(Paths.get(venvPath, "bin", "python").toString(), modulePath);
    }

    private static void upgradePip(String pipPath) throws 
    InterruptedException,
    IOException
    {
        Process process = new ProcessBuilder(pipPath, "install", "--upgrade", "pip")
        .inheritIO()
        .start();
                
        if (process.waitFor() != 0)
            System.err.println("Предупреждение: не удалось обновить pip. Продолжение работы...");
    }

    private static String setupVenv(String directory) throws InterruptedException, IOException 
    {
        String venvPath = Paths.get(directory, "venv").toString();
        File venvDir = new File(venvPath);
        
        if (!venvDir.exists()) 
        {
            System.out.println("Создание виртуального окружения...");
            Process process = new ProcessBuilder("python3", "-m", "venv", "venv")
            .directory(new File(directory))
            .inheritIO()
            .start();
            
            if (process.waitFor() != 0)
                throw new IOException("Не удалось создать виртуальное окружение");
        }
        return venvPath;
    }

    private static void installRequiredLibraries(String modulePath, String pipPath) throws 
    InterruptedException, 
    IOException 
    {
        List<String> libraries = extractLibsFromModule(modulePath);
        if (libraries.isEmpty()) return;

        System.out.println("Обнаружены зависимости: " + libraries);

        for (String lib : libraries) 
        {
            if (!isLibraryInstalled(pipPath, lib)) 
            {
                System.out.println("Установка: " + lib);
                Process process = new ProcessBuilder(pipPath, "install", lib)
                .inheritIO()
                .start();
                        
                if (process.waitFor() != 0)
                    throw new IOException("Ошибка установки библиотеки: " + lib);
            }
        }
    }

    private static List<String> extractLibsFromModule(String modulePath) throws 
    IOException 
    {
        String content = Files.readString(Paths.get(modulePath));
        Pattern pattern = Pattern.compile("(?i)(LIBS_FOR_WENDY|libs_for_wendy|LFW)\\s*=\\s*[\"']{3}([\\s\\S]*?)[\"']{3}");
        Matcher matcher = pattern.matcher(content);

        return matcher.find() ? Arrays.asList(matcher.group(2).trim().split(",\\s*")) : List.of();
    }
    
    private static boolean isLibraryInstalled(String pipPath, String library) throws 
    InterruptedException,
    IOException
    {
        return new ProcessBuilder(pipPath, "show", library)
        .redirectOutput(ProcessBuilder.Redirect.DISCARD)
        .redirectError(ProcessBuilder.Redirect.DISCARD)
        .start()
        .waitFor() == 0;
    }


    // Запуск модуля --------------------------------------------------------------------------

    private static void startModules(String... command) throws 
    InterruptedException, 
    IOException
    {
        new ProcessBuilder(command)
        .inheritIO()
        .start();
    }
}