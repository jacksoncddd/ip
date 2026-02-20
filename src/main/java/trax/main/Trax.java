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
 * Main program flow for Trax task manager.
 */
public class Trax {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    private static final String FILE_PATH = "./data/tasks.txt";

    /**
     * Constructor for Trax.
     */
    public Trax() {
        ui = new Ui();
        storage = new Storage(FILE_PATH);
        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (TraxException e) {
            System.out.println(ui.showLoadingError());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main program loop.
     */
    public String run(String input) {
        String response;
        try {
            String command = Parser.parseCommand(input);

            switch (command) {
            case "bye":
                storage.saveTasks(tasks);
                response = ui.showGoodbye();
                break;

            case "list":
                response = ui.showTaskList(tasks);
                break;
            case "mark":
                response = handleMark(input);
                storage.saveTasks(tasks);
                break;

            case "unmark":
                response = handleUnmark(input);
                storage.saveTasks(tasks);
                break;

            case "todo":
                response = handleTodo(input);
                storage.saveTasks(tasks);
                break;

            case "deadline":
                response = handleDeadline(input);
                storage.saveTasks(tasks);
                break;

            case "event":
                response = handleEvent(input);
                storage.saveTasks(tasks);
                break;

            case "delete":
                response = handleDelete(input);
                storage.saveTasks(tasks);
                break;

            case "find":
                response = handleFind(input);
                break;

            default:
                throw new UnknownCommandException();
            }
        } catch (TraxException e) {
            return ui.showError(e.getMessage());
        }
        return response;
    }

//    public static void main(String[] args) {
//        new Trax().run();
//    }

    /**
     * Shows welcome.
     */
    public String showWelcome() {
        return ui.showWelcome();
    }

    /**
     * Handles the todo command.
     */
    public String handleTodo(String input) throws TraxException {
        Task task = Parser.parseTodo(input);
        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the deadline command.
     */
    public String handleDeadline(String input) throws TraxException {
        Task task = Parser.parseDeadline(input);
        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the event command.
     */
    private String handleEvent(String input) throws TraxException {
        Task task = Parser.parseEvent(input);
        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the mark command.
     */
    private String handleMark(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);
        tasks.markTask(index);
        return ui.showTaskMarked(tasks.get(index));
    }

    /**
     * Handles the unmark command.
     */
    private String handleUnmark(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);
        tasks.unmarkTask(index);
        return ui.showTaskUnmarked(tasks.get(index));
    }

    /**
     * Handles the delete command.
     */
    private String handleDelete(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);
        Task deletedTask = tasks.delete(index);
        return ui.showTaskDeleted(deletedTask, tasks.size());
    }

    /**
     * Handles the find command.
     */
    private String handleFind(String input) throws TraxException {
        String keyword = Parser.parseFind(input);
        ArrayList<Task> matchingTasks = tasks.find(keyword);
        return ui.showFindResults(matchingTasks);
    }

}