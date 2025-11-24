package com.example.javaautocad;

import com.example.javaautocad.AutoCad.domain.*;
import com.example.javaautocad.AutoCad.parser.AutoParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    private AutoParser parser;
    private String testJson;

    @BeforeEach
    public void setUp() {
        parser = new AutoParser();
        testJson = """
        [
            {
                "type": "LINE",
                "start": [0.0, 0.0],
                "end": [100.0, 100.0]
            },
            {
                "type": "LINE",
                "start": [50.0, 50.0],
                "end": [150.0, 150.0]
            },
            {
                "type": "CIRCLE",
                "center": [50.0, 50.0],
                "radius": 25.0
            },
            {
                "type": "ARC",
                "center": [75.0, 75.0],
                "radius": 15.0,
                "start_angle": 0.0,
                "end_angle": 90.0
            },
            {
                "type": "ELLIPSE",
                "center": [100.0, 100.0],
                "major_axis": [20.0, 0.0],
                "ratio": 0.5
            }
        ]
        """;
    }

    @Test
    public void testLineParser() {
        List<Line> lines = parser.lineParser(testJson);

        assertNotNull(lines);
        assertEquals(2, lines.size());

        Line firstLine = lines.get(0);
        double length = firstLine.abLine();
        assertTrue(length > 0);
    }

    @Test
    public void testCircleParser() {
        List<Circle> circles = parser.circlesParser(testJson);

        assertNotNull(circles);
        assertEquals(1, circles.size());

        Circle circle = circles.get(0);
        assertEquals(50.0, circle.getPoint().getX());
        assertEquals(50.0, circle.getPoint().getY());
        assertEquals(25.0, circle.getRadius());

        double circumference = circle.circumference();
        assertTrue(circumference > 0);

        double curvature = circle.curvature();
        assertEquals(1.0 / 25.0, curvature, 0.0001);
    }

    @Test
    public void testArcParser() {
        List<Arc> arcs = parser.arcParser(testJson);

        assertNotNull(arcs);
        assertEquals(1, arcs.size());

        Arc arc = arcs.get(0);
        assertEquals(75.0, arc.getPoint().getX());
        assertEquals(75.0, arc.getPoint().getY());
        assertEquals(15.0, arc.getRadius());
        assertEquals(0.0, arc.getStartAngle());
        assertEquals(90.0, arc.getEndAngle());

        double arcLength = arc.arcLength();
        assertTrue(arcLength > 0);

        double curvature = arc.curvatuer();
        assertEquals(1.0 / 15.0, curvature, 0.0001);
    }

    @Test
    public void testEllipseParser() {
        List<Ellipse> ellipses = parser.ellipseParser(testJson);

        assertNotNull(ellipses);
        assertEquals(1, ellipses.size());

        Ellipse ellipse = ellipses.get(0);
        assertNotNull(ellipse);
    }

    @Test
    public void testParseStatistics() {
        EntityStatistics stats = parser.parseStatistics(testJson);

        assertNotNull(stats);

        int total = stats.totalEntities();
        assertEquals(5, total);

        double curveRatio = stats.curve();
        assertTrue(curveRatio > 0 && curveRatio <= 1.0);
        assertEquals(3.0 / 5.0, curveRatio, 0.0001);
    }

    @Test
    public void testEmptyJson() {
        String emptyJson = "[]";

        List<Line> lines = parser.lineParser(emptyJson);
        assertEquals(0, lines.size());

        Map<String, Integer> counts = parser.countEntities(emptyJson);
        assertTrue(counts.isEmpty());

        EntityStatistics stats = parser.parseStatistics(emptyJson);
        assertEquals(0, stats.totalEntities());
        assertEquals(0.0, stats.curve());
    }

    @Test
    public void testInvalidJson() {
        String invalidJson = "invalid json";

        assertThrows(IllegalArgumentException.class, () -> {
            parser.lineParser(invalidJson);
        });
    }

    @Test
    public void testLineCalculation() {
        String lineJson = """
        [
            {
                "type": "LINE",
                "start": [0.0, 0.0],
                "end": [3.0, 4.0]
            }
        ]
        """;

        List<Line> lines = parser.lineParser(lineJson);
        assertEquals(1, lines.size());

        double length = lines.get(0).abLine();
        assertEquals(5.0, length, 0.0001);
    }
}
