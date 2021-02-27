package datastructures.util.function;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class RunFunction {
    public static void main(String[] args) {
        Function<Integer, String> intoString = Objects::toString;
        Function<String, String> quote = s -> "'" + s + "'";
        Function<Integer, String> quoteIntoString = quote.compose(intoString);
        System.out.println(quoteIntoString.apply(5));
        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("john", 40);
        salaries.put("mark", 30);
        salaries.put("jeff", 50);
        salaries.replaceAll((name, oldValue) -> name.equals("jeff") ? oldValue : oldValue + 10);
        for (String s : salaries.keySet()) {
            System.out.println(s + "->" + salaries.get(s));
        }
    }
}
