package com.example.javaautocad.AutoCad.domain;

public class Line {
    private final Point start;
    private final Point end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    public double abLine() {
        return start.xyPoint(end);
    }
}
