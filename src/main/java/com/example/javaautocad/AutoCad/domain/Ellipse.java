package com.example.javaautocad.AutoCad.domain;

public class Ellipse {
    private final Point center;
    private final double majorRadius;
    private final double minorRadius;
    private final double rotation;

    public Ellipse(Point center, double majorRadius, double minorRadius, double rotation) {
        this.center = center;
        this.majorRadius = majorRadius;
        this.minorRadius = minorRadius;
        this.rotation = rotation;
    }

    public static Ellipse ellipse(Point center, double majorX, double majorY, double ratio) {
        double majorRadius = magnitude(majorX, majorY);
        double minorRadius = majorRadius * ratio;
        double rotation = Math.atan2(majorY, majorX);
        return new Ellipse(center, majorRadius, minorRadius, rotation);
    }

    private static double magnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    public double eccentricity() {
        if (majorRadius == 0) {
            return 0.0;
        }
        return Math.sqrt(1 - (minorRadius * minorRadius) / (majorRadius * majorRadius));
    }

    public double circumference() {
        double h = Math.pow(majorRadius - minorRadius, 2) / Math.pow(majorRadius + minorRadius, 2);
        return Math.PI * (majorRadius + minorRadius) * (1 + (3 * h) / (10 + Math.sqrt(4 - 3 * h)));
    }

    public double averageCurvature() {
        return 2.0 / (majorRadius + minorRadius);
    }

    public Point getCenter() {
        return center;
    }

    public double getMajorRadius() {
        return majorRadius;
    }

    public double getMinorRadius() {
        return minorRadius;
    }

    public double getRotation() {
        return rotation;
    }
}
