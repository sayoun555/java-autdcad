package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.parser.AutoParser;

public interface AutoCadService {
    Polyline autoStarts(AutoParser autoParser, String filePath);
}
