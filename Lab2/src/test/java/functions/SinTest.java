package functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinTest {
    private final Sin sin = new Sin();
    private final double precision = 1e-9;

    @ParameterizedTest(name = "sin({0}) ≈ {1}")
    @CsvSource({
            "0, 0.0",
            "1.5707963267948966, 1.0",    // π/2
            "3.141592653589793, 0.0",     // π
            "4.71238898038469, -1.0",     // 3π/2
            "6.283185307179586, 0.0",     // 2π
            "-1.5707963267948966, -1.0",  // -π/2
            "0.5235987755982988, 0.5",    // π/6
            "0.7853981633974483, 0.7071067811865476", // π/4
            "1.0471975511965976, 0.8660254037844386"  // π/3
    })
    @DisplayName("Проверка правильных значений sin(x)")
    void testCorrectValues(double x, double expected) {
        double result = sin.calculate(x, precision);
        assertEquals(expected, result, 1e-9, "sin(" + x + ")");
    }

    @ParameterizedTest(name = "sin({0}) ≈ sin(normalized)")
    @ValueSource(doubles = { 10 * Math.PI, -7 * Math.PI / 2, 12345.678 })
    @DisplayName("Проверка нормализации больших значений x")
    void testNormalization(double x) {
        double result = sin.calculate(x, precision);
        double expected = Math.sin(x);
        assertEquals(expected, result, 1e-9, "sin(" + x + ")");
    }

    @ParameterizedTest
    @ValueSource(doubles = { Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY })
    @DisplayName("sin(x) для NaN и бесконечностей → NaN")
    void testInvalidX(double x) {
        double result = sin.calculate(x, precision);
        assertTrue(Double.isNaN(result));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 0.0, -1.0, Double.NaN, Double.POSITIVE_INFINITY })
    @DisplayName("sin(x) для некорректной precision → NaN")
    void testInvalidPrecision(double badPrecision) {
        double result = sin.calculate(1.0, badPrecision);
        assertTrue(Double.isNaN(result));
    }
}
