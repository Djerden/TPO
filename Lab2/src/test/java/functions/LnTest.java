package functions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LnTest {
    private final Ln ln = new Ln();
    private final double precision = 1e-9;

    @ParameterizedTest(name = "ln({0}) ≈ {1}")
    @CsvSource({
            "1.0, 0.0",
            "2.718281828459045, 1.0",   // e
            "7.38905609893065, 2.0",    // e^2
            "0.5, -0.6931471805599453",
            "2.0, 0.6931471805599453",
            "10.0, 2.302585092994046",
            "0.1, -2.3025850929940455"
    })
    @DisplayName("Проверка правильных значений ln(x)")
    void testCorrectValues(double x, double expected) {
        double result = ln.calculate(x, precision);
        assertEquals(expected, result, 1e-8, "ln(" + x + ")");
    }

    @ParameterizedTest
    @ValueSource(doubles = { Double.NaN, -1.0, 0.0 })
    @DisplayName("ln(x) для NaN и x <= 0 → NaN")
    void testInvalidX(double x) {
        double result = ln.calculate(x, precision);
        assertTrue(Double.isNaN(result));
    }

    @ParameterizedTest
    @ValueSource(doubles = { Double.NaN, Double.POSITIVE_INFINITY, 0.0, -1e-6 })
    @DisplayName("ln(x) для некорректной precision → NaN")
    void testInvalidPrecision(double badPrecision) {
        double result = ln.calculate(2.0, badPrecision);
        assertTrue(Double.isNaN(result));
    }

    @ParameterizedTest
    @ValueSource(doubles = { 1e-6, 1e-9, 1e-12 })
    @DisplayName("ln(x) совпадает с Math.log(x) в пределах точности")
    void testAgainstMathLog(double prec) {
        double x = 5.0;
        double expected = Math.log(x);
        double result = ln.calculate(x, prec);
        assertEquals(expected, result, prec * 10, "ln(" + x + ")");
    }
}
