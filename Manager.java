package Tracker;

import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    int id = 0;
    int subTaskId = 0;
    Scanner scanner = new Scanner(System.in);
    HashMap<Integer, Task> taskList = new HashMap<>();

    public void epicStatusCheck() {
        for (Task checkEpic : taskList.values()) {
            if (checkEpic instanceof Epic updateEpic) {
                for (Subtask subtask : updateEpic.subtasks.values()) {
                    if (subtask.status != Status.DONE) {
                        return;
                    }
                }
                checkEpic.setStatus(Status.DONE);
                return;
            }
        }
    }

    public void showFunctions() {
        System.out.println("Выберите функцию:");
        System.out.println("1 - получение списка задач");
        System.out.println("2 - удаление всех задач");
        System.out.println("3 - получение по индентификатору");
        System.out.println("4 - создание задач");
        System.out.println("5 - обновление статуса задачи");
        System.out.println("6 - удаление задач по индентификатору");
        System.out.println("7 - завершить работу");
    }

    public void showTasks() {
        if (isEmptyCheck()) return;
        for (Task showTask : taskList.values()) {
            System.out.println("Название: " + showTask.name);
            System.out.println("Описание: " + showTask.description);
            System.out.println("Статус: " + showTask.status);
        }
    }

    public void removeAll() {
        if (isEmptyCheck()) return;
        taskList.clear();
        System.out.println("Все задачи удалены!");
    }

    public void getById() {
        if (isEmptyCheck()) return;
        while (true) {
            System.out.println("Введите индентификатор:");
            int getById = scanner.nextInt();
            if (taskList.containsKey(getById)) {
                System.out.println("Название: " + taskList.get(getById).name);
                System.out.println("Описание: " + taskList.get(getById).description);
                System.out.println("Статус: " + taskList.get(getById).status);
                return;
            } else {
                System.out.println("Неверный идентификатор!");
            }
        }
    }

    public void createTask() {
        while (true) {
            System.out.println("Что вы хотите создать?");
            System.out.println("1 - задача");
            System.out.println("2 - эпик");
            String command = scanner.nextLine();
            switch (command) {
                case ("1"):
                    System.out.println("Введите название задачи:");
                    String taskName = scanner.nextLine();
                    System.out.println("Введите описание задачи:");
                    String taskDescription = scanner.nextLine();
                    taskList.put(++id, new Task(taskName, taskDescription, Status.NEW));
                    return;
                case ("2"):
                    System.out.println("Введите название эпика:");
                    String epicName = scanner.nextLine();
                    System.out.println("Введите описание эпика:");
                    String epicDescription = scanner.nextLine();
                    Epic newEpic = new Epic(epicName, epicDescription, Status.NEW);

                    System.out.println("Сколько подзадач вы желаете добавить?");
                    int subtasks = scanner.nextInt();
                    for (int i = 0; i < subtasks; i++) {
                        System.out.println("Введите название подзадачи:");
                        String subtaskName = scanner.nextLine();
                        System.out.println("Введите описание подзадачи:");
                        String subtaskDescription = scanner.nextLine();
                        newEpic.subtasks.put(++subTaskId, new Subtask(subtaskName, subtaskDescription, Status.NEW));
                    }
                    taskList.put(++id, newEpic);
                    return;
                default:
                    System.out.println("Неверная команда");
            }
        }
    }

    public void updateStatus() {
        if (isEmptyCheck()) return;
        while (true) {
            System.out.println("Введите индентификатор задачи:");
            int selectId = scanner.nextInt();
            if (taskList.containsKey(selectId)) {
                if (taskList.get(selectId) instanceof Epic selectSubtask) {
                    System.out.println("Выберите подзадачу:");
                    for (Integer key : selectSubtask.subtasks.keySet()) {
                        System.out.println("id: " + key + " - " + selectSubtask.subtasks.get(key).name);
                    }
                    int subtaskId = scanner.nextInt();
                    setNewStatus( selectSubtask.subtasks.get(subtaskId));
                } else {
                    setNewStatus(taskList.get(selectId));
                }
                return;
            } else {
                System.out.println("Неверный идентификатор!");
            }
        }
    }

    //дополнение к updateStatus()
    public void setNewStatus(Task updateId) {
        while (true) {
            System.out.println("Введите новый статус:");
            System.out.println("1 - Новый");
            System.out.println("2 - В процессе");
            System.out.println("3 - Выполнен");
            String newStatus = scanner.nextLine();
            switch (newStatus) {
                case ("1"):
                    taskList.get(updateId).setStatus(Status.NEW);
                    return;
                case ("2"):
                    taskList.get(updateId).setStatus(Status.IN_PROGRESS);
                    return;
                case ("3"):
                    taskList.get(updateId).setStatus(Status.DONE);
                    return;
                default:
                    System.out.println("Неверный номер!");
                    break;
            }
        }
    }

    public void deleteTask() {
        if (isEmptyCheck()) return;
        while (true) {
            System.out.println("Введите идентификатор который желаете удалить:");
            int deleteId = scanner.nextInt();
            if (taskList.containsKey(deleteId)) {
                taskList.remove(deleteId);
                System.out.println("Удаление успешно!");
                return;
            } else {
                System.out.println("Неверный идентификатор!");
            }
        }
    }

    public Boolean isEmptyCheck() {
        if (taskList.isEmpty()) {
            System.out.println("Список задач пуст!");
            return true;
        }
        return false;
    }
}