package command;

import context.ICalculatorContext;
import exception.CommandException;

public interface ICommand {
    int getPriority();

    void execute(ICalculatorContext context) throws CommandException;

    void validate(ICalculatorContext context) throws CommandException;
}
