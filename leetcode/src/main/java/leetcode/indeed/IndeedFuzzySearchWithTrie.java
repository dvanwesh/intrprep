package leetcode.indeed;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
/*
* input is a list of job posts with description and jobId
* the for every search query calculate the search rank by calculating max numbers of
* search terms that match the jb desc and return top 10 ranked job ids.
* */
public class IndeedFuzzySearchWithTrie {
    private static final String DELI = " ";
    private static TrieNode root = new TrieNode('#');
    public static void storeDocument(final String document, final int documentNumber) {
        Arrays.stream(document.split(DELI)).distinct().forEach(s->populateTrieIndex(s, documentNumber));
    }

    private static void populateTrieIndex(String word, int jobId){
        TrieNode curr = root;
        for(char ch : word.toCharArray()){
            curr.getChildren().computeIfAbsent(ch, a->new TrieNode(ch));
            curr = curr.getChildren().get(ch);
        }
        curr.markEndOfCompletion();
        curr.addJobId(jobId);
    }

    public static String performSearch(final String search) {
        Map<Integer, Integer> map = new HashMap<>();
        String[] terms = search.split(DELI);
        for (String term : terms){
            for(int jobId : fetchJobIdsWithText(term)){
                map.put(jobId, map.getOrDefault(jobId, 0)+1);
            }
        }
        if(map.isEmpty()){
            return "-1";
        }
        Queue<Map.Entry<Integer, Integer>> queue = new PriorityQueue<>((a, b)
                -> a.getValue().equals(b.getValue()) ? Integer.compare(b.getKey(), a.getKey())
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

    private static List<Integer> fetchJobIdsWithText(String term) {
        TrieNode curr = root;
        int i = 0;
        while (i < term.length() && curr.getChildren().containsKey(term.charAt(i))){
            curr = curr.getChildren().get(term.charAt(i++));
        }
        if(i == term.length() && curr.isEndOfCompletion()){
            return curr.getJobIds().stream().collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public static void main(String args[] ) throws Exception {
        String [] docs = new String[]{"indeed engineer software engineer",
                                        "java software engineer",
                                        "java indeed engineer"};
        // Read documents
        InputStream inp = IndeedFuzzySearchWithTrie.class.getClassLoader().getResourceAsStream("indeed/jobs-data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inp));
        int n = Integer.parseInt(br.readLine());
        for (int i = 0; i < n; i++) {
            storeDocument(br.readLine(), i);
        }
        TrieNode curr = root;
        printTrie(curr, "");
        String [] searches = new String[]{"java engineer",
            "java",
            "engineer"};
        n = Integer.parseInt(br.readLine());
        // Read searches
        for (int j = 0; j < n; j++) {
            System.out.println(performSearch(br.readLine()));
        }
    }

    private static void printTrie(TrieNode curr, String completion){
        if(curr.isEndOfCompletion()){
            System.out.println(completion + "------" + curr.getJobIds());
        }
        for(char child : curr.getChildren().keySet()){
            printTrie(curr.getChildren().get(child), completion + child);
        }
    }
}
