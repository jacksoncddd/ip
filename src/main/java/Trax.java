import java.util.*;


public class Trax {
    static class Task {
        private String task;
        private boolean isCompleted;
        private char taskType;
        private String dateTime;

        public Task(String _task, boolean _completed, char _taskType, String _dateTime) {
            task = _task;
            isCompleted = _completed;
            taskType = _taskType;
            dateTime = _dateTime;
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

        @Override
        public String toString() {
            char _completed = ' ';
            if (this.isCompleted) {
                _completed = 'X';
            }
            return String.format("[%c][%c] %s %s", taskType, _completed, task, dateTime);
        }

        public char getTaskType() {
            return taskType;
        }

        public String getDateTime() {
            return dateTime;
        }
    }

    public static void main(String[] args) {

        ArrayList<Task> tasks = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        int taskNum = 0;

        System.out.println("Hello! I'm Trax");
        System.out.println("What can I do for you?\n");

        while (true) {

            try {
                String input = scanner.nextLine();
                //General event
                String[] inputArr = input.split(" ", 2);
                String command = inputArr[0];


                switch (command) {
                    case "bye":
                        System.out.println("Bye. Hope to see you again soon!");
                        break;
                    case "list":
                        System.out.println("Heres are the tasks in your list:");
                        for (int i = 1; i <= tasks.size(); i++) {
                            System.out.printf("%d. %s%n", i, tasks.get(i - 1).toString());
                        }
                        break;
                    case "delete":
                        handleDelete(tasks, inputArr);
                        break;
                    case "mark":
                    case "unmark":
                        handleMarkUnmark(tasks, inputArr, command);
                        break;
                    case "todo":
                        handleTodo(tasks, inputArr);
                        break;
                    case "deadline":
                        handleDeadline(tasks, inputArr);
                        break;
                    case "event":
                        handleEvent(tasks, inputArr);
                        break;
                    default:
                        throw new UnknownCommandException();
                }

            } catch (TraxException e) {
                System.out.println("     " + e.getMessage());
            } catch (Exception e) {
                System.out.println("     " + e.getMessage());

            }

        }

    }

    public static void printAddedTask(ArrayList<Task> tasks) {
        System.out.printf("Got it. I've added this task: \n %s\n", tasks.get(tasks.size() - 1).toString());
        System.out.printf("Now you have %d tasks in the list.\n", tasks.size());
    }

    public static void printDeletedTask(Task task, int listSize) {
        System.out.printf("Got it. I've removed this task: \n %s\n", task.toString());
        System.out.printf("Now you have %d tasks in the list.\n", listSize);
    }

    private static void handleMarkUnmark(ArrayList<Task> tasks, String[] inputArr, String command)
            throws InvalidTaskNumberException, EmptyDescriptionException {
        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException(command);
        }

        try {
            int taskNum = Integer.parseInt(inputArr[1].trim()) - 1;

            if (taskNum < 0 || taskNum >= tasks.size()) {
                throw new InvalidTaskNumberException();
            }

            if (command.equals("mark")) {
                tasks.get(taskNum).setCompleted(true);
                System.out.println("Nice! I've marked this task as done:\n" + tasks.get(taskNum).toString());

            } else {
                tasks.get(taskNum).setCompleted(false);
                System.out.println("OK, I've marked this task as not done yet:\n" + tasks.get(taskNum).toString());
            }
        } catch (NumberFormatException e) {
            throw new InvalidTaskNumberException();
        }
    }

    private static void handleTodo(ArrayList<Task> tasks, String[] inputArr)
            throws EmptyDescriptionException {
        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("todo");
        }

        tasks.add(new Trax.Task(inputArr[1], false, 'T', ""));
        printAddedTask(tasks);
    }

    private static void handleDeadline(ArrayList<Task> tasks, String[] inputArr)
            throws EmptyDescriptionException, InvalidFormatException {
        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }

        if (!inputArr[1].contains("/by")) {
            throw new InvalidFormatException("Deadline format should be: deadline <description> /by <date>");
        }

        String[] deadLineTemp = inputArr[1].split("/by\\s*", 2);

        if (deadLineTemp[0].trim().isEmpty()) {
            throw new EmptyDescriptionException("deadline");
        }

        if (deadLineTemp.length < 2 || deadLineTemp[1].trim().isEmpty()) {
            throw new InvalidFormatException("Deadline date cannot be empty.");
        }

        String deadLine = String.format("(by: %s)", deadLineTemp[1].trim());
        tasks.add(new Task(deadLineTemp[0].trim(), false, 'D', deadLine));
        printAddedTask(tasks);
    }

    private static void handleEvent(ArrayList<Task> tasks, String[] inputArr)
            throws EmptyDescriptionException, InvalidFormatException {
        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        if (!inputArr[1].contains("/from") || !inputArr[1].contains("/to")) {
            throw new InvalidFormatException("Event format should be: event <description> /from <start> /to <end>");
        }

        String[] eventTemp = inputArr[1].split("/from\\s*", 2);

        if (eventTemp[0].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        String[] eventTemp2 = eventTemp[1].split("/to\\s*", 2);

        if (eventTemp2.length < 2 || eventTemp2[0].trim().isEmpty() || eventTemp2[1].trim().isEmpty()) {
            throw new InvalidFormatException("Event start and end times cannot be empty.");
        }

        String start = eventTemp2[0].trim();
        String end = eventTemp2[1].trim();
        String duration = String.format("(from: %s to: %s)", start, end);
        tasks.add(new Task(eventTemp[0].trim(), false, 'E', duration));
        printAddedTask(tasks);
    }

    public static void handleDelete(ArrayList<Task> tasks, String[] inputArr)
            throws InvalidTaskNumberException, EmptyDescriptionException {
        if (inputArr.length < 2 || inputArr[1].trim().isEmpty()) {
            throw new EmptyDescriptionException("event");
        }

        int taskNum = Integer.parseInt(inputArr[1]) - 1;

        if (taskNum < 0 || taskNum >= tasks.size()) {
            throw new InvalidTaskNumberException();
        }
        Task t = tasks.get(taskNum);
        tasks.remove(taskNum);
        printDeletedTask(t, tasks.size());


    }

}
