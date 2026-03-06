package QB;

import java.util.ArrayList;

/**
 * Represents the list of tasks managed by the QB application.
 * Provides operations to add, remove, mark, and search tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<Task>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Returns the tasks in the TaskList.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Returns the size of the TaskList.
     */
    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Adds a task to the list.
     *
     * @param task The task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes and returns the task at the specified 1-based position.
     *
     * @param itemNumber The 1-based index of the task to delete.
     * @return The deleted task.
     * @throws QBException If the task number is out of range.
     */
    public Task deleteTask(int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, tasks)) {
            throw new QBException("Please enter a valid number");
        }
        return tasks.remove(itemNumber - 1);
    }

    /**
     * Marks the task at the specified 1-based position as done.
     *
     * @param itemNumber The 1-based index of the task to mark.
     * @throws QBException If the task number is invalid or already marked.
     */
    public void markTask(int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, tasks)) {
            throw new QBException("Please enter a valid number");
        } else if (!tasks.get(itemNumber - 1).getStatusIcon().equals("X")) {
            tasks.get(itemNumber - 1).markAsDone();
        } else {
            throw new QBException("Oops! This task is already marked as done");
        }
    }

    /**
     * Unmarks the task at the specified 1-based position as incomplete.
     *
     * @param itemNumber The 1-based index of the task to unmark.
     * @throws QBException If the task number is invalid or already unmarked.
     */
    public void unmarkTask(int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, tasks)) {
            throw new QBException("Please enter a valid number");
        } else if (!tasks.get(itemNumber - 1).getStatusIcon().equals("X")) {
            tasks.get(itemNumber - 1).unmarkAsDone();
        } else {
            throw new QBException("Oops! This task is already marked as incomplete");
        }
    }

    /**
     * Searches for tasks whose descriptions contain the given keyword.
     *
     * @param keyword The keyword to search for.
     * @return A list of tasks whose descriptions contain the keyword.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();
        for (Task task : tasks) {
            if (task.description.contains(keyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    private static boolean isInvalidTaskNumber(int itemNumber, ArrayList<Task> items) {
        return itemNumber < 1 || itemNumber > items.size();
    }
}
