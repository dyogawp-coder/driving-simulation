package com.drivingsimulation.cli;

import com.drivingsimulation.application.SimulationApplicationService;
import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Direction;
import com.drivingsimulation.domain.Field;
import com.drivingsimulation.domain.Position;
import com.drivingsimulation.simulation.CollisionDetector;
import com.drivingsimulation.simulation.SimulationEngine;

import java.util.Collection;

public final class CommandLineInterface {

    private final InputReader inputReader;
    private final OutputFormatter outputFormatter;

    private SimulationApplicationService simulationService;

    public CommandLineInterface(
            InputReader inputReader,
            OutputFormatter outputFormatter) {

        if (inputReader == null) {
            throw new IllegalArgumentException(
                    "Input reader must not be null."
            );
        }

        if (outputFormatter == null) {
            throw new IllegalArgumentException(
                    "Output formatter must not be null."
            );
        }

        this.inputReader = inputReader;
        this.outputFormatter = outputFormatter;
    }

    public void start() {

        System.out.println(outputFormatter.welcomeMessage());

        while (true) {

            createSimulation();

            showMainMenu();

            if (!startOverOrExit()) {
                return;
            }
        }
    }

    private void createSimulation() {

        int[] dimensions = readFieldDimensions();

        int width = dimensions[0];
        int height = dimensions[1];

        Field field = new Field(width, height);

        simulationService =
                new SimulationApplicationService(
                        field,
                        new SimulationEngine(
                                new CollisionDetector()
                        )
                );

        System.out.println();

        System.out.println(
                "You have created a field of "
                        + width
                        + " x "
                        + height
                        + "."
        );
    }

    /**
     * Reads the field dimensions in x y format.
     *
     * Both width and height must be greater than zero.
     */
    private int[] readFieldDimensions() {

        while (true) {

            System.out.println();

            System.out.println(
                    "Please enter the width and heigh of the simulation field in x y format:"
            );

            String[] tokens = inputReader.readTokens();

            if (tokens.length != 2) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Please enter width and height in x y format."
                        )
                );

