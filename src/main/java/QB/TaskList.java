package QB;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task deleteTask(int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, tasks)) {
            throw new QBException("Please enter a valid number");
        }
        return tasks.remove(itemNumber - 1);
    }

    public void markTask(int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, tasks)) {
            throw new QBException("Please enter a valid number");
        } else if (!tasks.get(itemNumber - 1).getStatusIcon().equals("X")) {
            tasks.get(itemNumber - 1).markAsDone();
        } else {
            throw new QBException("Oops! This task is already marked as done");
        }
    }

    public void unmarkTask(int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, tasks)) {
            throw new QBException("Please enter a valid number");
        } else if (!tasks.get(itemNumber - 1).getStatusIcon().equals("X")) {
            tasks.get(itemNumber - 1).unmarkAsDone();
        } else {
            throw new QBException("Oops! This task is already marked as incomplete");
        }
    }

    private static boolean isInvalidTaskNumber(int itemNumber, ArrayList<Task> items) {
        return itemNumber < 1 || itemNumber > items.size();
    }
}
