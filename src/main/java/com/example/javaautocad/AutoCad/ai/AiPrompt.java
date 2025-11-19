package com.example.javaautocad.AutoCad.ai;

import com.example.javaautocad.AutoCad.dto.*;

public class AiPrompt {

    private final String CONTEXT_MAIN = "[CONTEXT]";

    private final String CONTEXT = """
            You are a data-driven motorcycle tire cross-section analysis engineer.
            
            Your analysis must strictly follow the two principles below.
            
            Principle 1 — No external numerical fabrication
            • Do not create any numbers that do not exist in the provided data.
            • No invented radius, curvature, angles, counts, proportions, or any new values.
            • Any form of guessing, assuming, estimating, predicting, or extrapolating numerical values is strictly forbidden.
            
            Principle 2 — Evidence-based interpretation only
            • All judgments must be directly derived from the existing numbers in STATISTICS or DXF entities.
            • Terms like “higher / lower / insufficient / imbalanced” may only be used if you clearly show
              which numbers were compared and how they support the conclusion.
            • If evidence is insufficient, explicitly state “Unable to determine” or “Additional data required.”
            • Your role is to interpret patterns implied by the data, not to create new problems.
            • DXF JSON contains partial entity-level geometry. Do NOT infer or reconstruct the full profile shape.
            """;

    private final String STANDARD = """
            You analyze only the items listed below.
            All analysis must be strictly pattern-based and numerical-evidence-based.
            
            1. Compare curvature distributions (mean, deviation, range) across entity types.
            2. Compare curvature magnitude among Arc / Circle / Ellipse.
            3. Describe structural tendencies implied by the ratio of Lines vs Curves.
            4. Identify only local patterns. (No target specification or ideal-value suggestions.)
            
            Forbidden:
            • No prescriptive statements (e.g., “should be larger,” “must be reduced”).
            • No design parameter changes (e.g., modifying curvature, adding arcs).
            • No prediction, guessing, or inference of new profile shapes, coordinates, radii, or curvatures.
            • No reconstruction of the crown, shoulder, or sidewall geometry.
            • No filling missing data with fabricated numbers.
            
            Safety & Cornering Tendency Interpretation (Data-Limited)
            You may perform only the following simple interpretations, strictly based on the
            numeric differences present in STATISTICS:
            
            1. Overall curvature tendency
            - Compare average curvature among Arc / Circle / Ellipse.
            - Identify whether the profile appears numerically tighter or flatter.
            - No reconstruction and no zone-level inference.
            
            2. Structural uniformity
            - If average curvatures differ significantly, describe whether the curvature
            distribution appears consistent or highly varied.
            
            3. Curve-to-line ratio
             - Use only the curved-entity ratio to describe whether the structure is more
             curve-dominant or line-dominant.
             - No flat-zone or shoulder inference.
            
             4. Basic safety implication (allowed)
             - You may describe general stability tendencies such as “more uniform,”
             “more varied,” “tighter curvature segments,” strictly when directly supported
             by the numeric differences.
            
            5. Cornering tendency (allowed)
            - Higher curvature → may imply a steeper general tendency.
            - Lower curvature → may imply a flatter general tendency.
            - No performance prediction allowed.
            
            Strict Prohibitions:
            • No left/right symmetry inference.
            • No G1 continuity analysis.
            • No shoulder-zone interpretation.
            • No transition-zone interpretation.
            • No maximum/minimum/range inference.
            • No request for additional data.
            • Do not mention missing values.
            """;

    private final String STATISTICS = "[STATISTICS]";
    private final String DXF_CONTENT_DOMAIN = "[DXF CONTENT]";
    private final String CONTEXT_DXF = """
            The following DXF JSON fragment is provided only as structural reference.
            Do not infer the overall tire profile shape from it.
            """;
    private final String VALIDATION_CI = "Circle data unreliable → Interpret Circle-based results conservatively.\n";
    private final String VALIDATION_ELL = "Ellipse data unreliable → Ellipse-based observations should remain tentative.\n";
    private final String ENTITIES = "Prioritize ENTITIES-level observations.\n";
    private final String FINAL_OUTPUT = """
            After completing the analysis, write the final answer in Korean.
            """;

