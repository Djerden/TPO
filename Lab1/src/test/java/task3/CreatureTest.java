package task3;

import com.djeno.task3.Creatures;
import com.djeno.task3.enums.Race;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CreatureTest {
    @ParameterizedTest
    @MethodSource("aggressionChangeProvider")
    void changeAggressionLevel_shouldCorrectlyModifyLevel(
            int initialLevel,
            int change,
            int expectedLevel) {
        Creatures creature = new Creatures(Race.HUMAN, initialLevel);

        creature.changeAggressionLevel(change);

        assertEquals(expectedLevel, creature.getAggressionLevel());
    }

    private static Stream<Arguments> aggressionChangeProvider() {
        return Stream.of(
                Arguments.of(0, 50, 50),
                Arguments.of(50, 60, 100),
                Arguments.of(90, 20, 100),

                Arguments.of(50, -30, 20),
                Arguments.of(30, -40, 0),
                Arguments.of(10, -10, 0),

                Arguments.of(0, 0, 0),
                Arguments.of(100, -100, 0),
                Arguments.of(50, -50, 0),
                Arguments.of(0, 1000, 100),
                Arguments.of(100, -1000, 0)
        );
    }
}
