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
    private final String STOP = "stop";


    public AutoCadController(FileWatcher fileWatcher, InputView inputView, OutputView outputView, EnvConfig envConfig) {
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
        this.envConfig = envConfig;
    }

    private void mainView() {
//        outputView.startView();
        outputView.startPrint();
    }

    private void startWetchar() {
        outputView.inputFolderMessage();   // 폴더 안내 메시지 출력
        String folder = inputView.inputCommand();  // 폴더 입력 받기

        fileWatcher.start(folder);          // 감시 시작
        outputView.exitStop();              // stop 안내 메시지
    }

//        fileWatcher.start(inputView.input());
//        outputView.exitStop();

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
        startWetchar();

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
