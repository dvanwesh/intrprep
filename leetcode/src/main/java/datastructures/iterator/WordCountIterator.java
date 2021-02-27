package datastructures.iterator;

import datastructures.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/*Please use this Google doc during your interview (your interviewer will see what you write here). To free your hands for typing, we recommend using a headset or speakerphone.


    Given an Iterator<String>, code an Iterator<Pair<String,Integer>> which returns a word from the original iterator and the amount of times it appears in a row in the original iterator.

    Original iterator: { foo, foo, foo, bar, foo, bar, bar, xyz, ... }
    Custom iterator: { {foo, 3}, {bar, 1}, {foo, 1}, {bar, 2}, ... }

    req:
    example: i/p: { tim, tim, zack, henry, tim, tim, henry, henry,...}
    o/P; {{tim, 2}, {zack, 1}, {henry, 1},{tim, 2}, {henry, 2}...}

    input: foo, foo, bar
    foo, foo c=1, foo c=2, bar c=3, foo c=1, bar c= 1
                    {foo, 3} {bar, 1}
*/
public class WordCountIterator {
    String current = null;
    Iterator<String> it;
    boolean hasNext = true;

    public WordCountIterator(Iterator<String> it) {
        this.it = it;
    }

    public Pair<String, Integer> next() {
        if (!hasNext()) {
            throw new UnsupportedOperationException();
        }
        int count = 1;
        current = current == null ? it.next() : current;
        String ret = current;
        while (it.hasNext() && current == ret) {
            current = it.next();
            count++;
        }
        if (!it.hasNext() && current == ret) {
            hasNext = false;
        } else {
            count--;
        }
        Pair<String, Integer> res = new Pair(ret, count);
        return res;
    }


    public boolean hasNext() {
        return hasNext;
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("foo", "foo", "foo",
            "bar", "foo", "bar", "bar", "xyz"));
        List<String> list2 = new ArrayList<>(Arrays.asList("tim", "tim", "zack", "henry",
            "tim", "tim", "henry", "henry"));
        WordCountIterator sol = new WordCountIterator(list.iterator());
        while (sol.hasNext()) {
            System.out.println(sol.next());
        }
        for (int i = 0; i < 4; i++)
            System.out.println(sol.hasNext());
    }

}

