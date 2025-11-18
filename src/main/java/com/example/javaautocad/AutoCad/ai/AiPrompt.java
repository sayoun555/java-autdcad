package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.dto.*;

public class AiPrompt {
    private final String CONTEXT_MAIN = "[CONTEXT]";
    private final String CONTEXT = """
            당신은 오토바이 타이어 설계 엔지니어입니다.
            존재하지 않는 수치를 생성하거나 임의로 반지름·곡률·개수를 조정하지 마세요.
            개선안은 반드시 입력된 통계(STATISTICS) 또는 DXF 엔티티 구조(DXF CONTENT)에 직접 근거해 작성하세요.
            입력된 통계(STATISTICS)와 도면 DXF 구조(DXF CONTENT)를 기반으로
            타이어 단면 프로파일 곡률(Crown–Shoulder–Sidewall), 대칭성, 곡률 전이,
            내부 구조(Construction), 코너링 안정성, 접지 형상 변화 가능성의 관점에서
            정량적·기하학적 분석을 수행하세요.
            개선안의 수치는 입력된 통계값에서 직접 계산 가능한 값만 사용하세요.
            예: 평균, 범위, 편차 기반 보정, 비율 변경 등의 형태만 허용합니다.
            개선안은 실존하지 않는 숫자를 만들어내지 마세요.
            수치는 반드시 STATISTICS 내 실제 값 또는 그로부터 계산 가능한 값만 사용하세요.
            예측·추정·가상의 반지름/곡률/개수는 금지합니다.
            분석은 구조적·비교적·비율적 근거만 사용하세요.
            """;
    private final String STATISTICS = "[STATISTICS]";
    private final String DXF_CONTENT_DOMAIN = "[DXF CONTENT]";
    private final String EMPTY = "\n";
    private final String STANDARD = """
            1. 프로파일 곡률(곡률 평균·편차·전이)의 균형성
            2. 요소별 기하학적 통계값의 부정합(분산·표준편차·균일도)
            3. Arc/Circle/Ellipse 기반 곡률 프로파일의 연속성
            4. Line 기반 직선성·형상 안정성
            5. 도면 엔티티 분포(Line/Arc/Ellipse 비율)에 의한 구조적 성격
            6. 좌우 대칭 여부
            그리고 “추상적 목표 제시 금지”, 반드시 \s
            정확한 수치 기반 개선안(반경 조정, 분포 개선, 프로파일 R 보정 등)을 \s
            최소 4개 이상 제시하세요.""";
    private final String CONTEXT_DXF = "아래는 도면의 JSON 일부입니다. 구조를 참고하여 판단하세요.";
    private final String VALIDATION_CI = "circle 데이터 신뢰도 낮음 → circle 기반 분석은 보수적으로 수행";
    private final String VALIDATION_ELL = "ellipse 데이터 신뢰도 낮음 → ellipse 기반 분석은 참고용으로 제한";
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
        if (!circleStatisticsDto.isValid()) {
            stringBuilder.append(VALIDATION_CI);
        }
        if (ellipseStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[타원(Ellipse) 통계]").append(EMPTY);
            stringBuilder.append("평균 장축 반지름: ").append(ellipseStatisticsDto.getAverageMajorRadius()).append(EMPTY);
            stringBuilder.append("평균 단축 반지름: ").append(ellipseStatisticsDto.getAverageMinorRadius()).append(EMPTY);
            stringBuilder.append("평균 편심률: ").append(ellipseStatisticsDto.getAverageEccentricity()).append(EMPTY);
            stringBuilder.append("평균 곡률: ").append(ellipseStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("개수: ").append(ellipseStatisticsDto.getCount()).append(EMPTY);
        }
        if (!ellipseStatisticsDto.isValid()) {
            stringBuilder.append(VALIDATION_ELL);
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
        stringBuilder.append(newFile);
        return stringBuilder.toString();
    }
}
