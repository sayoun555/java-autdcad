package com.example.javaautocad.AutoCad.parser;

import com.example.javaautocad.AutoCad.domain.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.javaautocad.AutoCad.message.ErrorMessage;
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
    private final String CIRCLE = "CIRCLE";
    private final String ELLIPSE = "ELLIPSE";
    private final String MAJOR_AXIS = "major_axis";
    private final String RATIO = "ratio";

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
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Arc> arcs = new ArrayList<>();
            JsonNode node = objectMapper.readTree(file);
            for (int i = 0; i < node.size(); i++) {
                JsonNode jsonNode = node.get(i);
                if (!ARC.equals(jsonNode.get(TYPE).asText())) {
                    continue;
                }
                JsonNode center = jsonNode.get(CENTER);
                double radius = jsonNode.get(RADIUS).asDouble();
                double startAngle = jsonNode.get(START_ANGLE).asDouble();
                double endAngle = jsonNode.get(END_ANGLE).asDouble();

                Point point = new Point(center.get(0).asDouble(), center.get(1).asDouble());
                arcs.add(new Arc(point, radius, startAngle, endAngle));
            }
            return arcs;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.ARC_ERROR.getMessage());
        }
    }

    public List<Circle> circlesParser(String file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Circle> circles = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(file);
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode node = jsonNode.get(i);
                if (!CIRCLE.equals(node.get(TYPE).asText())) {
                    continue;
                }
                JsonNode center = node.get(CENTER);
                double radius = node.get(RADIUS).asDouble();
                Point centerPoint = new Point(center.get(0).asDouble(), center.get(1).asDouble());
                circles.add(new Circle(centerPoint, radius));
            }
            return circles;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.CIRCLES_ERROR.name());
        }
    }

    public List<Ellipse> ellipseParser(String file) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Ellipse> ellipses = new ArrayList<>();
            JsonNode jsonNode = objectMapper.readTree(file);
            for (int i = 0; i < jsonNode.size(); i++) {
                JsonNode node = jsonNode.get(i);
                if (!ELLIPSE.equals(node.get(TYPE).asText())) {
                    continue;
                }
                JsonNode center = node.get(CENTER);
                JsonNode majorAxis = node.get(MAJOR_AXIS);
                double ratio = node.get(RATIO).asDouble();

                Point centerPoint = new Point(center.get(0).asDouble(), center.get(1).asDouble());
                double majorRadius = Math.sqrt(
                        Math.pow(majorAxis.get(0).asDouble(), 2) +
                                Math.pow(majorAxis.get(1).asDouble(), 2)
                );
                double minorRadius = majorRadius * ratio;
                double rotation = Math.atan2(
                        majorAxis.get(1).asDouble(),
                        majorAxis.get(0).asDouble()
                );

                ellipses.add(new Ellipse(centerPoint, majorRadius, minorRadius, rotation));
            }
            return ellipses;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.ELLIPSE_ERROR.getMessage());
        }
    }

    public EntityStatistics parseStatistics(String file) {
        Map<String, Integer> counts = countEntities(file);
        return new EntityStatistics(counts.getOrDefault(LINE, 0), counts.getOrDefault(ARC, 0), counts.getOrDefault(CIRCLE, 0), counts.getOrDefault(ELLIPSE, 0));
    }
}
