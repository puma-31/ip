package QB;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interaction for the QB application,
 * including reading input and displaying output to the console.
 */
public class Ui {
    private static final String LINE =
            "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command entered by the user.
     *
     * @return The full input line as a String.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the Scanner used to read user input.
     */
    public void close() {
        scanner.close();
    }

    public void printLine() {
        System.out.println(LINE);
    }

    /**
     * Displays the greeting message and list of available commands.
     */
    public void printGreeting() {
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
                - Delete task: delete <task number>
                - Exit: bye""";

        System.out.println(LINE);
        System.out.println(greeting);
        System.out.println(LINE);
    }

    /**
     * Displays the goodbye message.
     */
    public void printBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Displays an error message to the user.
     *
     * @param message The error message to display.
     */
    public void printError(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    public void printLoadingError() {
        printError("No saved tasks found. Starting fresh.");
    }

    /**
     * Displays a confirmation message after a task is added.
     *
     * @param task The task that was added.
     * @param size The updated total number of tasks in the list.
     */
    public void printAdded(Task task, int size) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + size + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation message and size after a task is deleted.
     *
     * @param task The task that was added.
     * @param size The updated total number of tasks in the list.
     */
    public void printDeleted(Task task, int size) {
            System.out.println(LINE);
            System.out.println("Deleted this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + size + " tasks in your list:");
            System.out.println(LINE);
    }

    /**
     * Displays all tasks currently in the list.
     *
     * @param items The list of tasks to display.
     */
    public void printList(ArrayList<Task> items) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + "." + items.get(i));
        }
        System.out.println(LINE);
    }

    public void printTaskNumber(int count) {
        System.out.print("You currently have " + count + " tasks in your program\n");
        System.out.print(LINE + "\n");
    }

    /**
     * Displays a confirmation message after a task is marked as done.
     *
     * @param task The task that was marked.
     */
    public void printMarked(Task task) {
        System.out.println(LINE);
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Displays a confirmation message after a task is unmarked.
     *
     * @param task The task that was unmarked.
     */
    public void printUnmarked(Task task) {
        System.out.println(LINE);
        System.out.println("Alright! I've unmarked this task as incomplete:");
        System.out.println("  " + task);
        System.out.println(LINE);
    }

    /**
     * Displays tasks that match a search keyword.
     *
     * @param matches The list of matching tasks to display.
     */
    public void printFound(ArrayList<Task> matches, String keyword) {
        System.out.println(LINE);
        System.out.println("This are the tasks I found for " + keyword + ":");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println((i + 1) + "." + matches.get(i));
        }
        System.out.println(LINE);
    }
}



