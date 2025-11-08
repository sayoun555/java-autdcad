package com.example.javaautocad.AutoCad.factory;

import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.domain.Polyline;

import java.util.List;

public class AutoFactory {
    public Polyline createPolyline(List<Line> lines) {
        return new Polyline(lines);
    }
}
