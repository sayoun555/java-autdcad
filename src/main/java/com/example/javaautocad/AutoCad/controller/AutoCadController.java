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

    private String inputIf() {
        outputView.outputIf();
        return inputView.inputIfView();
    }

    private void startWatcher() {
        while (true) {
            try {
                outputView.inputFolderMessage();
                String folder = inputView.inputCommand();
                validator.validateFolder(folder);
                String input = inputIf();
                fileWatcher.tireType(input);
                fileWatcher.start(folder);
                outputView.exitStop();
                break;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private boolean envSetup() {
        if (!envConfig.hassConfig()) {
            newConfig();
            outputView.reJoin();
            return false;
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
                    outputView.reJoin();
                    return false;
                }
                break;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
        return true;
    }

    private void newConfig() {
        String python = inputPython();
        String script = inputScript();
        String key = inputKey();
        envConfig.save(python, script, key);
    }

    private String inputPython() {
        while (true) {
            try {
                outputView.outputPython();
                String python = inputView.inputPythonView();
                validator.validatePython(python);
                return python;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private String inputScript() {
        while (true) {
            try {
                outputView.outputScript();
                String script = inputView.inputScriptView();
                validator.validateScript(script);
                return script;
            } catch (IllegalArgumentException e) {
                outputView.errorMessage(e.getMessage());
            }
        }
    }

    private String inputKey() {
        while (true) {
            try {
                outputView.keyOutput();
                String key = inputView.inputKeyView();
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
        if (!envSetup()) {
            return;
        }
        mainLoop();
    }
}