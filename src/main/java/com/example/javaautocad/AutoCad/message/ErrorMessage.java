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
    CONVERT_ERROR("[ERROR] dxf 변환 실패"),
    FILE_ERROR("[ERROR] 파일 불러오기 실패"),
    ENV_ERROR("[ERROR] 설정 저장하기 실패"),
    CHOICES_ERROR("[ERROR] choices 가 없습니다."),
    ARC_ERROR("[ERROR] ARC 파싱 실패"),
    CIRCLES_ERROR("[ERROR] CIRCLES 파싱 실패"),
    LINE_ERROR("[ERROR] LINE 파싱 실패"),
    COUNT_ERROR("[ERROR] COUNT 파싱 실패"),
    ELLIPSE_ERROR("[ERROR] ELLIPSE 파싱 실패"),
    EMPTY_PATH("폴더 경로가 비어있습니다."),
    FOLDER_NOT_FOUND("폴더를 찾을 수 없습니다."),
    NOT_DIRECTORY("디렉토리가 아닙니다."),
    EMPTY_PYTHON_PATH("Python 경로가 비어있습니다."),
    PYTHON_NOT_FOUND("Python 실행 파일을 찾을 수 없습니다."),
    PYTHON_NOT_EXECUTABLE("Python 파일이 실행 가능하지 않습니다."),
    EMPTY_SCRIPT_PATH("스크립트 경로가 비어있습니다."),
    SCRIPT_NOT_FOUND("스크립트 파일을 찾을 수 없습니다."),
    INVALID_SCRIPT_EXTENSION("스크립트 파일은 .py 확장자여야 합니다."),
    EMPTY_API_KEY("API 키가 비어있습니다."),
    INVALID_API_KEY_LENGTH("API 키 길이가 유효하지 않습니다."),
    EMPTY_INPUT("입력값이 비어있습니다."),
    INVALID_YES_NO("y 또는 n을 입력해주세요."),
    ENV_ERROR_LOAD("env 파일 불러오기 실패 했씁니다.");


    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
