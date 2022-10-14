package command;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class AddCommandTest {
    private static final double DELTA = 1e-15;
    private final static AddCommand addCommand = new AddCommand();

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of(1, 2, 3),
                Arguments.of(5.6, 3.4, 9),
                Arguments.of(10, 0, 10),
                Arguments.of(5, -14, -9)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    void process(double number1, double number2, double result) {
        Assertions.assertEquals(result, addCommand.process(number1, number2), DELTA);
    }

}