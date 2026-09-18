package com.drivingsimulation;
import com.drivingsimulation.domain.Field;
import com.drivingsimulation.domain.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {


        @Test
        void shouldCreateFieldWithGivenDimensions() {
            Field field = new Field(10, 10);

            assertEquals(10, field.getWidth());
            assertEquals(10, field.getHeight());
        }

        @Test
        void shouldAcceptPositionsInsideField() {
            Field field = new Field(10, 10);

            assertTrue(field.contains(new Position(0, 0)));
            assertTrue(field.contains(new Position(5, 5)));
            assertTrue(field.contains(new Position(9, 9)));
        }

        @Test
        void shouldRejectPositionsOutsideField() {
            Field field = new Field(10, 10);

            assertFalse(field.contains(new Position(-1, 5)));
            assertFalse(field.contains(new Position(10, 5)));
            assertFalse(field.contains(new Position(5, -1)));
            assertFalse(field.contains(new Position(5, 10)));
        }

        @Test
        void shouldRejectNullPosition() {
            Field field = new Field(10, 10);

            assertFalse(field.contains(null));
        }

        @Test
        void shouldRejectZeroWidth() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Field(0, 10)
            );
        }

        @Test
        void shouldRejectZeroHeight() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Field(10, 0)
            );
        }

        @Test
        void shouldRejectNegativeDimensions() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Field(-1, 10)
            );

            assertThrows(
                    IllegalArgumentException.class,
                    () -> new Field(10, -1)
            );
        }

        @Test
        void shouldAllowOneByOneField() {
            Field field = new Field(1, 1);

            assertTrue(field.contains(new Position(0, 0)));

            assertFalse(field.contains(new Position(1, 0)));
            assertFalse(field.contains(new Position(0, 1)));
        }
    }

