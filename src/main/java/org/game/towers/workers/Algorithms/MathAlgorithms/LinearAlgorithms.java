package org.game.towers.workers.Algorithms.MathAlgorithms;

public class LinearAlgorithms {

    public static double dependentYFromX(double x, double x1, double x2, double y1, double y2) {
        double aFactor = (x - x1) / (x2 - x1);
        double y = (int)(y2 + ( aFactor * (y2 - y1) ));
        return y;
    }

    public static double dependentXFromY(double y, double x1, double x2, double y1, double y2) {
        double aFactor = (x2 - x1) / (y2 - y1);
        double x = (int)(aFactor * (y - y2) + x1);
        return x;
    }

    public static double direction(double x1, double x2, double y1, double y2) {
        if (x2 == x1) {
            return 90;
        }
        double quotient = (y1 - y2) / (x2 - x1);
        double grade = Math.atan(quotient);
        return Math.toDegrees(grade);
    }
}
