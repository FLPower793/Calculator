package iodriver;

public interface IIODriver {
    void printLine(String message);

    void printLine(String message, boolean newLine);

    String getLine();
}
