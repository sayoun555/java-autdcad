package com.example.javaautocad.AutoCad.dto;

public class ArcStatisticsDto {
    private final double averageRadius;
    private final double averageCurvature;
    private final double averageArcLength;
    private final int count;
    private final boolean valid;

    public ArcStatisticsDto(double averageRadius, double averageCurvature, double averageArcLength, int count, boolean valid) {
        this.averageRadius = averageRadius;
        this.averageCurvature = averageCurvature;
        this.averageArcLength = averageArcLength;
        this.count = count;
        this.valid = valid;
    }
    public double getAverageRadius() {
        return averageRadius;
    }

    public double getAverageCurvature() {
        return averageCurvature;
    }

    public double getAverageArcLength() {
        return averageArcLength;
    }

    public int getCount() {
        return count;
    }

    public boolean isValid() {
        return valid;
    }
}