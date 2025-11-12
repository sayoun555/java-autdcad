package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.LineDto;
import com.example.javaautocad.AutoCad.dto.StatisticsDto;
import com.example.javaautocad.AutoCad.factory.AutoFactory;

import java.util.List;

public class AutoMeasureServiceImpl implements AutoMeasureService {
    private final AutoFactory autoFactory;

    public AutoMeasureServiceImpl(AutoFactory autoFactory) {
        this.autoFactory = autoFactory;
    }

    public LineDto autoSummary(Polyline polyline) {
        return polyline.lineGraph();
    }

    public StatisticsDto lineStatistics(Lines lines) {
        return lines.lineDelivery();
    }

}
