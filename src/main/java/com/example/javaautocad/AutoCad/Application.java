package com.example.javaautocad.AutoCad;

import com.example.javaautocad.AutoCad.config.ControllerConfig;

public class Application {
    public static void main(String[] args) {
        try {
            ControllerConfig controllerConfig = new ControllerConfig();
            controllerConfig.build().run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
