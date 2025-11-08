package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.AutoDto;

public interface AutoMeasureService {
    AutoDto autoSummary(Polyline polyline);
}
