package calculator;

import command.ICommand;
import context.ICalculatorContext;
import exception.CommandException;
import exception.ParserException;
import expression.parser.IExpressionParser;
import expression.reader.IExpressionReader;
import uidriver.IUIDriver;

import java.util.List;

public class StackCalculator implements ICalculator {
    private final ICalculatorContext context;
    private final IExpressionReader reader;
    private final IExpressionParser parser;
    private final IUIDriver uiDriver;

    public StackCalculator(ICalculatorContext context, IExpressionReader reader, IExpressionParser parser, IUIDriver uiDriver) {
        this.context = context;
        this.reader = reader;
        this.parser = parser;
        this.uiDriver = uiDriver;
    }

    public void start() {
        while (reader.hasExpression()) {
            try {
                uiDriver.showMessage("Введите выражение: ", false);
                String enteredExpression = reader.readExpression().trim();
                if (enteredExpression.equals("") || !reader.hasExpression()) break;

                List<String> parsedExpression = parser.parse(enteredExpression);

                for (String element : parsedExpression) {
                    if (parser.isNumber(element)) {
                        context.getStack().push(Double.parseDouble(element));

                    } else if (context.getCommands().containsKey(element.charAt(0))) {
                        ICommand command = context.getCommands().get(element.charAt(0));
                        command.execute(context);

                    } else {
                        throw new ParserException(String.format("Ошибка: Команда не опознана: %s", element));
                    }
                }
                uiDriver.showAnswer(context.getStack().pop());

            } catch (CommandException | ParserException e) {
                uiDriver.showError(e.getMessage());
            }
            resetState();
        }
    }

    @Override
    public void resetState() {
        context.getStack().clear();
    }

}
