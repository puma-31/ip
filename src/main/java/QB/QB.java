package QB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;

public class QB {
    private static final String LINE =
            "____________________________________________________________";
    private static final String FILE_PATH = "./data/QBList.txt";

    public static void main(String[] args) throws QBException {
        printGreeting();
        Scanner in = new Scanner(System.in);
        ArrayList<Task> items = new ArrayList<Task>();
        System.out.print("You currently have " + loadTasks(items) + " tasks in your program\n");
        System.out.print(LINE + "\n");

        while (true) {
            String inputLine = in.nextLine();
            String[] inputParts = inputLine.split(" ", 2);
            String command = inputParts[0];

            if (command.equals("bye")) {
                printBye();
                in.close();
                return;
            }

            try {
                handleCommand(command, inputParts, items);
            } catch (QBException e) {
                printError(e.getMessage());
            }
        }
    }

    private static void handleCommand(String command, String[] inputParts,
                                     ArrayList<Task> items) throws QBException {
        switch (command) {
        case "list":
            printList(items);
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
        printAdded(items.get(items.size() - 1), items);
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
        printAdded(items.get(items.size() - 1), items);
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
        printAdded(items.get(items.size() - 1), items);
    }

    private static boolean hasNoArguments(String[] inputParts) {
        return inputParts.length < 2;
    }

    private static void printError(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    public static void printGreeting() {
        String logo = """
                  /$$$$$$   /$$$$$$$
                 /$$__  $$ | $$__  $$
                | $$  \\ $$ | $$  \\ $$
                | $$  | $$ | $$$$$$$
                | $$  | $$ | $$__  $$
                | $$/$$ $$ | $$  \\ $$
                |  $$$$$$/ | $$$$$$$/
                 \\____ $$$ |_______/
                      \\__/       \s""";
        System.out.println(logo);

        String greeting = """
                Hello! I'm QB, your personal TaskTracker
                What can I do for you?
                - Add a todo: todo <description>
                - Add a deadline: deadline <description> /by <time>
                - Add an event: event <description> /from <start> /to <end>
                - View all tasks: list
                - Mark task as done: mark <task number>
                - Unmark task: unmark <task number>
                - Exit: bye""";

        System.out.println(LINE);
        System.out.println(greeting);
        System.out.println(LINE);
    }

    public static void printBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static void printAdded(Task task, ArrayList<Task> items) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + items.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void printList(ArrayList<Task> items) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + "." + items.get(i));
        }
        System.out.println(LINE);
    }

    private static void markTask(ArrayList<Task> items, int itemNumber) throws QBException {


        if (isInvalidTaskNumber(itemNumber, items)) {
            throw new QBException("Please enter a valid number");
        } else if (!items.get(itemNumber - 1).getStatusIcon().equals("X")) {
            items.get(itemNumber - 1).markAsDone();
            System.out.println(LINE);
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + items.get(itemNumber - 1));
            System.out.println(LINE);
        } else {
            throw new QBException("Oops! This task is already marked as done");
        }
    }

    private static void unmarkTask(ArrayList<Task> items, int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, items)) {
            throw new QBException("Please enter a valid number");
        } else if (items.get(itemNumber - 1).getStatusIcon().equals("X")) {
            items.get(itemNumber - 1).unmarkAsDone();
            System.out.println(LINE);
            System.out.println("Alright! I've unmarked this task as incomplete:");
            System.out.println("  " + items.get(itemNumber - 1));
            System.out.println(LINE);
        } else {
            throw new QBException("Oops! This task is already marked as incomplete");
        }
    }

    private static void deleteTask(ArrayList<Task> items, int itemNumber) throws QBException {
        if (isInvalidTaskNumber(itemNumber, items)) {
            throw new QBException("Please enter a valid number");
        } else {
            System.out.println(LINE);
            System.out.println("Deleted this task:");
            System.out.println("  " + items.get(itemNumber - 1));
            System.out.println("Now you have " + (items.size() - 1) + " tasks in your list:");
            System.out.println(LINE);
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
            System.out.println("No saved tasks found. Starting fresh.");
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