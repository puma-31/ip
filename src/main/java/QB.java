import java.util.Scanner;

public class QB {
    private static final String LINE =
            "____________________________________________________________";
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
        System.out.println(LINE);
        System.out.println("Hello! I'm QB\nI can add whatever you say to a list till you say \"bye\".");
        System.out.println("Type \"list\" to view the items\nGo ahead, type something!");
        System.out.println(LINE);

        String inputLine;
        Scanner in = new Scanner(System.in);

        String[] itemList = new String[100];
        int i = 0;

        while (true){
            inputLine = in.nextLine();

            if (inputLine.equals("bye")){
                System.out.println(LINE);
                System.out.println("Bye. Hope to see you again soon!");
                System.out.println(LINE);
                break;
            }

            if (inputLine.equals("list")){
                System.out.println(LINE);
                for (int j = 0; j< itemList.length; j++){
                    if (itemList[j]!=null){

                        System.out.println((j+1) + ". " + itemList[j]);

                    }
                }
                System.out.println(LINE);
            }

            else {
            itemList[i] = inputLine;
            i++;
            System.out.println(LINE);
            System.out.println("added: " + inputLine);
            System.out.println(LINE);
            }
        }
    }
}