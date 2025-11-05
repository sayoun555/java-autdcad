package com.example.javaautocad.AutoCad.factory;

import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.domain.Point;
import com.example.javaautocad.AutoCad.domain.Polyline;

import java.util.List;

public class AutoFactory {
    public Polyline cadCreative(List<Line> lines) {
        List<Line> line = lines.stream()
                .map(list -> new Line(new Polyline(lines)))
    }
}
