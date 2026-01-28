import java.util.*;


public class Trax {
    static class Task {
        private String task;
        private boolean completed;
        private char taskType;
        private String dateTime;

        public Task(String _task, boolean _completed, char _taskType, String _dateTime) {
            task = _task;
            completed = _completed;
            taskType = _taskType;
            dateTime = _dateTime;
        }

        public String getTask() {
            return task;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            char _completed = ' ';
            if (this.completed) _completed = 'X';
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
                //after all done, then commit L6
//                case "delete":
//                    taskNum = Integer.parseInt(inputArr[1]) - 1;
//                    Task t = tasks.get(taskNum);
//                    tasks.remove(taskNum);
//                    printDeletedTask(t, tasks.size());
//                    break;
                case "mark":
                    taskNum = Integer.parseInt(inputArr[1]) - 1;
                    tasks.get(taskNum).setCompleted(true);
                    System.out.println("Nice! I've marked this task as done:\n" + tasks.get(taskNum).toString());
                    break;
                case "unmark":
                    taskNum = Integer.parseInt(inputArr[1]) - 1;
                    tasks.get(taskNum).setCompleted(false);
                    System.out.println("OK, I've marked this task as not done yet:\n" + tasks.get(taskNum).toString());
                    break;
                case "todo":
                    tasks.add(new Trax.Task(inputArr[1], false, 'T', ""));
                    printAddedTask(tasks);
                    break;
                case "deadline":
                    String[] deadLineTemp = inputArr[1].split("/by\\s*", 2);
                    String deadLine = String.format("(by: %s)", deadLineTemp[1].trim());
                    tasks.add(new Trax.Task(deadLineTemp[0].trim(), false, 'D', deadLine));
                    printAddedTask(tasks);
                    break;
                case "event":
                    String[] eventTemp = inputArr[1].split("/from\\s*", 2);
                    String[] eventTemp2 = eventTemp[1].split("/to\\s*", 2);
                    String start = eventTemp2[0].trim();
                    String end = eventTemp2[1].trim();
                    String duration = String.format("from: %s to: %s", start, end);
                    tasks.add(new Trax.Task(eventTemp[0], false, 'E', duration));
                    printAddedTask(tasks);
                    break;

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
}
