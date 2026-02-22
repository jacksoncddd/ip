package trax.ui;

import java.util.Scanner;
import java.util.ArrayList;

import trax.exception.TraxException;
import trax.task.Task;
import trax.tasklist.TaskList;

/**
 * Handles user interactions.
 */
public class Ui {
    private Scanner scanner;
    private static final String LINE = "    ____________________________________________________________";

    public Ui() {
        this.scanner = new Scanner(System.in);
        assert scanner != null : "Scanner should be initialized";
    }

    /**
     * Returns welcome message.
     */
    public String showWelcome() {
        return "Hello! I'm Trax\n" + "What can I do for you?\n";
    }

    /**
     * Returns goodbye message.
     */
    public String showGoodbye() {
        return "Bye. Hope to see you again soon!";
    }

    /**
     * Returns error when loading file fails.
     */
    public String showLoadingError() {
        return "Error loading tasks from local storage file. Starting with empty task list.";
    }

    /**
     * Returns error message.
     *
     * @param message Error message.
     */
    public String showError(String message) {
        return LINE + "\n"
                + "     " + message + "\n"
                + LINE;
    }


    /**
     * Returns formatted task list.
     */
    public String showTaskList(TaskList tasks) {
        if(tasks.getTasks().isEmpty()) {
            return "There are no current tasks. Lets add some!";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("Here are the tasks in your list:\n");
            try {
                for (int i = 0; i < tasks.size(); i++) {
                    sb.append(String.format("%d. %s%n", i + 1, tasks.get(i).toString()));
                }
            } catch (TraxException e) {
                return showError(e.getMessage());
            }
            return sb.toString();
        }
    }

    /**
     * Returns the results of find command.
     *
     * @param matchingTasks list of tasks that match the search keyword.
     */
    public String showFindResults(ArrayList<Task> matchingTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\n");
        if (matchingTasks.isEmpty()) {
            sb.append("     No matching tasks found.\n");
        } else {
            sb.append("     Here are the matching tasks in your list:\n");
            for (int i = 0; i < matchingTasks.size(); i++) {
                sb.append(String.format("     %d.%s%n", i + 1, matchingTasks.get(i).toString()));
            }
        }
        sb.append(LINE);
        return sb.toString();
    }

    /**
     * Returns message when task is added.
     */
    public String showTaskAdded(Task task, int totalTasks) {
        return String.format("Got it. I've added this task:%n %s%n", task.toString())
                + String.format("Now you have %d task%s in the list.%n",
                totalTasks,
                totalTasks == 1 ? "" : "s");
    }

    /**
     * Returns message when task is deleted.
     */
    public String showTaskDeleted(Task task, int totalTasks) {
        return String.format("Noted. I've removed this task:%n %s%n", task.toString())
                + String.format("Now you have %d task%s in the list.%n",
                totalTasks,
                totalTasks == 1 ? "" : "s");
    }

    /**
     * Returns  message when task is marked as done.
     */
    public String showTaskMarked(Task task) {
        return "Nice! I've marked this task as done:\n"
                + "  " + task.toString();
    }

    /**
     * Returns  message when task is unmarked.
     */
    public String showTaskUnmarked(Task task) {
        return "OK, I've marked this task as not done yet:\n"
                + "  " + task.toString();
    }

    /**
     * Reads user command.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}