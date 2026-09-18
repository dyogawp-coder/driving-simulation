package com.drivingsimulation.simulation;

import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CollisionDetector {

    public void detectCollisions(
            List<Car> cars,
            int step
    ) {

        if (cars == null) {
            throw new IllegalArgumentException(
                    "Cars must not be null."
            );
        }

        if (step <= 0) {
            throw new IllegalArgumentException(
                    "Simulation step must be greater than zero."
            );
        }

        Map<Position, List<Car>> carsByPosition =
                groupCarsByPosition(cars);

        for (List<Car> carsAtPosition
                : carsByPosition.values()) {

            /*
             * Two or more non-collided cars at the same
             * position means a collision.
             */
            if (carsAtPosition.size() > 1) {
                markCollided(
                        carsAtPosition,
                        step
                );
            }
        }
    }

    private Map<Position, List<Car>> groupCarsByPosition(
            List<Car> cars
    ) {

        Map<Position, List<Car>> carsByPosition =
                new HashMap<>();

        for (Car car : cars) {

            /*
             * Once a car has collided, it should not
             * participate in future collision detection.
             */
            if (car.getStatus() == Car.Status.COLLIDED) {
                continue;
            }

            carsByPosition
                    .computeIfAbsent(
                            car.getPosition(),
                            key -> new ArrayList<>()
                    )
                    .add(car);
        }

        return carsByPosition;
    }

    private void markCollided(
            List<Car> carsAtPosition,
            int step
    ) {

        for (Car car : carsAtPosition) {
            car.markCollided(step);
        }
    }
}