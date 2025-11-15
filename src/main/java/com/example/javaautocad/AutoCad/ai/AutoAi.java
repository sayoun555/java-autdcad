package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.parser.AutoParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutoAi {
    private final String KEY = System.getenv("API_KEY");

    public String analyze(Path file) {
        try {
            AiPrompt aiPrompt = new AiPrompt();
            AutoParser autoParser = new AutoParser();
            String newFile = Files.readString(file);
            Lines lines = new Lines(autoParser.lineParser(newFile));
            return aiPrompt.aiPromptBuilder(newFile, lines.lineDelivery());
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
