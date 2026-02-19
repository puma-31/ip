package QB;

import java.util.ArrayList;
import java.util.Scanner;

public class QB {
    private static final String LINE =
            "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) throws QBException {
        printGreeting();
        Scanner in = new Scanner(System.in);
        ArrayList<Task> items = new ArrayList<Task>();
        int taskCount = 0;

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
                taskCount = handleCommand(command, inputParts, items, taskCount);
            } catch (QBException e) {
                printError(e.getMessage());
            }
        }
    }

    private static int handleCommand(String command, String[] inputParts,
                                     ArrayList<Task> items, int taskCount) throws QBException {
        switch (command) {
        case "list":
            printList(items, taskCount);
            break;

        case "mark":
            handleMarkCommand(inputParts, items);
            break;

        case "unmark":
            handleUnmarkCommand(inputParts, items);
            break;

        case "todo":
            taskCount = handleTodoCommand(inputParts, items, taskCount);
            break;

        case "deadline":
            taskCount = handleDeadlineCommand(inputParts, items, taskCount);
            break;

        case "event":
            taskCount = handleEventCommand(inputParts, items, taskCount);
            break;

        default:
            throw new QBException("Sorry, I don't understand the command " + command);
        }

        return taskCount;
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

    private static int handleTodoCommand(String[] inputParts, ArrayList<Task> items, int taskCount) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a description for your Todo task");
        }

        items.add(taskCount, new Todo(inputParts[1]));
        taskCount++;
        printAdded(items.get(taskCount - 1), taskCount);
        return taskCount;
    }

    private static int handleDeadlineCommand(String[] inputParts, ArrayList<Task> items, int taskCount) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a task description and deadline using /by.");
        }

        String[] deadlineParts = inputParts[1].split(" /by ", 2);
        if (deadlineParts.length < 2) {
            throw new QBException("Please use format: deadline <description> /by <time>");
        }

        items.add(taskCount, new Deadline(deadlineParts[0], deadlineParts[1]));
        taskCount++;
        printAdded(items.get(taskCount - 1), taskCount);
        return taskCount;
    }

    private static int handleEventCommand(String[] inputParts, ArrayList<Task> items, int taskCount) throws QBException {
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

        items.add(taskCount, new Event(eventParts[0], timeParts[0], timeParts[1]));
        taskCount++;
        printAdded(items.get(taskCount - 1), taskCount);
        return taskCount;
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

    private static void printAdded(Task task, int taskCount) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        System.out.println(LINE);
    }

    private static void printList(ArrayList<Task> items, int taskCount) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
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

    private static boolean isInvalidTaskNumber(int itemNumber, ArrayList<Task> items) {
        return itemNumber < 1 || itemNumber > items.size();
    }
}