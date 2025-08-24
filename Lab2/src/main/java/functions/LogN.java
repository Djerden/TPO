package functions;


import interfaces.Calculable;

/**
 * Класс для вычисления логарифма с основанием N
 */
public class LogN implements Calculable {

    private Ln ln;
    private double base;
    private Double lnBase = null;

    public LogN(Ln ln, double base) {
        if (ln == null) {
            throw new NullPointerException("Ln function cannot be null");
        }
        if (base <= 0 || base == 1.0) {
            throw new IllegalArgumentException("Log base must be > 0 and != 1. Got: " + base);
        }
        this.ln = ln;
        this.base = base;
    }

    @Override
    public double calculate(double x, double precision) {
        if (Double.isNaN(x) || x <= 0) {
            return Double.NaN;
        }

        double lnX = ln.calculate(x, precision);
        double lnBaseValue = getLnBase(precision);

        if (Double.isNaN(lnX) || Double.isNaN(lnBaseValue)) {
            return Double.NaN;
        }

        if (Math.abs(lnBaseValue) < precision) {
            System.err.println("Warning: Denominator ln(base) is close to zero.");
            return Double.NaN;
        }

        return lnX / lnBaseValue;
    }

    private double getLnBase(double precision) {
        if (this.lnBase == null) {
            this.lnBase = ln.calculate(this.base, precision);
            if (Double.isNaN(this.lnBase) || Math.abs(this.lnBase) < precision) {
                System.err.println("Error: Could not calculate ln(base) or it's too close to zero for base=" + base +
                                " with precision=" + precision);
                this.lnBase = Double.NaN;
            }
        }
        return this.lnBase;
    }
}
