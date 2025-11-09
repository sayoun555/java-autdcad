package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

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

    public double slope() {
        double dx = end.getX() - start.getX();
        double dy = end.getY() - start.getY();
        if (dx == 0) {
            throw new IllegalArgumentException("수직선의 기울기가 잘 못 되었습니다.");
        }
        return dy / dx;
    }
}
