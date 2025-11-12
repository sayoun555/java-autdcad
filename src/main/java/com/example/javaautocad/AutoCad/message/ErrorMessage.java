package com.example.javaautocad.AutoCad.message;

public enum ErrorMessage {
    KEY_ERROR("[ERROR] open ai key가 맞지 않습니다"),
    ERROR_PYTHON("[ERROR] 파이썬 변환 실패");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
