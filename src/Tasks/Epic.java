package Tasks;

import Managers.InMemoryTaskManager;
import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {
    public ArrayList<Integer> subtasksId = new ArrayList<>();

    public Epic(Integer id){
        super(id);
    }

    public void setStatus() {
        int progress = 0;
        for (Integer integer : subtasksId) {
            if (Objects.equals(InMemoryTaskManager.subtasks.get(integer).getStatus(), Statuses.DONE)) {
                progress = progress + 2;
            } else if (Objects.equals(InMemoryTaskManager.subtasks.get(integer).getStatus(), Statuses.IN_PROGRESS)) {
                progress = progress + 1;
            }
        }
        if (progress == (subtasksId.size() * 2)) {
            this.status = Statuses.DONE;
        } else if (progress == 0) {
            this.status = Statuses.NEW;
        } else {
            this.status = Statuses.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        String taskString = "";
        taskString = taskString + title + "\n" + description + "\n" + id + "\n" + status
                + "\n" + subtasksId;
        return taskString;
    }
}
