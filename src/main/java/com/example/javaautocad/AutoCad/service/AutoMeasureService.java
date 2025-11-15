package com.example.javaautocad.AutoCad.service;

import java.nio.file.Path;

public interface AutoMeasureService {
    String aiCall(Path path);
    String analyzeDxf(Path dxf);
}