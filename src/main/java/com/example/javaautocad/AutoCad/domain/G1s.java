package com.example.javaautocad.AutoCad.domain;

import java.util.ArrayList;
import java.util.List;

public class G1s {
    private final List<Object> segment;

    public G1s(List<Object> segment) {
        this.segment = new ArrayList<>(segment);
    }

    public List<Object> val() {
        return segment;
    }
}
