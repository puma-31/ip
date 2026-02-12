import java.util.Scanner;

public class QB {
    private static final String LINE =
            "____________________________________________________________";
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) throws QBException {
        printGreeting();
        Scanner in = new Scanner(System.in);
        Task[] items = new Task[MAX_TASKS];
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
                                     Task[] items, int taskCount) throws QBException {
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

    private static void handleMarkCommand(String[] inputParts, Task[] items) {
        if (hasNoArguments(inputParts)) {
            printError("Please specify a task number.");
            return;
        }
        markTask(items, Integer.parseInt(inputParts[1]));
    }

    private static void handleUnmarkCommand(String[] inputParts, Task[] items) {
        if (hasNoArguments(inputParts)) {
            printError("Please specify a task number.");
            return;
        }
        unmarkTask(items, Integer.parseInt(inputParts[1]));
    }

    private static int handleTodoCommand(String[] inputParts, Task[] items, int taskCount) throws QBException {
        if (hasNoArguments(inputParts)) {
            throw new QBException("Please provide a description for your Todo task");
        }

        items[taskCount] = new Todo(inputParts[1]);
        taskCount++;
        printAdded(items[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleDeadlineCommand(String[] inputParts, Task[] items, int taskCount) {
        if (hasNoArguments(inputParts)) {
            printError("Please provide a task description and deadline.");
            return taskCount;
        }

        String[] deadlineParts = inputParts[1].split(" /by ", 2);
        if (deadlineParts.length < 2) {
            printError("Please use format: deadline <description> /by <time>");
            return taskCount;
        }

        items[taskCount] = new Deadline(deadlineParts[0], deadlineParts[1]);
        taskCount++;
        printAdded(items[taskCount - 1], taskCount);
        return taskCount;
    }

    private static int handleEventCommand(String[] inputParts, Task[] items, int taskCount) {
        if (hasNoArguments(inputParts)) {
            printError("Please provide a task description, start and end time.");
            return taskCount;
        }

        String[] eventParts = inputParts[1].split(" /from ", 2);
        if (eventParts.length < 2) {
            printError("Please use format: event <description> /from <start> /to <end>");
            return taskCount;
        }

        String[] timeParts = eventParts[1].split(" /to ", 2);
        if (timeParts.length < 2) {
            printError("Please use format: event <description> /from <start> /to <end>");
            return taskCount;
        }

        items[taskCount] = new Event(eventParts[0], timeParts[0], timeParts[1]);
        taskCount++;
        printAdded(items[taskCount - 1], taskCount);
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

    private static void printList(Task[] items, int taskCount) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + items[i]);
        }
        System.out.println(LINE);
    }

    private static void markTask(Task[] items, int itemNumber) {
        System.out.println(LINE);

        if (isValidTaskNumber(itemNumber, items)) {
            System.out.println("Please enter a valid number");
        } else if (!items[itemNumber - 1].getStatusIcon().equals("X")) {
            items[itemNumber - 1].markAsDone();
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + items[itemNumber - 1]);
        } else {
            System.out.println("Oops! This task is already marked as done");
        }

        System.out.println(LINE);
    }

    private static void unmarkTask(Task[] items, int itemNumber) {
        System.out.println(LINE);

        if (isValidTaskNumber(itemNumber, items)) {
            System.out.println("Please enter a valid number");
        } else if (items[itemNumber - 1].getStatusIcon().equals("X")) {
            items[itemNumber - 1].unmarkAsDone();
            System.out.println("Alright! I've unmarked this task as incomplete:");
            System.out.println("  " + items[itemNumber - 1]);
        } else {
            System.out.println("Oops! This task is already marked as incomplete");
        }

        System.out.println(LINE);
    }

    private static boolean isValidTaskNumber(int itemNumber, Task[] items) {
        boolean isWithinRange = itemNumber >= 1 && itemNumber <= MAX_TASKS;
        boolean taskExists = items[itemNumber - 1] != null;
        return !isWithinRange || !taskExists;
    }
}