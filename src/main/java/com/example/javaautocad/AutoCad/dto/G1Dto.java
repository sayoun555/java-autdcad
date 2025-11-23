package com.example.javaautocad.AutoCad.dto;

public class G1Dto {
    private final double maxJump;
    private final double argJump;
    private final int breakCount;

    public G1Dto(double maxJump, double argJump, int breakCount) {
        this.maxJump = maxJump;
        this.argJump = argJump;
        this.breakCount = breakCount;
    }

    public double getMaxJump() {
        return maxJump;
    }

    public double getArgJump() {
        return argJump;
    }

    public int getBreakCount() {
        return breakCount;
    }
}
