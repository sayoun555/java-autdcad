package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.config.EnvConfig;
import com.example.javaautocad.AutoCad.domain.EntityStatistics;
import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.dto.AiDto;
import com.example.javaautocad.AutoCad.dto.LineStatisticsDto;
import com.example.javaautocad.AutoCad.dto.MessageDto;
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
    private String fileResult;
    private final EnvConfig envConfig;


    public AutoAi(AutoParser autoParser, EnvConfig envConfig) {
        this.aiClient = new AiClient();
        this.autoParser = autoParser;
        this.envConfig = envConfig;
    }

    private void fileIo(Path path) {
        try {
            fileResult = Files.readString(path);
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.FILE_ERROR.getMessage());
        }
    }

    public EntityStatistics entityParser() {
        return new EntityStatistics(autoParser.)
    }

    private Lines parser() {
        return new Lines(autoParser.lineParser(fileResult));
    }

    private String promptBuild() {
        AiPrompt aiPrompt = new AiPrompt();
        return aiPrompt.aiPromptBuilder(fileResult, parser().lineDelivery());
    }


    public String analyze(Path path) {
        fileIo(path);
        List<MessageDto> messageDtos = new ArrayList<>();
        messageDtos.add(new MessageDto(USER, promptBuild()));
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
