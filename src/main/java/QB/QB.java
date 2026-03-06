package QB;

public class QB {

    private Ui ui;
    private Storage storage;
    private TaskList tasks;

    private static final String FILE_PATH = "./data/QBList.txt";

    public QB(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList();
        storage.loadTasks(tasks.getTasks());
    }

    public void run(){
        ui.printGreeting();
        ui.printTaskNumber(tasks.size());

        while (true) {
            String inputLine = ui.readCommand();
            if (inputLine.trim().equals("bye")) {
                ui.printBye();
                ui.close();
                return;
            }

            try {
                Parser.parse(inputLine, tasks, ui, storage);
            } catch (QBException e) {
                ui.printError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new QB(FILE_PATH).run();
    }
}