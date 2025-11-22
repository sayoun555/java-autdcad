package com.example.javaautocad.AutoCad.domain;

import com.example.javaautocad.AutoCad.dto.G1Dto;

import java.util.ArrayList;
import java.util.List;

public class G1 {
    private final G1s g1s;
    private final double THRESHOLD = 0.5;

    public G1(G1s g1s) {
        this.g1s = g1s;
    }

    private List<Double> tangent() {
        List<Double> result = new ArrayList<>();
        List<Object> list = g1s.val();

        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof Line line) {
                result.add(angleLine(line));
            }
            if (obj instanceof Arc arc) {
                result.add(angleArc(arc));
            }
        }
        return result;
    }

    private double angleLine(Line l) {
        double dx = l.getEnd().getX() - l.getStart().getX();
        double dy = l.getEnd().getY() - l.getStart().getY();
        return Math.toDegrees(Math.atan2(dy, dx));
    }

    private double angleArc(Arc a) {
        Point c = a.getPoint();
        double r = a.getRadius();
        double end = Math.toRadians(a.getEndAngle());
        double px = c.getX() + r * Math.cos(end);
        double py = c.getY() + r * Math.sin(end);
        double dx = px - c.getX();
        double dy = py - c.getY();
        double tx = -dy;
        double ty = dx;

        return Math.toDegrees(Math.atan2(ty, tx));
    }

    private double normalize(double deg) {
        while (deg > 180) {
            deg -= 360;
        }
        while (deg < -180) {
            deg += 360;
        }
        return Math.abs(deg);
    }

    private double maxJump() {
        List<Double> ang = tangent();
        double max = 0.0;

        for (int i = 0; i < ang.size() - 1; i++) {
            double diff = normalize(Math.abs(ang.get(i + 1) - ang.get(i)));
            if (diff > max) {
                max = diff;
            }
        }
        return max;
    }

    private double avgJump() {
        List<Double> ang = tangent();
        if (ang.size() < 2) {
            return 0.0;
        }

        double sum = 0.0;
        int count = 0;

        for (int i = 0; i < ang.size() - 1; i++) {
            double diff = Math.abs(ang.get(i + 1) - ang.get(i));
            sum += normalize(diff);
            count++;
        }
        return sum / count;
    }

    private int breakCount() {
        List<Double> ang = tangent();
        int breaks = 0;

        for (int i = 0; i < ang.size() - 1; i++) {
            double diff = Math.abs(ang.get(i + 1) - ang.get(i));
            if (normalize(diff) > THRESHOLD) {
                breaks++;
            }
        }
        return breaks;
    }

    public G1Dto g1Delivery() {
        double maxJump = maxJump();
        double avgJump = avgJump();
        int breakCount = breakCount();

        return new G1Dto(maxJump, avgJump, breakCount);
    }
}