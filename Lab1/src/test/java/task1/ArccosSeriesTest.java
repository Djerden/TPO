package task1;

import com.djeno.task1.ArccosSeries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArccosSeriesTest {

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of(0.0, Math.PI / 2, 50),
                Arguments.of(1.0, 0.0, 50),
                Arguments.of(-1.0, Math.PI, 50),
                Arguments.of(0.5, Math.PI / 3, 50),
                Arguments.of(-0.5, 2 * Math.PI / 3, 50),
                Arguments.of(Math.sqrt(2) / 2, Math.PI / 4, 50),
                Arguments.of(-Math.sqrt(2) / 2, 3 * Math.PI / 4, 50),
                Arguments.of(Math.sqrt(3) / 2, Math.PI / 6, 50),
                Arguments.of(-Math.sqrt(3) / 2, 5 * Math.PI / 6, 50)
        );
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void testArccos(double x, double expected, int terms) {
        double result = ArccosSeries.arccos(x, terms);
        assertEquals(expected, result, 1e-6);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-3.0, -2.0, 2.0, 1.1, -1.1, 2.0, 3.0})
    void testArccosInvalidInput(double x) {
        assertThrows(IllegalArgumentException.class, () -> {
            ArccosSeries.arccos(x, 50);
        });
    }
}