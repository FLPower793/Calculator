package command;

import context.ICalculatorContext;
import exception.CommandException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import stack.IStack;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BinaryStackCommandTest {
    private static final ICalculatorContext contextMock = mock(ICalculatorContext.class);
    private static final IStack<Double> stackMock = mock(IStack.class);
    private static BinaryStackCommand binaryStackCommand;
    private static final double DELTA = 1e-15;

    @BeforeAll
    static void init() {
        binaryStackCommand = new BinaryStackCommand() {
            @Override
            public int getPriority() {
                return 0;
            }

            @Override
            public Double process(Double number1, Double number2) {
                return number1;
            }
        };

        when(contextMock.getStack()).thenReturn(stackMock);
    }

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(1, 2),
                Arguments.of(5.6, 4),
                Arguments.of(0, 10),
                Arguments.of(-100, 5)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void execute(double number1, double number2) throws CommandException {
        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);

        when(stackMock.size()).thenReturn(2);
        when(stackMock.pop()).thenReturn(number2).thenReturn(number1);
        doNothing().when(stackMock).push(captor.capture());

        binaryStackCommand.execute(contextMock);
        assertEquals(number1, captor.getValue(), DELTA);
    }

    @Test
    void exceptionDuringProcessCall() {
        BinaryStackCommand binaryCommandProcessEx = new BinaryStackCommand() {
            @Override
            public int getPriority() {
                return 0;
            }

            @Override
            public Double process(Double number1, Double number2) throws CommandException {
                throw new CommandException("Exception");
            }
        };

        ArgumentCaptor<Double> captor = ArgumentCaptor.forClass(Double.class);

        when(stackMock.size()).thenReturn(2);
        when(stackMock.pop()).thenReturn(2.0).thenReturn(1.0);
        doNothing().when(stackMock).push(captor.capture());

        assertThrows(CommandException.class, () -> binaryCommandProcessEx.execute(contextMock));
        assertEquals(1.0, captor.getAllValues().get(0), DELTA);
        assertEquals(2.0, captor.getAllValues().get(1), DELTA);
    }

    @Test
    void validationError() {
        when(stackMock.size()).thenReturn(1);
        assertThrows(CommandException.class, () -> binaryStackCommand.execute(contextMock));
    }

}