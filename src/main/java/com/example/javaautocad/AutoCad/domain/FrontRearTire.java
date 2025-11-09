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

    private int countFrontLine() {
        return frontLine.size();
    }

    private int countRearLine() {
        return rearLine.size();
    }

    private double frontAverage() {
        if (frontLine.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (int i = 0; i < countFrontLine(); i++) {
            total += frontLine.get(i).abLine();
        }
        return total / countFrontLine();
    }

    private double rearAverage() {
        if (rearLine.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (int i = 0; i < countRearLine(); i++) {
            total += rearLine.get(i).abLine();
        }
        return total / countRearLine();
    }

    public double frontRearRatio() {
        if (frontLine.isEmpty() || rearLine.isEmpty()) {
            return 0.0;
        }
        double front = frontAverage();
        double rear = rearAverage();
        return front / rear;
    }

    public double frontRearDeviation() {
        double front = frontAverage();
        double rear = rearAverage();
        return Math.abs(front - rear);
    }
}

