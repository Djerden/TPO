package task3;

import com.djeno.task3.Galaxy;
import com.djeno.task3.Phrase;
import com.djeno.task3.SpaceTimeHole;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceTimeHoleTest {
//    @Test
//    void testSpaceTimeHoleTransport() {
//        SpaceTimeHole hole = new SpaceTimeHole(true, 100);
//        Galaxy galaxy = new Galaxy("Далекая галактика", "Где-то далеко во времени");
//        Phrase phrase = new Phrase("А у меня, кажется, большие проблемы с образом жизни");
//
//        // Проверяем, что метод transport выполняется без ошибок
//        assertDoesNotThrow(() -> hole.transport(phrase, galaxy));
//    }
//
//    @ParameterizedTest
//    @CsvSource({
//            "50, 20, true",  // Фраза меньше размера дыры
//            "50, 60, false", // Фраза больше размера дыры
//            "100, 100, true"  // Фраза равна размеру дыры
//    })
//    void testCanTransport(int holeSize, int phraseLength, boolean expected) {
//        SpaceTimeHole hole = new SpaceTimeHole(true, holeSize);
//        Phrase phrase = new Phrase("A".repeat(phraseLength)); // Создаем фразу заданной длины
//        assertEquals(expected, hole.canTransport(phrase));
//    }
}
