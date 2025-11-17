package com.example.javaautocad.AutoCad.domain;

public class Circle {
    private final Point point;
    private final double radius;

    public Circle(Point point, double radius) {
        this.point = point;
        this.radius = radius;
    }

    public double circumference() {
        return 2 * Math.PI * radius;
    }

    public double curvature() {
        return 1.0 / radius;
    }

    public Point getPoint() {
        return point;
    }

    public double getRadius() {
        return radius;
    }
}
