package com.example.javaautocad.AutoCad.dto;

public class LineDto {
    private final double total;
    private final double avg;
    private final double max;
    private final double min;

    public LineDto(double total, double avg, double max, double min) {
        this.total = total;
        this.avg = avg;
        this.max = max;
        this.min = min;
    }

    public double getTotal() {
        return total;
    }

    public double getAvg() {
        return avg;
    }

    public double getMax() {
        return max;
    }

    public double getMin() {
        return min;
    }
}
