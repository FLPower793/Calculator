package stack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StackTest {
    private static Stack<Double> stack;

    @BeforeEach
    void init() {
        stack = new Stack<>();
        stack.push(1.5);
    }

    @Test
    void peek() {
        assertEquals(1.5, stack.peek());
        assertEquals(1, stack.size());
    }

    @Test
    void pop() {
        assertEquals(1.5, stack.pop());
        assertEquals(0, stack.size());
    }

    @Test
    void push() {
        stack.push(3.14);
        assertEquals(2, stack.size());
    }

    @Test
    void size() {
        assertEquals(1, stack.size());
    }
}