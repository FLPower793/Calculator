package calculator;

import command.ICommand;
import context.ICalculatorContext;
import exception.CommandException;
import exception.ParserException;
import expression.parser.IExpressionParser;
import expression.reader.IExpressionReader;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import stack.IStack;
import uidriver.IUIDriver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StackCalculatorTest {
    private static ICalculatorContext contextMock;
    private static IExpressionParser parserMock;
    private static IExpressionReader readerMock;
    private static IUIDriver uiDriverMock;
    private static final ICommand commandMock = mock(ICommand.class);
    private static final ArgumentCaptor<String> uiStringCaptor = ArgumentCaptor.forClass(String.class);
    private static final ArgumentCaptor<Double> pushToStackCaptor = ArgumentCaptor.forClass(Double.class);

    void prepareContext() {
        IStack<Double> stackMock = mock(IStack.class);
        doNothing().when(stackMock).push(pushToStackCaptor.capture());
        when(stackMock.pop()).thenReturn(3.0);

        Map<Character, ICommand> commands = new HashMap<>();
        commands.put('+', commandMock);

        contextMock = mock(ICalculatorContext.class);
        when(contextMock.getStack()).thenReturn(stackMock);
        when(contextMock.getCommands()).thenReturn(commands);
    }

    void prepareParser() throws ParserException {
        parserMock = mock(IExpressionParser.class);
        List<String> parsedResult = new ArrayList<>();
        parsedResult.add("1");
        parsedResult.add("2");
        parsedResult.add("+");
        when(parserMock.parse(any())).thenReturn(parsedResult);
        when(parserMock.isNumber(any())).thenReturn(true).thenReturn(true).thenReturn(false);
    }

    void prepareReader() {
        readerMock = mock(IExpressionReader.class);
        when(readerMock.hasExpression()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(readerMock.readExpression()).thenReturn("1+2");
    }

    void prepareUIDriver() {
        uiDriverMock = mock(IUIDriver.class);
        doNothing().when(uiDriverMock).showMessage(uiStringCaptor.capture(), anyBoolean());
    }

    @Test
    void start() throws ParserException {
        prepareContext();
        prepareParser();
        prepareReader();
        prepareUIDriver();
        StackCalculator calculator = new StackCalculator(contextMock, readerMock, parserMock, uiDriverMock);
        calculator.start();

        verify(readerMock, times(3)).hasExpression();
        verify(uiDriverMock, times(1)).showMessage("Введите выражение: ", false);
        assertEquals("Введите выражение: ", uiStringCaptor.getAllValues().get(0));
        verify(parserMock, times(1)).parse(any());
        assertEquals(1.0, pushToStackCaptor.getAllValues().get(0));
        assertEquals(2.0, pushToStackCaptor.getAllValues().get(1));
        verify(contextMock, times(2)).getCommands();
        verify(uiDriverMock, times(1)).showAnswer(3.0);
    }

    @Test
    void startParserReturnUnexpectedCommand() throws ParserException {
        prepareContext();
        parserMock = mock(IExpressionParser.class);
        List<String> parsedResult = new ArrayList<>();
        parsedResult.add("X");
        when(parserMock.parse(any())).thenReturn(parsedResult);
        when(parserMock.isNumber(any())).thenReturn(false);
        prepareReader();
        prepareUIDriver();
        StackCalculator calculator = new StackCalculator(contextMock, readerMock, parserMock, uiDriverMock);
        calculator.start();

        verify(uiDriverMock, times(1)).showError("Ошибка: Команда не опознана: X");
    }

    @Test
    void startCommandExecuteThrowsException() throws ParserException, CommandException {
        prepareContext();
        doThrow(new CommandException("Test")).when(commandMock).execute(any(ICalculatorContext.class));
        prepareParser();
        prepareReader();
        prepareUIDriver();
        StackCalculator calculator = new StackCalculator(contextMock, readerMock, parserMock, uiDriverMock);
        calculator.start();

        verify(uiDriverMock, times(1)).showError("Test");
    }
}