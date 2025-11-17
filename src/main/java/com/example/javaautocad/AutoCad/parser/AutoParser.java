package com.example.javaautocad.AutoCad.parser;

import com.example.javaautocad.AutoCad.domain.Arc;
import com.example.javaautocad.AutoCad.domain.Line;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.javaautocad.AutoCad.domain.Point;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AutoParser {
    private final String START = "start";
    private final String END = "end";
    private final String LINE = "LINE";
    private final String TYPE = "type";
    private final String CENTER = "center";
    private final String RADIUS = "radius";
    private final String START_ANGLE = "start_angle";
    private final String END_ANGLE = "end_angle";
    private final String ARC = "ARC";

    public List<Line> lineParser(String file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Line> lines = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(file);
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

    public Map<String, Integer> countEntities(String file) {
        Map<String, Integer> map = new HashMap<>();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(file);

            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode node = jsonNode.get(i);
                String type = node.get(TYPE).asText();
                map.put(type, map.getOrDefault(type, 0) + 1);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
        return map;
    }

    public List<Arc> arcParser(String file) {

    }
}
