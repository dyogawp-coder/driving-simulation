package com.drivingsimulation;

import com.drivingsimulation.domain.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void shouldCreatePositionWithGivenCoordinates() {
        Position position = new Position(5, 4);

        assertEquals(5, position.getX());
        assertEquals(4, position.getY());
    }

    @Test
    void shouldMoveToNewPositionWithoutChangingOriginalPosition() {
        Position original = new Position(5, 4);

        Position moved = original.move(1, -2);

        assertEquals(new Position(6, 2), moved);

        // Position must be immutable
        assertEquals(new Position(5, 4), original);
    }

    @Test
    void shouldConsiderPositionsWithSameCoordinatesEqual() {
        Position first = new Position(5, 4);
        Position second = new Position(5, 4);

        assertEquals(first, second);
    }

    @Test
    void shouldConsiderPositionsWithDifferentCoordinatesNotEqual() {
        Position first = new Position(5, 4);
        Position second = new Position(5, 5);

        assertNotEquals(first, second);
    }

    @Test
    void shouldHaveSameHashCodeForEqualPositions() {
        Position first = new Position(5, 4);
        Position second = new Position(5, 4);

        assertEquals(first.hashCode(), second.hashCode());
    }

    @Test
    void shouldReturnCoordinatesInExpectedStringFormat() {
        Position position = new Position(5, 4);

        assertEquals("(5,4)", position.toString());
    }
}
