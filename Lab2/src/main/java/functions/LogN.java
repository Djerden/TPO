package functions;


import interfaces.Calculable;

/**
 * Класс для вычисления логарифма с основанием N
 */
public class LogN implements Calculable {

    private Ln ln;
    private double base;

    public LogN(Ln ln, double base) {
        this.ln = ln;
        this.base = base;
    }

    @Override
    public double calculate(double x, double precision) {
        if (x <= 0 || Double.isNaN(x) || Double.isInfinite(x)) {
            throw new IllegalArgumentException("x must be positive");
        }
        if (base < 0 || base == 1) {
            throw new IllegalArgumentException("Base must be greater than 0 and not equal to 1");
        }

        //log_a(x) = ln(x)/ln(a)
        double lnX = ln.calculate(x, precision);
        double lnBase = ln.calculate(base, precision);

        return lnX / lnBase;
    }
}
