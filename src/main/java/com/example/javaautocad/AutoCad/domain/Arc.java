package com.example.javaautocad.AutoCad.domain;

public class Arc {
    private final Point point;
    private final double radius;
    private final double startAngle;
    private final double endAngle;
    private final String layer;

    public Arc(Point point, double radius, double startAngle, double endAngle, String layer) {
        this.point = point;
        this.radius = radius;
        this.startAngle = startAngle;
        this.endAngle = endAngle;
        this.layer = layer;
    }

    public double arcLength() {
        double angleRange = Math.abs(endAngle - startAngle);
        return radius * Math.toRadians(angleRange);
    }

    public double curvatuer() {
        return 1.0 / radius;
    }

    public double getRadius() {
        return radius;
    }

    public Point getPoint() {
        return point;
    }

    public double getStartAngle() {
        return startAngle;
    }

    public double getEndAngle() {
        return endAngle;
    }

    public String getLayer() {
        return layer;
    }

    public Point getStartPoint() {
        double angle = Math.toRadians(startAngle);
        return new Point(
                point.getX() + radius * Math.cos(angle),
                point.getY() + radius * Math.sin(angle)
        );
    }

    public Point getEndPoint() {
        double angle = Math.toRadians(endAngle);
        return new Point(
                point.getX() + radius * Math.cos(angle),
                point.getY() + radius * Math.sin(angle)
        );
    }
}
