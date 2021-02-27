package leetcode.discussions;
/*
 * Click `Run` to execute the snippet below!
 */

import java.util.*;

/*
 Design a data structure for History Set with following operations:

Add(element) : Adds an element to the Set and returns a SequenceId
Discard(element): Discards an element from the Set and returns a SequenceId
Member(sequenceId): Return the state of historySet at a given sequenceId
Example:
seq1 = add("a")
seq2 = add("b")
seq3 = add("c")
seq4 = discard("b")

member(seq3) = ("a", "b", "c")
member(seq1) = ("a")
member(seq4) = ("a", "c")
 */

class HistorySet {
    class Pair {
        List<String> list;
        Set<String> discardSet;

        public Pair(List<String> list, Set<String> discardSet) {
            this.list = list;
            this.discardSet = discardSet;
        }
    }

    int c = 0;
    List<String> curr;
    Map<String, List<String>> map;
    Set<String> discardSet;

    public static void main(String[] args) {
        HistorySet sol = new HistorySet();
        System.out.println(sol.add("a"));
        System.out.println(sol.add("b"));
        System.out.println(sol.add("c"));
        System.out.println(sol.discard("b"));
        System.out.println(sol.add("b"));
        System.out.println(sol.member("seq3"));
        System.out.println(sol.member("seq1"));
        System.out.println(sol.member("seq4"));
        System.out.println(sol.member("seq5"));
    }

    public HistorySet() {
        curr = new ArrayList<>();
        map = new HashMap<>();
        discardSet = new HashSet<>();
    }

    public String add(String s) {
        c++;
        String key = "seq" + c;
        curr.add(s);
        map.put(key, new LinkedList<>(curr));
        return key;
    }

    public String discard(String s) {
        int idx = curr.indexOf(s);
        if (idx != -1) {
            curr.remove(idx);
        }
        c++;
        String key = "seq" + c;
        map.put(key, new ArrayList<>(curr));
        return key;
    }

    public List<String> member(String key) {
        return map.get(key);
    }
}

