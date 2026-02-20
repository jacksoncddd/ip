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

        assert ui != null : "Ui should be initialized";
        assert storage != null : "Storage should be initialized";

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
        assert input != null : "Input cannot be null";

        String response;
        try {
            String command = Parser.parseCommand(input);
            assert command != null : "Command should not be null after parsing";

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

            assert response != null : "Response should not be null";

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

        assert task != null : "Parsed task should not be null";
        assert task.getTaskType() == 'T' : "Task type should be 'T' for todo";

        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the deadline command.
     */
    public String handleDeadline(String input) throws TraxException {
        Task task = Parser.parseDeadline(input);

        assert task != null : "Parsed task should not be null";
        assert task.getTaskType() == 'D' : "Task type should be 'D' for deadline";
        assert task.getDeadlineDate() != null : "Deadline date should not be null";

        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the event command.
     */
    private String handleEvent(String input) throws TraxException {
        Task task = Parser.parseEvent(input);

        assert task != null : "Parsed task should not be null";
        assert task.getTaskType() == 'E' : "Task type should be 'E' for event";
        assert task.getEventStart() != null : "Event start time should not be null";
        assert task.getEventEnd() != null : "Event end time should not be null";
        assert task.getEventEnd().isAfter(task.getEventStart()) : "Event end should be after start";

        tasks.add(task);
        return ui.showTaskAdded(task, tasks.size());
    }

    /**
     * Handles the mark command.
     */
    private String handleMark(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);

        assert index >= 0 : "Task index should be non-negative";
        assert index < tasks.size() : "Task index should be within bounds";

        tasks.markTask(index);
        return ui.showTaskMarked(tasks.get(index));
    }

    /**
     * Handles the unmark command.
     */
    private String handleUnmark(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);

        assert index >= 0 : "Task index should be non-negative";
        assert index < tasks.size() : "Task index should be within bounds";

        tasks.unmarkTask(index);
        return ui.showTaskUnmarked(tasks.get(index));
    }

    /**
     * Handles the delete command.
     */
    private String handleDelete(String input) throws TraxException {
        int index = Parser.parseTaskIndex(input);

        assert index >= 0 : "Task index should be non-negative";
        assert index < tasks.size() : "Task index should be within bounds";

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