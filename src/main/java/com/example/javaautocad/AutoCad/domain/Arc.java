package com.example.javaautocad.AutoCad.domain;

public class Arc {
    private final Point point;
    private final double radius;
    private final double startAngle;
    private final double endAngle;

    public Arc(Point point, double radius, double startAngle, double endAngle) {
        this.point = point;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
    }

    public double arcLength() {
        double angleRange = Math.abs(endAngle - startAngle);
        return radius * Math.toRadians(angleRange);
    }

    public double curvatuer() {
        return 1.0 / radius;
    }

    public double getRadius() {
        return radius;
    }

    public Point getPoint() {
        return point;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }
}
