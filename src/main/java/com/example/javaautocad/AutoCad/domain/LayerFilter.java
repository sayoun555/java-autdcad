package com.example.javaautocad.AutoCad.domain;

public class LayerFilter {
    private final String targetLayer;

    public LayerFilter(String targetLayer) {
        this.targetLayer = targetLayer;
    }

    public boolean matches(Line line) {
        return targetLayer.equals(line.getLayer());
    }

    public boolean matches(Arc arc) {
        return targetLayer.equals(arc.getLayer());
    }

    public boolean matches(Circle circle) {
        return targetLayer.equals(circle.getLayer());
    }

    public boolean matches(Ellipse ellipse) {
        return targetLayer.equals(ellipse.getLayer());
    }
}