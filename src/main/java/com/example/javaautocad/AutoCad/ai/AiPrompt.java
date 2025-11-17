package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.dto.*;

public class AiPrompt {
    private final String CONTEXT_MAIN = "[CONTEXT]";
    private final String CONTEXT = "당신은 오토바이 타이어 전문가입니다.\n통계를 기반으로 설계 균형과 안정성을 평가하세요. 및 설계 개선을 보조하세요";
    private final String STATISTICS = "[STATISTICS]";
    private final String DXF_CONTENT_DOMAIN = "[DXF CONTENT]";
    private final String EMPTY = "\n";
    private final String STANDARD = "결과는 설계의 프로파일 곡률, 내부 구조(Construction), 안정성, 대칭성, 코너링 적합성을 기준으로 분석하고,\n" +
            "필요시 개선 방안 수치와 개선 방안을 3가지 이상 제시하세요";
    private final String CONTEXT_DXF = "아래는 도면의 JSON 일부입니다. 구조를 참고하여 판단하세요.";
    private final String VALIDATION = "데이터 유효성 낮음—보수적으로 판단";
    private final String ENTITIES = "ENTITIES 우선";

    public String aiPromptBuilder(String newFile,
                                  LineStatisticsDto lineStatisticsDto,
                                  EntityCountStatisticsDto entityCountStatisticsDto,
                                  CircleStatisticsDto circleStatisticsDto,
                                  ArcStatisticsDto arcStatisticsDto,
                                  EllipseStatisticsDto ellipseStatisticsDto) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(CONTEXT_MAIN).append(EMPTY);
        stringBuilder.append(CONTEXT).append(EMPTY);
        stringBuilder.append(STANDARD).append(EMPTY);
        stringBuilder.append(STATISTICS).append(EMPTY);
        if (lineStatisticsDto.isValid()) {
            stringBuilder.append("평균: ").append(lineStatisticsDto.getLineAverage()).append(EMPTY);
            stringBuilder.append("분산: ").append(lineStatisticsDto.getLineVariance()).append(EMPTY);
            stringBuilder.append("표준편차: ").append(lineStatisticsDto.getLineStdDeviation()).append(EMPTY);
            stringBuilder.append("균일도: ").append(lineStatisticsDto.getLineHomogeneity()).append(EMPTY);
            stringBuilder.append("유효성: ").append(lineStatisticsDto.isValid()).append(EMPTY);
        }
        if (arcStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[호(Arc) 통계]").append(EMPTY);
            stringBuilder.append("평균 반지름: ").append(arcStatisticsDto.getAverageRadius()).append(EMPTY);
            stringBuilder.append("평균 곡률: ").append(arcStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("평균 호 길이: ").append(arcStatisticsDto.getAverageArcLength()).append(EMPTY);
            stringBuilder.append("개수: ").append(arcStatisticsDto.getCount()).append(EMPTY);
        }

        if (circleStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[원(Circle) 통계]").append(EMPTY);
            stringBuilder.append("평균 반지름: ").append(circleStatisticsDto.getAverageRadius()).append(EMPTY);
            stringBuilder.append("평균 곡률: ").append(circleStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("평균 둘레: ").append(circleStatisticsDto.getAverageCircumference()).append(EMPTY);
            stringBuilder.append("개수: ").append(circleStatisticsDto.getCount()).append(EMPTY);
        }

        if (ellipseStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[타원(Ellipse) 통계]").append(EMPTY);
            stringBuilder.append("평균 장축 반지름: ").append(ellipseStatisticsDto.getAverageMajorRadius()).append(EMPTY);
            stringBuilder.append("평균 단축 반지름: ").append(ellipseStatisticsDto.getAverageMinorRadius()).append(EMPTY);
            stringBuilder.append("평균 편심률: ").append(ellipseStatisticsDto.getAverageEccentricity()).append(EMPTY);
            stringBuilder.append("평균 곡률: ").append(ellipseStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("개수: ").append(ellipseStatisticsDto.getCount()).append(EMPTY);
        }
        stringBuilder.append(EMPTY).append("[엔티티 개수]").append(EMPTY);
        stringBuilder.append("Line: ").append(entityCountStatisticsDto.getLineCount()).append(EMPTY);
        stringBuilder.append("Arc: ").append(entityCountStatisticsDto.getArcCount()).append(EMPTY);
        stringBuilder.append("Circle: ").append(entityCountStatisticsDto.getCircleCount()).append(EMPTY);
        stringBuilder.append("Ellipse: ").append(entityCountStatisticsDto.getEllipseCount()).append(EMPTY);
        stringBuilder.append("곡선 비율: ").append(entityCountStatisticsDto.getCurve()).append(EMPTY);

        stringBuilder.append(DXF_CONTENT_DOMAIN).append(EMPTY);
        stringBuilder.append(CONTEXT_DXF).append(EMPTY);
        stringBuilder.append(ENTITIES).append(EMPTY);

        int limit = Math.min(10000, newFile.length());
        stringBuilder.append(newFile, 0, limit);
        return stringBuilder.toString();
    }
}
