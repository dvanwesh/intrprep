package com.aoc.twenty20;

import com.aoc.common.Challenge;
import com.aoc.util.Pair;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Arrays.stream;

public class Day19 extends Challenge {
    Map<Long, Rule> rules;
    Map<Long, Set<String>> sol = new HashMap<>();
    String[] messages;
    List<String> res = new ArrayList<>();
    protected Day19() {
        super(19, 2020);
        String[] inp = getResourceAsString().split("\n\n");
        rules = stream(inp[0].split("\n")).map(e->e.split(": "))
            .collect(Collectors.toMap(e->Long.parseLong(e[0]), e->new Rule(Long.parseLong(e[0]), e[1])));
        messages = inp[1].split("\n");
    }

    public static void main(String[] args) {
        new Day19().executeTasks();
    }
    @Override
    protected Object task1() {
        rules.values().forEach(r->r.getPossibilities(rules, sol));
        return stream(messages).filter(
            m->sol.get(0l).stream().anyMatch(r->r.equals(m))).count();
    }

    /**
     * 8: 42 | 42 8
     * 11: 42 31 | 42 11 31
     * @return
     */
    @Override
    protected Object task2() {
        int maxDepth = 2;
        String[] input = getResourceAsString().split("\n\n");
        Set<String> all = sol.values().stream().flatMap(Collection::stream).collect(Collectors.toUnmodifiableSet());
        Set<String> s42 = sol.get(42L);
        Set<String> s31 = sol.get(31L);
        Set<String> s11 = sol.get(11L);
        for(int i = 2; i<=maxDepth; i++){
            Set<String> add = new HashSet<>(s42.size() * s31.size());
            Set<String> add2 = new HashSet<>(s42.size() * s31.size() * s11.size());
//            for(int i = 0; i<s42.size(); i++){
//                add.add(s+s);
//            }
//            for(String s : s31){
//                add.add(s+s);
//            }

            for(String o : s42){
                for(String o1 : s42){
                    add.add(o+o1);
                }
                for(String o2 : s31){
                    for(String o3 : s11) {
                        add2.add(o + o3 + o2);
                    }
                }
            }
            s42.addAll(add);
            s11.addAll(add2);
        }
        sol.put(11l, s11);
        sol.put(42l, s42);
        return stream(messages)
            .filter(e -> sol.values().stream().anyMatch(r -> r.contains(e)))
            .count();
    }
    @Data
    class Rule{
        private final long id;
        private final Optional<Character> letter;
        private final long[] rule1;
        private final long[] rule2;

        public Rule(long id, String rule) {
            this.id = id;
            if(rule.startsWith("\"")){
                letter = Optional.of(rule.charAt(1));
                rule1 = new long[]{};
                rule2 = new long[]{};
            } else{
                letter = Optional.empty();
                String[] rls = rule.split(" \\| ");
                rule1 = stream(rls[0].split(" ")).mapToLong(Long::parseLong).toArray();
                if(rls.length > 1){
                    rule2 = stream(rls[1].split(" ")).mapToLong(Long::parseLong).toArray();
                } else{
                    rule2 = new long[]{};
                }
            }
        }

        public Set<String> getPossibilities(Map<Long, Rule> rules, Map<Long, Set<String>> sol){
            if(sol.containsKey(id)){
                return sol.get(id);
            }
            if(letter.isEmpty()){
                Rule[] r = stream(rule1).mapToObj(rules::get).toArray(Rule[]::new);
                Rule[] orRule = stream(rule2).mapToObj(rules::get).toArray(Rule[]::new);
                Set<String> output = r[0].getPossibilities(rules, sol);
                for (int i = 1; i < r.length; i++){
                    Set<String> output2 = r[i].getPossibilities(rules, sol);
                    Set<String> res = new HashSet<>();
                    output.stream().forEach(o->output2.stream().forEach(o2->res.add(o+o2)));
                    output = res;
                }
                if(orRule.length > 0){
                    Set<String> orOutput = orRule[0].getPossibilities(rules, sol);
                    for (int i = 1; i < orRule.length; i++){
                        Set<String> output2 = orRule[i].getPossibilities(rules, sol);
                        Set<String> res = new HashSet<>();
                        orOutput.stream().forEach(o->output2.stream().forEach(o2->res.add(o+""+o2)));
                        orOutput = res;
                    }
                    output.addAll(orOutput);
                }
                sol.put(id, output);
                return output;
            }
            return new HashSet(Collections.singletonList(letter.get()+""));
        }
    }
}
