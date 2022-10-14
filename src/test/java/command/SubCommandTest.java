package command;

import exception.CommandException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class SubCommandTest {

    private static final double DELTA = 1e-15;
    private static final SubCommand subCommand = new SubCommand();

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(1, 2, -1),
                Arguments.of(5.6, 3.4, 2.2),
                Arguments.of(10, 0, 10),
                Arguments.of(5, -14, 19),
                Arguments.of(7, 7, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void process(double number1, double number2, double result) throws CommandException {
        Assertions.assertEquals(result, subCommand.process(number1, number2), DELTA);
    }

}