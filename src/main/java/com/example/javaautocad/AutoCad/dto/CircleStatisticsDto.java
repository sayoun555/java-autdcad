package com.example.javaautocad.AutoCad.dto;

public class CircleStatisticsDto {
    private final double averageRadius;
    private final double averageCurvature;
    private final double averageCircumference;
    private final int count;
    private final boolean valid;

    public CircleStatisticsDto(
            double averageRadius,
            double averageCurvature,
            double averageCircumference,
            int count,
            boolean valid
    ) {
        this.averageRadius = averageRadius;
        this.averageCurvature = averageCurvature;
        this.averageCircumference = averageCircumference;
        this.count = count;
        this.valid = valid;
    }

    public double getAverageRadius() {
        return averageRadius;
    }

    public double getAverageCurvature() {
        return averageCurvature;
    }

    public double getAverageCircumference() {
        return averageCircumference;
    }

    public int getCount() {
        return count;
    }

    public boolean isValid() {
        return valid;
    }
}
