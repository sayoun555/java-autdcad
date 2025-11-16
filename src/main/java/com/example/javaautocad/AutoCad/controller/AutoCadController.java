package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.config.EnvConfig;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class AutoCadController {
    private final FileWatcher fileWatcher;
    private final InputView inputView;
    private final OutputView outputView;
    private final EnvConfig envConfig;


    public AutoCadController(FileWatcher fileWatcher, InputView inputView, OutputView outputView, EnvConfig envConfig) {
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
        this.envConfig = envConfig;
    }

    private void mainView() {
        outputView.startView();
        outputView.startPrint();
    }

    private void startWetchar() {
        fileWatcher.start(inputView.input());
        outputView.exitStop();
    }

    private void envSetup() {
        if (!envConfig.hassConfig()) {
            newConfig();
            return;
        }

        outputView.config(envConfig.getPython(), envConfig.getScript());
        outputView.askOutput();
        if (inputView.askConfig()) {
            newConfig();
            outputView.configUpdate();
        }
    }

    private void newConfig() {
        outputView.outputPython();
        String python = inputView.inputPython();
        outputView.outputScript();
        String script = inputView.inputScript();
        outputView.keyOutput();
        String key = inputView.inputKey();
        envConfig.save(python, script, key);
    }

    private void mainLoop() {
        mainView();
        startWetchar();
        while (true) {
            if (inputView.stopInput()) {
                outputView.exitView();
                if (inputView.exitInput()) {
                    fileWatcher.stop();
                    break;
                }
            }
        }
    }

    public void run() {
        envSetup();
        mainLoop();

    }
}
