import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    protected ArrayList<Integer> subtasksId = new ArrayList<>();

    public void setStatus() {
        int progress = 0;
        for (Integer integer : subtasksId) {
            if (Objects.equals(TasksManager.subtasks.get(integer).getStatus(), "DONE")) {
                progress = progress + 2;
            } else if (Objects.equals(TasksManager.subtasks.get(integer).getStatus(), "IN_PROGRESS")) {
                progress = progress + 1;
            }
        }
        if (progress == (subtasksId.size() * 2)) {
            this.status = "DONE";
        } else if (progress == 0) {
            this.status = "NEW";
        } else {
            this.status = "IN_PROGRESS";
        }
    }
}
