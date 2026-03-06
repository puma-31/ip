package QB;

import java.util.ArrayList;

/**
 * Parses user input and executes the corresponding command,
 * coordinating between TaskList, Ui, and Storage.
 */
public class Parser {

    /**
     * Parses a full input line and executes the appropriate command.
     *
     * @param inputLine The raw input string from the user.
     * @param tasks     The current task list.
     * @param ui        The Ui instance for displaying output.
     * @param storage   The Storage instance for saving changes.
     * @throws QBException If the command is unknown or input is malformed.
     */
    public static void parse(String inputLine, TaskList tasks, Ui ui, Storage storage) throws QBException {
        String[] inputParts = inputLine.split(" ", 2);
        String command = inputParts[0];

        switch (command) {
        case "list":
            ui.printList(tasks.getTasks());
            break;

        case "mark":
            handleMarkCommand(inputParts, tasks, ui);
            storage.saveTasks(tasks.getTasks());
            break;

        case "unmark":
            handleUnmarkCommand(inputParts, tasks, ui);
            storage.saveTasks(tasks.getTasks());
            break;

        case "delete":
            handleDeleteCommand(inputParts, tasks, ui);
            storage.saveTasks(tasks.getTasks());
            break;

        case "find":
            if (hasNoArguments(inputParts)) {
                throw new QBException("Please provide a keyword to search for.");
            }
            ui.printFound(tasks.find(inputParts[1]), command);
            break;

        case "todo":
            handleTodoCommand(inputParts, tasks.getTasks(), ui);
            storage.saveTasks(tasks.getTasks());
            break;

        case "deadline":
            handleDeadlineCommand(inputParts, tasks.getTasks(), ui);
            storage.saveTasks(tasks.getTasks());
            break;

        case "event":
            handleEventCommand(inputParts, tasks.getTasks(), ui);
            storage.saveTasks(tasks.getTasks());
            break;

        default:
            throw new QBException("Sorry, I don't understand the command " + command);
        }
    }
    private static void handleMarkCommand(String[] inputParts, TaskList tasks, Ui ui) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please specify a task number.");
        }
        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            tasks.markTask(taskNumber);
            ui.printMarked(tasks.get(taskNumber - 1));
        } catch (NumberFormatException e) {
            throw new QBException("Task number must be a valid integer.");
        }
    }

    private static void handleUnmarkCommand(String[] inputParts, TaskList tasks, Ui ui) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please specify a task number.");
        }
        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            tasks.unmarkTask(taskNumber);
            ui.printUnmarked(tasks.get(taskNumber - 1));
        } catch (NumberFormatException e) {
            throw new QBException("Task number must be a valid integer.");
        }
    }

    private static void handleDeleteCommand(String[] inputParts, TaskList tasks, Ui ui) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please specify a task number");
        }

        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            Task deletedTask = tasks.deleteTask(taskNumber);
            ui.printDeleted(deletedTask, tasks.size());
        } catch (NumberFormatException e) {
            throw new QBException("Task number must be a valid integer.");
        }
    }

    private static void handleTodoCommand(String[] inputParts, ArrayList<Task> tasks, Ui ui) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a description for your Todo task");
        }

        tasks.add(new Todo(inputParts[1]));
        ui.printAdded(tasks.get(tasks.size() - 1), tasks.size());
    }

    private static void handleDeadlineCommand(String[] inputParts, ArrayList<Task> tasks, Ui ui) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a task description and deadline using /by.");
        }

        String[] deadlineParts = inputParts[1].split(" /by ", 2);
        if (deadlineParts.length < 2) {
            throw new QBException("Please use format: deadline <description> /by <time>");
        }

        tasks.add(new Deadline(deadlineParts[0], deadlineParts[1]));
        ui.printAdded(tasks.get(tasks.size() - 1), tasks.size());
    }

    private static void handleEventCommand(String[] inputParts, ArrayList<Task> tasks, Ui ui) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a task description, start and end time.");
        }

        String[] eventParts = inputParts[1].split(" /from ", 2);
        if (eventParts.length < 2) {
            throw new QBException("Please provide an event start time using /from");
        }

        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2) {
            throw new QBException("Please provide an event start time using /to");
        }

        tasks.add(new Event(eventParts[0], timeParts[0], timeParts[1]));
        ui.printAdded(tasks.get(tasks.size() - 1), tasks.size());
    }

    private static boolean hasNoArguments(String[] inputParts) {
        return inputParts.length < 2;
    }
}
