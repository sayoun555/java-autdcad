package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

public class Polyline {
    private final List<Line> lines;

    public Polyline (List<Line> lines) {
        this.lines = new ArrayList<>(lines);
    }

    public double resultLine() {
        double total = 0.0;
        for (Line line : lines) {
            total += line.abLine();
        }
        return total;
    }
}
