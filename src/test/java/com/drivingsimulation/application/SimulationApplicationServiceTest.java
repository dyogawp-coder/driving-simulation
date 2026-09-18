package com.drivingsimulation.application;

import com.drivingsimulation.application.SimulationApplicationService;
import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Direction;
import com.drivingsimulation.domain.Field;
import com.drivingsimulation.domain.Position;
import com.drivingsimulation.simulation.CollisionDetector;
import com.drivingsimulation.simulation.SimulationEngine;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimulationApplicationServiceTest {

    private SimulationApplicationService createService(
            int width,
            int height
    ) {

        Field field =
                new Field(
                        width,
                        height
                );

        SimulationEngine simulationEngine =
                new SimulationEngine(
                        new CollisionDetector()
                );

        return new SimulationApplicationService(
                field,
                simulationEngine
        );
    }

    @Test
    void shouldRunExampleOneSuccessfully() {

        SimulationApplicationService service =
                createService(10, 10);

        service.addCar(
                "A",
                new Position(1, 2),
                Direction.NORTH,
                "FFRFFFFRRL"
        );

        service.runSimulation();

        List<Car> cars =
                service.getCars()
                        .stream()
                        .toList();

        assertEquals(
                1,
                cars.size()
        );

        Car car =
                cars.get(0);

        assertEquals(
                new Position(5, 4),
                car.getPosition()
        );

        assertEquals(
                Direction.SOUTH,
                car.getDirection()
        );

        assertEquals(
                Car.Status.FINISHED,
                car.getStatus()
        );
    }

    @Test
    void shouldRunExampleTwoAndDetectCollisionAtStepSeven() {

        SimulationApplicationService service =
                createService(10, 10);

        service.addCar(
                "A",
                new Position(1, 2),
                Direction.NORTH,
                "FFRFFFFRRL"
        );

        service.addCar(
                "B",
                new Position(7, 8),
                Direction.WEST,
                "FFLFFFFFFF"
        );

        service.runSimulation();

        List<Car> cars =
                service.getCars()
                        .stream()
                        .toList();

        assertEquals(
                2,
                cars.size()
        );

        Car carA =
                cars.get(0);

        Car carB =
                cars.get(1);

        assertEquals(
                new Position(5, 4),
                carA.getPosition()
        );

        assertEquals(
                Direction.EAST,
                carA.getDirection()
        );

        assertEquals(
                Car.Status.COLLIDED,
                carA.getStatus()
        );

        assertEquals(
                7,
                carA.getCollisionStep()
        );

        assertEquals(
                new Position(5, 4),
                carB.getPosition()
        );

        assertEquals(
                Direction.SOUTH,
                carB.getDirection()
        );

        assertEquals(
                Car.Status.COLLIDED,
                carB.getStatus()
        );

        assertEquals(
                7,
                carB.getCollisionStep()
        );
    }

    @Test
    void shouldRejectDuplicateCarName() {

        SimulationApplicationService service =
                createService(10, 10);

        service.addCar(
                "A",
                new Position(1, 1),
                Direction.NORTH,
                "F"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addCar(
                        "A",
                        new Position(2, 2),
                        Direction.SOUTH,
                        "F"
                )
        );
    }

    @Test
    void shouldRejectDuplicateStartingPosition() {

        SimulationApplicationService service =
                createService(10, 10);

        service.addCar(
                "A",
                new Position(1, 1),
                Direction.NORTH,
                "F"
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addCar(
                        "B",
                        new Position(1, 1),
                        Direction.SOUTH,
                        "F"
                )
        );
    }

    @Test
    void shouldRejectCarOutsideField() {

        SimulationApplicationService service =
                createService(10, 10);

        assertThrows(
                IllegalArgumentException.class,
                () -> service.addCar(
                        "A",
                        new Position(10, 10),
                        Direction.NORTH,
                        "F"
                )
        );
    }

    @Test
    void shouldReturnCarsInInsertionOrder() {

        SimulationApplicationService service =
                createService(10, 10);

        service.addCar(
                "A",
                new Position(1, 1),
                Direction.NORTH,
                "F"
        );

        service.addCar(
                "B",
                new Position(2, 2),
                Direction.SOUTH,
                "F"
        );

        List<Car> cars =
                service.getCars()
                        .stream()
                        .toList();

        assertEquals(
                "A",
                cars.get(0).getName()
        );

        assertEquals(
                "B",
                cars.get(1).getName()
        );
    }
}