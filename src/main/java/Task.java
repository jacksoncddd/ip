public class Task {
    private String task;
    private boolean isCompleted;
    private char taskType;
    private String dateTime;

    public Task(String task, boolean completed, char taskType, String dateTime) {
        this.task = task;
        this.isCompleted = completed;
        this.taskType = taskType;
        this.dateTime = dateTime;
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
        char completed = ' ';
        if (this.isCompleted) {
            completed = 'X';
        }
        return String.format("[%c][%c] %s %s", taskType, completed, task, dateTime);
    }

    public char getTaskType() {
        return taskType;
    }

    public String getDateTime() {
        return dateTime;
    }


}

