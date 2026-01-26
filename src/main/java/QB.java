import java.util.Scanner;

public class QB {
    public static void main(String[] args) {
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
        System.out.println("____________________________________________________________");
        System.out.println("Hello! I'm QB\nI can repeat whatever you say and i'll keep going till you say \"bye\".");
        System.out.println("Go ahead, type something!");
        System.out.println("____________________________________________________________");

        String inputLine;
        Scanner in = new Scanner(System.in);

        while (true){
            inputLine = in.nextLine();

            if (inputLine.equals("bye")){
                System.out.println("____________________________________________________________");
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println("____________________________________________________________");
                break;
            }
            System.out.println("____________________________________________________________");
            System.out.println(inputLine);
            System.out.println("____________________________________________________________");
        }
    }
}