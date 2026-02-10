package trax.main;

import java.util.ArrayList;

import trax.task.Task;
import trax.tasklist.TaskList;
import trax.ui.Ui;
import trax.storage.Storage;
import trax.parser.Parser;
import trax.exception.TraxException;
import trax.exception.UnknownCommandException;

/**
 * Main class for Trax task manager.
 */
public class Trax {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructor for Trax.
     *
     * @param filePath path to the data file.
     */
    public Trax(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (TraxException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop.
     */
    public void run() {
        ui.showWelcome();

        boolean isRunning = true;
        while (isRunning) {
            try {
                String input = ui.readCommand();
                String command = Parser.parseCommand(input);

                switch (command) {
                case "bye":
                    storage.saveTasks(tasks);
                    ui.showGoodbye();
                    isRunning = false;
                    break;

                case "list":
                    ui.showTaskList(tasks);
                    break;

                case "mark":
                    handleMark(input);
                    storage.saveTasks(tasks);
                    break;

                case "unmark":
                    handleUnmark(input);
                    storage.saveTasks(tasks);
                    break;

                case "todo":
                    handleTodo(input);
                    storage.saveTasks(tasks);
                    break;

                case "deadline":
                    handleDeadline(input);
                    storage.saveTasks(tasks);
                    break;

                case "event":
                    handleEvent(input);
                    storage.saveTasks(tasks);
                    break;

                case "delete":
                    handleDelete(input);
                    storage.saveTasks(tasks);
                    break;

                case "find":
                    handleFind(input);
                    break;


                default:
                    throw new UnknownCommandException();
                }

            } catch (TraxException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.close();
    }

    /**
     * Handles the todo command.
     */
    private void handleTodo(String input) throws TraxException {
        Task task = Parser.parseTodo(input);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the deadline command.
     */
    private void handleDeadline(String input) throws TraxException {
        Task task = Parser.parseDeadline(input);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the event command.
     */
    private void handleEvent(String input) throws TraxException {
        Task task = Parser.parseEvent(input);
        tasks.add(task);
        ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the mark command.
     */
    private void handleMark(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);
        tasks.markTask(index);
        ui.showTaskMarked(tasks.get(index));
    }

    /**
     * Handles the unmark command.
     */
    private void handleUnmark(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);
        tasks.unmarkTask(index);
        ui.showTaskUnmarked(tasks.get(index));
    }

    /**
     * Handles the delete command.
     */
    private void handleDelete(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);
        Task deletedTask = tasks.delete(index);
        ui.showTaskDeleted(deletedTask, tasks.size());
    }

    public static void main(String[] args) {
        new Trax("./data/tasks.txt").run();
    }

    /**
     * Handles the find command.
     */
    private void handleFind(String input) throws TraxException {
        String keyword = Parser.parseFind(input);
        ArrayList<Task> matchingTasks = tasks.find(keyword);
        ui.showFindResults(matchingTasks);
    }

}