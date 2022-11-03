import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {

    @Override
    public void add(Task task) {
        history.add(task);
    }
    @Override
    public ArrayList<String> getHistory() {
        ArrayList<String> last10 = new ArrayList<>();
        if (history.size() < SIZE_OF_MEMORY) {
            for (Task task : history) {
                last10.add(task.getTitle());
            }
        } else {
            for (int i = (history.size() - SIZE_OF_MEMORY); i < history.size() ; i++) {
                last10.add(history.get(i).getTitle());
            }
        }
        return last10;
    }
}
