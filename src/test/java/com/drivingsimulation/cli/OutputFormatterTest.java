package com.drivingsimulation.cli;

import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Direction;
import com.drivingsimulation.domain.Position;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OutputFormatterTest {

    private final OutputFormatter outputFormatter =
            new OutputFormatter();

    @Test
    void shouldFormatWelcomeMessage() {

        assertEquals(
                "Welcome to Car Crash Java!",
                outputFormatter.welcomeMessage()
        );
    }

    @Test
    void shouldFormatInvalidInputMessage() {

        assertEquals(
                "Invalid input: Something went wrong.",
                outputFormatter.invalidInputMessage(
                        "Something went wrong."
                )
        );
    }

    @Test
    void shouldFormatNorthCar() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.NORTH,
                        "FF"
                );

        assertEquals(
                "- A, (1,2) N, FF",
                outputFormatter.formatCar(car)
        );
    }

    @Test
    void shouldFormatEastCar() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.EAST,
                        "FF"
                );

        assertEquals(
                "- A, (1,2) E, FF",
                outputFormatter.formatCar(car)
        );
    }

    @Test
    void shouldFormatSouthCar() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.SOUTH,
                        "FF"
                );

        assertEquals(
                "- A, (1,2) S, FF",
                outputFormatter.formatCar(car)
        );
    }

    @Test
    void shouldFormatWestCar() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.WEST,
                        "FF"
                );

        assertEquals(
                "- A, (1,2) W, FF",
                outputFormatter.formatCar(car)
        );
    }

    @Test
    void shouldFormatMultipleCars() {

        Car carA =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.NORTH,
                        "FF"
                );

        Car carB =
                new Car(
                        "B",
                        new Position(7, 8),
                        Direction.WEST,
                        "FF"
                );

        assertEquals(
                "- A, (1,2) N, FF"
                        + System.lineSeparator()
                        + "- B, (7,8) W, FF",
                outputFormatter.formatCars(
                        List.of(carA, carB)
                )
        );
    }

    @Test
    void shouldFormatSimulationResultForFinishedCar() {

        Car car =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.SOUTH,
                        ""
                );

        assertEquals(
                "- A, (5,4) S",
                outputFormatter.formatSimulationResult(
                        List.of(car)
                )
        );
    }

    @Test
    void shouldFormatSimulationResultForNormalCar() {

        Car car =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.SOUTH,
                        "FF"
                );

        assertEquals(
                "- A, (5,4) S",
                outputFormatter.formatSimulationResult(
                        List.of(car)
                )
        );
    }

    @Test
    void shouldFormatCollisionBetweenTwoCars() {

        Car carA =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.EAST,
                        "F"
                );

        Car carB =
                new Car(
                        "B",
                        new Position(5, 4),
                        Direction.SOUTH,
                        "F"
                );

        carA.markCollided(7);
        carB.markCollided(7);

        assertEquals(
                "- A, collides with B at (5,4) at step 7"
                        + System.lineSeparator()
                        + "- B, collides with A at (5,4) at step 7",
                outputFormatter.formatSimulationResult(
                        List.of(carA, carB)
                )
        );
    }
}