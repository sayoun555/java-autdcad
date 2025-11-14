package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.dto.LineDto;
import com.example.javaautocad.AutoCad.manager.AutoManager;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.message.ErrorMessage;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;
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

    public void run() {
        outputView.startView();
        if (!inputView.input()) {
            throw new IllegalArgumentException(ErrorMessage.MAPPING_ERROR.getMessage());
        }
        outputView.startPrint();
        fileWatcher.start();
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
}
