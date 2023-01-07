package ru.yandex.practiсum.managers;

import ru.yandex.practiсum.tasks.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.io.Writer;
import java.io.FileWriter;


/*
 Не до конца понял комментарий "Сейчас сохранение истории и задач добавляют данных в файлы и получается,
 что в файле ненастоящие данные (старые и новые)" Разве суть тз не как раз таки в том, чтобы предыдущие сессии
 сохранялись в файлах? "Проверьте, что история просмотра восстановилась верно и все задачи, эпики, подзадачи,
 которые были в старом, есть в новом менеджере."
*/
public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final Path TASK_MEMORY = Paths.get("src\\ru\\yandex\\practiсum\\storage\\tasksMemory.txt");
    private static final Path HISTORY_MEMORY = Paths.get( "src\\ru\\yandex\\practiсum\\storage\\historyMemory.txt");
    public static void main(String[] args) {
        System.out.println("Тестирование версии 1.3");
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager();
        // Используйте метод purification() для очищения файлов памяти,
        // если в этом нет необходимости закомменитируйте его.
        fileBackedTasksManager.purification();
        fileBackedTasksManager.createOrReadFile(fileBackedTasksManager);
        Task task1 = fileBackedTasksManager.createTask("Задача 1", "Описаиние задачи 1");
        System.out.println("Создали задачу 1");
        Epic epic1 = fileBackedTasksManager.createEpic("Эпик 1", "Описание Эпика 1");
        System.out.println("Создали эпик 1");
        Subtask subtask1 = fileBackedTasksManager.createSubTask("Подзадача 1", "Описание подзадачи 1", epic1);
        System.out.println("Создали подзадачу 1");
        Subtask subtask2 = fileBackedTasksManager.createSubTask("Подзадача 2", "Описание подзадачи 2", epic1);
        System.out.println("Создали подзадачу 2");
        Task task3 = fileBackedTasksManager.createTask("Задача 2", "Описаиние задачи 2");
        System.out.println("Создали задачу 2");
        fileBackedTasksManager.getTask(task1.getId());
        fileBackedTasksManager.getEpic(epic1.getId());
        fileBackedTasksManager.getSubtask(subtask1.getId());
        fileBackedTasksManager.getTask(task1.getId());
        fileBackedTasksManager.getSubtask(subtask2.getId());
        fileBackedTasksManager.updateTask(task1,"Сходить в магазин", "Молоко, Хлеб", Statuses.DONE);
        System.out.println("Изменили задачу 1");
        fileBackedTasksManager.deleteEpic(epic1.getId());
        System.out.println("Удалили эпик 1");
    }
    @Override
    public Task createTask(String title, String description) {
        Task task = new Task(++id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(Statuses.NEW);
        tasks.put(task.getId(), task);
        saveTasks();
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
        subtasks.put(subtask.getId(), subtask);
        saveTasks();
        return subtask;
    }

    @Override
    public Epic createEpic(String title, String description) {
        Epic epic = new Epic(++id);
        epic.setTitle(title);
        epic.setDescription(description);
        epic.setStatus(Statuses.NEW);
        epics.put(epic.getId(), epic);
        saveTasks();
        return epic;
    }
    @Override
    public Task getTask(Integer id) {
        historyManager.add(tasks.get(id));
        saveHistory();
        return tasks.get(id);
    }

    @Override
    public Subtask getSubtask(Integer id) {
        historyManager.add(subtasks.get(id));
        saveHistory();
        return subtasks.get(id);
    }

    @Override
    public Epic getEpic(Integer id) {
        historyManager.add(epics.get(id));
        saveHistory();
        return epics.get(id);
    }

    @Override
    public void deleteAllTasks() {
        for (Integer id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
        saveTasks();
        saveHistory();
    }

    @Override
    public void deleteAllEpics() {
        for (Integer id : epics.keySet()) {
            historyManager.remove(id);
        }
        epics.clear();
        for (Integer id : subtasks.keySet()) {
            historyManager.remove(id);
        }
        subtasks.clear();
        saveTasks();
        saveHistory();
    }

    @Override
    public void deleteTask(Integer id) {
        historyManager.remove(id);
        tasks.remove(id);
        saveTasks();
        saveHistory();
    }

    @Override
    public void deleteEpic(Integer id) {
        for (Integer i : epics.get(id).subtasksId) {
            historyManager.remove(i);
            subtasks.remove(i);
        }
        historyManager.remove(id);
        epics.remove(id);
        saveTasks();
        saveHistory();
    }

    @Override
    public void deleteSubtask(Integer id) {
        Epic epic = epics.get(subtasks.get(id).getIdEpic());
        historyManager.remove(id);
        epic.subtasksId.remove(id);
        subtasks.remove(id);
        epic.setStatus();
        saveTasks();
        saveHistory();
    }

    @Override
    public void updateTask(Task task, String title, String description, Statuses status) {
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        saveTasks();
    }

    @Override
    public void updateEpic (Epic epic, String title, String description) {
        epic.setTitle(title);
        epic.setDescription(description);
        saveTasks();
    }

    @Override
    public void updateSubtask (Subtask subtask, String title, String description, Statuses status) {
        subtask.setTitle(title);
        subtask.setDescription(description);
        subtask.setStatus(status);
        epics.get(subtask.getIdEpic()).setStatus();
        saveTasks();
    }


    private void createOrReadFile(FileBackedTasksManager fileBackedTasksManager){
        if (Files.exists(TASK_MEMORY)) {
            System.out.println("TASK_MEMORY уже создан.");
            String file = fileBackedTasksManager.read(TASK_MEMORY);
            if (file != null) {
                fileBackedTasksManager.writeTasks(file);
            } else {
                System.out.println("Не удалось прочитать файл");
            }
        } else {
            try {
                Files.createFile(TASK_MEMORY);
                System.out.println("TASK_MEMORY успешно создан.");
            } catch (IOException e) {
                System.out.println("ошибочка");
            }
        }
        if (Files.exists(HISTORY_MEMORY)) {
            System.out.println("HISTORY_MEMORY уже создан.");
            String file = fileBackedTasksManager.read(HISTORY_MEMORY);
            if (file != null) {
                fileBackedTasksManager.writeHistory(file);
            } else {
                System.out.println("Не удалось прочитать файл");
            }
        } else {
            try {
                Files.createFile(HISTORY_MEMORY);
                System.out.println("HISTORY_MEMORY успешно создан.");
            } catch (IOException e) {
                System.out.println("ошибочка");
            }
        }
    }
    private String read (Path path)  {
        try {
            return Files.readString(Path.of(path.toUri()));
        } catch (IOException e) {
            System.out.println("ошибочка");
            return null;
        }
    }
    private void writeTasks (String file) {
        String[] lines = file.split("\n");
        for (int i = 2; i < lines.length; i++) {
            if (lines[i].equals("Epics")) {
                for (int j = i + 2; j < lines.length; j++) {
                    if (lines[j].equals("Subtasks")) {
                        for (int q = j + 2; q < lines.length; q++) {
                            String[] values = lines[q].split(",");
                            if (id < Integer.parseInt(values[2])) {
                                id = Integer.parseInt(values[2]);
                            }
                            Subtask subtask = new Subtask(Integer.parseInt(values[2]));
                            subtask.setTitle(values[0]);
                            subtask.setDescription(values[1]);
                            subtask.setStatus(Statuses.valueOf(values[3]));
                            subtask.setIdEpic(Integer.parseInt(values[4]));
                            subtasks.put(subtask.getId(), subtask);
                        }
                        return;
                    }
                    String[] values = lines[j].split(",");
                    if (id < Integer.parseInt(values[2])) {
                        id = Integer.parseInt(values[2]);
                    }
                    Epic epic = new Epic(Integer.parseInt(values[2]));
                    epic.setTitle(values[0]);
                    epic.setDescription(values[1]);
                    epic.setStatus(Statuses.valueOf(values[3]));
                    String subtasksIdInLine = values[4].replace("[", "");
                    subtasksIdInLine = subtasksIdInLine.replace("]", "");
                    String[] substaskId = subtasksIdInLine.split(",");
                    for (String id : substaskId) {
                        epic.subtasksId.add(Integer.parseInt(id));
                    }
                    epics.put(epic.getId(), epic);
                }
            }
            String[] values = lines[i].split(",");
            if (id < Integer.parseInt(values[2])) {
                id = Integer.parseInt(values[2]);
            }
            Task task = new Task(Integer.parseInt(values[2]));
            task.setTitle(values[0]);
            task.setDescription(values[1]);
            task.setStatus(Statuses.valueOf(values[3]));
            tasks.put(task.getId(), task);
        }
    }

    private void writeHistory (String file) {
        String[] lines = file.split("\n");
        if (lines.length > 1 ) {
            String[] ids = lines[1].split(",");
            for (String id : ids) {
                if (tasks.containsKey(Integer.parseInt(id))) {
                    historyManager.add(tasks.get(Integer.parseInt(id)));
                } else if (epics.containsKey(Integer.parseInt(id))) {
                    historyManager.add(epics.get(Integer.parseInt(id)));
                } else {
                    historyManager.add(subtasks.get(Integer.parseInt(id)));
                }
        }
        }
    }

    private void saveTasks()  {
        try (Writer fileWriter = new FileWriter(TASK_MEMORY.toFile())) {
            fileWriter.write("Tasks\ntitle,description,id,status\n");
            for (Task task : tasks.values()) {
                fileWriter.write(toString(task) + "\n");
            }
            fileWriter.write("Epics\ntitle,description,id,status,subtasksId\n");
            for (Task task : epics.values()) {
                fileWriter.write(toString(task) + "\n");
            }
            fileWriter.write("Subtasks\ntitle,description,id,status,epicId\n");
            for (Task task : subtasks.values()) {
                fileWriter.write(toString(task) + "\n");
            }
        } catch (IOException e) {
            System.out.println("ошибочка");
        }
    }

    private void saveHistory()  {
        try (Writer fileWriter = new FileWriter(HISTORY_MEMORY.toFile())) {
            fileWriter.write("History\n");
            String ids = String.join(",", historyManager.getIdTasks());
            fileWriter.write(ids);
        } catch (IOException e) {
            System.out.println("ошибочка");
        }
    }

    private String toString(Task task) {
        String taskInString = String.join(",", task.getTitle(), task.getDescription(),
                task.getId().toString(), task.getStatus().toString());
        if (task instanceof Epic) {
            taskInString = String.join(",", taskInString, ((Epic) task).getSubtasksId().toString());
        } else if (task instanceof Subtask) {
            taskInString = String.join(",", taskInString, ((Subtask) task).getIdEpic().toString());
        }
        return taskInString;
    }

    private void purification() {
        try (Writer fileWriter = new FileWriter(TASK_MEMORY.toFile())) {
            fileWriter.write("Tasks\ntitle,description,id,status\n");
            fileWriter.write("Epics\ntitle,description,id,status,subtasksId\n");
            fileWriter.write("Subtasks\ntitle,description,id,status,epicId\n");
        } catch (IOException e) {
            System.out.println("ошибочка");
        }
        try (Writer fileWriter = new FileWriter(HISTORY_MEMORY.toFile())) {
            fileWriter.write("History\n");
        } catch (IOException e) {
            System.out.println("ошибочка");
        }
    }
    }


