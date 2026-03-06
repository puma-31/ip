package QB;

/**
 * Represents a Todo task, which is a task with only a description and no time constraint.
 */
public class Todo extends Task {

    /**
     * Constructs a Todo task with the given description.
     *
     * @param description The description of the todo task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the Todo task.
     *
     * @return A formatted string e.g. "[T][X] read book".
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}