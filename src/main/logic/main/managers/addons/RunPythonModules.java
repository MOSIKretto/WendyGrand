package src.main.logic.main.managers.addons;

import src.main.logic.helpers.Performer;
import src.main.voiceover.main.Voiceover;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.io.File;


public class RunPythonModules 
{
    public static void runPythonModule(String modulePath, String currentDirectory) throws 
    Exception 
    {
        String venvPath = setupVenv(currentDirectory);
        String pipPath = Paths.get(venvPath, "bin", "pip").toString();
        
        upgradePip(pipPath);
        installRequiredLibraries(modulePath, pipPath);
        Performer.execute(Paths.get(venvPath, "bin", "python").toString(), modulePath);
    }
    
    private static void upgradePip(String pipPath) throws 
    Exception 
    {
        Process process = new ProcessBuilder(pipPath, "install", "--upgrade", "pip").inheritIO().start();
        if (process.waitFor() != 0) Voiceover.startVoice("upgradepipERR");
    }
    
    private static String setupVenv(String directory) throws 
    Exception 
    {
        String venvPath = Paths.get(directory, "venv").toString();
        File venvDir = new File(venvPath);
        
        if (!venvDir.exists()) 
        {
            Process process = new ProcessBuilder("python3", "-m", "venv", "venv").directory(new File(directory)).inheritIO().start();
            if (process.waitFor() != 0) 
            {
                Voiceover.startVoice("venvcreateERR");
                throw new IOException("Не удалось создать виртуальное окружение");
            }
        }
        return venvPath;
    }
    
    private static void installRequiredLibraries(String modulePath, String pipPath) throws 
    Exception 
    {
        List<String> libraries = extractLibsFromModule(modulePath);
        if (libraries.isEmpty()) return;
        
        Voiceover.startVoice("dependenciesfound");
        
        for (String lib : libraries) 
        {
            if (!isLibraryInstalled(pipPath, lib)) 
            {
                Process process = new ProcessBuilder(pipPath, "install", lib).inheritIO().start();
                if (process.waitFor() != 0) 
                {
                    Voiceover.startVoice("installlibsERR ");
                    throw new IOException("Ошибка установки библиотеки: " + lib);
                }
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
    Exception 
    {
        return new ProcessBuilder(pipPath, "show", library)
            .redirectOutput(ProcessBuilder.Redirect.DISCARD)
            .redirectError(ProcessBuilder.Redirect.DISCARD)
            .start()
            .waitFor() == 0;
    }
}