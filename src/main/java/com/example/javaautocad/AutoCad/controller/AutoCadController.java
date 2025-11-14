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
    private final AutoMeasureService autoMeasureService;


    public AutoCadController(FileWatcher fileWatcher, InputView inputView, OutputView outputView, AutoMeasureService autoMeasureService) {
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
        this.autoMeasureService = autoMeasureService;
    }

    public void run() {
        if (!inputView.input()) {
            throw new IllegalArgumentException(ErrorMessage.MAPPING_ERROR.getMessage());
        }
        outputView.startPrint();
        fileWatcher.start();
    }

}
