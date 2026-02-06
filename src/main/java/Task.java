import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Task class represents a task object
 * 3 types of tasks - Todos, Deadline, Event
 */

public class Task {
    /** Task description */
    private String task;
    private boolean isCompleted;
    private char taskType;

    private LocalDateTime deadline;          // For deadlines
    private LocalDateTime eventStart;        // For event start time
    private LocalDateTime eventEnd;          // For event end time


    // Formatters for parsing and displaying
    private static final DateTimeFormatter INPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter OUTPUT_DATETIME_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy hh:mm a");


    /**
     * Constructor for Todos task
     *
     * @param task      Task description
     * @param completed Task completion
     * @param taskType  Task type
     */
    public Task(String task, boolean completed, char taskType) {
        this.task = task;
        this.isCompleted = completed;
        this.taskType = taskType;
        this.deadline = null;
        this.eventStart = null;
        this.eventEnd = null;
    }

    /**
     * Constructor for Deadline task
     *
     * @param task      Task description
     * @param completed Task completion
     * @param taskType  Task type
     * @param deadline  Deadline dateTime
     */
    public Task(String task, boolean completed, char taskType, LocalDateTime deadline) {
        this.task = task;
        this.isCompleted = completed;
        this.taskType = taskType;
        this.deadline = deadline;
        this.eventStart = null;
        this.eventEnd = null;
    }

    /**
     *
     * Constructor for Event task
     *
     * @param task       Task description
     * @param completed  Task completion
     * @param taskType   Task type
     * @param eventStart Event start dateTime
     * @param eventEnd   Event end dateTime
     */
    public Task(String task, boolean completed, char taskType,
                LocalDateTime eventStart, LocalDateTime eventEnd) {
        this.task = task;
        this.isCompleted = completed;
        this.taskType = taskType;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.deadline = null;
    }


    public String getTask() {
        return task;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public char getTaskType() {
        return taskType;
    }


    public LocalDateTime getDeadlineDate() {
        return deadline;
    }

    public LocalDateTime getEventStart() {
        return eventStart;
    }

    public LocalDateTime getEventEnd() {
        return eventEnd;
    }

    @Override
    public String toString() {
        char completed = ' ';
        if (this.isCompleted) {
            completed = 'X';
        }

        String dateTimeStr = "";
        if (taskType == 'D' && deadline != null) {
            dateTimeStr = "(by: " + deadline.format(OUTPUT_DATETIME_FORMAT) + ")";
        } else if (taskType == 'E' && eventStart != null && eventEnd != null) {
            dateTimeStr = "(from: " + eventStart.format(OUTPUT_DATETIME_FORMAT) +
                    " to: " + eventEnd.format(OUTPUT_DATETIME_FORMAT) + ")";
        }

        return String.format("[%c][%c] %s %s", taskType, completed, task, dateTimeStr).trim();
    }

    /**
     * Parses a datetime string in format yyyy-MM-dd HHmm
     *
     * @param dateTimeStr DateTime in string format
     * @return Local DateTime object
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        return LocalDateTime.parse(dateTimeStr.trim(), INPUT_DATETIME_FORMAT);
    }

    /**
     * Formats DateTime for file storage
     * For events: "start | end"
     * For deadlines: "date"
     * For todos: ""
     *
     * @return Formatted String Datetime
     */
    public String getDateTimeForStorage() {
        if (taskType == 'D' && deadline != null) {
            return deadline.format(INPUT_DATETIME_FORMAT);
        } else if (taskType == 'E' && eventStart != null && eventEnd != null) {
            return eventStart.format(INPUT_DATETIME_FORMAT) + " | " +
                    eventEnd.format(INPUT_DATETIME_FORMAT);
        }
        return "";
    }


}