                continue;
            }

            int width;
            int height;

            try {

                width = Integer.parseInt(tokens[0]);
                height = Integer.parseInt(tokens[1]);

            } catch (NumberFormatException exception) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Width and height must be valid integers."
                        )
                );

                continue;
            }

            /*
             * Both width and height cannot be zero or negative.
             */
            if (width <= 0 && height <= 0) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Field width and height must be greater than zero."
                        )
                );

                continue;
            }

            /*
             * Width must be greater than zero.
             */
            if (width <= 0) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Field width must be greater than zero."
                        )
                );

                continue;
            }

            /*
             * Height must be greater than zero.
             */
            if (height <= 0) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Field height must be greater than zero."
                        )
                );

                continue;
            }

            return new int[]{width, height};
        }
    }

    private void showMainMenu() {

        while (true) {

            System.out.println();

            System.out.println(
                    "Please choose from the following options:"
            );

            System.out.println("[1] Add a car to field");
            System.out.println("[2] Run simulation");

            try {

                int choice = inputReader.readInteger();

                switch (choice) {

                    case 1 -> addCar();

                    case 2 -> {
                        runSimulation();
                        return;
                    }

                    default ->
                            System.out.println(
                                    outputFormatter.invalidInputMessage(
                                            "Please choose 1 or 2."
                                    )
                            );
                }

            } catch (IllegalArgumentException exception) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                exception.getMessage()
                        )
                );
            }
        }
    }

    private void addCar() {

        String name = readCarName();

        while (true) {

            try {

                CarInput carInput = readCarInput(name);

                simulationService.addCar(
                        name,
                        carInput.position(),
                        carInput.direction(),
                        carInput.commands()
                );

                System.out.println();

                printCurrentCars();

                return;

            } catch (IllegalArgumentException exception) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                exception.getMessage()
                        )
                );

                name = readCarName();
            }
        }
    }

    private String readCarName() {

        while (true) {

            System.out.println();

            System.out.println(
                    "Please enter the name of the car:"
            );

            String name = inputReader.readLine().trim();

            if (name.isEmpty()) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Car name must not be blank."
                        )
                );

                continue;
            }

            return name;
        }
    }

    private CarInput readCarInput(String carName) {

        PositionAndDirection positionAndDirection =
                readPositionAndDirection(carName);

        String commands = readCommands(carName);

        return new CarInput(
                positionAndDirection.position(),
                positionAndDirection.direction(),
                commands
        );
    }

    private PositionAndDirection readPositionAndDirection(
            String carName) {

        while (true) {

            System.out.println();

            System.out.println(
                    "Please enter initial position of car "
                            + carName
                            + " in x y Direction format:"
            );

            String[] tokens = inputReader.readTokens();

            if (tokens.length != 3) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "Please enter position in x y Direction format."
                        )
                );

                continue;
            }

            int x;
            int y;

            try {

                x = Integer.parseInt(tokens[0]);
                y = Integer.parseInt(tokens[1]);

            } catch (NumberFormatException exception) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                "X and Y coordinates must be valid integers."
                        )
                );

                continue;
            }

            Direction direction;

            try {

                direction = parseDirection(tokens[2]);

            } catch (IllegalArgumentException exception) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                exception.getMessage()
                        )
                );

                continue;
            }

            return new PositionAndDirection(
                    new Position(x, y),
                    direction
            );
        }
    }

    private Direction parseDirection(String input) {

        return switch (input.toUpperCase()) {

            case "N" -> Direction.NORTH;
            case "E" -> Direction.EAST;
            case "S" -> Direction.SOUTH;
            case "W" -> Direction.WEST;

            default ->
                    throw new IllegalArgumentException(
                            "Direction must be N, E, S, or W."
                    );
        };
    }

    /**
     * Reads and validates the commands for a car.
     *
     * Valid commands:
     * L = Left
     * R = Right
     * F = Forward
     */
    private String readCommands(String carName) {

        while (true) {

            System.out.println();

            System.out.println(
                    "Please enter the commands for car "
                            + carName
                            + ":"
            );

            System.out.println(
                    "Valid commands: L (Left), R (Right), F (Forward)"
            );

            String commands =
                    inputReader.readLine()
                            .trim()
                            .toUpperCase();

            try {

                validateCommands(commands);

                return commands;

            } catch (IllegalArgumentException exception) {

                System.out.println();

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                exception.getMessage()
                        )
                );
            }
        }
    }

    /**
     * Validates every command.
     *
     * Only L, R and F are allowed.
     */
    private void validateCommands(String commands) {

        for (char command : commands.toCharArray()) {

            if (command != 'L'
                    && command != 'R'
                    && command != 'F') {

                throw new IllegalArgumentException(
                        "Invalid command: "
                                + command
                                + ". Valid commands are L (Left), "
                                + "R (Right), and F (Forward)."
                );
            }
        }
    }

    private void runSimulation() {

        Collection<Car> cars =
                simulationService.getCars();

        if (cars.isEmpty()) {

            System.out.println();

            System.out.println(
                    "No cars have been added."
            );

            return;
        }

        System.out.println();

        printCurrentCars();

        simulationService.runSimulation();

        System.out.println();

        System.out.println(
                "After simulation, the result is:"
        );

        System.out.println(
                outputFormatter.formatSimulationResult(
                        simulationService.getCars()
                )
        );
    }

    private void printCurrentCars() {

        System.out.println(
                "Your current list of cars are:"
        );

        System.out.println(
                outputFormatter.formatCars(
                        simulationService.getCars()
                )
        );
    }

    private boolean startOverOrExit() {

        while (true) {

            System.out.println();

            System.out.println(
                    "Please choose from the following options:"
            );

            System.out.println("[1] Start over");
            System.out.println("[2] Exit");

            try {

                int choice = inputReader.readInteger();

                switch (choice) {

                    case 1 -> {
                        return true;
                    }

                    case 2 -> {

                        System.out.println();

                        System.out.println(
                                "Thank you for running the simulation. Goodbye!"
                        );

                        return false;
                    }

                    default ->
                            System.out.println(
                                    outputFormatter.invalidInputMessage(
                                            "Please choose 1 or 2."
                                    )
                            );
                }

            } catch (IllegalArgumentException exception) {

                System.out.println(
                        outputFormatter.invalidInputMessage(
                                exception.getMessage()
                        )
                );
            }
        }
    }

    private record PositionAndDirection(
            Position position,
            Direction direction) {
    }

    private record CarInput(
            Position position,
            Direction direction,
            String commands) {
    }
}