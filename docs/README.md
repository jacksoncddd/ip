# Trax User Guide

Trax is a command-line and JavaFX-based task management application that helps you keep track of your todos, deadlines, and events.

![Trax GUI](./Ui.png)


## Features

- Add three types of tasks: Todos, Deadlines, and Events
- Mark tasks as done/not done
- Delete tasks
- Find tasks by keyword
- Automatic saving to local storage


## Commands

| Command | Format                                                                 | Example |
|---------|------------------------------------------------------------------------|---------|
| Add Todo | `td/todo <description>`                                                | `todo read book` |
| Add Deadline | `dl/deadline <description> /by <yyyy-MM-dd HHmm>`                      | `deadline return book /by 2024-12-25 1800` |
| Add Event | `ev/event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>` | `event meeting /from 2024-12-20 1400 /to 2024-12-20 1600` |
| List Tasks | `list`                                                                 | `list` |
| Mark Done | `mark <number>`                                                        | `mark 1` |
| Unmark | `unmark <number>`                                                      | `unmark 1` |
| Delete | `delete <number>`                                                      | `delete 2` |
| Find | `find <keyword>`                                                       | `find book` |
| Exit | `bye`                                                                  | `bye` |

*Commands td, dl and ev are command shortcuts.

## Date Format

Use `yyyy-MM-dd HHmm` format for dates and times:
- Year: 4 digits (e.g., 2024)
- Month: 2 digits (01-12)
- Day: 2 digits (01-31)
- Time: 24-hour format (e.g., 1800 = 6:00 PM)

## Data Storage

Tasks are automatically saved to `./data/tasks.txt` and loaded on startup.

## Example Usage

```
> todo read book
Got it. I've added this task:
 [T][ ] read book
Now you have 1 task in the list.

> deadline return book /by 2024-12-25 1800
Got it. I've added this task:
 [D][ ] return book (by: Dec 25 2024 06:00 PM)
Now you have 2 tasks in the list.

> list
Here are the tasks in your list:
1. [T][ ] read book
2. [D][ ] return book (by: Dec 25 2024 06:00 PM)

> mark 1
Nice! I've marked this task as done:
  [T][X] read book

> find book
    ____________________________________________________________
     Here are the matching tasks in your list:
     1.[T][X] read book
     2.[D][ ] return book (by: Dec 25 2024 06:00 PM)
    ____________________________________________________________

> bye
Bye. Hope to see you again soon!
```