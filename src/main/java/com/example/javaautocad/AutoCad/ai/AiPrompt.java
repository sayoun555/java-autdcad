package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.dto.StatisticsDto;

public class AiPrompt {
    private final String CONTEXT_MAIN = "[CONTEXT]";
    private final String CONTEXT = "당신은 오토바이 타이어 전문가입니다.\n통계를 기반으로 설계 균형과 안정성을 평가하세요. 및 설계 개선을 보조하세요";
    private final String STATISTICS = "[STATISTICS]";
    private final String DXF_CONTENT_DOMAIN = "[DXF CONTENT]";
    private final String EMPTY = "\n";
    private final String STANDARD = "결과는 설계의 프로파일 곡률, 내부 구조(Construction), 안정성, 대칭성, 코너링 적합성을 기준으로 분석하고,\n" +
            "필요시 개선 방안을 3가지 이상으로 제시하세요";
    private final String CONTEXT_DXF = "아래는 도면의 JSON 일부입니다. 구조를 참고하여 판단하세요.";
    private final String VALIDATION = "데이터 유효성 낮음—보수적으로 판단";
    private final String ENTITIES = "ENTITIES 우선";

    public String aiPromptBuilder(String newFile, StatisticsDto statisticsDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTEXT_MAIN).append(EMPTY);
        stringBuilder.append(CONTEXT).append(EMPTY);
        stringBuilder.append(STANDARD).append(EMPTY);
        stringBuilder.append(STATISTICS).append(EMPTY);
        stringBuilder.append("평균: ").append(statisticsDto.getLineAverage()).append(EMPTY);
        stringBuilder.append("분산: ").append(statisticsDto.getLineVariance()).append(EMPTY);
        stringBuilder.append("표준편차: ").append(statisticsDto.getLineStdDeviation()).append(EMPTY);
        stringBuilder.append("균일도: ").append(statisticsDto.getLineHomogeneity()).append(EMPTY);
        stringBuilder.append("유효성: ").append(statisticsDto.isValid()).append(EMPTY);
        if (!statisticsDto.isValid()) {
            stringBuilder.append(VALIDATION).append(EMPTY);
        }
        stringBuilder.append(DXF_CONTENT_DOMAIN).append(EMPTY);
        stringBuilder.append(CONTEXT_DXF).append(EMPTY);
        stringBuilder.append(ENTITIES).append(EMPTY);
        int limit = Math.min(6000, newFile.length());
        stringBuilder.append(newFile, 0, limit);
        return stringBuilder.toString();
    }
}
