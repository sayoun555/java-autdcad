package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.factory.AutoFactory;

import java.util.List;

public class AutoMeasureServiceImpl implements AutoMeasureService {
    private final AutoFactory autoFactory;

    public AutoMeasureServiceImpl(AutoFactory autoFactory) {
        this.autoFactory = autoFactory;
    }

    public Polyline autoSummary(Polyline polyline) {
        List<Line> list = polyline.getLines();
        return autoFactory.createPolyline(list);

    }

}
