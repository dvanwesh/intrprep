package leetcode.indeed;

import java.util.*;

// write iterator that produces sorted output for n input sorted int buffers
public class SortedIterator implements Iterator<Integer> {
    Queue<ListValue> pq;
    private List<List<Integer>> inp;
    public SortedIterator(List<List<Integer>> inp){
        this.inp = inp;
        pq = new PriorityQueue<>(Comparator.comparingInt(ListValue::getVal));
        inp.stream().filter(list->!list.isEmpty())
            .forEach(list -> {
                Iterator<Integer> it = list.iterator();
                pq.offer(new ListValue(it.next(), it));
            });
    }

    public static void main(String[] args) {
        List<List<Integer>> nums = new ArrayList<>();
        nums.add(Arrays.asList(1, 4, 5, 8, 9));
        nums.add(Arrays.asList(3, 4, 4, 6));
        nums.add(Arrays.asList(0, 2, 8));
        SortedIterator sortedIterator = new SortedIterator(nums);
        while (sortedIterator.hasNext()){
            System.out.print(sortedIterator.next()+", ");
        }
    }
    @Override
    public boolean hasNext(){
        return !pq.isEmpty();
    }

    @Override
    public Integer next(){
        if(pq.isEmpty()){
            throw  new RuntimeException("No more elements");
        }
        ListValue res = pq.poll();
        if(res.getIterator().hasNext()){
            pq.offer(new ListValue(res.getIterator().next(), res.getIterator()));
        }
        return res.getVal();
    }

    class ListValue{
        private final Integer val;
        private final Iterator<Integer> iterator;
        public ListValue(Integer v, Iterator<Integer> it){
            val = v;
            iterator = it;
        }
        public Integer getVal(){
            return val;
        }
        public Iterator<Integer> getIterator(){
            return iterator;
        }
    }
}
