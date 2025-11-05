package com.example.javaautocad.AutoCad.controller;

import com.example.javaautocad.AutoCad.service.AutoCadService;

public class AutoCadController {
    private final AutoCadService autoCadService;

    public AutoCadController(AutoCadService autoCadService) {
        this.autoCadService = autoCadService;
    }
}
