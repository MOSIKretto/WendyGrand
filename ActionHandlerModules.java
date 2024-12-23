import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.File;

public class ActionHandlerModules 
{
    //чтение Dictionary.txt
    public static void TXTreader(String word) throws IOException, InterruptedException
    {
        BufferedReader reader = new BufferedReader(new FileReader("../WendyGrand/Modules/Dictionary.txt"));
        String line;

        while ((line = reader.readLine()) != null) 
        {
            if (line.startsWith(word)){
                functionStart(line.split("=")[1]);}
        }

        reader.close();
    }

    //начало запуска модуля или предупреждение что его нет
    private static void functionStart(String function) throws IOException, InterruptedException
    {
        File Modules = new File("../WendyGrand/Modules/YourModules/");
        File[] files = Modules.listFiles();

        File ModulesCheck = new File(Modules, function);

        if (files != null && ModulesCheck.exists()) 
        {
            System.out.println("Активация модуля: " + function);
        
            VoiceoverScript("StandardModule_StandardResponse");
            runModules.run(function);
        }
        else{
            VoiceoverScript("ErrModule");}
    }

    //запуск озвучки
    private static void VoiceoverScript(String scriptPath) throws IOException{
        new ProcessBuilder("python3", "../WendyGrand/Voiceover.py", scriptPath).start();}
}