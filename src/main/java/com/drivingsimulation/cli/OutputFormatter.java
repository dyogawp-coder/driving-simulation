package com.drivingsimulation.cli;

import com.drivingsimulation.domain.Car;

import java.util.Collection;

public final class OutputFormatter {

    public String welcomeMessage() {
        return "Welcome to Car Crash Java!";
    }

    public String invalidInputMessage(
            String message
    ) {
        return "Invalid input: " + message;
    }

    public String formatCar(Car car) {

        return "- "
                + car.getName()
                + ", "
                + car.getPosition()
                + " "
                + formatDirection(car)
                + ", "
                + car.getCommands();
    }

    public String formatCars(
            Collection<Car> cars
    ) {

        StringBuilder output =
                new StringBuilder();

        for (Car car : cars) {

            if (!output.isEmpty()) {
                output.append(
                        System.lineSeparator()
                );
            }

            output.append(
                    formatCar(car)
            );
        }

        return output.toString();
    }

    public String formatSimulationResult(
            Collection<Car> cars
    ) {

        StringBuilder output =
                new StringBuilder();

        for (Car car : cars) {

            if (!output.isEmpty()) {
                output.append(
                        System.lineSeparator()
                );
            }

            output.append(
                    formatSimulationCar(
                            car,
                            cars
                    )
            );
        }

        return output.toString();
    }

    private String formatSimulationCar(
            Car car,
            Collection<Car> cars
    ) {

        if (car.getStatus()
                != Car.Status.COLLIDED) {

            return "- "
                    + car.getName()
                    + ", "
                    + car.getPosition()
                    + " "
                    + formatDirection(car);
        }

        String collisionWith =
                findCollisionPartner(
                        car,
                        cars
                );

        return "- "
                + car.getName()
                + ", collides with "
                + collisionWith
                + " at "
                + car.getPosition()
                + " at step "
                + car.getCollisionStep();
    }

    private String findCollisionPartner(
            Car car,
            Collection<Car> cars
    ) {

        for (Car other : cars) {

            if (other == car) {
                continue;
            }

            if (other.getStatus()
                    == Car.Status.COLLIDED

                    && other.getCollisionStep()
                    .equals(car.getCollisionStep())

                    && other.getPosition()
                    .equals(car.getPosition())) {

                return other.getName();
            }
        }

        return "another car";
    }

    private String formatDirection(Car car) {

        return switch (car.getDirection()) {

            case NORTH -> "N";

            case EAST -> "E";

            case SOUTH -> "S";

            case WEST -> "W";
        };
    }
}