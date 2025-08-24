package functions;

import interfaces.Calculable;

/**
 * Класс для вычисления натурального логарифма
 */
public class Ln implements Calculable {

    @Override
    public double calculate(double x, double precision) {
        if (
                Double.isNaN(x) ||
                x <= 0 ||
                precision <= 0 ||
                Double.isNaN(precision) ||
                Double.isInfinite(precision)
        ) {
            return Double.NaN;
        }
        if (x == 1.0) {
            return 0.0;
        }
        if (x == Double.POSITIVE_INFINITY) {
            return Double.POSITIVE_INFINITY;
        }

        // Используем ряд для ln((1+y)/(1-y)) = 2 * (y + y^3/3 + y^5/5 + ...)
        // где y = (x-1)/(x+1)
        double y = (x - 1.0) / (x + 1.0);
        double sum = 0.0;
        double term = y;
        double power = y;
        int n = 1;

        int iterations = 0;
        final int MAX_ITERATIONS = 10000;
        while (Math.abs(term / n) > precision / 2.0 && iterations < MAX_ITERATIONS) {
            sum += term / n;
            n += 2;
            power *= y * y;
            term = power;
            iterations++;
        }

        if (iterations == MAX_ITERATIONS && Math.abs(term / n) > precision / 2.0) {
            System.err.println("Warning: Ln calculation might be inaccurate due to max iterations reached for x=" + x);
        }

        double result = 2.0 * sum;

        return result;
    }
}
