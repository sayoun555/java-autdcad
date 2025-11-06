package com.example.javaautocad.AutoCad.service;

import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.domain.Polyline;
import com.example.javaautocad.AutoCad.factory.AutoFactory;
import com.example.javaautocad.AutoCad.parser.AutoParser;

import java.util.List;

public class AutoCadServiceImpl implements AutoCadService{
    private final AutoFactory autoFactory;

    public AutoCadServiceImpl(AutoFactory autoFactory) {
        this.autoFactory = autoFactory;
    }

    public Polyline autoStarts(AutoParser autoParser, String filePath) {
        List<Line> lines = autoParser.lineParser(filePath);
        return autoFactory.createPolyline(lines);
    }
}
