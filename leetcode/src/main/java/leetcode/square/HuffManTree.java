package leetcode.square;

import java.util.*;

/**
 * Technical interview：
 * Part 1: Given an arbitrary string, give me a mapping between character and the number of times that character occurs.
 * "aabbbbbcDDD" => a -> 2, b -> 5, c -> 1, D -> 3
 * Part 2: convert the map in the first step into a binary tree.
 * We take the two nodes with the lowest count as the starting points of our tree. (c, 1)
 * The count of the parent node should be the sum of its children’s counts. The character of the parent node doesn’t matter ("#" is used here).
 * Our tree now looks like this:
 * (#, 3)
 * /
 * (a, 2)    (c,1)
 * We take the next node with the lowest count and add it as a sibling of this tree. Continue in this way for all characters and we get:
 * (#, 11)
 * /
 * (b,5)    (#, 6)
 * /   \
 * (D, 3)   (#, 3)
 * /
 * (a, 2)    (c,1)
 * Return a pointer to the root of that tree.
 */
public class HuffManTree {

    private static Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getCount));

    public static void main(String[] args) {
        String encodedStr = encode("aabbbbbcDDD");
        System.out.println(encodedStr);
        String decodedStr = decode(encodedStr, pq.peek());
        System.out.println(decodedStr);
    }

    private static String decode(String encodedStr, Node root) {
        StringBuffer sb = new StringBuffer();
        Node curr = root;
        for (char ch : encodedStr.toCharArray()) {
            curr = ch == '0' ? curr.left : curr.right;
            if (curr.left == null && curr.right == null) {
                sb.append(curr.ch);
                curr = root;
            }
        }
        return sb.toString();
    }

    public static String encode(String str) {
        Map<Character, Integer> charFreqMap = new HashMap<>();
        for (char ch : str.toCharArray()) {
            charFreqMap.merge(ch, 1, Integer::sum);
        }

        charFreqMap.forEach((key, value) -> pq.offer(new Node(key, value, null, null)));

        while (pq.size() > 1) {
            Node left = pq.poll();
            Node right = pq.poll();

            pq.offer(new Node('#', left.count + right.count, left, right));
        }
        Map<Character, String> codesMap = new HashMap<>();
        preOrder(pq.peek(), "", codesMap);

        StringBuffer sb = new StringBuffer();
        for (char ch : str.toCharArray()) {
            sb.append(codesMap.get(ch));
        }
        return sb.toString();
    }

    private static void preOrder(Node root, String str, Map<Character, String> resMap) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            resMap.put(root.ch, !str.isEmpty() ? str : "1");
        }
        preOrder(root.left, str + "0", resMap);
        preOrder(root.right, str + "1", resMap);
    }

    static class Node {
        private char ch;
        private int count;
        private Node left;
        private Node right;

        public Node(char ch, int count, Node left, Node right) {
            this.ch = ch;
            this.count = count;
            this.left = left;
            this.right = right;
        }

        public char getCh() {
            return ch;
        }

        public void setCh(char ch) {
            this.ch = ch;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }
}
