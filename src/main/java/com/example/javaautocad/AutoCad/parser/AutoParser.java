package com.example.javaautocad.AutoCad.parser;

import com.example.javaautocad.AutoCad.domain.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.javaautocad.AutoCad.domain.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AutoParser {
    private final String START = "start";
    private final String END = "end";
    private final String LINE = "LINE";
    private final String TYPE = "type";

    public List<Line> lineParser(String file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Line> lines = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(new File(file));
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode node = jsonNode.get(i);
                if (!LINE.equals(node.get(TYPE).asText())) {
                    continue;
                }
                JsonNode start = node.get(START);
                JsonNode end = node.get(END);
                Point pointx = new Point(start.get(0).asDouble(), start.get(1).asDouble());
                Point pointy = new Point(end.get(0).asDouble(), end.get(1).asDouble());
                lines.add(new Line(pointx, pointy));
            }
            return lines;
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }

    }
}
