package functions;

import interfaces.Calculable;
import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public class SystemOfFunctions implements Calculable {

    @NonNull private Sin sin;
    @NonNull private Cos cos;
    @NonNull private Cot cot;
    @NonNull private Csc csc;
    @NonNull private Sec sec;
    @NonNull private Ln ln;
    @NonNull private LogN log2;
    @NonNull private LogN log3;
    @NonNull private LogN log5;

    @Override
    public double calculate(double x, double precision) {

        if (precision <= 0) {
            throw new IllegalArgumentException("Precision must be greater than zero");
        }

        if (x <= 0) {
            return trigonometricFunction(x, precision);
        } else {
            return logarithmicFunction(x, precision);
        }
    }

    private double trigonometricFunction(double x, double precision) {
        double secX = sec.calculate(x, precision);
        double cotX = cot.calculate(x, precision);
        double cosX = cos.calculate(x, precision);
        double sinX = sin.calculate(x, precision);
        double cscX = csc.calculate(x, precision);

        // (sec(x) - sec(x)) = 0
        double part1 = 0.0;

        // ((sec(x) - sec(x)) + cot(x)) = 0 + cotX
        double part2 = part1 + cotX;

        // (((sec(x) - sec(x)) + cot(x)) - cot(x)) = cotX - cotX = 0
        double part3 = part2 - cotX;

        // (cos(x) * sin(x)) / sin(x) = cosX (при sinX != 0)
        double part4 = (cosX * sinX) / sinX;

        // (cot(x) * sec(x)) ^ 2
        double cotSec = cotX * secX;
        double part5 = cotSec * cotSec;

        // (((cos(x) * sin(x)) / sin(x)) + ((cot(x) * sec(x)) ^ 2))
        double part6 = part4 + part5;

        // Левая часть: ((((sec(x) - sec(x)) + cot(x)) - cot(x)) - part6) = 0 - part6
        double leftPart = part3 - part6;

        // Правая часть: (csc(x) / cot(x)) * (csc(x) ^ 3)
        double cscDivCot = cscX / cotX;
        double cscCubed = cscX * cscX * cscX;
        double part7 = cscDivCot * cscCubed;

        // ((part7) ^ 3)
        double part8 = part7 * part7 * part7;

        // leftPart + part8
        return leftPart + part8;
    }

    private double logarithmicFunction(double x, double precision) {
        double lnX = ln.calculate(x, precision);
        double log2X = log2.calculate(x, precision);
        double log3X = log3.calculate(x, precision);
        double log5X = log5.calculate(x, precision);

        // Проверяем на особые случаи (деление на ноль, бесконечности)
        if (Double.isNaN(lnX) || Double.isNaN(log2X) || Double.isNaN(log3X) || Double.isNaN(log5X) ||
                Double.isInfinite(lnX) || Double.isInfinite(log2X) || Double.isInfinite(log3X) || Double.isInfinite(log5X)) {
            return Double.NaN;
        }

        // (ln(x) * log2(x))
        double product = lnX * log2X;

        // ((ln(x) * log₂(x)) ^ 2)
        double numerator = product * product; // возведение в квадрат

        // (log₃(x) / ln(x))
        double division = log3X / lnX;

        // ((log3(x) / ln(x)) - log5(x))
        double denominator = division - log5X;

        // Проверяем деление на ноль
        if (Math.abs(denominator) < precision) {
            return Double.NaN;
        }

        // (((ln(x) * log₂(x)) ^ 2) / ((log3(x) / ln(x)) - log5(x)))
        double fraction = numerator / denominator;

        // ((((ln(x) * log2(x)) ^ 2) / ((log3(x) / ln(x)) - log5(x))) ^ 3)
        double cubed = fraction * fraction * fraction;

        // (((((ln(x) * log2(x)) ^ 2) / ((log3(x) / ln(x)) - log5(x))) ^ 3) ^ 2)
        double result = cubed * cubed;

        return result;
    }
}
