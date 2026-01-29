import java.util.Scanner;

public class QB {
    private static final String LINE =
            "____________________________________________________________";

    public static void main(String[] args) {
        printGreeting();
        String inputLine;
        Scanner in = new Scanner(System.in);

        String[] items = new String[100];
        int i = 0;

        while (true){
            inputLine = in.nextLine();

            if (inputLine.equals("bye")){
                printBye();
                break;
            }

            if (inputLine.equals("list")){
                printList(items);
            }

            else {
                items[i] = inputLine;
                i++;
                printAdded(inputLine);
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

    private static void printList(String[] items) {
        System.out.println(LINE);
        for (int i = 0; i < 100; i++) {
            if (items[i] != null) {
                System.out.println((i + 1) + ". " + items[i]);
            }
        }
        System.out.println(LINE);
    }
}