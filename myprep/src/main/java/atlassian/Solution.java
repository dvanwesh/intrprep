package atlassian;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Imagine you are given a stream of content ids along with an associated action to be performed on them.
 * Example of contents are video, pages, posts etc. There can be two actions associated with a content id:
 * increasePopularity → increases the popularity of the content by 1. The popularity increases when someone comments on the content or likes the content
 * decreasePopularity → decreases the popularity of the content by 1. The popularity decreases when a spam bot's/users comments are deleted from the content or its likes are removed from the content
 * content ids are positive integers
 * Implement a class that can return the mostPopular content id at any time while consuming the stream of content ids and its associated action. If there are no contentIds with popularity greater than 0, return -1
 *
 *
 * Sample execution:
 *         [
 *         popularityTracker.increasePopularity(7);
 *   popularityTracker.increasePopularity(7);
 *   popularityTracker.increasePopularity(8);
 *   popularityTracker.mostPopular();        // returns 7
 *   popularityTracker.increasePopularity(8);
 *   popularityTracker.increasePopularity(8);
 *   popularityTracker.mostPopular();        // returns 8
 *   popularityTracker.decreasePopularity(8);
 *   popularityTracker.decreasePopularity(8);
 *   popularityTracker.mostPopular();        // returns 7
 *   popularityTracker.decreasePopularity(7);
 *   popularityTracker.decreasePopularity(7);
 *   popularityTracker.decreasePopularity(8);
 *   popularityTracker.mostPopular();        // returns -1 since there is no content with popularity greater than 0
 * ]
 *
 */
public class Solution {
    public static void main(String[] args) {

        MostPopular popularityTracker = new MostPopularImpl();
        popularityTracker.increasePopularity(7);
        popularityTracker.increasePopularity(7);
           popularityTracker.increasePopularity(8);
        System.out.println(popularityTracker.mostPopular() == 7);        // returns 7
  popularityTracker.increasePopularity(8);
   popularityTracker.increasePopularity(8);
        System.out.println(popularityTracker.mostPopular() == 8);        // returns 8
  popularityTracker.decreasePopularity(8);
  popularityTracker.decreasePopularity(8);
        System.out.println(popularityTracker.mostPopular() == 7);        // returns 7
    popularityTracker.decreasePopularity(7);
    popularityTracker.decreasePopularity(7);
    popularityTracker.decreasePopularity(8);
        System.out.println(popularityTracker.mostPopular());
        System.out.println(popularityTracker.mostPopular() == -1);
    }
}

class Node {
    private int popularity;
    private Node prev;
    private Node next;

    private Set<Integer> contentIds = new HashSet<>();

    public Node(int popularity) {
        this.popularity = popularity;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Set<Integer> getContentIds() {
        return contentIds;
    }

    public void setContentIds(Set<Integer> contentIds) {
        this.contentIds = contentIds;
    }
}

class MostPopularImpl implements MostPopular {

    private Node head;
    private Node tail;
    private Map<Integer, Node> map;

    public MostPopularImpl() {
        map = new HashMap<>();
        head = new Node(-1);
        tail = new Node(-1);
        head.setNext(tail);
        tail.setPrev(head);
    }

    /**
     *
     * @param contentId
     */
    @Override
    public void increasePopularity(Integer contentId) {
        if (map.containsKey(contentId)) {
            Node node = map.get(contentId);
            node.getContentIds().remove(contentId);
            int popularity = node.getPopularity();
            Node nextNode = node.getNext();
            if (nextNode == tail || nextNode.getPopularity() != popularity + 1) {
                Node newNode = new Node(popularity + 1);
                newNode.setNext(nextNode);
                newNode.setPrev(node);
                node.setNext(newNode);
                nextNode.setPrev(newNode);
                newNode.getContentIds().add(contentId);
                map.put(contentId, newNode);
            } else {
                nextNode.getContentIds().add(contentId);
                map.put(contentId, nextNode);
            }
            if(node.getContentIds().isEmpty()) {
                removeNode(node);
            }
        } else {
            Node firstNode = head.getNext();
            if(firstNode == tail || firstNode.getPopularity() > 1) {
                Node newNode = new Node(1);
                newNode.setPrev(head);
                newNode.setNext(firstNode);
                firstNode.setPrev(newNode);
                head.setNext(newNode);
                newNode.getContentIds().add(contentId);
                map.put(contentId, newNode);
            } else {
                firstNode.getContentIds().add(contentId);
                map.put(contentId, firstNode);
            }
        }
    }

    private void removeNode(Node node) {
        Node prevNode = node.getPrev();
        Node nextNode = node.getNext();
        prevNode.setNext(nextNode);
        nextNode.setPrev(prevNode);
    }

    @Override
    public Integer mostPopular() {
        Node node = tail.getPrev();
        if(node == head) {
            return -1;
        }
        return node.getContentIds().iterator().next();
    }

    @Override
    public void decreasePopularity(Integer contentId) {
        if(!map.containsKey(contentId)) {
            return;
        }
        Node node = map.get(contentId);
        int popularity = node.getPopularity();
        node.getContentIds().remove(contentId);
        Node prevNode = node.getPrev();
        if(popularity == 1) {
            if(node.getContentIds().isEmpty()) {
                removeNode(node);
            }
            map.remove(contentId);
            return;
        }
        if(prevNode == head || prevNode.getPopularity() != popularity - 1) {
            Node newNode = new Node(popularity - 1);
            newNode.setPrev(prevNode);
            newNode.setNext(node);
            prevNode.setNext(newNode);
            node.setPrev(newNode);
            map.put(contentId, newNode);
            newNode.getContentIds().add(contentId);
        } else {
            prevNode.getContentIds().add(contentId);
            map.put(contentId, prevNode);
        }
        if(node.getContentIds().isEmpty()) {
            removeNode(node);
        }
    }
}
interface MostPopular {
    void increasePopularity(Integer contentId);
    Integer mostPopular();
    void decreasePopularity(Integer contentId);
}

