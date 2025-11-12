package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.domain.Lines;
import com.example.javaautocad.AutoCad.dto.StatisticsDto;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.parser.AutoParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class AutoAi {
    private final String KEY = System.getenv("API_KEY");
    private final String CONTEXT_MAIN = "[CONTEXT]";
    private final String CONTEXT = "당신은 오토바이 타이어 전문가입니다.\n통계를 기반으로 설계 균형과 안정성을 평가하세요. 및 보조해주세요";
    private final String STATISTICS = "[STATISTICS]";
    private final String DXF_CONTENT = "[DXF CONTENT]";
    private final String EMPTY = "\n";


    public String aiPrompt(String newFile, StatisticsDto statisticsDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTEXT_MAIN).append(EMPTY);
        stringBuilder.append(CONTEXT).append(EMPTY);
        stringBuilder.append(STATISTICS).append(EMPTY);
        stringBuilder.append("평균: ").append(statisticsDto.getLineAverage()).append(EMPTY);
        stringBuilder.append("분산: ").append(statisticsDto.getLineVariance()).append(EMPTY);
        stringBuilder.append("표준편차: ").append(statisticsDto.getLineStdDeviation()).append(EMPTY);
        stringBuilder.append("균일도: ").append(statisticsDto.getLineHomogeneity()).append(EMPTY);
        stringBuilder.append("유효성: ").append(statisticsDto.isValid()).append(EMPTY);
        stringBuilder.append(DXF_CONTENT).append(EMPTY);
        int limit = Math.min(6000, newFile.length());
        stringBuilder.append(newFile, 0, limit);
        return stringBuilder.toString();
    }

    public String analyze(Path file) {
        try {
            AutoParser autoParser = new AutoParser();
            String newFile = Files.readString(file);
            Lines lines = new Lines(autoParser.lineParser(newFile));
            return aiPrompt(newFile, lines.lineDelivery());
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public void keyMapping() {
        if (KEY == null || KEY.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.KEY_ERROR.getMessage());
        }
    }
}
