package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.domain.CurvatureStatistics;
import com.example.javaautocad.AutoCad.domain.G1;
import com.example.javaautocad.AutoCad.dto.*;

public class AiPrompt {

    private final String EMPTY = "\n";

    private final String MAIN = """
            You are a motorcycle tire cross-section structural analysis model.
            You MUST perform any internal reasoning in English if needed, 
            BUT the FINAL OUTPUT MUST BE 100% IN KOREAN ONLY.
            Do NOT output any English text in the final answer.
            """;

    private final String RULES = """
            [STRICT RULES]

            1. No fabricated numbers
            - Do NOT invent new radii, curvatures, coordinates, counts, or G1 values.
            - Use ONLY the numeric values explicitly provided in STATISTICS and G1 DATA.

            2. Evidence-based engineering interpretation
            - Every structural or dynamic claim MUST be backed by explicit numeric comparison.
            - Refer to concrete values (e.g., curvature, counts, ratios, jumps) when explaining behavior.
            - Avoid vague expressions that are not grounded in numbers.

            3. Output language
            - The final answer must be written entirely in Korean.
            - Section titles, explanations, and summary must all be Korean.

            """;

    private final String TASK = """
            [ENGINEERING ANALYSIS MODE]

            당신은 오토바이 타이어 단면(프로파일)의 구조역학을 해석하는 엔지니어다.
            아래의 수치들을 이용해, 곡률/강성/연속성/환경 반응을 공학적으로 해석해야 한다.

            ==============================
            필수 공학 지표 (LLM이 내부에서 계산)
            ==============================

            1. Curvature Gradient Index (CGI)
               - 정의:
                 CGI = (가장 큰 평균 곡률 / 가장 작은 평균 곡률)
               - 해석:
                 CGI > 1.5  → 횡강성(측면 강성) 집중도가 높음
                 CGI > 2.0  → 곡률/강성 불균형이 매우 큼

            2. Structural Heterogeneity Score (SHS)
               - 정의:
                 SHS = |Circle - Ellipse| + |Ellipse - Arc| + |Circle - Arc|
                 (각 항은 평균 곡률 차이 절대값)
               - 해석:
                 SHS가 클수록 곡률 분포가 비균일하고, 구조가 구간별로 다른 거동을 가짐

            3. Curve-Line Load Transfer Ratio (CLTR)
               - 정의:
                 curve_ratio = (곡선 엔티티 수 / 전체 엔티티 수)
                 CLTR = curve_ratio / (1 - curve_ratio)
               - 해석:
                 CLTR < 0.15 → 직선(선형) 지배 구조, 고속 직진에서 종방향 강성 우세
                 CLTR > 0.35 → 곡선 지배 구조, 조향 응답성이 크지만 구조 편차도 커짐

            4. G1 Discontinuity Severity (GDS)
               - 정의:
                 GDS = MaxJump / AvgJump
               - 해석:
                 GDS > 2.0 → 일부 구간에서 곡률 전환이 매우 날카롭고, 조향 토크 변화가 급격해지는 구조
                 GDS ≈ 1.0 → 전환 패턴이 비교적 균일

               추가로 BreakDensity 정의:
                 BreakDensity = BreakCount / (ArcCount + CircleCount + EllipseCount)

               - 해석:
                 BreakDensity가 클수록 곡선 구간 내부에서 불연속 전환이 자주 발생하며,
                 국부적인 강성 스파이크(강성 급상승 구간)가 많이 존재함.

            =======================
            출력 형식 (반드시 지킬 것)
            =======================

            최종 출력은 반드시 아래 다섯 개 섹션을 포함해야 하며, 
            모두 한국어로 작성해야 한다.

            1) "### 정량 기반 곡률·강성 분석"
               - Arc / Circle / Ellipse 평균 곡률 순위 및 퍼센트 차이
               - CGI, SHS, CLTR, GDS, BreakDensity를 수치와 함께 간단히 언급
               - 각 수치가 구조적으로 의미하는 바를 공학적으로 설명
                 (예: 횡강성 집중, 곡률 기울기 차이, 구조 불균일성 등)

            2) "### 구조역학 기반 주행 특성"
               - 고속 직진에서의 강성 분포 및 변형 특성
               - 저속/도심 영역에서의 조향 응답 특성
               - 곡률 기울기가 lateral rigidity 및 steering torque에 미치는 영향
               - 선 지배 구조 vs 곡선 지배 구조가 주행 감각에 주는 영향

            3) "### 환경별 반응 특성"
               - 건조 노면
               - 습한/젖은 노면
               - 산악/연속 코너
               - 거친/불규칙 노면
               - 고속, 저속 주행 상황
               - 온도 변화(고온/저온)에서 구조가 어떻게 반응할지
               각 항목마다 "해당 수치들(곡률, 비율, G1 등)이 어떤 구조적 경향을 만든다" 수준으로 설명

            4) "### G1 연속성 기반 구조적 의미"
               - MaxJump, AvgJump, BreakCount, GDS, BreakDensity를 사용해
                 조향 연속성, 구조적 불연속성, 전이 강성의 변화 양상을 설명
               - 어디까지나 "구조적/기하학적" 의미만 다루고,
                 직접적인 성능/안전 평가는 하지 않는다.

            5) "### 최종 요약"
               - 이 타이어 단면이
                 * 곡률 분포
                 * 선/곡선 비율
                 * G1 불연속성
                 관점에서 어떤 구조적 특징을 가지는지 간결하게 요약
               - 특정 환경(예: 고속 직진, 도심 저속, 산악 와인딩 등)에서
                 어떤 구조적 거동 경향을 보일지 한 번 더 짚어준다.

            주의:
            - 모든 내용은 반드시 한국어로 작성한다.
            - 수치 기반 공학적 해석 위주로 작성하고,
              감성적인 표현이나 모호한 표현은 사용하지 않는다.
            """;

