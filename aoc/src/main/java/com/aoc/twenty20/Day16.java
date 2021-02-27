package com.aoc.twenty20;

import com.aoc.common.Challenge;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.ArrayUtils.subarray;

public class Day16 extends Challenge {
    private final Rule[] rules;
    private final long[] myTicket;
    private final List<List<Long>> tickets;
    private static final String TYPE = "seat";
    protected Day16() {
        super(16, 2020);
        String[] input = getResourceAsString().split("\n\n");
        this.rules = stream(input[0].split("\n")).map(s->new Rule(s.split(": "))).toArray(Rule[]::new);
        this.myTicket = stream(input[1].split("\n")[1].split(",")).mapToLong(Long::parseLong).toArray();
        String[] ticketStrings = input[2].split("\n");
        this.tickets = stream(subarray(ticketStrings, 1, ticketStrings.length)).map(s -> stream(s.split(",")).map(Long::parseLong).collect(toList())).collect(toList());
    }

    public static void main(String[] args) {
        new Day16().executeTasks();
    }
    @Override
    protected Object task1() {
        return tickets.stream().flatMapToLong(t->t.stream().filter(n->stream(rules).noneMatch(r->r.check(n))).mapToLong(e->e)).sum();
    }

    @Override
    protected Object task2() {
        List<List<Long>> validTickets = tickets.stream().filter(t->t.stream().allMatch(n->stream(rules).anyMatch(r->r.check(n)))).collect(toList());
        Multimap<Integer, Rule> ruleIndex = MultimapBuilder.hashKeys().arrayListValues().build();
        for (Rule r : rules) {
            for (int j = 0; j < validTickets.get(0).size(); j++) {
                int finalJ = j;
                if (validTickets.stream().allMatch(t -> r.check(t.get(finalJ)))) {
                    ruleIndex.put(j, r);
                }
            }
        }
        Optional<Map.Entry<Integer, Collection<Rule>>> res;
        Set<Integer> indices = new HashSet<>();
        while ((res = ruleIndex.asMap().entrySet().stream().filter(e->e.getValue().size() == 1 && !indices.contains(e.getKey())).findAny()).isPresent()) {
            Map.Entry<Integer, Collection<Rule>> r = res.get();
            int index = r.getKey();
            Rule rule = ((List<Rule>)r.getValue()).get(0);
            for(int i = 0; i < rules.length; i++){
                Map.Entry<Integer, Collection<Rule>> t = new ArrayList<>(ruleIndex.asMap().entrySet()).get(i);
                if(t.getKey() != index){
                    t.getValue().remove(rule);
                }
            }
            indices.add(index);
        }
        return ruleIndex.asMap().entrySet().stream().filter(e->e.getValue().stream().anyMatch(Rule::isDeparture))
            .mapToLong(e->myTicket[e.getKey()]).reduce((a,b)->a*b).getAsLong();
    }

    @Data
    @Value
    static class Rule {
        String name;
        long lower1;
        long upper1;
        long lower2;
        long upper2;

        public Rule(String[] split) {
            this.name = split[0];
            lower1 = Long.parseLong(split[1].split(" or ")[0].split("-")[0]);
            upper1 = Long.parseLong(split[1].split(" or ")[0].split("-")[1]);
            lower2 = Long.parseLong(split[1].split(" or ")[1].split("-")[0]);
            upper2 = Long.parseLong(split[1].split(" or ")[1].split("-")[1]);

        }

        public boolean check(long val) {
            return (val >= lower1 && val <= upper1) || (val >= lower2 && val <= upper2);
        }

        public boolean isDeparture() {
            return name.startsWith("departure");
        }

        @Override
        public String toString() {
            return "Rule{" +
                "name='" + name + '\'' +
                ", lower1=" + lower1 +
                ", upper1=" + upper1 +
                ", lower2=" + lower2 +
                ", upper2=" + upper2 +
                '}';
        }
    }
}
