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

        while (true){
            inputLine = in.nextLine();
            String[] inputParts = inputLine.split(" ", 2);
            String command = inputParts[0];

            switch(command){
            case "bye": printBye();
                return;

            case "list": printList(items);
                break;

            case "mark": if (inputParts.length < 2) {
                System.out.println(LINE);
                System.out.println("Please specify a task number.");
                System.out.println(LINE);
                break;
            }
                markTask(items, Integer.parseInt(inputParts[1]));
                break;

            case "unmark": if (inputParts.length < 2) {
                System.out.println(LINE);
                System.out.println("Please specify a task number.");
                System.out.println(LINE);
                break;
            }
                unmarkTask(items, Integer.parseInt(inputParts[1]));
                break;

            default: items[i] = new Task(inputLine);
                i++;
                printAdded(inputLine);
                break;
            }
        }
    }

    public static void printGreeting(){
        String logo = "  /$$$$$$   /$$$$$$$\n"
                +" /$$__  $$ | $$__  $$\n"
                +"| $$  \\ $$ | $$  \\ $$\n"
                +"| $$  | $$ | $$$$$$$\n"
                +"| $$  | $$ | $$__  $$\n"
                +"| $$/$$ $$ | $$  \\ $$\n"
                +"|  $$$$$$/ | $$$$$$$/\n"
                +" \\____ $$$ |_______/\n"
                +"      \\__/        ";
        System.out.println(logo);

        System.out.println(LINE);
        System.out.println("Hello! I'm QB\nI can add whatever you say to a list till you say \"bye\".");
        System.out.println("Type \"list\" to view the items\nGo ahead, type something!");
        System.out.println(LINE);
    }

    public static void printBye(){
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

    private static void markTask(Task[] items, int itemNumber){
        System.out.println(LINE);

        if (itemNumber<1 || items[itemNumber-1] == null || itemNumber>100){
            System.out.println("Please enter a valid number");
        }

        else if (!items[itemNumber-1].getStatusIcon().equals("X")){
            items[itemNumber-1].markAsDone();
            System.out.println("Nice! I've marked this task as done:\n\t[X] " + items[itemNumber-1].description);
        }

        else{
            System.out.println("Oops! This task is already marked as done");
        }

        System.out.println(LINE);
    }

    private static void unmarkTask(Task[] items, int itemNumber){
        System.out.println(LINE);

        if (itemNumber<1 || items[itemNumber-1] == null || itemNumber>100){
            System.out.println("Please enter a valid number");
        }

        else if (items[itemNumber-1].getStatusIcon().equals("X")){
            items[itemNumber-1].unmarkAsDone();
            System.out.println("Alright! I've unmarked this task as incomplete:\n\t[ ] " + items[itemNumber-1].description);
        }

        else{
            System.out.println("Oops! This task is already marked as incomplete");
        }

        System.out.println(LINE);
    }


}