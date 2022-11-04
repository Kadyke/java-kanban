package Managers;

import Tasks.Task;
import java.util.ArrayList;

public interface HistoryManager {
    Integer SIZE_OF_MEMORY = 10;
    ArrayList<Task> history = new ArrayList<>();

    void add(Task task);

    ArrayList<String> getHistory();

}
