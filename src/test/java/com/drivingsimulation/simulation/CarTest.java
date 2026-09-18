package com.drivingsimulation;

import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Direction;
import com.drivingsimulation.domain.Field;
import com.drivingsimulation.domain.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CarTest {

    private final Field field =
            new Field(10, 10);

    @Test
    void shouldMoveForwardNorth() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.NORTH,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(1, 3),
                car.getPosition()
        );

        assertEquals(
                Direction.NORTH,
                car.getDirection()
        );
    }

    @Test
    void shouldMoveForwardEast() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.EAST,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(2, 2),
                car.getPosition()
        );
    }

    @Test
    void shouldMoveForwardSouth() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.SOUTH,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(1, 1),
                car.getPosition()
        );
    }

    @Test
    void shouldMoveForwardWest() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 2),
                        Direction.WEST,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(0, 2),
                car.getPosition()
        );
    }

    @Test
    void shouldIgnoreMovementBeyondNorthBoundary() {

        Car car =
                new Car(
                        "A",
                        new Position(5, 9),
                        Direction.NORTH,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(5, 9),
                car.getPosition()
        );
    }

    @Test
    void shouldIgnoreMovementBeyondEastBoundary() {

        Car car =
                new Car(
                        "A",
                        new Position(9, 5),
                        Direction.EAST,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(9, 5),
                car.getPosition()
        );
    }

    @Test
    void shouldIgnoreMovementBeyondSouthBoundary() {

        Car car =
                new Car(
                        "A",
                        new Position(5, 0),
                        Direction.SOUTH,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(5, 0),
                car.getPosition()
        );
    }

    @Test
    void shouldIgnoreMovementBeyondWestBoundary() {

        Car car =
                new Car(
                        "A",
                        new Position(0, 5),
                        Direction.WEST,
                        "F"
                );

        car.executeNextCommand(field);

        assertEquals(
                new Position(0, 5),
                car.getPosition()
        );
    }

    @Test
    void shouldTurnLeft() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 1),
                        Direction.NORTH,
                        "L"
                );

        car.executeNextCommand(field);

        assertEquals(
                Direction.WEST,
                car.getDirection()
        );
    }

    @Test
    void shouldTurnRight() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 1),
                        Direction.NORTH,
                        "R"
                );

        car.executeNextCommand(field);

        assertEquals(
                Direction.EAST,
                car.getDirection()
        );
    }

    @Test
    void shouldFinishAfterExecutingAllCommands() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 1),
                        Direction.NORTH,
                        "F"
                );

        assertTrue(car.hasNextCommand());

        car.executeNextCommand(field);

        assertFalse(car.hasNextCommand());

        assertEquals(
                Car.Status.FINISHED,
                car.getStatus()
        );
    }

    @Test
    void shouldImmediatelyFinishWhenCommandsAreEmpty() {

        Car car =
                new Car(
                        "A",
                        new Position(1, 1),
                        Direction.NORTH,
                        ""
                );

        assertEquals(
                Car.Status.FINISHED,
                car.getStatus()
        );

        assertFalse(
                car.hasNextCommand()
        );
    }
}