package com.example.javaautocad.AutoCad.dto;

public class StatisticsDto {
    private final double lineAverage;
    private final double lineVariance;
    private final double lineStdDeviation;
    private final double lineHomogeneity;

    public StatisticsDto(double lineAverage, double lineVariance, double lineStdDeviation, double lineHomogeneity) {
        this.lineAverage = lineAverage;
        this.lineVariance = lineVariance;
        this.lineStdDeviation = lineStdDeviation;
        this.lineHomogeneity = lineHomogeneity;
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
}
