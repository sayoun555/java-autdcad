package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class AutoCadController {
    private final FileWatcher fileWatcher;
    private final InputView inputView;
    private final OutputView outputView;


    public AutoCadController(FileWatcher fileWatcher, InputView inputView, OutputView outputView) {
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private void mainView(){
        outputView.startView();
        outputView.startPrint();
    }

    private void startWetchar() {
        fileWatcher.start(inputView.input());
        outputView.exitStop();
    }

    private void mainLoop() {
        mainView();
        startWetchar();
        while (true) {
            if (inputView.stopInput()) {
                outputView.exitView();
                break;
            }
        }
        while (true) {
            if (inputView.exitInput()) {
                fileWatcher.stop();
                break;
            }
        }
    }

    public void run() {
        mainLoop();
    }
}
