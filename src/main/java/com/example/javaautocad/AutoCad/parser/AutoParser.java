package com.example.javaautocad.AutoCad.parser;

import com.example.javaautocad.AutoCad.domain.*;

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
    public static final String LAYER = "layer";

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<Line> lineParser(String file) {
        try {
            JsonNode jsonNode = parseJson(file);
            List<Line> lines = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                if (type(node, LINE)) {
                    lines.add(parseLine(node));
                }
            }
            return lines;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.LINE_ERROR.getMessage());
        }
    }

    public List<Arc> arcParser(String file) {
        try {
            JsonNode jsonNode = parseJson(file);
            List<Arc> arcs = new ArrayList<>();

            for (JsonNode node : jsonNode) {
                if (type(node, ARC)) {
                    arcs.add(parseArc(node));
                }
            }
            return arcs;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.ARC_ERROR.getMessage());
        }
    }

    public List<Circle> circlesParser(String file) {
        try {
            JsonNode jsonNode = parseJson(file);
            List<Circle> circles = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                if (type(node, CIRCLE)) {
                    circles.add(parseCircle(node));
                }
            }
            return circles;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.CIRCLES_ERROR.getMessage());
        }
    }

    public List<Ellipse> ellipseParser(String file) {
        try {
            JsonNode jsonNode = parseJson(file);
            List<Ellipse> ellipses = new ArrayList<>();
            for (JsonNode node : jsonNode) {
                if (type(node, ELLIPSE)) {
                    ellipses.add(parseEllipse(node));
                }
            }
            return ellipses;
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.ELLIPSE_ERROR.getMessage());
        }
    }

    public Map<String, Integer> countEntities(String file) {
        Map<String, Integer> map = new HashMap<>();
        try {
            JsonNode jsonNode = parseJson(file);
            for (JsonNode node : jsonNode) {
                String type = node.get(TYPE).asText();
                map.put(type, map.getOrDefault(type, 0) + 1);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.COUNT_ERROR.getMessage());
        }
        return map;
    }

    public EntityStatistics parseStatistics(String file) {
        Map<String, Integer> counts = countEntities(file);
        return new EntityStatistics(
                counts.getOrDefault(LINE, 0),
                counts.getOrDefault(ARC, 0),
                counts.getOrDefault(CIRCLE, 0),
                counts.getOrDefault(ELLIPSE, 0)
        );
    }

    private JsonNode parseJson(String file) throws Exception {
        return objectMapper.readTree(file);
    }

    private boolean type(JsonNode node, String type) {
        return type.equals(node.get(TYPE).asText());
    }

    private Line parseLine(JsonNode node) {
        JsonNode start = node.get(START);
        JsonNode end = node.get(END);
        String layer = "0";
        if (node.has(LAYER)) {
            layer = node.get(LAYER).asText();
        }
        Point startPoint = point(start);
        Point endPoint = point(end);
        return new Line(startPoint, endPoint, layer);
    }

    private Arc parseArc(JsonNode node) {
        Point center = point(node.get(CENTER));
        double radius = node.get(RADIUS).asDouble();
        double startAngle = node.get(START_ANGLE).asDouble();
        double endAngle = node.get(END_ANGLE).asDouble();
        String layer = "0";
        if (node.has(LAYER)) {
            layer = node.get(LAYER).asText();
        }
        return new Arc(center, radius, startAngle, endAngle, layer);
    }

    private Circle parseCircle(JsonNode node) {
        Point center = point(node.get(CENTER));
        double radius = node.get(RADIUS).asDouble();
        String layer = "0";
        if (node.has(LAYER)) {
            layer = node.get(LAYER).asText();
        }
        return new Circle(center, radius, layer);
    }

    private Ellipse parseEllipse(JsonNode node) {
        Point center = point(node.get(CENTER));
        JsonNode majorAxis = node.get(MAJOR_AXIS);
        double ratio = node.get(RATIO).asDouble();
        String layer = "0";
        if (node.has(LAYER)) {
            layer = node.get(LAYER).asText();
        }
        return Ellipse.ellipse(
                center,
                majorAxis.get(0).asDouble(),
                majorAxis.get(1).asDouble(),
                ratio,
                layer
        );
    }

    private Point point(JsonNode node) {
        return new Point(node.get(0).asDouble(), node.get(1).asDouble());
    }
}