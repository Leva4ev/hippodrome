import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {

    @Test
    void nullNameException () {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
        assertEquals("Name cannot be null.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   ", "\t", "\n"})
    void blankNameException(String name) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());

    }

    @ParameterizedTest
    @ValueSource(doubles = { -1.0, -0.001, -100.0, -Double.MIN_VALUE, -Double.MAX_VALUE })
    void notNegativeSpeedException(Double d) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", d, 1));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @ParameterizedTest
    @ValueSource(doubles = { -1.0, -0.001, -100.0, -Double.MIN_VALUE, -Double.MAX_VALUE })
    void notNegativeDistanceException(Double d) {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("horse", 1, d));
        assertEquals("Distance cannot be negative.", e.getMessage());
    }

    @Test
    void getName() {
        Horse horse = new Horse("Unicorn", 1, 2);
        assertEquals("Unicorn", horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("Unicorn", 1, 2);
        assertEquals(1, horse.getSpeed());
    }

    @Test
    void getDistance() {
        Horse horse = new Horse("Unicorn", 1, 30);
        assertEquals(30, horse.getDistance());
    }

    @Test
    void getDefaultDistance() {
        Horse horse = new Horse("Unicorn", 1);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void moveUseGetRandom() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {

            Horse horse = new Horse("Unicorn", 1);
            horse.move();

            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.2, 0.5, 0.9, 1.0, 0.0, 999.99})
    void move(double random) {
        try (MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)) {
            Horse horse = new Horse("Unicorn", 20, 250);
            mockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(random);

            horse.move();

            assertEquals(250 + 20 * random, horse.getDistance());
        }
    }
}

