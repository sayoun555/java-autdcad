package com.example.javaautocad.AutoCad.service;

import java.nio.file.Path;

public interface AutoMeasureService {
    String analyzeDxf(Path dxf);
    void tireType(String type);
}

