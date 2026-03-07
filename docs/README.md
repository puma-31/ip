# QB - Task Manager Chatbot

QB is a personal task manager that runs in your terminal. It helps you track todos, deadlines, and events with no clicking required.

---

## Quick Start

1. Ensure you have **Java 17** installed.
2. Download the latest `QB.jar` from the releases page.
3. Run it with:
   ```
   java -jar QB.jar
   ```
4. Type a command and press Enter. Type `bye` to exit.

Your tasks are automatically saved to `data/QBList.txt` and reloaded on the next run.

---

## Features

#### NOTE: All commands are case-sensitive and in lower case

### Add a Todo: `todo`
A task with just a description and no time constraint.

**Format:** `todo <description>`

**Example:**
```
todo read book
```
```
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
```

---

### Add a Deadline: `deadline`
A task that must be completed by a specific time.

**Format:** `deadline <description> /by <time>`

**Example:**
```
deadline return book /by June 6th
```
```
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in the list.
____________________________________________________________
```

---

### Add an Event: `event`
A task that spans a time period with a start and end.

**Format:** `event <description> /from <start> /to <end>`

**Example:**
```
event project meeting /from Mon 2pm /to Mon 4pm
```
```
____________________________________________________________
Got it. I've added this task:
  [E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
Now you have 3 tasks in the list.
____________________________________________________________
```

---

### List All Tasks: `list`
Displays all tasks currently in your list.

**Format:** `list`

**Example:**
```
____________________________________________________________
Here are the tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: June 6th)
3.[E][ ] project meeting (from: Mon 2pm to: Mon 4pm)
____________________________________________________________
```

---

### Mark a Task as Done: `mark`
Marks a task as completed. The task number corresponds to its position in `list`.

**Format:** `mark <task number>`

**Example:**
```
mark 1
```
```
____________________________________________________________
Nice! I've marked this task as done:
  [T][X] read book
____________________________________________________________
```

---

### Unmark a Task: `unmark`
Marks a completed task as incomplete again.

**Format:** `unmark <task number>`



---

### Delete a Task: `delete`
Permanently removes a task from the list and prints the remaining number of items.

**Format:** `delete <task number>`

**Example:**
```
delete 2
```
```
____________________________________________________________
Deleted this task:
  [D][ ] return book (by: June 6th)
Now you have 2 tasks in your list.
____________________________________________________________
```

---

### Find Tasks by Keyword: `find`
Searches for tasks whose descriptions contain the given keyword.

**Format:** `find <keyword>`

**Example:**
```
find book
```
```
____________________________________________________________
These are the tasks I found for book:
1.[T][ ] read book
2.[D][ ] return book (by: June 6th)
____________________________________________________________
```

---

### Exit: `bye`
Exits the application. Tasks are saved automatically.

**Format:** `bye`

---

## Task Status Icons

| Icon | Meaning |
|------|---------|
| `[ ]` | Task is not done |
| `[X]` | Task is done |
| `[T]` | Todo |
| `[D]` | Deadline |
| `[E]` | Event |

---

## Command Summary

| Command | Format |
|---------|--------|
| Add todo | `todo <description>` |
| Add deadline | `deadline <description> /by <time>` |
| Add event | `event <description> /from <start> /to <end>` |
| List tasks | `list` |
| Mark done | `mark <task number>` |
| Unmark | `unmark <task number>` |
| Delete | `delete <task number>` |
| Find | `find <keyword>` |
| Exit | `bye` |
