package com.example.javaautocad.AutoCad.config;

public class LayerConfig {
    private static final String TARGET_LAYER = "도면층3";

    private LayerConfig() {
    }

    public static String getTargetLayer() {
        return TARGET_LAYER;
    }
}