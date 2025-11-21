package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.config.EnvConfig;
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
        return new Lines(autoParser.lineParser(fileResult));
    }

    private Arcs arcParser(String fileResult) {
        return new Arcs(autoParser.arcParser(fileResult));
    }

    private Circles circleParser(String fileResult) {
        return new Circles(autoParser.circlesParser(fileResult));
    }

    private Ellipses ellipseParser(String fileResult) {
        return new Ellipses(autoParser.ellipseParser(fileResult));
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

    private String promptBuild(String fileResult) {
        AiPrompt aiPrompt = new AiPrompt();
        Lines lines = lineParser(fileResult);
        Arcs arcs = arcParser(fileResult);
        LineStatisticsDto lineStats = lineParser(fileResult).lineDelivery();
        ArcStatisticsDto arcStats = arcParser(fileResult).aecDelivery();
        CircleStatisticsDto circleStats = circleParser(fileResult).ciseclsDelivery();
        EllipseStatisticsDto ellipseStats = ellipseParser(fileResult).ellipesDelivery();
        EntityCountStatisticsDto entityStats = entityParser(fileResult).entityDelivery();
        G1 g1 = mixG1(lines, arcs);
        return aiPrompt.aiPromptBuilder(
                fileResult,
                lineStats,
                entityStats,
                circleStats,
                arcStats,
                ellipseStats,
                userInput,
                g1
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
