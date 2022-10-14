package expression.parser;

import context.ICalculatorContext;
import exception.ParserException;
import org.apache.commons.lang3.StringUtils;
import stack.IStack;
import stack.Stack;

import java.util.ArrayList;
import java.util.List;

public class StringExpressionToOPZParser implements IExpressionParser {
    private final static char LEFT_BRACE = '(';
    private final static char RIGHT_BRACE = ')';
    private final static char DECIMAL_POINT = '.';

    private final ICalculatorContext context;

    public StringExpressionToOPZParser(ICalculatorContext context) {
        this.context = context;
    }

    @Override
    public List<String> parse(String expression) throws ParserException {
        List<String> postfixExpression = new ArrayList<>();
        IStack<Character> operationStack = new Stack<>();
        StringBuilder number = new StringBuilder();

        if (StringUtils.countMatches(expression, LEFT_BRACE) != StringUtils.countMatches(expression, RIGHT_BRACE)) {
            throw new ParserException("Ошибка: Число открывающих и закрывающих скобок не совпадает");
        }

        char[] chars = expression.toCharArray();
        for (char symbol : chars) {
            if (Character.isDigit(symbol) || symbol == DECIMAL_POINT) {
                number.append(symbol);
                continue;
            }

            if (number.length() > 0 && isNumber(number.toString())) {
                postfixExpression.add(number.toString());
                number.setLength(0);
            }

            if (symbol == LEFT_BRACE) {
                operationStack.push(symbol);

            } else if (symbol == RIGHT_BRACE) {
                while (operationStack.size() > 0 && operationStack.peek() != LEFT_BRACE) {
                    postfixExpression.add(operationStack.pop().toString());
                }
                // Remove left brace
                operationStack.pop();

            } else if (context.getCommands().containsKey(symbol)) {
                // Put commands with a higher priority into output list
                while (operationStack.size() > 0 && operationStack.peek() != LEFT_BRACE &&
                        context.getCommands().get(operationStack.peek()).getPriority() >=
                                context.getCommands().get(symbol).getPriority()) {
                    postfixExpression.add(operationStack.pop().toString());
                }
                operationStack.push(symbol);

            } else {
                throw new ParserException(String.format("Неожиданный символ: %c", symbol));
            }
        }

        if (number.length() > 0 && isNumber(number.toString())) {
            postfixExpression.add(number.toString());
            number.setLength(0);
        }

        while (operationStack.size() > 0) {
            postfixExpression.add(operationStack.pop().toString());
        }

        return postfixExpression;
    }

    @Override
    public boolean isNumber(String probablyNumber) {
        boolean isNumber = true;
        try {
            Double.parseDouble(probablyNumber);
        } catch (NumberFormatException e) {
            isNumber = false;
        }
        return isNumber;
    }
}
