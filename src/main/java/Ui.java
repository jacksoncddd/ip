import java.util.Scanner;

/**
 * Handles user interactions.
 *
 */
public class Ui {
    private Scanner scanner;
    private static final String LINE = "    ____________________________________________________________";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays welcome message.
     */
    public void showWelcome() {
        System.out.println("Hello! I'm Trax");
        System.out.println("What can I do for you?\n");
    }

    /**
     * Displays goodbye message.
     */
    public void showGoodbye() {

        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Displays error when loading file fails.
     */
    public void showLoadingError() {
        System.out.println("Error loading tasks from local storage file. Starting with empty task list.");
    }

    /**
     * Displays error message.
     *
     * @param message Error message.
     */
    public void showError(String message) {
        System.out.println(LINE);
        System.out.println("     " + message);
        System.out.println(LINE);
    }

    /**
     * Displays task list.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        try {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.printf("%d. %s%n", i + 1, tasks.get(i).toString());
            }
        } catch (TraxException e) {
            showError(e.getMessage());
            Exception ex = new Exception();
        }
    }

    /**
     * Displays message when task is added.
     */
    public void showTaskAdded(Task task, int totalTasks) {
        System.out.printf("Got it. I've added this task:%n %s%n", task.toString());
        System.out.printf("Now you have %d task%s in the list.%n",
                totalTasks,
                totalTasks == 1 ? "" : "s");
    }

    /**
     * Displays message when task is deleted.
     */
    public void showTaskDeleted(Task task, int totalTasks) {
        System.out.printf("Noted. I've removed this task:%n %s%n", task.toString());
        System.out.printf("Now you have %d task%s in the list.%n",
                totalTasks,
                totalTasks == 1 ? "" : "s");
    }

    /**
     * Displays message when task is marked as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task.toString());
    }

    /**
     * Displays message when task is unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println("  " + task.toString());
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