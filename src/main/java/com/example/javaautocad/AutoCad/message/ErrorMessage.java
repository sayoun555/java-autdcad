package com.example.javaautocad.AutoCad.message;

public enum ErrorMessage {
    KEY_ERROR("[ERROR] open ai key가 맞지 않습니다"),
    ERROR_PYTHON("[ERROR] 파이썬 변환 실패"),
    JSON_ERROR("[ERROR] json 변환 실패"),
    RESPONSE_ERROR("[ERROR] 서버 응답이 없습니다."),
    INPUT_ERROR("[ERROR] 엔터만 눌러주세요"),
    MAPPING_ERROR("[ERROR] 매핑에 실패했습니다."),
    CLOSE_ERROR("[ERROR] 프로그램 종료에 실패했습니다."),
    START_ERROR("[ERROR] 파일 감시 실패"),
    CONVERT_ERROR("[ERROR] dxf 변환 실패");



    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
