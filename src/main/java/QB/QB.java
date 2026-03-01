package QB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;

public class QB {

    private static Ui ui;
    private static Storage storage;

    private static final String FILE_PATH = "./data/QBList.txt";

    public static void main(String[] args) throws QBException {

        ui = new Ui();
        ui.printGreeting();

        storage = new Storage("./data/QBList.txt");
        ArrayList<Task> items = new ArrayList<Task>();
        ui.printTaskNumber(storage.loadTasks(items));

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
                handleCommand(command, inputParts, items);
            } catch (QBException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    private static void handleCommand(String command, String[] inputParts,
                                     ArrayList<Task> items) throws QBException {
        switch (command) {
        case "list":
            ui.printList(items);
            break;

        case "mark":
            handleMarkCommand(inputParts, items);
            storage.saveTasks(items);
            break;

        case "unmark":
            handleUnmarkCommand(inputParts, items);
            storage.saveTasks(items);
            break;

        case "delete":
            handleDeleteCommand(inputParts, items);
            storage.saveTasks(items);
            break;

        case "todo":
            handleTodoCommand(inputParts, items);
            storage.saveTasks(items);
            break;

        case "deadline":
            handleDeadlineCommand(inputParts, items);
            storage.saveTasks(items);
            break;

        case "event":
            handleEventCommand(inputParts, items);
            storage.saveTasks(items);
            break;

        default:
            throw new QBException("Sorry, I don't understand the command " + command);
        }
    }

    private static void handleMarkCommand(String[] inputParts, ArrayList<Task> items) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please specify a task number.");
        }

        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            markTask(items, taskNumber);
        } catch (NumberFormatException e) {
            throw new QBException("Task number must be a valid integer.");
        }
    }

    private static void handleUnmarkCommand(String[] inputParts, ArrayList<Task> items) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please specify a task number.");
        }
        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            unmarkTask(items, taskNumber);
        } catch (NumberFormatException e) {
            throw new QBException("Task number must be a valid integer.");
        }
    }

    private static void handleDeleteCommand(String[] inputParts, ArrayList<Task> items) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please specify a task number");
        }

        try {
            int taskNumber = Integer.parseInt(inputParts[1]);
            deleteTask(items, taskNumber);
        } catch (NumberFormatException e) {
            throw new QBException("Task number must be a valid integer.");
        }
    }

    private static void handleTodoCommand(String[] inputParts, ArrayList<Task> items) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a description for your Todo task");
        }

        items.add(new Todo(inputParts[1]));
        ui.printAdded(items.get(items.size() - 1), items.size());
    }

    private static void handleDeadlineCommand(String[] inputParts, ArrayList<Task> items) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a task description and deadline using /by.");
        }

        String[] deadlineParts = inputParts[1].split(" /by ", 2);
        if (deadlineParts.length < 2) {
            throw new QBException("Please use format: deadline <description> /by <time>");
        }

        items.add(new Deadline(deadlineParts[0], deadlineParts[1]));
        ui.printAdded(items.get(items.size() - 1), items.size());
    }

    private static void handleEventCommand(String[] inputParts, ArrayList<Task> items) throws QBException {
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

        items.add(new Event(eventParts[0], timeParts[0], timeParts[1]));
        ui.printAdded(items.get(items.size() - 1), items.size());
    }

    private static boolean hasNoArguments(String[] inputParts) {
        return inputParts.length < 2;
    }

    private static void markTask(ArrayList<Task> items, int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, items)) {
            throw new QBException("Please enter a valid number");
        } else if (!items.get(itemNumber - 1).getStatusIcon().equals("X")) {
            items.get(itemNumber - 1).markAsDone();
            ui.printMarked(items.get(itemNumber - 1));
        } else {
            throw new QBException("Oops! This task is already marked as done");
        }
    }

    private static void unmarkTask(ArrayList<Task> items, int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, items)) {
            throw new QBException("Please enter a valid number");
        } else if (items.get(itemNumber - 1).getStatusIcon().equals("X")) {
            items.get(itemNumber - 1).unmarkAsDone();
            ui.printUnmarked(items.get(itemNumber - 1));
        } else {
            throw new QBException("Oops! This task is already marked as incomplete");
        }
    }

    private static void deleteTask(ArrayList<Task> items, int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, items)) {
            throw new QBException("Please enter a valid number");
        } else {
            ui.printDeleted(items.get(itemNumber-1), items.size()-1);
            items.remove(itemNumber - 1);
        }
    }

    private static boolean isInvalidTaskNumber(int itemNumber, ArrayList<Task> items) {
        return itemNumber < 1 || itemNumber > items.size();
    }
}