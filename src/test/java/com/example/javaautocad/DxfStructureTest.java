package com.example.javaautocad;

import org.junit.jupiter.api.Test;
import org.kabeja.dxf.*;
import org.kabeja.parser.DXFParser;

import java.util.Iterator;

public class DxfStructureTest {

    @Test
    void printStructure() throws Exception {
        String path = "/Users/sanghyunyoun/springtest/testfile.dxf";

        DXFParser parser = new DXFParser();
        parser.parse(path, "UTF-8");
        DXFDocument document = parser.getDocument();

        System.out.println("===== DXF 구조 탐색 시작 =====");
        Iterator<?> layerIterator = document.getDXFLayerIterator();
        while (layerIterator.hasNext()) {
            DXFLayer layer = (DXFLayer) layerIterator.next();
            System.out.println("레이어 이름: " + layer.getName());

            Iterator<?> typeIterator = layer.getDXFEntityTypeIterator();
            while (typeIterator.hasNext()) {
                String type = (String) typeIterator.next();
                int count = layer.getDXFEntities(type).size();
                System.out.println("  └── 엔티티 타입: " + type + " (개수: " + count + ")");
            }
        }
        System.out.println("===== DXF 구조 탐색 완료 =====");
    }
}
