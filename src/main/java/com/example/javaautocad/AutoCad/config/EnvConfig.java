package com.example.javaautocad.AutoCad.config;

import com.example.javaautocad.AutoCad.message.ErrorMessage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class EnvConfig {
    private static final String ENVFILE = ".env";
    private Map<String, String> envMap;
    private final String PYTHON = "PYTHON_PATH";
    private final String SCRIPT = "SCRIPT_PATH";
    private final String APIKEY = "API_KEY";
    private final Path envFilePath;

    public EnvConfig() {
        this.envFilePath = envFilePath();
        this.envMap = loadEnv();
    }

    private Path envFilePath() {
        try {
            String jarPath = EnvConfig.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();
            if (jarPath.startsWith("/") && jarPath.length() > 2 && jarPath.charAt(2) == ':') {
                jarPath = jarPath.substring(1);
            }
            File jarFile = new File(jarPath);
            File baseDir = jarFile;
            if (jarFile.isFile()) {
                baseDir = jarFile.getParentFile();
                return baseDir.toPath().resolve(ENVFILE);
            }
            while (baseDir != null && !new File(baseDir, ".env").exists()) {
                baseDir = baseDir.getParentFile();
            }
            if (baseDir == null) {
                return Paths.get(ENVFILE);
            }
            return baseDir.toPath().resolve(ENVFILE);
        } catch (Exception e) {
            return Paths.get(ENVFILE);
        }
    }

    private Map<String, String> loadEnv() {
        Map<String, String> env = new HashMap<>();
        try {
            Files.lines(envFilePath)
                    .filter(line -> line.contains("="))
                    .forEach(line -> {
                        String[] parts = line.split("=", 2);
                        env.put(parts[0].trim(), parts[1].trim());
                    });
        } catch (Exception e) {
            throw new IllegalArgumentException(ErrorMessage.ENV_ERROR_LOAD.getMessage());
        }
        return env;
    }

    public boolean hassConfig() {
        return envMap.containsKey(PYTHON) && envMap.containsKey(SCRIPT) && envMap.containsKey(APIKEY);
    }

    public String getPython() {
        return envMap.get(PYTHON);
    }

    public String getScript() {
        return envMap.get(SCRIPT);
    }

    public String getApiKey() {
        return envMap.get(APIKEY);
    }

    public void save(String python, String script, String key) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(envFilePath.toFile()))) {
            writer.println("PYTHON_PATH=" + python);
            writer.println("SCRIPT_PATH=" + script);
            writer.println("API_KEY=" + key);
        } catch (IOException e) {
            throw new IllegalArgumentException(ErrorMessage.ENV_ERROR.getMessage());
        }
        this.envMap = loadEnv();
    }
}
