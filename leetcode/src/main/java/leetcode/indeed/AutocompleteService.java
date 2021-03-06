package leetcode.indeed;

import java.util.*;

/*
    example: {"python", "software", "pythogorus"}
    partial : "py" res: {"python", "pythogorus"}
    partial : "ja"
*/
class AutocompleteService {
    public static void main(String[] args) {
        String[] completions = new String[]{"python", "software", "pythogorus", "software engineer"};
        AutocompleteService acs = new AutocompleteService(completions);
        System.out.println(acs.suggestFor("s"));
    }
    TrieNode root;
    /*
        n : length of completions
        l : avg length of each completion
        time: O(n * l)
        space: O(n * l)
    */
    AutocompleteService(String[] completions) {
        root = new TrieNode('#');
        for(String completion : completions){
            loadCompletion(completion);
        }
    }

    private void loadCompletion(String completion){
        TrieNode curr = root;
        for(char ch : completion.toCharArray()){
            curr.getChildren().computeIfAbsent(ch, a->new TrieNode(ch));
            curr = curr.getChildren().get(ch);
        }
        curr.markEndOfCompletion();
    }

    /*
        k : length of partial;
        worse case time: O(n * l)
    List<String> suggestFor(String partial)
        avg case time:  k + (n * l)/k * 26;
        space:

    */
    List<String> suggestFor(String partial) {
        List<String> res = new ArrayList<>();
        TrieNode curr = root;
        int len = 0;
        for(char ch : partial.toCharArray()){
            if(!curr.getChildren().containsKey(ch)){
                return res;
            }
            curr = curr.getChildren().get(ch);
            len++;
        }
        if(len > 0){
            dfs(curr, res, partial);
        }
        return res;
    }

    private void dfs(TrieNode curr, List<String> res, String completion){
        if(curr.isEndOfCompletion()){
            res.add(completion);
        }
        for(char child : curr.getChildren().keySet()){
            dfs(curr.getChildren().get(child), res, completion + child);
        }
    }
}

class TrieNode{
    private final char ch;
    private Map<Character, TrieNode> children;
    private boolean endOfCompletion;
    private Set<Integer> jobIds;
    public TrieNode(char ch){
        this.ch = ch;
        endOfCompletion = false;
        children = new HashMap<>();
        jobIds = new HashSet<>();
    }

    public char getChar(){
        return ch;
    }

    public Map<Character, TrieNode> getChildren(){
        return children;
    }

    public boolean isEndOfCompletion(){
        return endOfCompletion;
    }

    public void markEndOfCompletion(){
        endOfCompletion = true;
    }

    public void addJobId(int id){
        jobIds.add(id);
    }

    public Set<Integer> getJobIds() {
        return jobIds;
    }
}
