package com.drivingsimulation;

import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Direction;
import com.drivingsimulation.domain.Field;
import com.drivingsimulation.domain.Position;
import com.drivingsimulation.simulation.CollisionDetector;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CollisionTest {

    private final CollisionDetector collisionDetector =
            new CollisionDetector();

    @Test
    void shouldDetectCollisionBetweenTwoCars() {

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

        collisionDetector.detectCollisions(
                List.of(carA, carB),
                1
        );

        assertEquals(
                Car.Status.COLLIDED,
                carA.getStatus()
        );

        assertEquals(
                Car.Status.COLLIDED,
                carB.getStatus()
        );

        assertEquals(
                1,
                carA.getCollisionStep()
        );

        assertEquals(
                1,
                carB.getCollisionStep()
        );
    }

    @Test
    void shouldMarkAllCarsWhenThreeCarsOccupySamePosition() {

        Car carA =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.NORTH,
                        "F"
                );

        Car carB =
                new Car(
                        "B",
                        new Position(5, 4),
                        Direction.SOUTH,
                        "F"
                );

        Car carC =
                new Car(
                        "C",
                        new Position(5, 4),
                        Direction.EAST,
                        "F"
                );

        collisionDetector.detectCollisions(
                List.of(carA, carB, carC),
                2
        );

        assertEquals(
                Car.Status.COLLIDED,
                carA.getStatus()
        );

        assertEquals(
                Car.Status.COLLIDED,
                carB.getStatus()
        );

        assertEquals(
                Car.Status.COLLIDED,
                carC.getStatus()
        );

        assertEquals(
                2,
                carA.getCollisionStep()
        );

        assertEquals(
                2,
                carB.getCollisionStep()
        );

        assertEquals(
                2,
                carC.getCollisionStep()
        );
    }

    @Test
    void shouldNotDetectCollisionWhenCarsAreAtDifferentPositions() {

        Car carA =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.NORTH,
                        "F"
                );

        Car carB =
                new Car(
                        "B",
                        new Position(6, 4),
                        Direction.SOUTH,
                        "F"
                );

        collisionDetector.detectCollisions(
                List.of(carA, carB),
                1
        );

        assertEquals(
                Car.Status.ACTIVE,
                carA.getStatus()
        );

        assertEquals(
                Car.Status.ACTIVE,
                carB.getStatus()
        );

        assertEquals(
                null,
                carA.getCollisionStep()
        );

        assertEquals(
                null,
                carB.getCollisionStep()
        );
    }

    @Test
    void shouldIgnoreAlreadyCollidedCars() {

        Car carA =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.NORTH,
                        "F"
                );

        Car carB =
                new Car(
                        "B",
                        new Position(5, 4),
                        Direction.SOUTH,
                        "F"
                );

        carA.markCollided(1);

        collisionDetector.detectCollisions(
                List.of(carA, carB),
                2
        );

        assertEquals(
                Car.Status.COLLIDED,
                carA.getStatus()
        );

        assertEquals(
                Car.Status.ACTIVE,
                carB.getStatus()
        );

        assertEquals(
                1,
                carA.getCollisionStep()
        );

        assertEquals(
                null,
                carB.getCollisionStep()
        );
    }

    @Test
    void shouldDetectCollisionWithFinishedCar() {

        Car finishedCar =
                new Car(
                        "A",
                        new Position(5, 4),
                        Direction.NORTH,
                        ""
                );

        Car movingCar =
                new Car(
                        "B",
                        new Position(5, 4),
                        Direction.SOUTH,
                        ""
                );

        collisionDetector.detectCollisions(
                List.of(finishedCar, movingCar),
                3
        );

        assertEquals(
                Car.Status.COLLIDED,
                finishedCar.getStatus()
        );

        assertEquals(
                Car.Status.COLLIDED,
                movingCar.getStatus()
        );

        assertEquals(
                3,
                finishedCar.getCollisionStep()
        );

        assertEquals(
                3,
                movingCar.getCollisionStep()
        );
    }

    @Test
    void shouldNotDetectCollisionWhenCarsCrossEachOther() {

        Car carA =
                new Car(
                        "A",
                        new Position(1, 0),
                        Direction.EAST,
                        "F"
                );

        Car carB =
                new Car(
                        "B",
                        new Position(2, 0),
                        Direction.WEST,
                        "F"
                );

        carA.executeNextCommand(
                new Field(10, 10)
        );

        carB.executeNextCommand(
                new Field(10, 10)
        );

        collisionDetector.detectCollisions(
                List.of(carA, carB),
                1
        );

        assertEquals(
                new Position(2, 0),
                carA.getPosition()
        );

        assertEquals(
                new Position(1, 0),
                carB.getPosition()
        );

        assertEquals(
                Car.Status.FINISHED,
                carA.getStatus()
        );

        assertEquals(
                Car.Status.FINISHED,
                carB.getStatus()
        );

        assertEquals(
                null,
                carA.getCollisionStep()
        );

        assertEquals(
                null,
                carB.getCollisionStep()
        );
    }
}