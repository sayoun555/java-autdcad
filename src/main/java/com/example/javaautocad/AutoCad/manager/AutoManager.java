package com.example.javaautocad.AutoCad.manager;

import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.AutoDto;
import com.example.javaautocad.AutoCad.factory.AutoFactory;
import com.example.javaautocad.AutoCad.parser.AutoParser;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;

import java.util.List;

public class AutoManager {
    private final AutoFactory autoFactory;
    private final AutoParser autoParser;
    private final AutoMeasureService autoMeasureService;

    public AutoManager(AutoFactory autoFactory, AutoParser autoParser, AutoMeasureService autoMeasureService) {
        this.autoFactory = autoFactory;
        this.autoParser = autoParser;
        this.autoMeasureService = autoMeasureService;
    }

    public AutoDto processBuild(String file) {
        List<Line> lines = autoParser.lineParser(file);
        Polyline polyline = autoFactory.createPolyline(lines);
        return autoMeasureService.autoSummary(polyline);
    }
}
