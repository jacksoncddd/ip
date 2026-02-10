package trax.parser;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import trax.task.Task;
import trax.exception.TraxException;
import trax.exception.InvalidTaskNumberException;
import trax.exception.InvalidFormatException;
import trax.exception.InvalidDateFormatException;
import trax.exception.EmptyDescriptionException;

/**
 * Parses user input and creates appropriate commands.
 */
public class Parser {

    /**
     * Parses user input and returns the command type.
     */
    public static String parseCommand(String input) {
        String[] inputArr = input.trim().split(" ", 2);
        return inputArr[0].toLowerCase();
    }

    /**
     * Parses a todo command and creates a Task.
     *
     * @return Todo task object.
     * @exception TraxException if input command is empty.
     */
    public static Task parseTodo(String input) throws TraxException {
        String[] inputArr = input.split(" ", 2);

        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }

        return new Task(inputArr[1].trim(), false, 'T');
    }

    /**
     * Parses a deadline command and creates a Task.
     * @return Deadline task object.
     * @exception TraxException if input command is empty or invalid format.
     */
    public static Task parseDeadline(String input) throws TraxException {
        String[] inputArr = input.split(" ", 2);

        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }

        if (!inputArr[1].contains("/by")) {
            throw new InvalidFormatException(
                    "Deadline format should be: deadline <description> /by <yyyy-MM-dd HHmm>");
        }

        String[] deadLineTemp = inputArr[1].split("/by\\s*", 2);

        if (deadLineTemp[0].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }

        if (deadLineTemp.length < 2 || deadLineTemp[1].trim().isEmpty()) {
            throw new InvalidFormatException("Deadline date/time cannot be empty.");
        }

        try {
            LocalDateTime deadline = Task.parseDateTime(deadLineTemp[1].trim());
            return new Task(deadLineTemp[0].trim(), false, 'D', deadline);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(
                    "Please use yyyy-MM-dd HHmm format (e.g., 2019-12-02 1800)");
        }
    }

    /**
     * Parses an event command and creates a Task.
     * @return Event task object.
     * @exception TraxException if input command is empty or invalid format.
     */
    public static Task parseEvent(String input) throws TraxException {
        String[] inputArr = input.split(" ", 2);

        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        if (!inputArr[1].contains("/from") || !inputArr[1].contains("/to")) {
            throw new InvalidFormatException(
                    "Event format should be: event <description> /from <yyyy-MM-dd HHmm> /to <yyyy-MM-dd HHmm>");
        }

        String[] eventTemp = inputArr[1].split("/from\\s*", 2);

        if (eventTemp[0].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        String[] eventTemp2 = eventTemp[1].split("/to\\s*", 2);

        if (eventTemp2.length < 2 || eventTemp2[0].trim().isEmpty() || eventTemp2[1].trim().isEmpty()) {
            throw new InvalidFormatException("Event start and end times cannot be empty.");
        }

        try {
            LocalDateTime startTime = Task.parseDateTime(eventTemp2[0].trim());
            LocalDateTime endTime = Task.parseDateTime(eventTemp2[1].trim());

            if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
                throw new InvalidFormatException("Event end time must be after start time.");
            }

            return new Task(eventTemp[0].trim(), false, 'E', startTime, endTime);
        } catch (DateTimeParseException e) {
            throw new InvalidDateFormatException(
                    "Please use yyyy-MM-dd HHmm format (e.g., 2019-12-02 1800)");
        }
    }

    /**
     * Parses task index from mark/unmark/delete commands.
     *
     * @return Task Index.
     * @exception TraxException if input index is empty or invalid format.
     */
    public static int parseTaskIndex(String input) throws TraxException {
        String[] inputArr = input.split(" ", 2);

        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("task number");
        }

        try {
            return Integer.parseInt(inputArr[1].trim()) - 1;
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException();
        }
    }

    /**
     * Parses the keyword from find command.
     *
     * @param input User input.
     * @return The keyword to search for.
     * @throws TraxException if keyword is empty.
     */
    public static String parseFind(String input) throws TraxException {
        String[] inputArr = input.split(" ", 2);

        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("find keyword");
        }

        return inputArr[1].trim();
    }

}