package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.config.ControllerConfig;
import com.example.javaautocad.AutoCad.dto.LineDto;
import com.example.javaautocad.AutoCad.factory.AutoFactory;
import com.example.javaautocad.AutoCad.manager.AutoManager;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

import java.util.List;

public class AutoCadController {
    private final AutoManager autoManager;
    private final FileWatcher fileWatcher;
    private final InputView inputView;
    private final OutputView outputView;


    public AutoCadController(AutoManager autoManager, FileWatcher fileWatcher, InputView inputView, OutputView outputView) {
        this.autoManager = autoManager;
        this.fileWatcher = fileWatcher;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        LineDto lineDto = autoManager.processBuild(fileWatcher.start());
    }

}
