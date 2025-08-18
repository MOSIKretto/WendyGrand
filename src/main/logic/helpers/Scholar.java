package src.main.logic.helpers;

import java.util.stream.Collectors;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;


public class Scholar
{
    // очищение текста
    public static String cleanInput(String input, Collection<String> words) 
    {
        Set<String> toRemove = words instanceof Set ? (Set<String>)words : new HashSet<>(words);
        return Arrays.stream(input.split("\\s+"))
        .filter(word -> !toRemove.contains(word))
        .collect(Collectors.joining(" "));
    }
}