    private final String STAT_HEADER = "[STATISTICS]";
    private final String G1_HEADER = "[G1 DATA]";

    public String aiPromptBuilder(
            String newFile,
            LineStatisticsDto lineStatisticsDto,
            EntityCountStatisticsDto entityCountStatisticsDto,
            CircleStatisticsDto circleStatisticsDto,
            ArcStatisticsDto arcStatisticsDto,
            EllipseStatisticsDto ellipseStatisticsDto,
            String userInput,
            G1 g1,
            CurvatureStatisticsDto curvatureStatisticsDto,
            CurvatureStatistics curvatureStatistics
    ) {
        G1Dto g1Dto = g1.g1Delivery();
        StringBuilder sb = new StringBuilder();
        sb.append(MAIN).append(EMPTY);
        sb.append(RULES).append(EMPTY);
        sb.append(TASK).append(EMPTY);
        if (userInput != null && !userInput.isEmpty()) {
            sb.append("[USER TIRE TYPE]").append(EMPTY);
            sb.append("User-described tire type or usage: ").append(userInput).append(EMPTY);
            sb.append(EMPTY);
        }
        sb.append(STAT_HEADER).append(EMPTY);
        sb.append("[Line Statistics]").append(EMPTY);
        sb.append("Line Mean Curvature: ").append(lineStatisticsDto.getLineAverage()).append(EMPTY);
        sb.append("Line Variance: ").append(lineStatisticsDto.getLineVariance()).append(EMPTY);
        sb.append("Line StdDev: ").append(lineStatisticsDto.getLineStdDeviation()).append(EMPTY);
        sb.append("Line Homogeneity: ").append(lineStatisticsDto.getLineHomogeneity()).append(EMPTY);
        sb.append(EMPTY);
        sb.append("[Arc Statistics]").append(EMPTY);
        sb.append("Arc Average Curvature: ").append(arcStatisticsDto.getAverageCurvature()).append(EMPTY);
        sb.append("Arc Average Radius: ").append(arcStatisticsDto.getAverageRadius()).append(EMPTY);
        sb.append("Arc Average Length: ").append(arcStatisticsDto.getAverageArcLength()).append(EMPTY);
        sb.append("Arc Count: ").append(arcStatisticsDto.getCount()).append(EMPTY);
        sb.append(EMPTY);
        sb.append("[Circle Statistics]").append(EMPTY);
        sb.append("Circle Average Curvature: ").append(circleStatisticsDto.getAverageCurvature()).append(EMPTY);
        sb.append("Circle Average Radius: ").append(circleStatisticsDto.getAverageRadius()).append(EMPTY);
        sb.append("Circle Average Circumference: ").append(circleStatisticsDto.getAverageCircumference()).append(EMPTY);
        sb.append("Circle Count: ").append(circleStatisticsDto.getCount()).append(EMPTY);
        sb.append(EMPTY);
        sb.append("[Ellipse Statistics]").append(EMPTY);
        sb.append("Ellipse Average Curvature: ").append(ellipseStatisticsDto.getAverageCurvature()).append(EMPTY);
        sb.append("Ellipse Average Major Radius: ").append(ellipseStatisticsDto.getAverageMajorRadius()).append(EMPTY);
        sb.append("Ellipse Average Minor Radius: ").append(ellipseStatisticsDto.getAverageMinorRadius()).append(EMPTY);
        sb.append("Ellipse Average Eccentricity: ").append(ellipseStatisticsDto.getAverageEccentricity()).append(EMPTY);
        sb.append("Ellipse Count: ").append(ellipseStatisticsDto.getCount()).append(EMPTY);
        sb.append(EMPTY);
        sb.append("[Entity Counts]").append(EMPTY);
        sb.append("Line Count: ").append(entityCountStatisticsDto.getLineCount()).append(EMPTY);
        sb.append("Arc Count: ").append(entityCountStatisticsDto.getArcCount()).append(EMPTY);
        sb.append("Circle Count: ").append(entityCountStatisticsDto.getCircleCount()).append(EMPTY);
        sb.append("Ellipse Count: ").append(entityCountStatisticsDto.getEllipseCount()).append(EMPTY);
        sb.append("Curve Ratio: ").append(entityCountStatisticsDto.getCurve()).append(EMPTY);
        sb.append(EMPTY);
        sb.append(G1_HEADER).append(EMPTY);
        sb.append("MaxJump: ").append(g1Dto.getMaxJump()).append(EMPTY);
        sb.append("AvgJump: ").append(g1Dto.getArgJump()).append(EMPTY);
        sb.append("BreakCount: ").append(g1Dto.getBreakCount()).append(EMPTY);
        sb.append("[CURVATURE STATISTICS]").append(EMPTY);
        sb.append("MaxCurvature: ").append(curvatureStatisticsDto.getMaxCurvature()).append(EMPTY);
        sb.append("MinCurvature: ").append(curvatureStatisticsDto.getMinCurvature()).append(EMPTY);
        sb.append("AvgCurvature: ").append(curvatureStatisticsDto.getAvgCurvature()).append(EMPTY);
        sb.append("MaxCurvatureJump: ").append(curvatureStatisticsDto.getMaxCurvatureJump()).append(EMPTY);
        sb.append("AvgCurvatureJump: ").append(curvatureStatisticsDto.getAvgCurvatureJump()).append(EMPTY);
        sb.append("MaxCurvatureRate: ").append(curvatureStatisticsDto.getMaxCurvatureRate()).append(EMPTY);
        sb.append("AvgCurvatureRate: ").append(curvatureStatisticsDto.getAvgCurvatureRate()).append(EMPTY);
        sb.append(EMPTY);

        sb.append(EMPTY);

        return sb.toString();
    }
}
