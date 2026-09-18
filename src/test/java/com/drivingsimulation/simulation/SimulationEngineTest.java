package com.drivingsimulation.simulation;

import com.drivingsimulation.domain.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimulationEngineTest {
        private final Field field = new Field(10, 10);

        private final SimulationEngine simulationEngine =
                new SimulationEngine(new CollisionDetector());

        @Test
        void shouldExecuteSingleCarSimulation() {
            Car car = new Car(
                    "A",
                    new Position(1, 2),
                    Direction.NORTH,
                    "FFRFFFFRRL"
            );

            simulationEngine.run(field, List.of(car));

            assertEquals(new Position(5, 4), car.getPosition());
            assertEquals(Direction.SOUTH, car.getDirection());
            assertEquals(Car.Status.FINISHED, car.getStatus());
        }

        @Test
        void shouldExecuteCarsSynchronously() {
            Car carA = new Car(
                    "A",
                    new Position(1, 2),
                    Direction.NORTH,
                    "FFR"
            );

            Car carB = new Car(
                    "B",
                    new Position(7, 8),
                    Direction.WEST,
                    "FF"
            );

            simulationEngine.run(field, List.of(carA, carB));

            assertEquals(new Position(1, 4), carA.getPosition());
            assertEquals(Direction.EAST, carA.getDirection());

            assertEquals(new Position(5, 8), carB.getPosition());
            assertEquals(Direction.WEST, carB.getDirection());
        }

        @Test
        void shouldIgnoreMovementBeyondFieldBoundary() {
            Car car = new Car(
                    "A",
                    new Position(9, 9),
                    Direction.NORTH,
                    "F"
            );

            simulationEngine.run(field, List.of(car));

            assertEquals(new Position(9, 9), car.getPosition());
            assertEquals(Direction.NORTH, car.getDirection());
            assertEquals(Car.Status.FINISHED, car.getStatus());
        }

        @Test
        void shouldDetectCollisionWhenCarsReachSamePosition() {
            Car carA = new Car(
                    "A",
                    new Position(4, 4),
                    Direction.EAST,
                    "F"
            );

            Car carB = new Car(
                    "B",
                    new Position(6, 4),
                    Direction.WEST,
                    "F"
            );

            simulationEngine.run(field, List.of(carA, carB));

            assertEquals(new Position(5, 4), carA.getPosition());
            assertEquals(new Position(5, 4), carB.getPosition());

            assertEquals(Car.Status.COLLIDED, carA.getStatus());
            assertEquals(Car.Status.COLLIDED, carB.getStatus());
        }

        @Test
        void shouldContinueSimulationAfterCollision() {
            Car carA = new Car(
                    "A",
                    new Position(4, 4),
                    Direction.EAST,
                    "F"
            );

            Car carB = new Car(
                    "B",
                    new Position(6, 4),
                    Direction.WEST,
                    "F"
            );

            Car carC = new Car(
                    "C",
                    new Position(0, 0),
                    Direction.EAST,
                    "FFF"
            );

            simulationEngine.run(field, List.of(carA, carB, carC));

            assertEquals(Car.Status.COLLIDED, carA.getStatus());
            assertEquals(Car.Status.COLLIDED, carB.getStatus());

            assertEquals(new Position(3, 0), carC.getPosition());
            assertEquals(Car.Status.FINISHED, carC.getStatus());
        }

    @Test
    void shouldDetectCollisionFromAssignmentExample() {
        Car carA = new Car(
                "A",
                new Position(1, 2),
                Direction.NORTH,
                "FFRFFFFRRL"
        );

        Car carB = new Car(
                "B",
                new Position(7, 8),
                Direction.WEST,
                "FFLFFFFFFF"
        );

        simulationEngine.run(field, List.of(carA, carB));

        assertEquals(new Position(5, 4), carA.getPosition());
        assertEquals(new Position(5, 4), carB.getPosition());

        assertEquals(Car.Status.COLLIDED, carA.getStatus());
        assertEquals(Car.Status.COLLIDED, carB.getStatus());
    }

}
