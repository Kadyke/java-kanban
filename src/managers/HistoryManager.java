package managers;

import tasks.Task;
import java.util.ArrayList;

public interface HistoryManager {
    CustomLinkedList history = new CustomLinkedList();

    void add(Task task);

    void remove(Integer integer);

    ArrayList<String> getHistory();

}
