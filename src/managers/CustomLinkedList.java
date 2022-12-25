package managers;

import java.util.ArrayList;
import java.util.HashMap;
import tasks.Task;

public class CustomLinkedList {
    private Node head;
    private Node tail;

    private HashMap<Integer, Node> hashMap = new HashMap<>();

    public void add (Task task) {
        Node node = new Node(task);
        if (hashMap.containsKey(task.getId())) {
            remove(task.getId());
        }
        if (hashMap.isEmpty()) {
            head = node;
            tail = node;
        } else {
            head.next = node;
            node.prev = head;
            head = node;
        }
        hashMap.put(node.getId(), node);
    }



    public void remove(Integer integer) {
        Node node = hashMap.get(integer);
        if (hashMap.containsKey(integer)) {
            if (node.prev != null) {
                node.prev.next = node.next;
            } else {
                tail = node.next;
            }
            if (node.next != null) {
                node.next.prev = node.prev;
            } else {
                head = node.prev;
            }
            hashMap.remove(node.getId());
        }
    }

    public ArrayList<String> getTasks() {
        ArrayList<String> arrayList = new ArrayList<>();
        if (hashMap.isEmpty()) {
            return arrayList;
        }
        Node localTail = tail;
        arrayList.add(localTail.data.getTitle());
        while (localTail.next != null) {
            arrayList.add(localTail.next.data.getTitle());
            localTail = localTail.next;
        }
        return arrayList;
    }

    private class Node  {
        public Task data;
        public Node next;
        public Node prev;

        public Node(Task task) {
            this.data = task;
            this.next = null;
            this.prev = null;
        }

        public Integer getId() {
            return data.getId();
        }
    }

}
