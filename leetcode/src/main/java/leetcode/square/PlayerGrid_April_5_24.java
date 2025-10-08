package leetcode.square;

import java.util.*;

// Close Players
// Imagine you are playing a game represented by a 2D coordinate plane. Each player has an ID and a current position represented by an x, y coordinate. Coordinates are positive or negative integers.


// Players are considered “close” if they are within +/- 10 on the x-axis and within +/- 10 on the y-axis, inclusive. For example, a player at (3, 5) is close to a player at (13, 5) or (3, 15), but is not close to a player at (14, 16).


// ===========================
// PART 1
// Find the close players for each player.


// Example input:
// A list of player objects, below is an example JSON representation
// [
//   {
//     "id": 1,
//     "x": 0,
//     "y": 0
//   },
//   {
//     "id": 2,
//     "x": 10,
//     "y": 10
//   },
//   {
//     "id": 3,
//     "x": 11,
//     "y": 10
//   },
//   {
//     "id": 4,
//     "x": -10,
//     "y": -10
//   },
//   {
//     "id": 5,
//     "x": -10,
//     "y": -11
//   }
// ]


// Example output:
// { '1': [ 2, 4 ], '2': [ 1, 3 ], '3': [ 2 ], '4': [ 1, 5 ], '5': [ 4 ] }


// ===========================
// PART 2
// How can we make the runtime better for larger inputs, potentially in the millions?


// Is there a constraint that could help make this more efficient?


/*
{
 (0,0): 1
 10: 2
 11: 3
 -1: 4
}
*/


class PlayerGrid_April_5_24 {
    private static final int CLOSEST_DIST = 10;
    private static List<Player> players;
    static Map<Player, Set<Player>> closestPayerMap = new HashMap<>();
    static Map<String, Player> playerPosMap = new HashMap<>();


    static class Player {
        int id;
        int x;
        int y;

        public Player(int playerId, int playerX, int playerY) {
            id = playerId;
            x = playerX;
            y = playerY;
        }

        public String toString() {
            return String.valueOf(id);
        }
    }


    public static void findClosestPlayers2(Player currPlayer) {
        List<String> closestPos = new ArrayList<>();
        String currPos = currPlayer.x+","+currPlayer.y;
        int left = currPlayer.x - CLOSEST_DIST;
        int right = currPlayer.x + CLOSEST_DIST;
        int up = currPlayer.y + CLOSEST_DIST;
        int down = currPlayer.y - CLOSEST_DIST;


        for(int i = left; i <= right; i++) {
            for(int j=down; j <= up; j++) {
                closestPos.add(i+","+j);
            }
        }


        Set<Player> closestPlayers = new HashSet<>();
        for(String pos:closestPos) {
            if(!pos.equals(currPos) && playerPosMap.containsKey(pos)) {
                closestPlayers.add(playerPosMap.get(pos));
            }
        }
        closestPayerMap.put(currPlayer, closestPlayers);
    }


    public static Set<Player> findClosestPlayers(Player currPlayer) {
        Set<Player> closestPlayers = new HashSet<>();
        for(Player player:players) {
            if(currPlayer != player && areClosePlayers(currPlayer, player)) {
                closestPlayers.add(player);
            }
        }
        closestPayerMap.put(currPlayer, closestPlayers);
        return closestPlayers;
    }


    private static boolean areClosePlayers(Player p1, Player p2) {
        return Math.abs(p1.x - p2.x) <= 10 && Math.abs(p1.y - p2.y) <= 10;
    }
    public static void main(String[] args) {
        Player p1 = new Player(1, 0, 0);
        Player p2 = new Player(2, 10, 10);
        Player p3 = new Player(3, 11, 10);
        Player p4 = new Player(4, -10, -10);
        Player p5 = new Player(5, -10, -11);

        Player[] playersArr = {p1, p2, p3, p4, p5};
        players = Arrays.asList(playersArr);
        for(Player player:players) {
            playerPosMap.put(new Pair(player.x, player.y).toString(), player);
        }
        for(Player player:players) {
            findClosestPlayers2(player);
        }
        System.out.println(closestPayerMap);
    }
}




class Pair {
    public int x;
    public int y;


    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public boolean equals(Object obj) {
        Pair p = (Pair) obj;
        return p.x == x && p.y == y;
    }


    public String toString() {
        return x+","+y;
    }
}

