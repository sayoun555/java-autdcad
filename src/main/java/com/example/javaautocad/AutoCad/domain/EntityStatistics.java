package com.example.javaautocad.AutoCad.domain;

import com.example.javaautocad.AutoCad.dto.EntityCountStatisticsDto;

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
        if (totalEntities() == 0) {
            return 0.0;
        }
        int totalCurves = arcCount + circleCount + ellipseCount;
        return (double) totalCurves / totalEntities();
    }

    public EntityCountStatisticsDto entityDelivery() {
        return new EntityCountStatisticsDto(
                lineCount,
                arcCount,
                circleCount,
                ellipseCount,
                curve()
        );
    }

}
