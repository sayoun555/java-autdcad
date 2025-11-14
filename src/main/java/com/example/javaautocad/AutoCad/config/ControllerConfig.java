package com.example.javaautocad.AutoCad.config;

import com.example.javaautocad.AutoCad.ai.AutoAi;
import com.example.javaautocad.AutoCad.controller.AutoCadController;
import com.example.javaautocad.AutoCad.manager.FileWatcher;
import com.example.javaautocad.AutoCad.parser.AutoParser;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class ControllerConfig {
    public AutoCadController build() {
        OutputView view = new OutputView();
        AutoParser parser = new AutoParser();
        AutoAi ai = new AutoAi();
        FileWatcher watcher = new FileWatcher(ai, null, null, view);
        InputView inputView = new InputView();
        return new AutoCadController(watcher, inputView, view);
    }
}
