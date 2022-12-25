package managers;

import tasks.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    @Override
    public void add(Task task) {
        history.add(task);
    }

    @Override
    public void remove(Integer integer) {
        history.remove(integer);
    }
    @Override
    public ArrayList<String> getHistory() {
        return history.getTasks();
    }
}
