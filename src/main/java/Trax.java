import java.util.*;


public class Trax {
    static class Task {
        private String task;
        private boolean completed;

        public Task(String _task, boolean _completed) {
            task = _task;
            completed = _completed;
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
            return String.format("[%c] %s", _completed, task);
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
            String[] inputArr = input.split(" ");
            String command = inputArr[0];

            switch (command) {
                case "bye":
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                case "list":
                    for (int i = 1; i <= tasks.size(); i++) {
                        System.out.printf("%d. %s%n", i, tasks.get(i - 1).toString());
                    }
                    break;
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
                default:
                    tasks.add(new Trax.Task(input, false));
                    System.out.println("added: " + input);
            }

        }

    }
}
