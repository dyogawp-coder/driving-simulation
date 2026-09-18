package com.drivingsimulation.domain;

public final class Field {

    private final int width;
    private final int height;

    public Field(int width, int height) {

        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException(
                    "Field width and height must be greater than zero."
            );
        }

        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean contains(Position position) {

        if (position == null) {
            return false;
        }

        return position.getX() >= 0
                && position.getX() < width
                && position.getY() >= 0
                && position.getY() < height;
    }
}