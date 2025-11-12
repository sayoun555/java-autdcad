package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.LineDto;
import com.example.javaautocad.AutoCad.dto.StatisticsDto;
import com.example.javaautocad.AutoCad.factory.AutoFactory;

import java.nio.file.Path;
import java.util.List;

public class AutoMeasureServiceImpl implements AutoMeasureService {
    private final AutoAi ai;
    private final AutoFactory autoFactory;

    public AutoMeasureServiceImpl(AutoFactory autoFactory, AutoAi ai) {
        this.autoFactory = autoFactory;
        this.ai = ai;
    }

    public LineDto autoSummary(Polyline polyline) {
        return polyline.lineGraph();
    }

    public StatisticsDto lineStatistics(Lines lines) {
        return lines.lineDelivery();
    }

    public String aiCall(Path path) {
        return ai.analyze(path);
    }


}
