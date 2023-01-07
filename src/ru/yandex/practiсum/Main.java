package ru.yandex.practiсum;

import ru.yandex.practiсum.managers.Managers;
import ru.yandex.practiсum.managers.TaskManager;
import ru.yandex.practiсum.tasks.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("Тестирование версии 1.2");
        TaskManager taskManager = Managers.getDefault();
        Task task1 = taskManager.createTask("Задача 1", "Описаиние задачи 1");
        System.out.println("Создали задачу 1");
        taskManager.tasks.put(task1.getId(), task1);
        System.out.println("Сохранили задачу 1");
        System.out.println(taskManager.getTask(task1.getId()));
        taskManager.updateTask(task1,"Сходить в магазин", "Молоко, Хлеб", Statuses.DONE);
        System.out.println("Изменили задачу 1");
        System.out.println(taskManager.getTask(task1.getId()));
        Task task2 = taskManager.createTask("Задача 2", "Описаиние задачи 2");
        System.out.println("Создали задачу 2");
        taskManager.tasks.put(task2.getId(), task2);
        System.out.println("Сохранили задачу 2");
        Task task3 = taskManager.createTask("Задача 3", "Описаиние задачи 3");
        System.out.println("Создали задачу 3");
        taskManager.tasks.put(task3.getId(), task3);
        System.out.println("Сохранили задачу 3");
        System.out.println(taskManager.getAllTasks());
        System.out.println("Получили список всех задач");
        taskManager.deleteTask(task2.getId());
        System.out.println("Удалили задачу 2");
        System.out.println(taskManager.getAllTasks());
        System.out.println("Получили список всех задач");
        taskManager.deleteAllTasks();
        System.out.println("Удалили все задачи");
        System.out.println(taskManager.getAllTasks());
        System.out.println("Получили список всех задач");
        Epic epic1 = taskManager.createEpic("Эпик 1", "Описание Эпика 1");
        System.out.println("Создали эпик 1");
        taskManager.epics.put(epic1.getId(), epic1);
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println("Сохранили эпик 1");
        taskManager.updateEpic(epic1, "Починить ноутбук", "Проблемы с питанием");
        System.out.println("Изменили эпик 1");
        System.out.println(taskManager.getEpic(epic1.getId()));
        System.out.println(taskManager.getAllEpics());
        System.out.println("Получили список всех эпиков");
        Subtask subtask1 = taskManager.createSubTask("Подзадача 1", "Описание подзадачи 1", epic1);
        System.out.println("Создали подзадачу 1");
        taskManager.subtasks.put(subtask1.getId(), subtask1);
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println("Сохранили подзадачу 1");
        System.out.println(taskManager.historyManager.getHistory());
        System.out.println("Получили историю задач");
        Subtask subtask2 = taskManager.createSubTask("Подзадача 2", "Описание подзадачи 2", epic1);
        System.out.println("Создали подзадачу 2");
        taskManager.subtasks.put(subtask2.getId(), subtask2);
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println("Сохранили подзадачу 2");
        Subtask subtask3 = taskManager.createSubTask("Подзадача 3", "Описание подзадачи 3", epic1);
        System.out.println("Создали подзадачу 3");
        taskManager.subtasks.put(subtask3.getId(), subtask3);
        System.out.println(taskManager.getSubtask(subtask3.getId()));
        System.out.println("Сохранили подзадачу 2");
        System.out.println(taskManager.getAllSubtasks());
        System.out.println("Получили список всех подзадач");
        taskManager.updateSubtask(subtask1, "Провести диагностику",
                "Описание диагностики", Statuses.DONE);
        System.out.println("Изменили подзадачу 1");
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        taskManager.updateSubtask(subtask2, "Купили коплектующие",
                "Аккумулятор, зарядка", Statuses.DONE);
        System.out.println("Изменили подзадачу 2");
        System.out.println(taskManager.getSubtask(subtask2.getId()));
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        taskManager.updateSubtask(subtask3, "Замена комплектующих",
                "Достать и засунть", Statuses.DONE);
        System.out.println("Изменили подзадачу 3");
        System.out.println(taskManager.getSubtask(subtask1.getId()));
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        taskManager.updateSubtask(subtask3, "Замена комплектующих",
                "Достать и засунть", Statuses.IN_PROGRESS);
        System.out.println("Изменили подзадачу 3");
        System.out.println(taskManager.getSubtask(subtask3.getId()));
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        taskManager.deleteSubtask(subtask3.getId());
        System.out.println("Удалили подзадачу 3");
        System.out.println("Статус эпика 1: " + epic1.getStatus());
        taskManager.deleteEpic(epic1.getId());
        System.out.println("Удалили эпик 1");
        System.out.println(taskManager.getAllEpics());
        System.out.println("Получили список всех эпиков");
        System.out.println(taskManager.getAllSubtasks());
        System.out.println("Получили список всех подзадач");
        System.out.println(taskManager.historyManager.getHistory());
        System.out.println("Получили историю задач");
        System.out.println("Тестирование успешно завершенно!");
    }
}
