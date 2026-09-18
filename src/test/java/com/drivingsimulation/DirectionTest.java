package com.drivingsimulation;

import com.drivingsimulation.domain.Direction;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionTest {

        @Test
        void shouldReturnCorrectMovementForEachDirection() {
            assertEquals(0, Direction.NORTH.deltaX());
            assertEquals(1, Direction.NORTH.deltaY());

            assertEquals(1, Direction.EAST.deltaX());
            assertEquals(0, Direction.EAST.deltaY());

            assertEquals(0, Direction.SOUTH.deltaX());
            assertEquals(-1, Direction.SOUTH.deltaY());

            assertEquals(-1, Direction.WEST.deltaX());
            assertEquals(0, Direction.WEST.deltaY());
        }

        @Test
        void shouldTurnLeftCorrectly() {
            assertEquals(Direction.WEST, Direction.NORTH.turnLeft());
            assertEquals(Direction.SOUTH, Direction.WEST.turnLeft());
            assertEquals(Direction.EAST, Direction.SOUTH.turnLeft());
            assertEquals(Direction.NORTH, Direction.EAST.turnLeft());
        }

        @Test
        void shouldTurnRightCorrectly() {
            assertEquals(Direction.EAST, Direction.NORTH.turnRight());
            assertEquals(Direction.SOUTH, Direction.EAST.turnRight());
            assertEquals(Direction.WEST, Direction.SOUTH.turnRight());
            assertEquals(Direction.NORTH, Direction.WEST.turnRight());
        }
    }

