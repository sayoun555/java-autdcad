package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.manager.AutoManager;
import com.example.javaautocad.AutoCad.service.AutoMeasureService;
import com.example.javaautocad.AutoCad.view.InputView;
import com.example.javaautocad.AutoCad.view.OutputView;

public class AutoCadController {
    private final AutoManager autoManager;
    private final AutoMeasureService autoMeasureService;
    private final InputView inputView;
    private final OutputView outputView;


    public AutoCadController(AutoManager autoManager, AutoMeasureService autoMeasureService, InputView inputView, OutputView outputView) {
        this.autoManager = autoManager;
        this.autoMeasureService = autoMeasureService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void

}
