package com.example.javaautocad.AutoCad.domain;

import com.example.javaautocad.AutoCad.dto.ArcStatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class Arcs {
    private final List<Arc> arcs;

    public Arcs(List<Arc> arcs) {
        this.arcs = new ArrayList<>(arcs);
    }

    public Arcs filter(LayerFilter filter) {
        List<Arc> filtered = new ArrayList<>();
        for (Arc arc : arcs) {
            if (filter.matches(arc)) {
                filtered.add(arc);
            }
        }
        return new Arcs(filtered);
    }


    private double averageRadius() {
        if (arcs.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Arc arc : arcs) {
            sum += arc.getRadius();
        }
        return sum / arcs.size();
    }

    private double averageCurvature() {
        if (arcs.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Arc arc : arcs) {
            sum += arc.curvatuer();
        }
        return sum / arcs.size();
    }

    private double averageArcLength() {
        if (arcs.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Arc arc : arcs) {
            sum += arc.arcLength();
        }
        return sum / arcs.size();
    }

    public ArcStatisticsDto aecDelivery() {
        boolean valid = !arcs.isEmpty();
        return new ArcStatisticsDto(
                averageRadius(),
                averageCurvature(),
                averageArcLength(),
                arcs.size(),
                valid
        );
    }

    public List<Arc> getArcs() {
        return arcs;
    }
}
