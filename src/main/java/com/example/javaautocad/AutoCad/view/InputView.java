package com.example.javaautocad.AutoCad.view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String inputCommand() {
        return scanner.nextLine().trim();
    }

    public String inputPython() {
        return scanner.nextLine().trim();
    }

    public String inputScript() {
        return scanner.nextLine().trim();
    }

    public boolean askConfig() {
        return scanner.nextLine().trim().equalsIgnoreCase("y");
    }

    public String inputKey() {
        return scanner.nextLine().trim();
    }
}