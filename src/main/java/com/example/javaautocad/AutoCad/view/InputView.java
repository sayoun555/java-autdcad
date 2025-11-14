package com.example.javaautocad.AutoCad.view;

import java.util.Objects;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);
    private final String ENTER = "";
    private final String STOP = "stop";

    public boolean input() {
        String check = scanner.nextLine();
        return Objects.equals(check, ENTER);
    }

    public boolean stopInput() {
        String check = scanner.nextLine();
        return Objects.equals(check, STOP);
    }

    public boolean exitInput() {
        String check = scanner.nextLine();
        return Objects.equals(check, ENTER);
    }
}