package com.example.javaautocad.AutoCad.view;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.domain.Line;
import com.example.javaautocad.AutoCad.dto.StatisticsDto;

public class OutputView {
    private final String START = "[감시 시작]\n";
    private final String LINE = "=============================";

    public void result(String outPut) {
        System.out.println(outPut);
    }

    public void startPrint() {
        System.out.println(START);
        System.out.println(LINE);
    }
}
