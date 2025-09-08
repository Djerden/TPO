package functions;

import interfaces.Calculable;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Класс для вычисления косинуса
 */
@AllArgsConstructor
public class Cos implements Calculable {

    @NonNull
    private Sin sin;

    @Override
    public double calculate(double x, double precision) {

        // Через тригонометрическое тождество
        double sinX = sin.calculate(x, precision);
        double cosAbs = Math.sqrt(Math.max(0.0, 1.0 - sinX * sinX));

        double mod = x % (2 * Math.PI);
        if (mod < 0) {
            mod += 2 * Math.PI;
        }

        if (mod > Math.PI / 2 && mod < 3 * Math.PI / 2) {
            return -cosAbs;
        } else {
            return cosAbs;
        }
    }
}
