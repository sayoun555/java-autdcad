package com.example.javaautocad.AutoCad.domain;

public class Circle {
    private final Point point;
    private final double radius;
    private final String layer;

    public Circle(Point point, double radius, String layer) {
        this.point = point;
        this.radius = radius;
        this.layer = layer;
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

    public String getLayer() {
        return layer;
    }

    public boolean isLayer() {
        return "도면층3".equals(layer);
    }

}
