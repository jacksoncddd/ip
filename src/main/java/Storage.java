import java.util.ArrayList;
import java.time.LocalDateTime;
import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

//deletion is handled automatically

/**
 * Handle storage and loading of inputted tasks from local storage
 */

public class Storage {

    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from file
     *
     * @return ArrayList of Task loaded from file
     */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        // Create directory if it doesn't exist
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        // If file doesn't exist, return empty list
        if (!file.exists()) {
            return tasks;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Task task = parseTask(line);
                if (task != null) {
                    tasks.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Saves tasks to the file
     *
     * @param tasks ArrayList of Task to save
     * @throws IOException if unable to write to file
     */
    public void saveTasks(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);

        // Create directory if it doesn't exist
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            directory.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Task task : tasks) {
                writer.write(formatTask(task));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    /**
     * Converts a Task object to file format
     * Format: TaskType | Completed | Description | DateTime
     *
     * @param task Task object
     * @return Formatted task
     */
    private String formatTask(Task task) {
        int completed = task.isCompleted() ? 1 : 0;
        return String.format("%c | %d | %s | %s",
                task.getTaskType(),
                completed,
                task.getTask(),
                task.getDateTimeForStorage());
    }

    /**
     * Parses a line of formatted task from file into a Task object
     *
     * @param line Formatted task
     * @return Task object
     */
    private Task parseTask(String line) {
        try {
            String[] parts = line.split(" \\| ");

            if (parts.length < 3) {
                return null;
            }

            char taskType = parts[0].charAt(0);
            boolean completed = parts[1].equals("1");
            String description = parts[2];
            //String dateTime = parts.length > 3 ? parts[3] : "";

            //Create respective tasks
            if(taskType == 'T') {
                return new Task(description, completed, taskType);
            } else if (taskType == 'D') {
                if (parts.length >= 4 && !parts[3].trim().isEmpty()) {
                    LocalDateTime deadline = Task.parseDateTime(parts[3]);
                    return new Task(description, completed, taskType, deadline);
                }
            } else if (taskType == 'E') {
                // For events, the format is "... | startDateTime | endDateTime"
                // parts[3] = startDateTime, parts[4] = endDateTime
                if (parts.length >= 5) {
                    LocalDateTime startTime = Task.parseDateTime(parts[3]);
                    LocalDateTime endTime = Task.parseDateTime(parts[4]);
                    return new Task(description, completed, taskType, startTime, endTime);
                } else {
                    return new Task(description, completed, taskType);
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error parsing line: " + line);
            return null;
        }
    }
}