package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.config.EnvConfig;
import com.example.javaautocad.AutoCad.config.LayerConfig;
import com.example.javaautocad.AutoCad.domain.*;
import com.example.javaautocad.AutoCad.dto.*;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.parser.AutoParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AutoAi {
    private final String USER = "user";
    private final AiClient aiClient;
    private final AutoParser autoParser;
    private final EnvConfig envConfig;
    private String userInput;

    public AutoAi(AutoParser autoParser, EnvConfig envConfig) {
        this.aiClient = new AiClient();
        this.autoParser = autoParser;
        this.envConfig = envConfig;
    }

    public void tireType(String type) {
        this.userInput = type;
    }

    private String fileIo(Path path) {
        try {
            return Files.readString(path);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.FILE_ERROR.getMessage());
        }
    }

    private Lines lineParser(String fileResult) {
        Lines allLines = new Lines(autoParser.lineParser(fileResult));
        LayerFilter filter = new LayerFilter(LayerConfig.getTargetLayer());
        return allLines.filter(filter);
    }

    private Arcs arcParser(String fileResult) {
        Arcs allArcs = new Arcs(autoParser.arcParser(fileResult));
        LayerFilter filter = new LayerFilter(LayerConfig.getTargetLayer());
        return allArcs.filter(filter);
    }

    private Circles circleParser(String fileResult) {
        Circles allCircles = new Circles(autoParser.circlesParser(fileResult));
        LayerFilter filter = new LayerFilter(LayerConfig.getTargetLayer());
        return allCircles.filter(filter);
    }

    private Ellipses ellipseParser(String fileResult) {
        Ellipses allEllipses = new Ellipses(autoParser.ellipseParser(fileResult));
        LayerFilter filter = new LayerFilter(LayerConfig.getTargetLayer());
        return allEllipses.filter(filter);
    }

    private EntityStatistics entityParser(String fileResult) {
        return autoParser.parseStatistics(fileResult);
    }

    public G1 mixG1(Lines lines, Arcs arcs) {
        List<Object> list = new ArrayList<>();
        list.addAll(lines.getLineList());
        list.addAll(arcs.getArcs());
        G1s g1s = new G1s(list);
        return new G1(g1s);
    }

    public CurvatureStatistics mixCurvature(Lines lines, Arcs arcs) {
        List<Object> list = new ArrayList<>();
        list.addAll(lines.getLineList());
        list.addAll(arcs.getArcs());
        G1s g1s = new G1s(list);
        return new CurvatureStatistics(g1s);
    }

    private String promptBuild(String fileResult) {
        AiPrompt aiPrompt = new AiPrompt();
        Lines lines = lineParser(fileResult);
        Arcs arcs = arcParser(fileResult);
        LineStatisticsDto lineStats = lines.lineDelivery();
        ArcStatisticsDto arcStats = arcs.aecDelivery();
        CircleStatisticsDto circleStats = circleParser(fileResult).ciseclsDelivery();
        EllipseStatisticsDto ellipseStats = ellipseParser(fileResult).ellipesDelivery();
        EntityCountStatisticsDto entityStats = entityParser(fileResult).entityDelivery();
        G1 g1 = mixG1(lines, arcs);
        CurvatureStatistics curvatureStatistics = mixCurvature(lines, arcs);
        CurvatureStatisticsDto curvatureStatisticsDto = curvatureStatistics.curvatureDelivery();
        return aiPrompt.aiPromptBuilder(
                fileResult,
                lineStats,
                entityStats,
                circleStats,
                arcStats,
                ellipseStats,
                userInput,
                g1,
                curvatureStatisticsDto,
                curvatureStatistics
        );
    }

    public String analyze(Path path) {
        String fileResult = fileIo(path);
        List<MessageDto> messageDtos = new ArrayList<>();
        messageDtos.add(new MessageDto(USER, promptBuild(fileResult)));
        AiDto aiDto = new AiDto(messageDtos, 0.7);
        return aiClient.aiParser(this, aiDto);
    }

    public String keyMapping() {
        String key = envConfig.getApiKey();
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.KEY_ERROR.getMessage());
        }
        return key;
    }
}