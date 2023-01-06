package managers;

import tasks.Task;
import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private CustomLinkedList history = new CustomLinkedList();
    @Override
    public void add(Task task) {
        history.add(task);
    }
    @Override
    public ArrayList<String> getIdTasks() {
        return history.getIdTasks();
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
