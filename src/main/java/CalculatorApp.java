import calculator.StackCalculator;
import command.*;
import context.CalculatorContext;
import context.ICalculatorContext;
import expression.parser.IExpressionParser;
import expression.parser.StringExpressionToOPZParser;
import expression.reader.IExpressionReader;
import expression.reader.UIExpressionReader;
import iodriver.ConsoleIODriver;
import stack.IStack;
import stack.Stack;
import uidriver.ConsoleUIDriver;
import uidriver.IUIDriver;

import java.util.HashMap;
import java.util.Map;

public class CalculatorApp {
    public static void main(String[] args) {
        Map<Character, ICommand> commands = new HashMap<>();
        commands.put(AddCommand.getName(), new AddCommand());
        commands.put(SubCommand.getName(), new SubCommand());
        commands.put(MulCommand.getName(), new MulCommand());
        commands.put(DivCommand.getName(), new DivCommand());
        IStack<Double> stack = new Stack<>();
        ICalculatorContext context = new CalculatorContext(commands, stack);

        IUIDriver uiDriver = new ConsoleUIDriver(new ConsoleIODriver());
        IExpressionReader reader = new UIExpressionReader(uiDriver);
        IExpressionParser parser = new StringExpressionToOPZParser(context);

        StackCalculator stackCalculator = new StackCalculator(context, reader, parser, uiDriver);
        stackCalculator.start();
    }
}
