package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.LineDto;
import com.example.javaautocad.AutoCad.dto.StatisticsDto;

import java.nio.file.Path;

public interface AutoMeasureService {
    LineDto autoSummary(Polyline polyline);
    StatisticsDto lineStatistics(Lines lines);
    public String aiCall(Path path);
}