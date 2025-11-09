package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

public class FrontRearTire {
    private final List<Line> frontLine;
    private final List<Line> rearLine;

    public FrontRearTire(List<Line> frontLine, List<Line> rearLine) {
        this.frontLine = new ArrayList<>();
        this.rearLine = new ArrayList<>();
    }

    private void tireDivision(double median, Line line) {
        if (line.abLine() < median) {
            frontLine.add(line);
        }
        if (line.abLine() > median) {
            rearLine.add(line);
        }
    }

    public void divideAll(Lines lines) {
        double median = lines.medianLength();
        for (int i = 0; i < lines.getLineList().size(); i++) {
            Line line = lines.getLineList().get(i);
            tireDivision(median, line);
        }
    }
}

