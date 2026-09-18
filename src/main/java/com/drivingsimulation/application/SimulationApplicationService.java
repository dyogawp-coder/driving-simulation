package com.drivingsimulation.application;

import com.drivingsimulation.domain.Car;
import com.drivingsimulation.domain.Direction;
import com.drivingsimulation.domain.Field;
import com.drivingsimulation.domain.Position;
import com.drivingsimulation.simulation.SimulationEngine;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class SimulationApplicationService {

    private final Field field;
    private final SimulationEngine simulationEngine;

    /*
     * LinkedHashMap gives us:
     *
     * 1. Fast lookup by car name.
     * 2. Unique car names.
     * 3. Insertion order for CLI output.
     */
    private final Map<String, Car> cars;

    public SimulationApplicationService(
            Field field,
            SimulationEngine simulationEngine
    ) {

        if (field == null) {
            throw new IllegalArgumentException(
                    "Field must not be null."
            );
        }

        if (simulationEngine == null) {
            throw new IllegalArgumentException(
                    "Simulation engine must not be null."
            );
        }

        this.field = field;
        this.simulationEngine = simulationEngine;
        this.cars = new LinkedHashMap<>();
    }

    public void addCar(
            String name,
            Position position,
            Direction direction,
            String commands
    ) {

        if (cars.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Car name already exists: " + name
            );
        }

        if (!field.contains(position)) {
            throw new IllegalArgumentException(
                    "Car position is outside the field: "
                            + position
            );
        }

        if (isPositionOccupied(position)) {
            throw new IllegalArgumentException(
                    "Car position is already occupied: "
                            + position
            );
        }

        Car car =
                new Car(
                        name,
                        position,
                        direction,
                        commands
                );

        cars.put(name, car);
    }

    public void runSimulation() {

        simulationEngine.run(
                field,
                cars.values().stream().toList()
        );
    }

    public Collection<Car> getCars() {

        return Collections.unmodifiableCollection(
                cars.values()
        );
    }

    private boolean isPositionOccupied(
            Position position
    ) {

        return cars.values()
                .stream()
                .anyMatch(
                        car -> car.getPosition()
                                .equals(position)
                );
    }
}