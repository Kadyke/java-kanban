package Tasks;

public class Subtask extends Task {
    protected Integer idEpic;

    public Subtask(Integer id){
        super(id);
    }
    public Integer getIdEpic() {
        return idEpic;
    }

    public void setIdEpic(Integer idEpic) {
        this.idEpic = idEpic;
    }

    @Override
    public String toString() {
        String taskString = "";
        taskString = taskString + title + "\n" + description + "\n" + id + "\n" + status
                + "\n" + idEpic;
        return taskString;
    }
}
