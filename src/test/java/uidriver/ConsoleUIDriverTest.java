package uidriver;

import iodriver.IIODriver;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ConsoleUIDriverTest {
    private static final IIODriver mockIODriver = mock(IIODriver.class);
    private static final ConsoleUIDriver consoleUIDriver = new ConsoleUIDriver(mockIODriver);

    @Test
    void showMessage() {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockIODriver).printLine(stringCaptor.capture());

        consoleUIDriver.showMessage("This is a test message");
        assertEquals("This is a test message", stringCaptor.getValue());
    }

    @Test
    void showError() {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockIODriver).printLine(stringCaptor.capture());

        consoleUIDriver.showError("Error");
        assertEquals("Error", stringCaptor.getValue());
    }

    @Test
    void getExpression() {
        when(mockIODriver.getLine()).thenReturn("2+3");
        assertEquals("2+3", consoleUIDriver.getExpression());
    }

    @Test
    void showAnswer() {
        ArgumentCaptor<String> stringCaptor = ArgumentCaptor.forClass(String.class);
        doNothing().when(mockIODriver).printLine(stringCaptor.capture());
        consoleUIDriver.showAnswer(5.43);
        assertEquals("Результат: 5.43", stringCaptor.getValue());
    }

}