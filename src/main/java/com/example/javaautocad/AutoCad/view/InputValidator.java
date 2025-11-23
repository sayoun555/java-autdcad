package com.example.javaautocad.AutoCad.view;

import com.example.javaautocad.AutoCad.message.ErrorMessage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InputValidator {

    public void validateFolder(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_PATH.getMessage());
        }

        Path folder = Paths.get(path);
        if (!Files.exists(folder)) {
            throw new IllegalArgumentException(ErrorMessage.FOLDER_NOT_FOUND.getMessage());
        }

        if (!Files.isDirectory(folder)) {
            throw new IllegalArgumentException(ErrorMessage.NOT_DIRECTORY.getMessage());
        }
    }

    public void validatePython(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_PYTHON_PATH.getMessage());
        }

        Path python = Paths.get(path);
        if (!Files.exists(python)) {
            throw new IllegalArgumentException(ErrorMessage.PYTHON_NOT_FOUND.getMessage());
        }

        if (!Files.isExecutable(python)) {
            throw new IllegalArgumentException(ErrorMessage.PYTHON_NOT_EXECUTABLE.getMessage());
        }
    }

    public void validateScript(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_SCRIPT_PATH.getMessage());
        }

        Path script = Paths.get(path);
        if (!Files.exists(script)) {
            throw new IllegalArgumentException(ErrorMessage.SCRIPT_NOT_FOUND.getMessage());
        }

        if (!path.endsWith(".py")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_SCRIPT_EXTENSION.getMessage());
        }
    }

    public void validateApiKey(String key) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_API_KEY.getMessage());
        }

        if (key.length() < 20) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_API_KEY_LENGTH.getMessage());
        }
    }

    public void validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(ErrorMessage.EMPTY_INPUT.getMessage());
        }

        String normalized = input.trim().toLowerCase();
        if (!normalized.equals("y") && !normalized.equals("n")) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_YES_NO.getMessage());
        }
    }
}