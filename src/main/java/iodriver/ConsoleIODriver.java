package iodriver;

import java.util.Scanner;

public class ConsoleIODriver implements IIODriver {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printLine(String message) {
        System.out.println(message);
    }

    @Override
    public void printLine(String message, boolean newLine) {
        if (newLine) {
            printLine(message);
        } else {
            System.out.print(message);
        }
    }

    @Override
    public String getLine() {
        return scanner.nextLine();
    }

    public Scanner getScanner() {
        return scanner;
    }
}
