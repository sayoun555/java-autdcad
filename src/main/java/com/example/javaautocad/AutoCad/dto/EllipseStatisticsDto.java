package com.example.javaautocad.AutoCad.dto;

public class EllipseStatisticsDto {
    private final double averageMajorRadius;
    private final double averageMinorRadius;
    private final double averageEccentricity;
    private final double averageCurvature;
    private final int count;
    private final boolean valid;

    public EllipseStatisticsDto(
            double averageMajorRadius,
            double averageMinorRadius,
            double averageEccentricity,
            double averageCurvature,
            int count,
            boolean valid
    ) {
        this.averageMajorRadius = averageMajorRadius;
        this.averageMinorRadius = averageMinorRadius;
        this.averageEccentricity = averageEccentricity;
        this.averageCurvature = averageCurvature;
        this.count = count;
        this.valid = valid;
    }

    public double getAverageMajorRadius() {
        return averageMajorRadius;
    }

    public double getAverageMinorRadius() {
        return averageMinorRadius;
    }

    public double getAverageEccentricity() {
        return averageEccentricity;
    }

    public double getAverageCurvature() {
        return averageCurvature;
    }

    public int getCount() {
        return count;
    }

    public boolean isValid() {
        return valid;
    }
}