package functions;

import interfaces.Calculable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

/**
 * Класс для вычисления котангенса
 */
@AllArgsConstructor
public class Cot implements Calculable {

    @NonNull
    private Sin sin;
    @NonNull
    private Cos cos;

    @Override
    public double calculate(double x, double precision) {

        // cot(x) = cos(x) / sin(x)
        double sinValue = sin.calculate(x, precision);
        double cosValue = cos.calculate(x, precision);

        if (Double.isNaN(sinValue) || Double.isNaN(cosValue)) {
            return Double.NaN;
        }

        return cosValue / sinValue;
    }
}
