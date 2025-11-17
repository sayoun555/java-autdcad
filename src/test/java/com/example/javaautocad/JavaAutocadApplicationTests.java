package com.example.javaautocad;

import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.domain.Point;
import com.example.javaautocad.AutoCad.dto.LineStatisticsDto;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaAutocadApplicationTests {
    @Test
    void lineStatistics_basicCalculation() {
        List<Line> lines = List.of(
                new Line(new Point(0, 0), new Point(1, 0)),
                new Line(new Point(0, 0), new Point(2, 0)),
                new Line(new Point(0, 0), new Point(3, 0))
        );
        Lines linesDomain = new Lines(lines);
        LineStatisticsDto dto = linesDomain.lineDelivery();
        assertEquals(2.0, dto.getLineAverage(), 1e-9);
        assertEquals(2.0 / 3.0, dto.getLineVariance(), 1e-9);
        assertEquals(Math.sqrt(2.0 / 3.0), dto.getLineStdDeviation(), 1e-9);
        assertTrue(dto.isValid());
    }

}
