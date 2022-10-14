package context;

import command.ICommand;
import stack.IStack;

import java.util.Map;

public class CalculatorContext implements ICalculatorContext {
    private final Map<Character, ICommand> commands;
    private final IStack<Double> stack;

    public CalculatorContext(Map<Character, ICommand> commands, IStack<Double> stack) {
        this.commands = commands;
        this.stack = stack;
    }

    @Override
    public Map<Character, ICommand> getCommands() {
        return commands;
    }

    @Override
    public IStack<Double> getStack() {
        return stack;
    }

}
