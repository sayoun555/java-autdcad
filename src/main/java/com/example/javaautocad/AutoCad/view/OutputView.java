package com.example.javaautocad.AutoCad.view;

public class OutputView {
    private final String START = "[감시 시작]\n";
    private final String LINE = "====================================================";
    private final String EXIT = "종료 하시겠습니까?";
    private final String STOP = "프로그램을 종료하려면 stop을 적어주세요";

    public void result(String outPut) {
        System.out.println(outPut);
    }

    public void exitView() {
        System.out.println(EXIT);
    }

    public void exitStop() {
        System.out.println(STOP);
    }

    public void startPrint() {
        System.out.println(START);
        System.out.println(LINE);
    }
}
