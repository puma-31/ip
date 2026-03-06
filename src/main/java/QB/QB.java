package QB;

import java.util.ArrayList;

public class QB {

    private static Ui ui;
    private static Storage storage;
    private static TaskList tasks;

    private static final String FILE_PATH = "./data/QBList.txt";

    public static void main(String[] args) throws QBException {

        ui = new Ui();
        ui.printGreeting();

        storage = new Storage("./data/QBList.txt");
        tasks = new TaskList();
        ui.printTaskNumber(storage.loadTasks(tasks.getTasks()));

        while (true) {
            String inputLine = ui.readCommand();
            String[] inputParts = inputLine.split(" ", 2);
            String command = inputParts[0];

            if (command.equals("bye")) {
                ui.printBye();
                ui.close();
                return;
            }

            try {
                handleCommand(command, inputParts, tasks);
            } catch (QBException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    private static void handleCommand(String command, String[] inputParts,
                                     TaskList tasks) throws QBException {
        switch (command) {
        case "list":
            ui.printList(tasks.getTasks());
            break;

        case "mark":
            handleMarkCommand(inputParts, tasks);
            storage.saveTasks(tasks.getTasks());
            break;

        case "unmark":
            handleUnmarkCommand(inputParts, tasks);
            storage.saveTasks(tasks.getTasks());
            break;

        case "delete":
            handleDeleteCommand(inputParts, tasks);
            storage.saveTasks(tasks.getTasks());
            break;

        case "todo":
            handleTodoCommand(inputParts, tasks.getTasks());
            storage.saveTasks(tasks.getTasks());
            break;

        case "deadline":
            handleDeadlineCommand(inputParts, tasks.getTasks());
            storage.saveTasks(tasks.getTasks());
            break;

        case "event":
            handleEventCommand(inputParts, tasks.getTasks());
            storage.saveTasks(tasks.getTasks());
            break;

        default:
            throw new QBException("Sorry, I don't understand the command " + command);
        }
    }

    private static void handleMarkCommand(String[] inputParts, TaskList tasks) throws QBException {
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

    private static void handleUnmarkCommand(String[] inputParts, TaskList tasks) throws QBException {
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

    private static void handleDeleteCommand(String[] inputParts, TaskList tasks) throws QBException {
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

    private static void handleTodoCommand(String[] inputParts, ArrayList<Task> tasks) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a description for your Todo task");
        }

        tasks.add(new Todo(inputParts[1]));
        ui.printAdded(tasks.get(tasks.size() - 1), tasks.size());
    }

    private static void handleDeadlineCommand(String[] inputParts, ArrayList<Task> tasks) throws QBException {
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

    private static void handleEventCommand(String[] inputParts, ArrayList<Task> tasks) throws QBException {
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