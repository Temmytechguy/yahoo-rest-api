package com.temmytech.yahoorestapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * @author TemmyTechie
 */

public class LambdaSample {

    public static void main(String[] args) {

      //  Predicate<String> test = s -> s.length() == 3;
        String result = "Ilesanmi";
        Consumer<String> print = s -> System.out.println(s);
        //print.accept(result);

        //List<String> output= retainStringsOfLength3(List.of(result));
      //  System.out.println(output);

        Map<Character,Integer> charCounts = countCharactersInWord(result);

        System.out.println(charCounts);

    }

    static Map<Character, Integer> countCharactersInWord(String input)
    {
        Map<Character, Integer> result = new HashMap<>();
        for(int i = 0; i < input.length();i++) {
            char eachChar = input.charAt(i);
            if(Character.isLetter(eachChar)) {
                char c = Character.toLowerCase(eachChar);{
                    result.put(c, result.getOrDefault(c, 0)+1);
                }
            }
        }
        return result;
    }

   static List<String> retainStringsOfLength3(List<String> strings) {
        Predicate<String> predicate = s -> s.length() > 3;
        List<String> stringsOfLength3 = new ArrayList<>();
        for (String s: strings) {
            if (predicate.test(s)) {
                stringsOfLength3.add(s);}
        }
        return stringsOfLength3;
    }

}
