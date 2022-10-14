package iodriver;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ConsoleIODriverTest {

    @Test
    void initTest() {
        ConsoleIODriver consoleIODriver = new ConsoleIODriver();
        assertNotNull(consoleIODriver.getScanner());
    }

}