package QB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;

public class QB {

    private static Ui ui;

    private static final String FILE_PATH = "./data/QBList.txt";

    public static void main(String[] args) throws QBException {

        ui = new Ui();
        ui.printGreeting();
        ArrayList<Task> items = new ArrayList<Task>();
        ui.printTaskNumber(loadTasks(items));

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
            saveTasks(items);
            break;

        case "unmark":
            handleUnmarkCommand(inputParts, items);
            saveTasks(items);
            break;

        case "delete":
            handleDeleteCommand(inputParts, items);
            saveTasks(items);
            break;

        case "todo":
            handleTodoCommand(inputParts, items);
            saveTasks(items);
            break;

        case "deadline":
            handleDeadlineCommand(inputParts, items);
            saveTasks(items);
            break;

        case "event":
            handleEventCommand(inputParts, items);
            saveTasks(items);
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
    private static void saveTasks(ArrayList<Task> items) {
        try {
            File file = new File(FILE_PATH);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(FILE_PATH);
            for (int i = 0; i < items.size(); i++) {
                fw.write(taskToFileFormat(items.get(i)) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    private static String taskToFileFormat(Task task) {
        int done = task.getStatusIcon().equals("X") ? 1 : 0;
        String result = "";
        if (task instanceof Todo) {
            Todo t = (Todo) task;
            result =  "T | " + done + " | " + t.description;
        } else if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            result =  "D | " + done + " | " + d.description + " | " + d.getBy();
        } else if (task instanceof Event) {
            Event e = (Event) task;
            result =  "E | " + done + " | " + e.description + " | " + e.getFrom() + " | " + e.getTo();
        }
        return result;
    }

    private static int loadTasks(ArrayList<Task> items) {
        File file = new File(FILE_PATH);

        if (!file.exists()) {
            ui.printLoadingError();
            return 0;
        }

        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                try {
                    String[] parts = line.split(" \\| ");
                    String type = parts[0];
                    boolean isDone = parts[1].equals("1");

                    switch (type) {
                    case "T":
                        items.add(new Todo(parts[2]));
                        break;
                    case "D":
                        items.add(new Deadline(parts[2], parts[3]));
                        break;
                    case "E":
                        items.add(new Event(parts[2], parts[3], parts[4]));
                        break;
                    default:
                        System.out.println("Skipping corrupted line: " + line);
                        continue;
                    }

                    if (isDone) items.get(items.size()-1).markAsDone();

                } catch (Exception e) {
                    System.out.println("Skipping corrupted line: " + line);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return items.size();
    }

    private static boolean isInvalidTaskNumber(int itemNumber, ArrayList<Task> items) {
        return itemNumber < 1 || itemNumber > items.size();
    }
}