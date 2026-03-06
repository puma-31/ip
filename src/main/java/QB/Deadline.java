package QB;

/**
 * Represents a Deadline task, which is a task that must be completed by a specific time.
 */
public class Deadline extends Task {
    private String by;

    /**
     * Constructs a Deadline task with the given description and deadline.
     *
     * @param description The description of the task.
     * @param by The deadline by which the task must be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return A formatted string e.g. "[D][X] return book (by: June 6th)".
     */

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns the deadline of this task.
     *
     * @return The deadline as a String.
     */
    public String getBy() {
        return by;
    }
}