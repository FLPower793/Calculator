package expression.reader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import uidriver.IUIDriver;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UIExpressionReaderTest {
    private static final IUIDriver uiDriverMock = mock(IUIDriver.class);
    private static final UIExpressionReader reader = new UIExpressionReader(uiDriverMock);

    @Test
    void initTest() {
        UIExpressionReader cleanReader = new UIExpressionReader(uiDriverMock);
        assertEquals("", cleanReader.getLastExpression());
        assertNotNull(cleanReader.getUIDriver());
    }

    @Test
    void readExpression() {
        String test = "1+2-3";
        when(uiDriverMock.getExpression()).thenReturn(test);
        assertEquals(test, reader.readExpression());
        assertEquals(test, reader.getLastExpression());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Exit", "exit", "EXIT", "Выход", "выход", "ВЫХОД"})
    void hasExpression(String line) {
        when(uiDriverMock.getExpression()).thenReturn(line);
        reader.readExpression();
        assertFalse(reader.hasExpression());
    }

    @Test
    void hasExpressionTrue() {
        String test = "(56-76)*3.3";
        when(uiDriverMock.getExpression()).thenReturn(test);
        reader.readExpression();
        assertTrue(reader.hasExpression());
    }

}