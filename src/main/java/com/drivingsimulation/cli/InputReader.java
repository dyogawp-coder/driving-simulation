package com.drivingsimulation.cli;

import java.util.Scanner;

public final class InputReader {

    private final Scanner scanner;

    public InputReader(Scanner scanner) {

        if (scanner == null) {
            throw new IllegalArgumentException(
                    "Scanner must not be null."
            );
        }

        this.scanner = scanner;
    }

    public String readLine() {
        return scanner.nextLine();
    }

    public int readInteger() {

        String input =
                readLine().trim();

        try {

            return Integer.parseInt(input);

        } catch (NumberFormatException exception) {

            throw new IllegalArgumentException(
                    "Input must be a valid integer: "
                            + input,
                    exception
            );
        }
    }

    public String[] readTokens() {

        String input =
                readLine().trim();

        if (input.isEmpty()) {
            return new String[0];
        }

        return input.split("\\s+");
    }
}