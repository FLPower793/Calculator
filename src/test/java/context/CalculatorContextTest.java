package context;

import org.junit.jupiter.api.Test;
import stack.IStack;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

class CalculatorContextTest {
    private static final CalculatorContext context = new CalculatorContext(mock(Map.class), mock(IStack.class));

    @Test
    void getCommands() {
        assertNotNull(context.getCommands());
    }

    @Test
    void getStack() {
        assertNotNull(context.getStack());
    }

}