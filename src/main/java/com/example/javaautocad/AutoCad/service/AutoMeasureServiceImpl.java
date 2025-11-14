package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.LineDto;
import com.example.javaautocad.AutoCad.dto.StatisticsDto;

import java.nio.file.Path;

public class AutoMeasureServiceImpl implements AutoMeasureService {
    private final AutoAi ai;

    public AutoMeasureServiceImpl(AutoAi ai) {
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
