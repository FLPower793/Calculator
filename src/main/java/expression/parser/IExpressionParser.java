package expression.parser;

import exception.ParserException;

import java.util.List;

public interface IExpressionParser {
    List<String> parse(String expression) throws ParserException;
    boolean isNumber(String number);
}
