package com.example.javaautocad.AutoCad.domain;

import com.example.javaautocad.AutoCad.dto.CurvatureStatisticsDto;

import java.util.ArrayList;
import java.util.List;

public class CurvatureStatistics {
    private final G1s g1s;

    public CurvatureStatistics(G1s g1s) {
        this.g1s = g1s;
    }

    private List<Object> segments() {
        return g1s.val();
    }

    private double curvature(Object obj) {
        if (obj instanceof Line line) {
            return 0.0;
        }
        if (obj instanceof Arc arc) {
            double r = arc.getRadius();
            if (r == 0.0) {
                return 0.0;
            }
            return 1.0 / r;
        }
        return 0.0;
    }

    private double length(Object obj) {
        if (obj instanceof Line line) {
            return line.abLine();
        }
        if (obj instanceof Arc arc) {
            return arc.arcLength();
        }
        return 0.0;
    }

    private List<Double> makeCurvature(List<Object> list) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            double kappa = curvature(list.get(i));
            result.add(kappa);
        }
        return result;
    }

    private List<Double> makeLength(List<Object> list) {
        List<Double> result = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            double len = length(list.get(i));
            result.add(len);
        }
        return result;
    }

    private double[] curvatureStats(List<Double> kappaList) {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.POSITIVE_INFINITY;
        double sum = 0.0;

        for (int i = 0; i < kappaList.size(); i++) {
            double k = kappaList.get(i);
            if (k > max) {
                max = k;
            }
            if (k < min) {
                min = k;
            }
            sum = sum + k;
        }
        double avg = sum / kappaList.size();
        return new double[]{max, min, avg};
    }

    private double[] curvatureJump(List<Double> kappaList) {
        double maxJump = 0.0;
        double sum = 0.0;
        int count = 0;

        for (int i = 0; i < kappaList.size() - 1; i++) {
            double k1 = kappaList.get(i);
            double k2 = kappaList.get(i + 1);
            double dk = k2 - k1;
            if (dk < 0.0) {
                dk = -dk;
            }
            if (dk > maxJump) {
                maxJump = dk;
            }
            sum = sum + dk;
            count = count + 1;
        }
        double avg = 0.0;
        if (count > 0) {
            avg = sum / count;
        }
        return new double[]{maxJump, avg};
    }

    private double[] curvatureRate(List<Double> kappaList, List<Double> lengthList) {
        double maxRate = 0.0;
        double sum = 0.0;
        int count = 0;

        for (int i = 0; i < kappaList.size() - 1; i++) {
            double dk = kappaList.get(i + 1) - kappaList.get(i);
            if (dk < 0.0) {
                dk = -dk;
            }
            double s1 = lengthList.get(i);
            double s2 = lengthList.get(i + 1);
            double avgS = (s1 + s2) / 2.0;
            if (avgS == 0.0) {
                continue;
            }
            double rate = dk / avgS;
            if (rate > maxRate) {
                maxRate = rate;
            }
            sum = sum + rate;
            count = count + 1;
        }
        double avg = 0.0;
        if (count > 0) {
            avg = sum / count;
        }
        return new double[]{maxRate, avg};
    }

    public CurvatureStatisticsDto curvatureDelivery() {
        List<Object> seg = new ArrayList<>(segments());
        int size = seg.size();

        if (size == 0) {
            return new CurvatureStatisticsDto(
                    0.0, 0.0, 0.0,
                    0.0, 0.0,
                    0.0, 0.0
            );
        }
        List<Double> kappaList = makeCurvature(seg);
        List<Double> lengthList = makeLength(seg);
        double[] absStats = curvatureStats(kappaList);
        double[] jumpStats = curvatureJump(kappaList);
        double[] rateStats = curvatureRate(kappaList, lengthList);
        return new CurvatureStatisticsDto(
                absStats[0],  // maxCurvature
                absStats[1],  // minCurvature
                absStats[2],  // avgCurvature
                jumpStats[0], // maxJump
                jumpStats[1], // avgJump
                rateStats[0], // maxRate
                rateStats[1]  // avgRate
        );
    }
}
