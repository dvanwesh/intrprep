package leetcode.hbo;

import java.util.ArrayList;
import java.util.List;

public class Combinations {
    public static void main(String[] args) {
        Combinations cmb = new Combinations();
        List<Character> inp = new ArrayList<>();
        inp.add('a');
        inp.add('b');
        System.out.println(cmb.getAllCombinations(inp));
        inp.add('c');
        System.out.println(cmb.getAllCombinations(inp));
        inp.add('d');
        System.out.println(cmb.getAllCombinations(inp));
    }

    public List<String> getAllCombinations(List<Character> inp) {
        List<String> res = new ArrayList<>();
        getAllCombinations(inp, 0, res, new StringBuilder());
        return res;
    }

    private void getAllCombinations(List<Character> inp, int idx, List<String> res, StringBuilder sb) {
        if (idx == inp.size()) {
            return;
        }
        for (int i = idx; i < inp.size(); i++) {
            sb.append(inp.get(i));
            res.add(sb.toString());
            getAllCombinations(inp, i + 1, res, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
