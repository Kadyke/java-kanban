package Managers;

import Tasks.Epic;
import Tasks.Statuses;
import Tasks.Subtask;
import Tasks.Task;
import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, Subtask> subtasks = new HashMap<>();
    HashMap<Integer, Epic> epics = new HashMap<>();
    HistoryManager historyManager = Managers.getDefaultHistory();

    Task createTask(String title, String description);

    Subtask createSubTask(String title, String description, Epic epic);

    Epic createEpic(String title, String description);

    ArrayList<String> getAllTasks();

    ArrayList<String> getAllEpics();

    ArrayList<String> getAllSubtasks();

    void deleteAllEpics();

    Task getTask(Integer id);

    void deleteAllTasks();

    Subtask getSubtask(Integer id);

    Epic getEpic(Integer id);

    void deleteTask(Integer id);

    void deleteEpic(Integer id);

    void deleteSubtask(Integer id);

    void updateTask(Task task, String title, String description, Statuses status);

    void updateEpic (Epic epic, String title, String description);

    void updateSubtask (Subtask subtask, String title, String description, Statuses status);
}
