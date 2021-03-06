package leetcode.indeed;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;
/*
 * input is a list of job posts with description and jobId
 * the for every search query calculate the search rank by calculating max numbers of
 * search terms that match the jb desc and return top 10 ranked job ids.
 * */
public class IndeedFuzzySearch {
    private static Map<String, List<Integer>> searchIndex = new HashMap<>();
    private static final String DELI = " ";
    public static void storeDocument(final String document, final int documentNumber) {
        Arrays.stream(document.split(DELI)).distinct().forEach(s->populateIndex(s, documentNumber));
    }

    private static void populateIndex(String word, int jobId) {
        searchIndex.computeIfAbsent(word, a -> new ArrayList<>()).add(jobId);
    }

    public static String performSearch(final String search) {
        Map<Integer, Integer> map = new HashMap<>();
        String[] terms = search.split(DELI);
        for (String term : terms){
            for(int jobId : searchIndex.getOrDefault(term, new ArrayList<>())){
                map.put(jobId, map.getOrDefault(jobId, 0)+1);
            }
        }
        if(map.isEmpty()){
            return "-1";
        }
        Queue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((a, b)
                -> a.getValue() == b.getValue() ? Integer.compare(b.getKey(), a.getKey())
                : Integer.compare(a.getValue(), b.getValue()));
        map.entrySet().stream().forEach(e->{
            queue.offer(e);
            if(queue.size() > 10){
                queue.poll();
            }
        });
        StringBuffer sb = new StringBuffer();
        while (!queue.isEmpty()){
            sb.append(queue.poll().getKey()).append(DELI);
        }
        return sb.reverse().toString().trim();
    }


    public static void main(String args[] ) throws Exception {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT */
        final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        final int N = Integer.parseInt(br.readLine());
        // Read documents
        for (int i = 0; i < N; i++) {
            storeDocument(br.readLine(), i);
        }

        final int M = Integer.parseInt(br.readLine());
        // Read searches
        for (int j = 0; j < M; j++) {
            System.out.println(performSearch(br.readLine()));
        }
    }
}
