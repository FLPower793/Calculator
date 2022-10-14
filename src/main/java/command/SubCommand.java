package command;

import exception.CommandException;

public class SubCommand extends BinaryStackCommand {
    private static final char NAME = '-';
    private static final int PRIORITY = 1;

    @Override
    public int getPriority() {
        return PRIORITY;
    }

    @Override
    public Double process(Double number1, Double number2) throws CommandException {
        return number1 - number2;
    }

    public static char getName() {
        return NAME;
    }
}
