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

        // cos(x) = sin(pi/2 - x)
        return sin.calculate(Math.PI / 2.0 - x, precision);
    }
}
