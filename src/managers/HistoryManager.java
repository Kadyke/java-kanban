package managers;

import tasks.Task;
import java.util.ArrayList;

public interface HistoryManager {

    void add(Task task);

    void remove(Integer integer);

    ArrayList<String> getHistory();

    ArrayList<String> getIdTasks();
}
