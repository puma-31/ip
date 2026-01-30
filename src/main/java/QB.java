import java.util.Scanner;

public class QB {
    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        printGreeting();
        String inputLine;
        Scanner in = new Scanner(System.in);

        Task[] items = new Task[100];
        int i = 0;

        while (true) {
            inputLine = in.nextLine();
            String[] inputParts = inputLine.split(" ", 2);
            String command = inputParts[0];

            switch (command) {
            case "bye":
                printBye();
                return;

            case "list":
                printList(items);
                break;

            case "mark":
                if (inputParts.length < 2) {
                System.out.println(LINE);
                System.out.println("Please specify a task number.");
                System.out.println(LINE);
                break;
            }
                markTask(items, Integer.parseInt(inputParts[1]));
                break;

            case "unmark":
                if (inputParts.length < 2) {
                System.out.println(LINE);
                System.out.println("Please specify a task number.");
                System.out.println(LINE);
                break;
            }
                unmarkTask(items, Integer.parseInt(inputParts[1]));
                break;

            default:
                items[i] = new Task(inputLine);
                i++;
                printAdded(inputLine);
                break;
            }
        }
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
                You can add tasks by just listing them out
                To view your tasks, type "list"
                mark or unmark tasks as completed by providing their list number
                to exit, just type "bye"!""";

        System.out.println(LINE);
        System.out.println(greeting);
        System.out.println(LINE);
    }

    public static void printBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    private static void printAdded(String item) {
        System.out.println(LINE);
        System.out.println("added: " + item);
        System.out.println(LINE);
    }

    private static void printList(Task[] items) {
        System.out.println(LINE);
        int i = 0;
        while (items[i] != null){
            System.out.println((i + 1) + ". " + "[" + items[i].getStatusIcon() + "] "+ items[i].description);
            i++;
        }
        System.out.println(LINE);
    }

    private static void markTask(Task[] items, int itemNumber) {
        System.out.println(LINE);

        if (itemNumber<1 || items[itemNumber-1] == null || itemNumber>100) {
            System.out.println("Please enter a valid number");
        }

        else if (!items[itemNumber-1].getStatusIcon().equals("X")) {
            items[itemNumber-1].markAsDone();
            System.out.println("Nice! I've marked this task as done:\n\t[X] " + items[itemNumber-1].description);
        }

        else {
            System.out.println("Oops! This task is already marked as done");
        }

        System.out.println(LINE);
    }

    private static void unmarkTask(Task[] items, int itemNumber) {
        System.out.println(LINE);

        if (itemNumber<1 || items[itemNumber-1] == null || itemNumber>100) {
            System.out.println("Please enter a valid number");
        }

        else if (items[itemNumber-1].getStatusIcon().equals("X")) {
            items[itemNumber-1].unmarkAsDone();
            System.out.println("Alright! I've unmarked this task as incomplete:\n\t[ ] " + items[itemNumber-1].description);
        }

        else {
            System.out.println("Oops! This task is already marked as incomplete");
        }

        System.out.println(LINE);
    }
}