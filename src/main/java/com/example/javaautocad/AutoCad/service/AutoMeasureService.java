package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.LineDto;

public interface AutoMeasureService {
    LineDto autoSummary(Polyline polyline);
}
