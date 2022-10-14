package stack;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stack<E> implements IStack<E> {
    private final Deque<E> stack;

    public Stack() {
        this.stack = new ArrayDeque<>();
    }

    @Override
    public E peek() {
        return stack.peek();
    }

    @Override
    public E pop() {
        return stack.pop();
    }

    @Override
    public void push(E item) {
        stack.push(item);
    }

    @Override
    public int size() {
        return stack.size();
    }
}
