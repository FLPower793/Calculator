package parser;

import command.ICommand;
import context.CalculatorContext;
import exception.ParserException;
import expression.parser.StringExpressionToOPZParser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StringExpressionToOPZParserTest {
    private static StringExpressionToOPZParser opzParser;

    @BeforeAll
    static void init() {
        Map<Character, ICommand> commands = new HashMap<>();

        ICommand addCommandMock = mock(ICommand.class);
        when(addCommandMock.getPriority()).thenReturn(1);
        commands.put('+', addCommandMock);

        ICommand subCommandMock = mock(ICommand.class);
        when(subCommandMock.getPriority()).thenReturn(1);
        commands.put('-', subCommandMock);

        ICommand mulCommandMock = mock(ICommand.class);
        when(mulCommandMock.getPriority()).thenReturn(2);
        commands.put('*', mulCommandMock);

        ICommand divCommandMock = mock(ICommand.class);
        when(divCommandMock.getPriority()).thenReturn(2);
        commands.put('/', divCommandMock);

        CalculatorContext contextMock = mock(CalculatorContext.class);
        when(contextMock.getCommands()).thenReturn(commands);

        opzParser = new StringExpressionToOPZParser(contextMock);
    }

    @Test
    void parseToOPZ1() throws ParserException {
        String infixExpression = "5*2+10";
        List<String> expected = new ArrayList<>();
        expected.add("5");
        expected.add("2");
        expected.add("*");
        expected.add("10");
        expected.add("+");
        assertEquals(expected, opzParser.parse(infixExpression));
    }

    @Test
    void parseToOPZ2() throws ParserException {
        String infixExpression = "(6+10-4)/(1+1*2)+1";
        List<String> expected = new ArrayList<>();
        expected.add("6");
        expected.add("10");
        expected.add("+");
        expected.add("4");
        expected.add("-");
        expected.add("1");
        expected.add("1");
        expected.add("2");
        expected.add("*");
        expected.add("+");
        expected.add("/");
        expected.add("1");
        expected.add("+");
        assertEquals(expected, opzParser.parse(infixExpression));
    }

    @Test
    void parseToOPZ3() throws ParserException {
        String infixExpression = "(2.1+(2.2*2.3))";
        List<String> expected = new ArrayList<>();
        expected.add(Double.toString(2.1));
        expected.add(Double.toString(2.2));
        expected.add(Double.toString(2.3));
        expected.add("*");
        expected.add("+");
        assertEquals(expected, opzParser.parse(infixExpression));
    }

    @Test
    void parseToOPZThrowsExceptionWhenParsingUnexpectedSymbol() {
        assertThrows(ParserException.class, () -> opzParser.parse("3avs+2"));
    }

    @Test
    void parseToOPZThrowsExceptionWhenDifferentBracesCount() {
        assertThrows(ParserException.class, () -> opzParser.parse("(1+(2+3)))"));
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("1", true),
                Arguments.of("5.6", true),
                Arguments.of("10..", false),
                Arguments.of("abc", false),
                Arguments.of("0", true),
                Arguments.of("-5.789", true),
                Arguments.of("--1", false),
                Arguments.of("1x3", false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void isNumber(String number, boolean expected){
        assertEquals(expected, opzParser.isNumber(number));
    }
}