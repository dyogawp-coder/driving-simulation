package com.drivingsimulation.cli;

import org.junit.jupiter.api.Test;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputReaderTest {

    @Test
    void shouldReadLine() {
        Scanner scanner = new Scanner("Hello World");

        InputReader inputReader = new InputReader(scanner);

        assertEquals("Hello World", inputReader.readLine());
    }

    @Test
    void shouldReadMultipleLines() {
        Scanner scanner = new Scanner(
                "First Line\nSecond Line"
        );

        InputReader inputReader = new InputReader(scanner);

        assertEquals("First Line", inputReader.readLine());
        assertEquals("Second Line", inputReader.readLine());
    }

    @Test
    void shouldRejectNullScanner() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new InputReader(null)
        );
    }

    @Test
    void shouldReadPositiveInteger() {
        Scanner scanner = new Scanner("10");

        InputReader inputReader = new InputReader(scanner);

        assertEquals(10, inputReader.readInteger());
    }

    @Test
    void shouldReadZero() {
        Scanner scanner = new Scanner("0");

        InputReader inputReader = new InputReader(scanner);

        assertEquals(0, inputReader.readInteger());
    }

    @Test
    void shouldReadNegativeInteger() {
        Scanner scanner = new Scanner("-5");

        InputReader inputReader = new InputReader(scanner);

        assertEquals(-5, inputReader.readInteger());
    }

    @Test
    void shouldIgnoreWhitespaceAroundInteger() {
        Scanner scanner = new Scanner("   25   ");

        InputReader inputReader = new InputReader(scanner);

        assertEquals(25, inputReader.readInteger());
    }

    @Test
    void shouldRejectInvalidInteger() {
        Scanner scanner = new Scanner("abc");

        InputReader inputReader = new InputReader(scanner);

        assertThrows(
                IllegalArgumentException.class,
                inputReader::readInteger
        );
    }

    @Test
    void shouldRejectDecimalNumber() {
        Scanner scanner = new Scanner("10.5");

        InputReader inputReader = new InputReader(scanner);

        assertThrows(
                IllegalArgumentException.class,
                inputReader::readInteger
        );
    }
}
