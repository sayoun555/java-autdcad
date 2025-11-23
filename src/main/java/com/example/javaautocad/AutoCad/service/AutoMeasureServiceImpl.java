package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.manager.DxfConverter;

import java.nio.file.Path;

public class AutoMeasureServiceImpl implements AutoMeasureService {
    private final AutoAi ai;
    private final DxfConverter dxfConverter;

    public AutoMeasureServiceImpl(AutoAi ai, DxfConverter dxfConverter) {
        this.ai = ai;
        this.dxfConverter = dxfConverter;
    }

    public String analyzeDxf(Path dxf) {
        Path jsonPath = dxfConverter.convert(dxf);
        return ai.analyze(jsonPath);
    }

    public void tireType(String type) {
        ai.tireType(type);
    }

}
