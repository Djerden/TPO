package functions;

import interfaces.Calculable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * Класс для вычисления секанса
 */
@AllArgsConstructor
public class Sec implements Calculable {

    @NonNull
    private Cos cos;

    @Override
    public double calculate(double x, double precision) {
        // sec(x) = 1 / cos(x)
        double cosValue = cos.calculate(x, precision);

        if (Double.isNaN(cosValue)) {
            return Double.NaN;
        }

        if (Math.abs(cosValue) < precision) {
            return Double.NaN;
        }

        return 1.0 / cosValue;
    }
}
