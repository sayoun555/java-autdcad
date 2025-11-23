package com.example.javaautocad.AutoCad.manager;

import com.example.javaautocad.AutoCad.message.ErrorMessage;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DxfConverter {
    private final String python;
    private final String script;

    public DxfConverter(String python, String script) {
        this.python = python;
        this.script = script;
    }

    public Path convert(Path dxf) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(python, script, dxf.toString());
            processBuilder.inheritIO();
            Process process = processBuilder.start();
            process.waitFor();
            String jsonPathStr = dxf.toString().replace(".dxf", ".json");
            return Paths.get(new File(jsonPathStr).getAbsolutePath());
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.CONVERT_ERROR.getMessage());
        }
    }
}
