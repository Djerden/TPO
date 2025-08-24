package functions;

import interfaces.Calculable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * Класс для вычисления косеканса
 */
@AllArgsConstructor
public class Csc implements Calculable {

    @NonNull
    private Sin sin;

    @Override
    public double calculate(double x, double precision) {

        // csc(x) = 1 / sin(x)
        double sinValue = sin.calculate(x, precision);

        if (Double.isNaN(sinValue)) {
            return Double.NaN;
        }

        if (Math.abs(sinValue) < precision) {
            return Double.NaN;
        }

        return 1.0 / sinValue;
    }
}
