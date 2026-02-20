package trax.tasklist;

import java.util.ArrayList;

import trax.task.Task;
import trax.exception.TraxException;
import trax.exception.InvalidTaskNumberException;


/**
 * Contains the task list and operations to manipulate it.
 */

public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert tasks != null : "Tasks list should be initialized";
    }

    /**
     * Creates a TaskList with existing tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Input tasks list cannot be null";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     */
    public void add(Task task) {
        assert task != null : "Task to add cannot be null";

        tasks.add(task);
        assert tasks.contains(task) : "Task list should contain the added task";
    }

    /**
     * Deletes a task from the list.
     */
    public Task delete(int index) throws TraxException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        int sizeBefore = tasks.size();
        Task deletedTask = tasks.remove(index);

        assert deletedTask != null : "Deleted task should not be null";
        assert tasks.size() == sizeBefore - 1 : "Task list size should decrease by 1 after deletion";

        return deletedTask;
    }

    /**
     * Gets a task from the list.
     *
     * @return Task object at the specified index.
     * @throws TraxException if task number is <0 or out of range.
     */
    public Task get(int index) throws TraxException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }

        Task task = tasks.get(index);
        assert task != null : "Retrieved task should not be null";

        return task;
    }

    /**
     * Marks a task as done.
     *
     * @throws TraxException if task number is <0 or out of range.
     */
    public void markTask(int index) throws TraxException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        tasks.get(index).setCompleted(true);
    }

    /**
     * Marks a task as not done.
     *
     * @throws TraxException if task number is <0 or out of range.
     */
    public void unmarkTask(int index) throws TraxException {
        if (index < 0 || index >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        tasks.get(index).setCompleted(false);
    }


    /**
     * Finds tasks that contain the keyword in their description.
     * Non case sensitive.
     *
     * @param keyword The keyword to search for.
     * @return ArrayList of tasks that match the keyword.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        assert lowerKeyword != null : "Lowercase keyword should not be null";

        for (Task task : tasks) {
            if (task.getTask().toLowerCase().contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }

        return matchingTasks;
    }


    /**
     * Returns the number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the ArrayList of tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }
}