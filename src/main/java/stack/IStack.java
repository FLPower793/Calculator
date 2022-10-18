package stack;

public interface IStack<E> {
    E peek();

    E pop();

    void push(E item);

    int size();

    void clear();
}
