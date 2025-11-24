package com.example.javaautocad.AutoCad.dto;

public class CurvatureStatisticsDto {
    private final double maxCurvature;
    private final double minCurvature;
    private final double avgCurvature;
    private final double maxCurvatureJump;
    private final double avgCurvatureJump;
    private final double maxCurvatureRate;
    private final double avgCurvatureRate;

    public CurvatureStatisticsDto(
            double maxCurvature,
            double minCurvature,
            double avgCurvature,
            double maxCurvatureJump,
            double avgCurvatureJump,
            double maxCurvatureRate,
            double avgCurvatureRate
    ) {
        this.maxCurvature = maxCurvature;
        this.minCurvature = minCurvature;
        this.avgCurvature = avgCurvature;
        this.maxCurvatureJump = maxCurvatureJump;
        this.avgCurvatureJump = avgCurvatureJump;
        this.maxCurvatureRate = maxCurvatureRate;
        this.avgCurvatureRate = avgCurvatureRate;
    }

    public double getMaxCurvature() {
        return maxCurvature;
    }

    public double getMinCurvature() {
        return minCurvature;
    }

    public double getAvgCurvature() {
        return avgCurvature;
    }

    public double getMaxCurvatureJump() {
        return maxCurvatureJump;
    }

    public double getAvgCurvatureJump() {
        return avgCurvatureJump;
    }

    public double getMaxCurvatureRate() {
        return maxCurvatureRate;
    }

    public double getAvgCurvatureRate() {
        return avgCurvatureRate;
    }
}
