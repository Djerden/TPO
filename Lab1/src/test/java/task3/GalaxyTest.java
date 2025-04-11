package task3;

import com.djeno.task3.Creatures;
import com.djeno.task3.Galaxy;
import com.djeno.task3.Phrase;
import com.djeno.task3.enums.EmotionalState;
import com.djeno.task3.enums.Race;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GalaxyTest {

    private Galaxy galaxy;
    private Creatures human;
    private Creatures klingon;

    @BeforeEach
    void setUp() {
        galaxy = new Galaxy("Milky Way", "Spiral", new ArrayList<>(), false);
        human = new Creatures(Race.HUMAN, 30);
        klingon = new Creatures(Race.KLINGON, 60);
        galaxy.addCreature(human);
        galaxy.addCreature(klingon);
    }

    private static Stream<Arguments> emotionEffectProvider() {
        return Stream.of(
                Arguments.of(EmotionalState.HAPPY, 0, 10, false),
                Arguments.of(EmotionalState.SAD, 40, 70, false),
                Arguments.of(EmotionalState.PANIC, 60, 90, true),
                Arguments.of(EmotionalState.ANGRY, 80, 100, true),
                Arguments.of(EmotionalState.CALM, 30, 60, false)
        );
    }

    @ParameterizedTest
    @MethodSource("emotionEffectProvider")
    void getPhraseFromSpaceTimeHole_shouldChangeAggressionCorrectly(
            EmotionalState emotion,
            int expectedHumanAggression,
            int expectedKlingonAggression,
            boolean expectedWarStatus) {

        galaxy.getPhraseFromSpaceTimeHole(new Phrase("Test phrase", emotion));

        assertAll(
                () -> assertEquals(expectedHumanAggression, human.getAggressionLevel(),
                        "Неправильный уровень агрессии у человека"),
                () -> assertEquals(expectedKlingonAggression, klingon.getAggressionLevel(),
                        "Неправильный уровень агрессии у клингона"),
                () -> assertEquals(expectedWarStatus, galaxy.isWar(),
                        "Неверный статус войны")
        );
    }

    private static Stream<Arguments> warStatusProvider() {
        return Stream.of(
                Arguments.of(30, 60, false, false),
                Arguments.of(80, 90, false, true),
                Arguments.of(30, 60, true, false),
                Arguments.of(80, 90, true, true)
        );
    }

    @ParameterizedTest
    @MethodSource("warStatusProvider")
    void checkWarStatus_shouldChangeWarStatusCorrectly(
            int humanAggression,
            int klingonAggression,
            boolean initialWarStatus,
            boolean expectedWarStatus) {

        human.setAggressionLevel(humanAggression);
        klingon.setAggressionLevel(klingonAggression);
        galaxy.setWar(initialWarStatus);

        galaxy.getPhraseFromSpaceTimeHole(new Phrase("Test", EmotionalState.CALM));

        assertEquals(expectedWarStatus, galaxy.isWar());
    }

    @Test
    void getPhraseFromSpaceTimeHole_withEmptyGalaxy_shouldNotFail() {
        Galaxy emptyGalaxy = new Galaxy("Empty", "Void", new ArrayList<>(), false);
        assertDoesNotThrow(() ->
                emptyGalaxy.getPhraseFromSpaceTimeHole(new Phrase("Test", EmotionalState.ANGRY)));
    }

}
