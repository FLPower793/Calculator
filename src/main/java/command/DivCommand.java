package command;

import exception.CommandException;

public class DivCommand extends BinaryStackCommand {
    private static final char NAME = '/';
    private static final int PRIORITY = 2;

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public Double process(Double number1, Double number2) throws CommandException {
        if (number2.equals(Double.valueOf("0.0"))) {
            throw new CommandException("Ошибка: деление на ноль");
        }
        return number1 / number2;
    }

    public static char getName() {
        return NAME;
    }
}
