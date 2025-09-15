package src.main.logic.helpers;

import java.util.Collection;


public class Scholar 
{
    public static String cleanInput(String input, Collection<String> words) 
    {
        if (input == null || words == null) return input;
        
        for (String word : words) input = input.replace(word, "").replace("  ", " ").trim();
        return input;
    }
}