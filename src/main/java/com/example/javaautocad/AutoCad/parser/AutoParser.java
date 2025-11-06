package com.example.javaautocad.AutoCad.parser;

import com.example.javaautocad.AutoCad.domain.Line;

import java.util.ArrayList;
import java.util.List;

import com.example.javaautocad.AutoCad.domain.Point;
import org.kabeja.dxf.DXFDocument;
import org.kabeja.dxf.DXFEntity;
import org.kabeja.dxf.DXFLine;
import org.kabeja.parser.DXFParser;
import org.kabeja.parser.SAXParserBuilder;

import javax.swing.text.html.parser.DocumentParser;
import javax.swing.text.html.parser.Parser;

public class AutoParser {
    private final String LINE = "LINE";

    public List<Line> lineParser(String file) {
        return new ArrayList<>();
    }

    public DXFDocument dxfParser(String file) {
        try {
            DXFParser parser = new DXFParser();
            parser.parse(file, "UTF-8");
            return parser.getDocument();
        } catch (org.kabeja.parser.ParseException e) {
            throw new IllegalArgumentException("DXF 파싱 실패: " + file, e);
        }
    }

    public List<Line> docsParser(String file, String name) {
        DXFDocument dxfDocument = dxfParser(file);
        List<Line> lines = new ArrayList<>();
        List<DXFEntity> dxfEntities = dxfDocument.getDXFLayer(name).getDXFEntities(LINE);
        for (int i = 0; i < dxfEntities.size(); i++) {
            DXFLine dxfLine = (DXFLine) dxfEntities.get(i);
            Point pointStart = new Point(dxfLine.getStartPoint().getX(), dxfLine.getStartPoint().getY());
            Point pointEnd = new Point(dxfLine.getEndPoint().getX(), dxfLine.getEndPoint().getY());
            Line line = new Line(pointStart, pointEnd);
            lines.add(line);
        }
            return lines;
    }
}
