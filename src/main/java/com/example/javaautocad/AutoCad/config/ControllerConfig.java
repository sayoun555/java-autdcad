package com.example.javaautocad.AutoCad.config;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.controller.AutoCadController;
import com.example.javaautocad.AutoCad.manager.DxfConverter;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.parser.AutoParser;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;
import com.example.javaautocad.AutoCad.service.AutoMeasureServiceImpl;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class ControllerConfig {
    public AutoCadController build() {
        OutputView view = new OutputView();
        AutoParser autoParser = new AutoParser();
        AutoAi ai = new AutoAi(autoParser);
        DxfConverter dxfConverter = new DxfConverter();
        AutoMeasureService autoMeasureService = new AutoMeasureServiceImpl(ai, dxfConverter);
        FileWatcher watcher = new FileWatcher(autoMeasureService, view);
        InputView inputView = new InputView();
        return new AutoCadController(watcher, inputView, view);
    }
}
