package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

public class Lines {
    private final List<Line> lineList;

    public Lines(List<Line> lineList) {
        this.lineList = new ArrayList<>(lineList);
    }
    public double medianLength() {
        List<Double> y = new ArrayList<>();
        for (Line line : lineList) {
            y.add(line.abLine());
        }
        y.sort(null);
        int num = y.size();
        if (num % 2 > 0) {
            return y.get(num / 2);
        }
        return (y.get(num/2 - 1) + y.get(num/2)) / 2;
    }

    private double lineAverage() {
        double average = 0.0;
        double value;
        for (int i = 0; i < lineList.size(); i++) {
            value = lineList.get(i).abLine();
            average += value;
        }
        return average / lineList.size();
    }
    private double lineVariance() {
        double mean = lineAverage();
        double cumulative = 0.0;
        double value;
        for (int i = 0; i < lineList.size(); i++) {
            value = lineList.get(i).abLine();
            cumulative += Math.pow(value - mean, 2);
        }
        return cumulative / lineList.size();
    }

    private double lineStdDeviation() {
        double variance = lineVariance();
        return Math.sqrt(variance);
    }

    public double lineHomogeneity() {
        return 1 - (lineStdDeviation() / lineAverage());
    }

    public double lineDelivery() {

    }

    public List<Line> getLineList() {
        return lineList;
    }
}
