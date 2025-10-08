package leetcode.attentive;

import java.util.*;

class Mouse {
    String id;
    String name;
    Mouse mother;
    Mouse father;

    public Mouse(String id, String name, Mouse mother, Mouse father) {
        this.id = id;
        this.name = name;
        this.mother = mother;
        this.father = father;
    }

    public String toString() {
        return "Mouse{" +
                "ID='" + id + '\'' +
                ", Name='" + name + '\'' +
                '}';
    }
}

class RelationshipTracker {
    private Map<String, Mouse> mice;

    public RelationshipTracker() {
        this.mice = new HashMap<>();
    }

    public void addMouse(String id, String name, String motherId, String fatherId) {
        Mouse mother = mice.get(motherId);
        Mouse father = mice.get(fatherId);
        Mouse mouse = new Mouse(id, name, mother, father);
        mice.put(id, mouse);
    }

    public boolean areRelated(String mouseId1, String mouseId2) {
        Mouse mouse1 = mice.get(mouseId1);
        Mouse mouse2 = mice.get(mouseId2);

        if (mouse1 == null || mouse2 == null) {
            throw new IllegalArgumentException("One or both mouse IDs do not exist.");
        }

        Set<Mouse> ancestors1 = getAncestors(mouse1);
        Set<Mouse> ancestors2 = getAncestors(mouse2);

        ancestors1.retainAll(ancestors2);
        return !ancestors1.isEmpty();
    }

    private Set<Mouse> getAncestors(Mouse mouse) {
        Set<Mouse> ancestors = new HashSet<>();
        Queue<Mouse> queue = new LinkedList<>();
        queue.add(mouse);
        ancestors.add(mouse);
        while (!queue.isEmpty()) {
            Mouse current = queue.poll();
            if (current.mother != null) {
                ancestors.add(current.mother);
                queue.add(current.mother);
            }
            if (current.father != null) {
                ancestors.add(current.father);
                queue.add(current.father);
            }
        }

        return ancestors;
    }
}

public class MiceLab {
    public static void main(String[] args) {
        RelationshipTracker tracker = new RelationshipTracker();
        tracker.addMouse("1", "Alice", null, null);
        tracker.addMouse("2", "Bob", null, null);
        tracker.addMouse("3", "Charlie", "1", "2");
        tracker.addMouse("4", "Daisy", "3", "2");

        System.out.println(tracker.areRelated("3", "4")); // true
        System.out.println(tracker.areRelated("1", "4")); // true
        System.out.println(tracker.areRelated("1", "2")); // false
    }
}

