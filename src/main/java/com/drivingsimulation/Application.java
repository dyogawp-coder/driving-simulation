package com.drivingsimulation;

import com.drivingsimulation.cli.CommandLineInterface;
import com.drivingsimulation.cli.InputReader;
import com.drivingsimulation.cli.OutputFormatter;

import java.util.Scanner;

public class Application {

    public static void main(String[] args) {

        InputReader inputReader =
                new InputReader(new Scanner(System.in));

        OutputFormatter outputFormatter =
                new OutputFormatter();

        CommandLineInterface commandLineInterface =
                new CommandLineInterface(
                        inputReader,
                        outputFormatter
                );

        commandLineInterface.start();
    }
}