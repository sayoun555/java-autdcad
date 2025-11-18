package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.config.EnvConfig;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.view.InputValidator;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class AutoCadController {
    private final FileWatcher fileWatcher;
    private final InputView inputView;
    private final OutputView outputView;
    private final EnvConfig envConfig;
    private final InputValidator validator;
    private final String STOP = "stop";

    public AutoCadController(FileWatcher fileWatcher, InputView inputView, OutputView outputView, EnvConfig envConfig) {
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
        this.envConfig = envConfig;
        this.validator = new InputValidator();
    }

    private void startWatcher() {
        while (true) {
            try {
                outputView.inputFolderMessage();
                String folder = inputView.inputCommand();
                validator.validateFolder(folder);

                fileWatcher.start(folder);
                outputView.exitStop();
                break;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private void envSetup() {
        if (!envConfig.hassConfig()) {
            newConfig();
            return;
        }

        outputView.config(envConfig.getPython(), envConfig.getScript());
        outputView.askOutput();

        while (true) {
            try {
                String input = inputView.inputCommand();
                validator.validateInput(input);

                if (input.trim().equalsIgnoreCase("y")) {
                    newConfig();
                    outputView.configUpdate();
                }
                break;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private void newConfig() {
        String python = inputPythonWithValidation();
        String script = inputScriptWithValidation();
        String key = inputKeyWithValidation();
        envConfig.save(python, script, key);
    }

    private String inputPythonWithValidation() {
        while (true) {
            try {
                outputView.outputPython();
                String python = inputView.inputPython();
                validator.validatePython(python);
                return python;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private String inputScriptWithValidation() {
        while (true) {
            try {
                outputView.outputScript();
                String script = inputView.inputScript();
                validator.validateScript(script);
                return script;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private String inputKeyWithValidation() {
        while (true) {
            try {
                outputView.keyOutput();
                String key = inputView.inputKey();
                validator.validateApiKey(key);
                return key;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private void mainLoop() {
        startWatcher();
        while (true) {
            String cmd = inputView.inputCommand();
            if (cmd.equalsIgnoreCase(STOP)) {
                fileWatcher.stop();
                outputView.exitView();
                break;
            }
        }
    }

    public void run() {
        envSetup();
        mainLoop();
    }
}