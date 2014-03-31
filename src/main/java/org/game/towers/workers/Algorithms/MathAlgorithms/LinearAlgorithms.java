package org.game.towers.workers.Algorithms.MathAlgorithms;

public class LinearAlgorithms {

    public static double dependentYFromX(double x, double x1, double x2, double y1, double y2) {
        double aFactor = (x - x1) / (x2 - x1);
        double y = (int)(y1 + ( aFactor * (y2 - y1) ));
        return y;
    }

    public static double dependentXFromY(double y, double x1, double x2, double y1, double y2) {
        double aFactor = (x2 - x1) / (y2 - y1);
        double x = (int)(aFactor * (y - y2) - x1);
        return x;
    }

    public static double direction(double x1, double x2, double y1, double y2) {
        if (x2 == x1) {
            return 90;
        }

        return Math.toDegrees(angleByCoordinate(x1,x2,y1,y2));
    }

    public static double angleByCoordinate(double x1, double x2, double y1, double y2) {

        if (x2 == x1) {
            return Math.PI/2;
        }

        double quotient = (y2 - y1) / (x2 - x1);
        double grade = Math.atan(quotient);

        if (x2 - x1 < 0) {
            grade+=Math.PI;
        }
        return grade;
    }

    public static double radiusVector(double x1, double x2, double y1, double y2){
        double radiusVector = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return radiusVector;
    }

    public static double[] shiftingByPolarSystem(double radius, double angle) {
        double[] coordinates = new double[2];
        double x = radius * Math.cos(angle);
        double y = radius * Math.sin(angle);


        coordinates[0] = x;
        coordinates[1] = y;
        return coordinates;
    }
}
