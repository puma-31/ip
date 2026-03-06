package QB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileWriter;

/**
 * Handles loading and saving tasks to a file,
 * allowing task data to persist between sessions.
 */
public class Storage {
    private static String filePath;

    /**
     * Constructs a Storage instance with the specified file path.
     *
     * @param filePath Path to the file used for reading and writing tasks.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the save file.
     *
     * @return An ArrayList of tasks read from the file.
     *         Returns an empty list if the file does not exist or cannot be read.
     */
    public int loadTasks(ArrayList<Task> items) {
        File file = new File(filePath);

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

    /**
     * converts task to file saving format.
     *
     * @param task The list of tasks to convert.
     */
    private String taskToFileFormat(Task task) {
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

    /**
     * Saves the current list of tasks to the save file.
     *
     * @param items The list of tasks to save.
     */
    public void saveTasks(ArrayList<Task> items) {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            FileWriter fw = new FileWriter(filePath);
            for (int i = 0; i < items.size(); i++) {
                fw.write(taskToFileFormat(items.get(i)) + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
