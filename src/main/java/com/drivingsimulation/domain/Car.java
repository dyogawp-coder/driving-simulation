package com.drivingsimulation.domain;

public final class Car {

    public enum Status {
        ACTIVE,
        FINISHED,
        COLLIDED
    }

    private final String name;
    private Position position;
    private Direction direction;
    private final String commands;

    private int nextCommandIndex;
    private Status status;
    private Integer collisionStep;

    public Car(
            String name,
            Position position,
            Direction direction,
            String commands
    ) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Car name must not be blank."
            );
        }

        if (position == null) {
            throw new IllegalArgumentException(
                    "Car position must not be null."
            );
        }

        if (direction == null) {
            throw new IllegalArgumentException(
                    "Car direction must not be null."
            );
        }

        if (commands == null) {
            throw new IllegalArgumentException(
                    "Car commands must not be null."
            );
        }

        validateCommands(commands);

        this.name = name;
        this.position = position;
        this.direction = direction;
        this.commands = commands;
        this.nextCommandIndex = 0;

        this.status = commands.isEmpty()
                ? Status.FINISHED
                : Status.ACTIVE;

        this.collisionStep = null;
    }

    private void validateCommands(String commands) {

        for (char command : commands.toCharArray()) {

            if (command != 'L'
                    && command != 'R'
                    && command != 'F') {

                throw new IllegalArgumentException(
                        "Invalid command: " + command
                );
            }
        }
    }

    public String getName() {
        return name;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getCommands() {
        return commands;
    }

    public Status getStatus() {
        return status;
    }

    public Integer getCollisionStep() {
        return collisionStep;
    }

    public boolean hasNextCommand() {

        return status == Status.ACTIVE
                && nextCommandIndex < commands.length();
    }

    public void executeNextCommand(Field field) {

        if (field == null) {
            throw new IllegalArgumentException(
                    "Field must not be null."
            );
        }

        if (!hasNextCommand()) {
            return;
        }

        char command =
                commands.charAt(nextCommandIndex);

        nextCommandIndex++;

        switch (command) {

            case 'L' -> turnLeft();

            case 'R' -> turnRight();

            case 'F' -> moveForward(field);

            default -> throw new IllegalStateException(
                    "Unexpected command: " + command
            );
        }

        if (nextCommandIndex == commands.length()) {
            status = Status.FINISHED;
        }
    }

    private void turnLeft() {
        direction = direction.turnLeft();
    }

    private void turnRight() {
        direction = direction.turnRight();
    }

    private void moveForward(Field field) {

        Position newPosition =
                position.move(
                        direction.deltaX(),
                        direction.deltaY()
                );

        /*
         * Moving beyond the boundary is ignored.
         * The car remains in its current position.
         */
        if (field.contains(newPosition)) {
            position = newPosition;
        }
    }

    public void markCollided(int step) {

        if (step <= 0) {
            throw new IllegalArgumentException(
                    "Collision step must be greater than zero."
            );
        }

        status = Status.COLLIDED;
        collisionStep = step;
    }
}