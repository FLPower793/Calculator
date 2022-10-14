package command;

import context.ICalculatorContext;
import exception.CommandException;
import exception.ValidateCommandException;
import stack.IStack;

abstract class BinaryStackCommand implements ICommand {

    @Override
    public void execute(ICalculatorContext context) throws CommandException {
        validate(context);

        Double number1 = 0.0, number2 = 0.0;
        IStack<Double> stack = context.getStack();
        try {
            number2 = stack.pop();
            number1 = stack.pop();
            stack.push(process(number1, number2));

        } catch (CommandException e) {
            stack.push(number1);
            stack.push(number2);
            throw e;
        }
    }

    abstract public Double process(Double number1, Double number2) throws CommandException;

    @Override
    public void validate(ICalculatorContext context) throws CommandException {
        if (context.getStack().size() < 2) {
            throw new ValidateCommandException("Ошибка: Недостаточно операндов для операции");
        }
    }

}
