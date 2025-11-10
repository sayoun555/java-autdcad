package com.example.javaautocad.AutoCad.dto;

import java.util.List;

public class StatisticsDto {
    private final double lineAverage;
    private final double lineVariance;
    private final double lineStdDeviation;
    private final double lineHomogeneity;
    private final boolean valid;
    private final List<Double> lineLength;

    public StatisticsDto(double lineAverage, double lineVariance, double lineStdDeviation, double lineHomogeneity, boolean valid, List<Double> lineLength) {
        this.lineAverage = lineAverage;
        this.lineVariance = lineVariance;
        this.lineStdDeviation = lineStdDeviation;
        this.lineHomogeneity = lineHomogeneity;
        this.valid = valid;
        this.lineLength = lineLength;
    }

    public double getLineAverage() {
        return lineAverage;
    }

    public double getLineVariance() {
        return lineVariance;
    }

    public double getLineStdDeviation() {
        return lineStdDeviation;
    }

    public double getLineHomogeneity() {
        return lineHomogeneity;
    }

    public boolean isValid() {
        return valid;
    }

    public List<Double> getLineLength() {
        return lineLength;
    }
}
