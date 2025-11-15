package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

import java.util.List;

public class AutoCadController {
    private final FileWatcher fileWatcher;
    private final InputView inputView;
    private final OutputView outputView;


    public AutoCadController(FileWatcher fileWatcher, InputView inputView, OutputView outputView) {
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    private void mainLoop() {
        outputView.startView();
        outputView.startPrint();
        fileWatcher.start(inputView.input());
        outputView.exitStop();
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
        mainLoop();
    }
}
