package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.dto.AutoDto;
import com.example.javaautocad.AutoCad.factory.AutoFactory;

public class AutoMeasureServiceImpl implements AutoMeasureService {
    private final AutoFactory autoFactory;

    public AutoMeasureServiceImpl(AutoFactory autoFactory) {
        this.autoFactory = autoFactory;
    }

    public AutoDto autoSummary(Polyline polyline) {
         = polyline.getLines();
        return new AutoDto();

    }

}
