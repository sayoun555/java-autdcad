package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.config.EnvConfig;
import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.dto.AiDto;
import com.example.javaautocad.AutoCad.dto.MessageDto;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.parser.AutoParser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AutoAi {
    private final String KEY = new EnvConfig().getApiKey();
    private final AiClient aiClient;
    private final AutoParser autoParser;
    private String fileResult;


    public AutoAi(AutoParser autoParser) {
        this.aiClient = new AiClient();
        this.autoParser = autoParser;
    }

    private void fileIo(Path path) {
        try {
            fileResult =  Files.readString(path);
        }catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.FILE_ERROR.getMessage());
        }
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
        messageDtos.add(new MessageDto("user", promptBuild()));
        AiDto aiDto = new AiDto(messageDtos, 0.7);
        return aiClient.aiParser(this, aiDto);
    }

    public String keyMapping() {
        if (KEY == null || KEY.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.KEY_ERROR.getMessage());
        }
        return KEY;
    }
}
