import calculator.StackCalculator;
import command.*;
import context.CalculatorContext;
import context.ICalculatorContext;
import expression.parser.IExpressionParser;
import expression.parser.StringExpressionToOPZParser;
import expression.reader.IExpressionReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import stack.IStack;
import stack.Stack;
import uidriver.IUIDriver;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

class CalculatorAppTest {
    private static final IExpressionReader readerMock = mock(IExpressionReader.class);
    private static final IUIDriver uiDriverMock = mock(IUIDriver.class);
    private static StackCalculator stackCalculator;
    private static final ArgumentCaptor<Double> answerCaptor = ArgumentCaptor.forClass(Double.class);
    private static final double DELTA = 1e-15;

    @BeforeAll
    static void prepareCalculatorApp() {
        Map<Character, ICommand> commands = new HashMap<>();
        commands.put(AddCommand.getName(), new AddCommand());
        commands.put(SubCommand.getName(), new SubCommand());
        commands.put(MulCommand.getName(), new MulCommand());
        commands.put(DivCommand.getName(), new DivCommand());
        IStack<Double> stack = new Stack<>();
        ICalculatorContext context = new CalculatorContext(commands, stack);
        IExpressionParser parser = new StringExpressionToOPZParser(context);

        stackCalculator = new StackCalculator(context, readerMock, parser, uiDriverMock);
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of("1+2-3", 0.0),
                Arguments.of("5*2+10", 20.0),
                Arguments.of("(6+10-4)/(1+1*2)+1", 5.0),
                Arguments.of("3.7-(1.2+1.1)", 1.4),
                Arguments.of("1.7*(2.56-100)", -165.648)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void calculate(String expression, double answer) {
        when(readerMock.hasExpression()).thenReturn(true).thenReturn(true).thenReturn(false);
        when(readerMock.readExpression()).thenReturn(expression);
        doNothing().when(uiDriverMock).showAnswer(answerCaptor.capture());
        stackCalculator.start();
        Assertions.assertEquals(answer, answerCaptor.getValue(), DELTA);
    }

}