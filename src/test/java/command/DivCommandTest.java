package command;

import exception.CommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class DivCommandTest {
    private static final DivCommand divCommand = new DivCommand();
    private static final double DELTA = 1e-15;

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(6, 2, 3),
                Arguments.of(7.5, 5, 1.5),
                Arguments.of(0, 4.67, 0),
                Arguments.of(-100, 2, -50)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void process(double number1, double number2, double result) throws CommandException {
        Assertions.assertEquals(result, divCommand.process(number1, number2), DELTA);
    }

    @Test
    void divideByZero() {
        Assertions.assertThrows(CommandException.class, () -> divCommand.process(12.4, 0.0));
    }

}