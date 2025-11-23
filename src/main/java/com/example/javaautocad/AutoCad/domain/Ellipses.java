package com.example.javaautocad.AutoCad.domain;

import com.example.javaautocad.AutoCad.dto.EllipseStatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class Ellipses {
    private final List<Ellipse> ellipseList;

    public Ellipses(List<Ellipse> ellipseList) {
        this.ellipseList = new ArrayList<>(ellipseList);
    }

    public Ellipses filter(LayerFilter filter) {
        List<Ellipse> filtered = new ArrayList<>();
        for (Ellipse ellipse : ellipseList) {
            if (filter.matches(ellipse)) {
                filtered.add(ellipse);
            }
        }
        return new Ellipses(filtered);
    }

    private double averageMajorRadius() {
        if (ellipseList.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Ellipse ellipse : ellipseList) {
            sum += ellipse.getMajorRadius();
        }
        return sum / ellipseList.size();
    }

    private double averageMinorRadius() {
        if (ellipseList.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Ellipse ellipse : ellipseList) {
            sum += ellipse.getMinorRadius();
        }
        return sum / ellipseList.size();
    }

    private double averageEccentricity() {
        if (ellipseList.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Ellipse ellipse : ellipseList) {
            sum += ellipse.eccentricity();
        }
        return sum / ellipseList.size();
    }

    private double averageCurvature() {
        if (ellipseList.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Ellipse ellipse : ellipseList) {
            sum += ellipse.averageCurvature();
        }
        return sum / ellipseList.size();
    }

    public EllipseStatisticsDto ellipesDelivery() {
        boolean valid = !ellipseList.isEmpty();
        return new EllipseStatisticsDto(
                averageMajorRadius(),
                averageMinorRadius(),
                averageEccentricity(),
                averageCurvature(),
                ellipseList.size(),
                valid
        );
    }

    public List<Ellipse> getEllipseList() {
        return ellipseList;
    }
}