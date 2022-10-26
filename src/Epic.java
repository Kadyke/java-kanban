import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public void setStatus() {
        int j = 0;
        for (Integer integer : subtasksId) {
            if (Objects.equals(TasksManager.subtasks.get(integer).getStatus(), "DONE")) {
                j = j +2;
            } else if (Objects.equals(TasksManager.subtasks.get(integer).getStatus(), "IN_PROGRESS")) {
                j = j +1;
            }
        }
        if (j == (subtasksId.size() * 2)) {
            this.status = "DONE";
        } else if (j == 0) {
            this.status = "NEW";
        } else {
            this.status = "IN_PROGRESS";
        }
    }
}
