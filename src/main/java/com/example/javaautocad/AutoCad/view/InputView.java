package com.example.javaautocad.AutoCad.view;

import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);

    public String inputCommand() {
        return scanner.nextLine().trim();
    }

    public String inputPythonView() {
        return scanner.nextLine().trim();
    }

    public String inputScriptView() {
        return scanner.nextLine().trim();
    }

    public String inputKeyView() {
        return scanner.nextLine().trim();
    }

    public String inputIfView() {
        return scanner.nextLine();
    }
}