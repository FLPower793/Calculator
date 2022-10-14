package context;

import command.ICommand;
import stack.IStack;
import uidriver.IUIDriver;

import java.util.Map;

public interface ICalculatorContext {
    Map<Character, ICommand> getCommands();

    IStack<Double> getStack();

}
