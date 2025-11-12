package com.example.javaautocad.AutoCad.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Point {
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.y = y;
        this.x = x;
    }

    public double xyPoint(Point other) {
        double dx = other.x - this.x;
        double dy = other.y - this.y;
        return Math.sqrt(dx * dx + dy);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
