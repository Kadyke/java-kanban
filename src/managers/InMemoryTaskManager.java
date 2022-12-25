package managers;

import tasks.Epic;
import tasks.Statuses;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;

public class InMemoryTaskManager implements TaskManager {

    Integer id = 0;

    @Override
    public Task createTask(String title, String description) {
        Task task = new Task(++id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(Statuses.NEW);
        return task;
    }

    @Override
    public Subtask createSubTask(String title, String description, Epic epic) {
        Subtask subtask = new Subtask(++id);
        subtask.setTitle(title);
        subtask.setDescription(description);
        subtask.setStatus(Statuses.NEW);
        subtask.setIdEpic(epic.getId());
        epic.subtasksId.add(id);
        return subtask;
    }

    @Override
    public Epic createEpic(String title, String description) {
        Epic epic = new Epic(++id);
        epic.setTitle(title);
        epic.setDescription(description);
        epic.setStatus(Statuses.NEW);
        return epic;
    }

    @Override
    public ArrayList<String> getAllTasks() {
        ArrayList<String> allTitles = new ArrayList<>();
        for (Task task : TaskManager.tasks.values()) {
            allTitles.add(task.getTitle());
        }
        return allTitles;
    }

    @Override
    public ArrayList<String> getAllEpics() {
        ArrayList<String> allTitles = new ArrayList<>();
        for (Epic epic : TaskManager.epics.values()) {
            allTitles.add(epic.getTitle());
        }
        return allTitles;
    }

    @Override
    public ArrayList<String> getAllSubtasks() {
        ArrayList<String> allTitles = new ArrayList<>();
        for (Subtask subtask : TaskManager.subtasks.values()) {
            allTitles.add(subtask.getTitle());
        }
        return allTitles;
    }

    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            TaskManager.historyManager.remove(id);
        }
        TaskManager.tasks.clear();
    }

    @Override
    public void deleteAllEpics() {
        for (Integer id : epics.keySet()) {
            TaskManager.historyManager.remove(id);
        }
        TaskManager.epics.clear();
        for (Integer id : subtasks.keySet()) {
            TaskManager.historyManager.remove(id);
        }
        TaskManager.subtasks.clear();
    }

    @Override
    public Task getTask(Integer id) {
        TaskManager.historyManager.add(TaskManager.tasks.get(id));
        return TaskManager.tasks.get(id);
    }

    @Override
    public Subtask getSubtask(Integer id) {
        TaskManager.historyManager.add(TaskManager.subtasks.get(id));
        return TaskManager.subtasks.get(id);
    }

    @Override
    public Epic getEpic(Integer id) {
        TaskManager.historyManager.add(TaskManager.epics.get(id));
        return TaskManager.epics.get(id);
    }

    @Override
    public void deleteTask(Integer id) {
        TaskManager.historyManager.remove(id);
        TaskManager.tasks.remove(id);
    }

    @Override
    public void deleteEpic(Integer id) {
        for (Integer i : TaskManager.epics.get(id).subtasksId) {
            TaskManager.historyManager.remove(i);
            TaskManager.subtasks.remove(i);
        }
        TaskManager.historyManager.remove(id);
        TaskManager.epics.remove(id);
    }

    @Override
    public void deleteSubtask(Integer id) {
        Epic epic = TaskManager.epics.get(TaskManager.subtasks.get(id).getIdEpic());
        TaskManager.historyManager.remove(id);
        epic.subtasksId.remove(id);
        TaskManager.subtasks.remove(id);
        epic.setStatus();
    }

    @Override
    public void updateTask(Task task, String title, String description, Statuses status) {
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
    }

    @Override
    public void updateEpic (Epic epic, String title, String description) {
        epic.setTitle(title);
        epic.setDescription(description);
    }

    @Override
    public void updateSubtask (Subtask subtask, String title, String description, Statuses status) {
        subtask.setTitle(title);
        subtask.setDescription(description);
        subtask.setStatus(status);
        TaskManager.epics.get(subtask.getIdEpic()).setStatus();
    }
}
