package com.example.javaautocad.AutoCad.domain;

public class Line {
    private final Point start;
    private final Point end;
    private final String layer;

    public Line(Point start, Point end, String layer) {
        this.start = start;
        this.end = end;
        this.layer = layer;
    }

    public double abLine() {
        return start.xyPoint(end);
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public String getLayer() {
        return layer;
    }
}
