package leetcode.square;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Rank teams by votes:
 *
 * In a special ranking system, each voter gives a rank from highest to lowest to all teams participated in the competition.
 * The ordering of teams is decided by who received the most position-one votes. If two or more teams tie in the first position,
 * we consider the second position to resolve the conflict, if they tie again, we continue this process until the ties are resolved.
 * If two or more teams are still tied after considering all positions, we rank them alphabetically based on their team letter.
 *
 * Given an array of strings votes which is the votes of all voters in the ranking systems. Sort all teams according to the ranking
 * system described above.
 *
 * Return a string of all teams sorted by the ranking system.
 * */
public class RankTeamsByVotes {
    int[][] teamRanks = null;
    String[] teamNames = null;
    public static void main(String[] args) {
        RankTeamsByVotes rankTeamsByVotes = new RankTeamsByVotes();
        String[] rankings = new String[]{"1-2-3-4-5", "2-1-4-3-5", "2-1-4-3-5", "2-1-3-4-5"};
        System.out.println(rankTeamsByVotes.rankTeams(rankings, new String[]{"A","B", "D", "C", "E"}));
    }

    /**
     * teamRanks[0] = {2,2,0,0}
     * teamRanks[1] = {2,2,0,0}
     * teamRanks[2] = {0,0,0,4}
     * */
    public List<Integer> rankTeams(String[] rankings, String[] teamNames){
        this.teamNames = teamNames;
        Map<String, rankCriteria> strategy = new HashMap<>();
        strategy.put("userRank", this::rankByPosition);
        List<Integer> res = new ArrayList<>();
        teamRanks = new int[teamNames.length][teamNames.length];
        for(String ranking : rankings){
            int i = 0;
            for(String rank : ranking.split("-")){
                int rnk = Integer.parseInt(rank);
                teamRanks[i++][rnk-1]++;
            }
        }
        Queue<int[]> pq = new PriorityQueue<>((a, b) -> strategy.get("userRank").rankBy(a[0], b[0], a[1]));
        int i = 0;
        while(i < teamRanks.length){
            pq.offer(new int[]{i++, 0});
        }
        while (!pq.isEmpty()){
            res.add(pq.poll()[0]);
        }
        return res;
    }

    private int rankByPosition(int i, int j, int idx){
        while(idx < teamRanks[i].length && teamRanks[i][idx] == teamRanks[j][idx]){
            idx++;
        }
        return idx < teamRanks[i].length ? teamRanks[j][idx] - teamRanks[i][idx] : teamNames[i].compareTo(teamNames[j]);
    }

    public interface rankCriteria {
        int rankBy(int i, int j, int idx);
    }
}
