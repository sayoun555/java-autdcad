package com.example.javaautocad.AutoCad.domain;

public class Line {
    private final Point start;
    private final Point end;

    public Line (Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public double abLine() {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
