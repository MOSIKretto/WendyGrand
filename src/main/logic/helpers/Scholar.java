package src.main.logic.helpers;

import java.util.*;

public class Scholar 
{
    public static String cleanInput(String input, Collection<String> words) 
    {
        if (input == null || input.isEmpty() || words == null || words.isEmpty()) return input;
        
        Set<String> toRemove = (words instanceof Set) ? (Set<String>) words : new HashSet<>(words);
        
        if (toRemove.isEmpty()) return input;
        
        char[] chars = input.toCharArray();
        StringBuilder result = new StringBuilder(chars.length);
        int wordStart = -1;
        boolean firstWord = true;
        
        for (int i = 0; i <= chars.length; i++) 
        {
            if (i == chars.length || chars[i] == ' ' || chars[i] == '\t') 
            {
                if (wordStart != -1) 
                {
                    String word = new String(chars, wordStart, i - wordStart);
                    if (!toRemove.contains(word)) 
                    {
                        if (!firstWord) result.append(' ');
                        result.append(chars, wordStart, i - wordStart);
                        firstWord = false;
                    }
                    wordStart = -1;
                }
            } 
            else if (wordStart == -1) wordStart = i;
        }
        
        return result.toString();
    }
}