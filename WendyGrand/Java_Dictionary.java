/* *Java_Dictionary*
 *
 * RU Ищет соответствие по словарям и отдает команду на выполнение 
 * ---------------------------------------------------------------------
 * EN Looks for a match in dictionaries and issues a command to execute
 * 
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import static java.util.Map.entry;

public class Java_Dictionary 
{
    public static void main(String[] args)
    {
        for (String arg : args) 
        {

            /*
            * RU Словари с командами 
            * EU Dictionaries with commands
            */
            Map <String, String> FunctionsDictionary = Map.ofEntries(entry("Browser", "CallBrowser"));
            ArrayList <String> Hello = new ArrayList<String>(Arrays.asList("венди привет", "венди здравствуй", "венди ты тут?"));
            ArrayList <String> HowYou = new ArrayList<String>(Arrays.asList("венди как дела", "венди как ты"));
            ArrayList <String> Browsers = new ArrayList<String>(Arrays.asList("венди открой браузер", "венди браузер", "венди интернет", "венди открой интернет"));

            /*
            * RU Проверка на соответсвие и отдача команды на выполнение задачи 
            * EU Checking for compliance and issuing a command to complete the task
            */
            if (Hello.contains(arg))
            {
                System.out.println("Привет, User!");
            }
            //--------------------------------------------------------------------------------------------------------------
            if (HowYou.contains(arg))
            {
                System.out.println("Хорошо! Надеюсь у вас еще лучше, User!");
            }
            //--------------------------------------------------------------------------------------------------------------
            if (Browsers.contains(arg))
            {
                ActionsHandler.CallFunction(FunctionsDictionary.get("Browser"));
            }
            //--------------------------------------------------------------------------------------------------------------
            //if..
        }
    }
}
