package com.example.javaautocad.AutoCad.config;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.controller.AutoCadController;
import com.example.javaautocad.AutoCad.factory.AutoFactory;
import com.example.javaautocad.AutoCad.manager.AutoManager;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.parser.AutoParser;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;
import com.example.javaautocad.AutoCad.service.AutoMeasureServiceImpl;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class ControllerConfig {
    public AutoCadController build() {
        AutoFactory factory = new AutoFactory();
        AutoParser parser = new AutoParser();
        AutoAi ai = new AutoAi();
        AutoMeasureService service = new AutoMeasureServiceImpl(factory, ai);
        AutoManager manager = new AutoManager(factory, parser, service);
        FileWatcher watcher = new FileWatcher(ai, null, null);
        InputView inputView = new InputView();
        OutputView view = new OutputView();
        return new AutoCadController(manager, service, watcher, inputView, view);
    }
}
