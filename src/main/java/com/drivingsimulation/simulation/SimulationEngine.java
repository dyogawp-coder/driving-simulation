package com.drivingsimulation.simulation;

import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Field;

import java.util.List;

public final class SimulationEngine {

    private final CollisionDetector collisionDetector;

    public SimulationEngine(
            CollisionDetector collisionDetector
    ) {

        if (collisionDetector == null) {
            throw new IllegalArgumentException(
                    "Collision detector must not be null."
            );
        }

        this.collisionDetector = collisionDetector;
    }

    public void run(
            Field field,
            List<Car> cars
    ) {

        if (field == null) {
            throw new IllegalArgumentException(
                    "Field must not be null."
            );
        }

        if (cars == null) {
            throw new IllegalArgumentException(
                    "Cars must not be null."
            );
        }

        int step = 0;

        while (hasActiveCars(cars)) {

            step++;

            /*
             * Every ACTIVE car executes exactly one command
             * during one simulation step.
             */
            executeOneStep(
                    field,
                    cars
            );

            /*
             * Collision detection happens after all active
             * cars have executed their command for this step.
             */
            collisionDetector.detectCollisions(
                    cars,
                    step
            );
        }
    }

    private void executeOneStep(
            Field field,
            List<Car> cars
    ) {

        for (Car car : cars) {

            if (car.getStatus() == Car.Status.ACTIVE) {
                car.executeNextCommand(field);
            }
        }
    }

    private boolean hasActiveCars(List<Car> cars) {

        for (Car car : cars) {

            if (car.getStatus() == Car.Status.ACTIVE) {
                return true;
            }
        }

        return false;
    }
}