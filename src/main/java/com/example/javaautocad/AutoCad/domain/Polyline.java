package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

public class Polyline {
    private final List<Line> lines;

    public Polyline(List<Line> lines) {
        this.lines = new ArrayList<>(lines);
    }

    private double resultLine() {
        double total = 0.0;
        for (Line line : lines) {
            total += line.abLine();
        }
        return total;
    }

    private double averageLine() {
        double total = resultLine();
        return total / lines.size();
    }

    private double maxLine() {
        double max = lines.stream()
                .mapToDouble(Line::abLine)
                .max()
                .getAsDouble();
        return max;
    }

    private double minLine() {
        double min = lines.stream()
                .mapToDouble(Line::abLine)
                .min()
                .getAsDouble();
        return min;
    }

    public List<Line> getLines() {
        return lines;
    }
}
