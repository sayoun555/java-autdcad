package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.dto.AiDto;
import com.example.javaautocad.AutoCad.dto.MessageDto;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.parser.AutoParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class AutoAi {
    private final String KEY = System.getenv("API_KEY");
    private final AiClient aiClient;
    private final AutoParser autoParser;

    public AutoAi(AutoParser autoParser) {
        this.aiClient = new AiClient();
        this.autoParser = autoParser;
    }

    public String analyze(Path file) {
        try {
            List<MessageDto> messageDtos = new ArrayList<>();
            AiPrompt aiPrompt = new AiPrompt();
            String newFile = Files.readString(file);
            Lines lines = new Lines(autoParser.lineParser(newFile));
            String prompt = aiPrompt.aiPromptBuilder(newFile, lines.lineDelivery());
            messageDtos.add(new MessageDto("user", prompt));
            AiDto aiDto = new AiDto(messageDtos, 0.7);
            return aiClient.aiParser(this, aiDto);
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public String keyMapping() {
        if (KEY == null || KEY.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.KEY_ERROR.getMessage());
        }
        return KEY;
    }
}
