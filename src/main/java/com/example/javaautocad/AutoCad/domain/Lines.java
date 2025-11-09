package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

public class Lines {
    private final List<Line> lineList;

    public Lines(List<Line> lineList) {
        this.lineList = new ArrayList<>(lineList);
    }
    public double medianLength() {
        List<Double> y = new ArrayList<>();
        for (Line line : lineList) {
            y.add(line.abLine());
        }
        y.sort(null);
        int num = y.size();
        if (num % 2 > 0) {
            return y.get(num / 2);
        }
        return (y.get(num/2 - 1) + y.get(num/2)) / 2;
    }

    public List<Line> getLineList() {
        return lineList;
    }
}
