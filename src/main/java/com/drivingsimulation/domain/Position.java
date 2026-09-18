package com.drivingsimulation.domain;

import java.util.Objects;

public final class Position {

    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position move(int deltaX, int deltaY) {
        return new Position(
                x + deltaX,
                y + deltaY
        );
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (!(object instanceof Position other)) {
            return false;
        }

        return x == other.x
                && y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}