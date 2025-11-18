package com.example.javaautocad.AutoCad.view;

import java.nio.file.Files;

public class OutputView {
    private final String EXIT = "종료 하겠습니다";
    private final String STOP = "프로그램을 종료하려면 stop을 적어주세요";
    private final String START_PRINT = "감시할 폴더 경로를 입력하세요:";
    private final String PYTHON = "파이썬: ";
    private final String SCRIPT = "스크립트: ";
    private final String UPDATE = "설정 업데이트 완료";
    private final String OUTPUT_PYTHON = "파이썬 파일 경로를 입력해주세요.";
    private final String OUTPUT_SCRIPT = "DXF 스크립트 경로를 입력해주세요. ";
    private final String CONFIG = "변경 하시겠습니까? (y/n)";
    private final String KEY = "open api key를 입력해주세요.";
    private final String REJOIN = "설정이 끝났습니다. 프로그램을 다시 켜주세요.";
    private final String ERROR = "[ERROR]";

    public void result(String outPut) {
        System.out.println(outPut);
    }

    public void reJoin() {
        System.out.println(REJOIN);
    }

    public void exitView() {
        System.out.println(EXIT);
    }

    public void exitStop() {
        System.out.println(STOP);
    }

    public void config(String python, String script) {
        System.out.println(PYTHON + python);
        System.out.println(SCRIPT + script);
    }

    public void configUpdate() {
        System.out.println(UPDATE);
    }

    public void outputPython() {
        System.out.println(OUTPUT_PYTHON);
    }

    public void outputScript() {
        System.out.println(OUTPUT_SCRIPT);
    }

    public void askOutput() {
        System.out.println(CONFIG);
    }

    public void keyOutput() {
        System.out.println(KEY);
    }

    public void inputFolderMessage() {
        System.out.println(START_PRINT);
    }

    public void errorMessage(String message) {
        System.out.println(ERROR + message);
    }

}
