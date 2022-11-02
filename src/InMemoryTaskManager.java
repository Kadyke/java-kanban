import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {

    Integer id = 0;

    public Task createTask(String title, String description) {
        Task task = new Task(++id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(Statuses.NEW);
        return task;
    }

    public Subtask createSubTask(String title, String description, Epic epic) {
        Subtask subtask = new Subtask(++id);
        subtask.setTitle(title);
        subtask.setDescription(description);
        subtask.setStatus(Statuses.NEW);
        subtask.setIdEpic(epic.id);
        epic.subtasksId.add(id);
        return subtask;
    }

    public Epic createEpic(String title, String description) {
        Epic epic = new Epic(++id);
        epic.setTitle(title);
        epic.setDescription(description);
        epic.setStatus(Statuses.NEW);
        return epic;
    }

    public ArrayList<String> getAllTasks() {
        ArrayList<String> allTitles = new ArrayList<>();
        for (Task task : tasks.values()) {
            allTitles.add(task.title);
        }
        return allTitles;
    }

    public ArrayList<String> getAllEpics() {
        ArrayList<String> allTitles = new ArrayList<>();
        for (Epic epic : epics.values()) {
            allTitles.add(epic.title);
        }
        return allTitles;
    }

    public ArrayList<String> getAllSubtasks() {
        ArrayList<String> allTitles = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            allTitles.add(subtask.title);
        }
        return allTitles;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public Task getTask(Integer id) {
        InMemoryHistoryManager.history.add(tasks.get(id));
        return tasks.get(id);
    }

    public Subtask getSubtask(Integer id) {
        InMemoryHistoryManager.history.add(subtasks.get(id));
        return subtasks.get(id);
    }

    public Epic getEpic(Integer id) {
        InMemoryHistoryManager.history.add(epics.get(id));
        return epics.get(id);
    }

    public void deleteTask(Integer id) {
        tasks.remove(id);
    }

    public void deleteEpic(Integer id) {
        for (Integer i : epics.get(id).subtasksId) {
            subtasks.remove(i);
        }
        epics.remove(id);
    }

    public void deleteSubtask(Integer id) {
        Epic epic = epics.get(subtasks.get(id).getIdEpic());
        epic.subtasksId.remove(id);
        subtasks.remove(id);
        epic.setStatus();
    }

    public void updateTask(Task task, String title, String description, Statuses status) {
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
    }

    public void updateEpic (Epic epic, String title, String description) {
        epic.setTitle(title);
        epic.setDescription(description);
    }

    public void updateSubtask (Subtask subtask, String title, String description, Statuses status) {
        subtask.setTitle(title);
        subtask.setDescription(description);
        subtask.setStatus(status);
        epics.get(subtask.getIdEpic()).setStatus();
    }
}