package com.example.javaautocad.AutoCad.domain;

import com.example.javaautocad.AutoCad.dto.CircleStatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class Circles {
    private final List<Circle> circles;

    public Circles(List<Circle> circles) {
        this.circles = new ArrayList<>();
    }

    private double averageRadius() {
        if (circles.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Circle circle :circles) {
            sum += circle.getRadius();
        }
        return sum / circles.size();
    }


    private double averageCurvature() {
        if (circles.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Circle circle : circles) {
            sum += circle.curvature();
        }
        return sum / circles.size();
    }

    private double averageCircumference() {
        if (circles.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Circle circle : circles) {
            sum += circle.circumference();
        }
        return sum / circles.size();
    }

    public CircleStatisticsDto toDto() {
        boolean valid = !circles.isEmpty();
        return new CircleStatisticsDto(
                averageRadius(),
                averageCurvature(),
                averageCircumference(),
                circles.size(),
                valid
        );
    }

    public List<Circle> getCircles() {
        return circles;
    }
}