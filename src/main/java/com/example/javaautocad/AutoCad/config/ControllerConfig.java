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
        EnvConfig envConfig = new EnvConfig();
        InputView inputView = new InputView();
        OutputView view = new OutputView();
        AutoParser autoParser = new AutoParser();
        AutoAi ai = new AutoAi(autoParser);
        DxfConverter dxfConverter = new DxfConverter(envConfig.getPython(), envConfig.getScript());
        AutoMeasureService autoMeasureService = new AutoMeasureServiceImpl(ai, dxfConverter);
        FileWatcher watcher = new FileWatcher(autoMeasureService, view);
        return new AutoCadController(watcher, inputView, view, envConfig);
    }
}
