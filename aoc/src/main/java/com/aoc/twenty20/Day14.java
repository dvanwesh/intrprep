package com.aoc.twenty20;

import com.aoc.common.Challenge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 extends Challenge {
    Map<Long, Long> mem = new HashMap<>();
    List<MaskObject> tasks = new ArrayList<>();
    //Matcher m = Pattern.compile("\\[[.*?]\\)").matcher(x);
    public Day14() {
        super(14, 2020);
        MaskObject msk = null;
        Map<Long, List<Long>> instructions = null;
        for(String line: inputList){
            String value = line.split("=")[1].trim();
            if(line.startsWith("mask")){
                if(msk != null){
                    tasks.add(msk);
                    msk.setInstructions(instructions);
                }
                msk = new MaskObject(value);
                instructions = new HashMap<>();
            } else{
                Long address = Long.parseLong(line.substring(line.indexOf("[")+1,line.indexOf("]")));
                instructions.computeIfAbsent(address, a->new ArrayList<>()).add(Long.parseLong(value));
            }
        }
        msk.setInstructions(instructions);
        tasks.add(msk);
    }

    public static void main(String[] args) {
        new Day14().executeTasks();
    }
    // 8332632930672
    @Override
    protected Object task1() {
        for(MaskObject task : tasks){
            mem.putAll(task.executeVersion1());
        }
        return mem.values().stream().mapToLong(Long::valueOf).sum();
    }
    // 4521158178278 too low
    // correct ans: 4753238784664
    @Override
    protected Object task2() {
        mem = new HashMap<>();
        for(MaskObject task : tasks){
            mem.putAll(task.executeVersion2_2());
        }
        return mem.values().stream().mapToLong(Long::valueOf).sum();
    }

    class MaskObject{
        String bitmask;
        Map<Long, List<Long>> instructions;

        public MaskObject(String bitmask) {
            this.bitmask = new StringBuilder(bitmask).reverse().toString();
        }

        public void setInstructions(Map<Long, List<Long>> instructions) {
            this.instructions = instructions;
        }

        public Map<Long, Long> executeVersion1(){
            Map<Long, Long> memMap = new HashMap<>();
            for(Long address: instructions.keySet()){
                for(Long val: instructions.get(address)){
                    String curr = new StringBuilder(Long.toBinaryString(val)).reverse().toString();
                    long res = 0l;
                    int i = 0;
                    while(i<curr.length()){
                        if(i < bitmask.length() && (bitmask.charAt(i) == '1' || bitmask.charAt(i) == '0')){
                            res += Math.pow(2, i)*(bitmask.charAt(i)-'0');
                        } else if(curr.charAt(i) == '1'){
                            res += Math.pow(2, i);
                        }
                        i++;
                    }
                    while(i<bitmask.length()){
                        if(bitmask.charAt(i) == '1'){
                            res += Math.pow(2, i);
                        }
                        i++;
                    }
                    memMap.put(address, res);
                }
            }
            return memMap;
        }
        private String getReverseBinary(long address, int len){
            StringBuilder addr = new StringBuilder(Long.toBinaryString(address));
            while(addr.length() < len){
                addr.insert(0,'0');
            }
            return addr.reverse().toString();
        }
        public Map<Long, Long> executeVersion2_2(){
            Map<Long, Long> memMap = new HashMap<>();
            for(Long address: instructions.keySet()) {
                String curr = getReverseBinary(address, bitmask.length());
                Long val = instructions.get(address).get(instructions.get(address).size()-1);
                List<Long> res = new ArrayList<>();
                List<String> bins = new ArrayList<>();
                bins.add("");
                res.add(0L);
                int i = 0;
                while(i<bitmask.length()){
                    int finalI = i;
                    if(i < bitmask.length()){
                        if(bitmask.charAt(i) == '0'){
                            bins = bins.stream().map(s->s+curr.charAt(finalI)).collect(Collectors.toList());
                            res = res.stream().map(r-> (long) (r+Math.pow(2, finalI)*(curr.charAt(finalI)-'0'))).collect(Collectors.toList());
                        } else if(bitmask.charAt(i) == '1'){
                            bins = bins.stream().map(s->s+'1').collect(Collectors.toList());
                            res = res.stream().map(r-> (long) (r+Math.pow(2, finalI))).collect(Collectors.toList());
                        } else if(bitmask.charAt(i) == 'X'){
                            res.addAll(res.stream().map(r-> (long) (r+Math.pow(2, finalI))).collect(Collectors.toList()));
                            bins.addAll(bins.stream().map(s->s+'1').collect(Collectors.toList()));
                            if(i == 0){
                                bins.remove(0);
                                bins.add("0");
                            }
                        }
                    }
                    i++;
                }
                for(Long mem : res){
                    memMap.put(mem, val);
                }
                    /*for(String bin : bins){
                        memMap.put(Long.parseLong(new StringBuilder(bin).reverse().toString(), 2), val);
                    }*/
            }
            return memMap;
        }
        public Map<Long, Long> executeVersion2(){
            Map<Long, Long> memMap = new HashMap<>();
            for(Long address: instructions.keySet()) {
                for(Long val: instructions.get(address)){
                    String curr = new StringBuilder(Long.toBinaryString(address)).reverse().toString();
                    List<Long> res = new ArrayList<>();
                    res.add(0L);
                    int i = 0;
                    while(i<curr.length()){
                        int finalI = i;
                        if(i < bitmask.length()){
                            if(bitmask.charAt(i) == '0'){
                                res = res.stream().map(r-> (long) (r+Math.pow(2, finalI)*(curr.charAt(finalI)-'0'))).collect(Collectors.toList());
                            } else if(bitmask.charAt(i) == '1'){
                                res = res.stream().map(r-> (long) (r+Math.pow(2, finalI))).collect(Collectors.toList());
                            } else{
                                List<Long> currRes = new ArrayList<>(res);
                                res.addAll(currRes.stream().map(r-> (long) (r+Math.pow(2, finalI))).collect(Collectors.toList()));
                            }
                        } else if(curr.charAt(i) == '1'){
                            res = res.stream().map(r-> (long) (r+Math.pow(2, finalI)*(curr.charAt(finalI)-'0'))).collect(Collectors.toList());
                        }
                        i++;
                    }
                    while(i<bitmask.length()){
                        int finalI = i;
                        if(bitmask.charAt(i) == '1'){
                            res = res.stream().map(r-> (long) (r+Math.pow(2, finalI))).collect(Collectors.toList());
                        } else if(bitmask.charAt(i) == 'X'){
                            List<Long> currRes = new ArrayList<>(res);
                            res.addAll(currRes.stream().map(r-> (long) (r+Math.pow(2, finalI))).collect(Collectors.toList()));
                        }
                        i++;
                    }
                    for(Long mem : res){
                        memMap.put(mem, val);
                    }
                }
            }
            return memMap;
        }
    }
}
