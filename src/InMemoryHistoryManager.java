import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    public static ArrayList<Task> history = new ArrayList<>();

    public  ArrayList<String> getHistory() {
        ArrayList<String> last10 = new ArrayList<>();
        if (history.size() < 10) {
            for (Task task : history) {
                last10.add(task.getTitle());
            }
        } else {
            for (int i = (history.size() - 10); i < history.size() ; i++) {
                last10.add(history.get(i).getTitle());
            }
        }
        return last10;
    }
}
