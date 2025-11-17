package com.example.javaautocad.AutoCad.dto;

public class EntityCountStatisticsDto {
    private final int lineCount;
    private final int arcCount;
    private final int circleCount;
    private final int ellipseCount;
    private final double curve;

    public EntityCountStatisticsDto(int lineCount, int arcCount, int circleCount, int ellipseCount, double curve) {
        this.lineCount = lineCount;
        this.arcCount = arcCount;
        this.circleCount = circleCount;
        this.ellipseCount = ellipseCount;
        this.curve = curve;
    }

    public int getLineCount() {
        return lineCount;
    }

    public int getArcCount() {
        return arcCount;
    }

    public int getCircleCount() {
        return circleCount;
    }

    public int getEllipseCount() {
        return ellipseCount;
    }

    public double getCurve() {
        return curve;
    }
}
