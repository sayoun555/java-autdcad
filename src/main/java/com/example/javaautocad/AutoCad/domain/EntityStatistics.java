package com.example.javaautocad.AutoCad.domain;

public class EntityStatistics {
    private final int lineCount;
    private final int arcCount;
    private final int circleCount;
    private final int ellipseCount;

    public EntityStatistics(int lineCount, int arcCount, int circleCount, int ellipseCount) {
        this.lineCount = lineCount;
        this.arcCount = arcCount;
        this.circleCount = circleCount;
        this.ellipseCount = ellipseCount;
    }

    public int totalEntities() {
        return lineCount + arcCount + circleCount + ellipseCount;
    }

    public double curve() {
        int totalCurves = arcCount + circleCount + ellipseCount;
        return (double) totalCurves / totalEntities();
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
}
