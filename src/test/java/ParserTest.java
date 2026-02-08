import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

/**
 * Tests for the Parser class
 */
public class ParserTest {

    @Test
    public void parseCommand_validCommands_success() {
        assertEquals("todo", Parser.parseCommand("todo read book"));
        assertEquals("deadline", Parser.parseCommand("deadline return book /by 2019-12-02 1800"));
        assertEquals("event", Parser.parseCommand("event meeting /from 2019-12-02 1400 /to 2019-12-02 1600"));
        assertEquals("list", Parser.parseCommand("list"));
        assertEquals("mark", Parser.parseCommand("mark 1"));
        assertEquals("bye", Parser.parseCommand("bye"));
    }

    @Test
    public void parseCommand_mixedCase_returnLowerCase() {
        assertEquals("todo", Parser.parseCommand("TODO read book"));
        assertEquals("list", Parser.parseCommand("LiSt"));
        assertEquals("bye", Parser.parseCommand("BYE"));
    }

    @Test
    public void parseCommand_whitespace_handledCorrectly() {
        assertEquals("todo", Parser.parseCommand("  todo   read book  "));
        assertEquals("list", Parser.parseCommand("   list   "));
    }

    @Test
    public void parseTodo_validInput_success() throws TraxException {
        Task task = Parser.parseTodo("todo read book");

        assertEquals("read book", task.getTask());
        assertEquals('T', task.getTaskType());
        assertFalse(task.isCompleted());
    }

}