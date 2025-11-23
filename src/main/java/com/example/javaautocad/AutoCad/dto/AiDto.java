package com.example.javaautocad.AutoCad.dto;

import java.util.List;

public class AiDto {
    private final String model = "gpt-4o-mini";
    private final List<MessageDto> messages;
    private final double temperature;

    public AiDto(List<MessageDto> messages, double temperature) {
        this.messages = messages;
        this.temperature = temperature;
    }

    public String getModel() {
        return model;
    }

    public List<MessageDto> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }
}
