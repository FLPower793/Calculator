package uidriver;

import iodriver.IIODriver;

public class ConsoleUIDriver implements IUIDriver {
    private final IIODriver ioDriver;

    public ConsoleUIDriver(IIODriver ioDriver) {
        this.ioDriver = ioDriver;
    }

    @Override
    public void showMessage(String message) {
        ioDriver.printLine(message);
    }

    @Override
    public void showMessage(String message, boolean newLine) {
        ioDriver.printLine(message, newLine);
    }

    @Override
    public void showError(String error) {
        ioDriver.printLine(error);
    }

    @Override
    public String getExpression() {
        return ioDriver.getLine();
    }

    @Override
    public void showAnswer(double answer) {
        ioDriver.printLine(String.format("Результат: %s", answer));
    }
}
