package utils;

import functions.SystemOfFunctions;
import interfaces.Calculable;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;

/**
 * Класс, позволяющий выводить результаты любого модуля, реализующего интерфейс Callable
 */
public class CsvWriter {

    private static final String separator = ";";

    public static void writeResultsToCsv(
            String nameOfFile,
            Calculable module,
            double startOfInterval,
            double endOfInterval,
            double step,
            double precision
    ) throws IOException {
        if (step == 0) {
            throw new IllegalArgumentException("Step cannot be zero");
        }
        if (startOfInterval < endOfInterval && step < 0) {
            throw new IllegalArgumentException("Step must be positive if startX < endX.");
        }
        if (startOfInterval > endOfInterval && step > 0) {
            throw new IllegalArgumentException("Step must be negative if startX > endX.");
        }
        if (precision <= 0 || Double.isNaN(precision) || Double.isInfinite(precision)) {
            throw new IllegalArgumentException("Precision must be positive and finite.");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nameOfFile))) {
            writer.write("X" + separator + "Result");
            writer.newLine();

            double currentX = startOfInterval;

            if (step > 0) {
                while (currentX <= endOfInterval) {
                    writeLine(writer, module, currentX, precision);
                    currentX += step;

                    if (Math.abs(currentX - (currentX - step)) < Math.abs(step) / 1000.0) {
                        System.err.printf("Warning: Step %f might be too small relative to X %f, potential infinite loop avoided.%n", step, currentX);
                        break;
                    }
                }
                if (currentX - step < endOfInterval && startOfInterval <= endOfInterval) {
                    writeLine(writer, module, endOfInterval, precision);
                }
            } else {
                while (currentX >= endOfInterval) {
                    writeLine(writer, module, currentX, precision);
                    currentX += step;

                    if (Math.abs(currentX - (currentX - step)) < Math.abs(step) / 1000.0) {
                        System.err.printf("Warning: Step %f might be too small relative to X %f, potential infinite loop avoided.%n", step, currentX);
                        break;
                    }
                }
                if (currentX - step > endOfInterval && startOfInterval >= endOfInterval) {
                    writeLine(writer, module, endOfInterval, precision);
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to CSV file '" + nameOfFile + "': " + e.getMessage());
            throw e;
        }
    }

    private static void writeLine(
            BufferedWriter writer,
            Calculable function,
            double x,
            double precision
    ) throws IOException {
        double result = function.calculate(x, precision);
        String resultString;

        if (Double.isNaN(result)) {
            resultString = "NaN";
        } else if (Double.isInfinite(result)) {
            resultString = (result > 0) ? "Infinity" : "-Infinity";
        } else {
            resultString = String.format(Locale.US, "%.10f", result);
        }
        String xString = String.format(Locale.US, "%.10f", x);

        writer.write(xString + separator + resultString);
        writer.newLine();
    }
}