    private final String EMPTY = "\n";

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
            stringBuilder.append("Line Mean: ").append(lineStatisticsDto.getLineAverage()).append(EMPTY);
            stringBuilder.append("Line Variance: ").append(lineStatisticsDto.getLineVariance()).append(EMPTY);
            stringBuilder.append("Line StdDev: ").append(lineStatisticsDto.getLineStdDeviation()).append(EMPTY);
            stringBuilder.append("Line Homogeneity: ").append(lineStatisticsDto.getLineHomogeneity()).append(EMPTY);
            stringBuilder.append("Valid: ").append(lineStatisticsDto.isValid()).append(EMPTY);
        }

        if (arcStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[Arc Statistics]").append(EMPTY);
            stringBuilder.append("Average Radius: ").append(arcStatisticsDto.getAverageRadius()).append(EMPTY);
            stringBuilder.append("Average Curvature: ").append(arcStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("Average Arc Length: ").append(arcStatisticsDto.getAverageArcLength()).append(EMPTY);
            stringBuilder.append("Count: ").append(arcStatisticsDto.getCount()).append(EMPTY);
        }

        if (circleStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[Circle Statistics]").append(EMPTY);
            stringBuilder.append("Average Radius: ").append(circleStatisticsDto.getAverageRadius()).append(EMPTY);
            stringBuilder.append("Average Curvature: ").append(circleStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("Average Circumference: ").append(circleStatisticsDto.getAverageCircumference()).append(EMPTY);
            stringBuilder.append("Count: ").append(circleStatisticsDto.getCount()).append(EMPTY);

        }
        if (!circleStatisticsDto.isValid()) {
            stringBuilder.append(VALIDATION_CI);
        }

        if (ellipseStatisticsDto.isValid()) {
            stringBuilder.append(EMPTY).append("[Ellipse Statistics]").append(EMPTY);
            stringBuilder.append("Average Major Radius: ").append(ellipseStatisticsDto.getAverageMajorRadius()).append(EMPTY);
            stringBuilder.append("Average Minor Radius: ").append(ellipseStatisticsDto.getAverageMinorRadius()).append(EMPTY);
            stringBuilder.append("Average Eccentricity: ").append(ellipseStatisticsDto.getAverageEccentricity()).append(EMPTY);
            stringBuilder.append("Average Curvature: ").append(ellipseStatisticsDto.getAverageCurvature()).append(EMPTY);
            stringBuilder.append("Count: ").append(ellipseStatisticsDto.getCount()).append(EMPTY);
            if (!ellipseStatisticsDto.isValid()) {
                stringBuilder.append(VALIDATION_ELL);
            }
        }
        stringBuilder.append(EMPTY).append("[Entity Counts]").append(EMPTY);
        stringBuilder.append("Line: ").append(entityCountStatisticsDto.getLineCount()).append(EMPTY);
        stringBuilder.append("Arc: ").append(entityCountStatisticsDto.getArcCount()).append(EMPTY);
        stringBuilder.append("Circle: ").append(entityCountStatisticsDto.getCircleCount()).append(EMPTY);
        stringBuilder.append("Ellipse: ").append(entityCountStatisticsDto.getEllipseCount()).append(EMPTY);
        stringBuilder.append("Curve Ratio: ").append(entityCountStatisticsDto.getCurve()).append(EMPTY);
        stringBuilder.append(DXF_CONTENT_DOMAIN).append(EMPTY);
        stringBuilder.append(CONTEXT_DXF).append(EMPTY);
        stringBuilder.append(ENTITIES).append(EMPTY);
        stringBuilder.append(newFile).append(EMPTY);
        stringBuilder.append(FINAL_OUTPUT);
        return stringBuilder.toString();
    }
}
