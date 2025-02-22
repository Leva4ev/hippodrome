import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HippodromeTest {

    @Test
    void nullArgumentException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    void emptyListException() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(new ArrayList<>()));
        assertEquals("Horses cannot be empty.", e.getMessage());
    }

    @Test
    void getHorses() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            horses.add(new Horse("" + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(horses);
        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    void move() {
        List<Horse> horses = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse : horses) {
            Mockito.verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = new Horse("first", 1, 4.1);
        Horse horse2 = new Horse("second", 1, 3);
        Horse horse3 = new Horse("third", 1, 4);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3));

        assertSame(horse1, hippodrome.getWinner());
    }
}