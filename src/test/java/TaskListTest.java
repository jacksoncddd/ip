import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Tests for the TaskList class
 */
public class TaskListTest {

    private TaskList taskList;
    private Task todoTask;
    private Task deadlineTask;
    private Task eventTask;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList();
        todoTask = new Task("read book", false, 'T');
        deadlineTask = new Task("return book", false, 'D',
                LocalDateTime.of(2019, 12, 2, 18, 0));
        eventTask = new Task("project meeting", false, 'E',
                LocalDateTime.of(2019, 8, 6, 14, 0),
                LocalDateTime.of(2019, 8, 6, 16, 0));
    }

    @Test
    public void constructor_noArgs_createsEmptyList() {
        TaskList emptyList = new TaskList();
        assertEquals(0, emptyList.size());
    }

    @Test
    public void constructor_withArrayList_createsListWithTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(todoTask);
        tasks.add(deadlineTask);

        TaskList list = new TaskList(tasks);
        assertEquals(2, list.size());
    }

    @Test
    public void add_singleTask_increasesSize() {
        assertEquals(0, taskList.size());

        taskList.add(todoTask);
        assertEquals(1, taskList.size());

        taskList.add(deadlineTask);
        assertEquals(2, taskList.size());
    }

    @Test
    public void add_multipleTasks_maintainsOrder() throws TraxException {
        taskList.add(todoTask);
        taskList.add(deadlineTask);
        taskList.add(eventTask);

        assertEquals(todoTask, taskList.get(0));
        assertEquals(deadlineTask, taskList.get(1));
        assertEquals(eventTask, taskList.get(2));
    }
}