package command;

import exception.CommandException;

public class MulCommand extends BinaryStackCommand {
    private static final char NAME = '*';
    private static final int PRIORITY = 2;

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public Double process(Double number1, Double number2) throws CommandException {
        return number1 * number2;
    }

    public static char getName() {
        return NAME;
    }
}
