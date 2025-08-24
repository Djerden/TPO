import functions.*;
import lombok.NonNull;
import utils.CsvWriter;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        String systemOutputFile = "system_of_functions.csv";
        String cotOutputFile = "cot_output.csv";
        String log5OutputFile = "log5_output.csv";

        double start = -2;
        double end = 3;
        double step = 0.5;
        double precision = 0.001;

        Sin sin = new Sin();
        Cos cos = new Cos(sin);
        Cot cot = new Cot(sin, cos);
        Csc csc = new Csc(sin);
        Sec sec = new Sec(cos);
        Ln ln = new Ln();
        LogN log2 = new LogN(ln, 2);
        LogN log3 = new LogN(ln, 3);
        LogN log5 = new LogN(ln, 5);

        SystemOfFunctions systemOfFunctions = new SystemOfFunctions(sin, cos, cot, csc, sec, ln, log2, log3, log5);

        System.out.println("Generating CSV for the entire function system...");
        try {
            CsvWriter.writeResultsToCsv(
                    systemOutputFile,
                    systemOfFunctions,
                    start,
                    end,
                    step,
                    precision
            );
            System.out.println("Successfully wrote system results to " + systemOutputFile);
        } catch (IOException e) {
            System.err.println("Failed to write system results CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Configuration error for system CSV writing: " + e.getMessage());
        }

        System.out.println("Generating CSV for cot function...");
        try {
            CsvWriter.writeResultsToCsv(
                    cotOutputFile,
                    cot,
                    start,
                    end,
                    step,
                    precision
            );
            System.out.println("Successfully wrote cot results to " + cotOutputFile);
        } catch (IOException e) {
            System.err.println("Failed to write cot results CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Configuration error for cot CSV writing: " + e.getMessage());
        }

        System.out.println("Generating CSV for log5 function...");
        try {
            CsvWriter.writeResultsToCsv(
                    log5OutputFile,
                    log5,
                    start,
                    end,
                    step,
                    precision
            );
            System.out.println("Successfully wrote log5 results to " + log5OutputFile);
        } catch (IOException e) {
            System.err.println("Failed to write log5 results CSV: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Configuration error for log5 CSV writing: " + e.getMessage());
        }
    }
}
