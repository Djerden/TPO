package com.djeno.task1;

public class ArccosSeries {
    public static double arccos(double x, int terms) {
        if (x < -1 || x > 1) {
            throw new IllegalArgumentException("x must be in the range [-1, 1]");
        }

        if (x == 1) {
            return 0;
        }

        if (x == -1) {
            return Math.PI;
        }

        double sum = 0.0;
        double term = 1.0;
        double xSquared = x * x;

        for (int n = 0; n < terms; n++) {
            if (n > 0) {
                term *= (2 * n - 1) * xSquared / (2 * n);
            }
            sum += term / (2 * n + 1);
        }

        return (Math.PI / 2) - (x * sum);
    }

    public static double arccos(double x) {
        return arccos(x, 50);
    }
}