package com.example.javaautocad.AutoCad.view;

import java.util.Objects;
import java.util.Scanner;

public class InputView {
    private final String START = "시작 하시겠습니까?";
    private final Scanner scanner = new Scanner(System.in);
    private final String ENTER = "";
    private final String STOP = "stop";

    public boolean input() {
        System.out.println(START);
        String check = scanner.nextLine();
        return Objects.equals(check, ENTER);
    }

    public boolean stopInput() {
        System.out.println(START);
        String check = scanner.nextLine();
        return Objects.equals(check, STOP);
    }

    public boolean exitInput() {
        String check = scanner.nextLine();
        return Objects.equals(check, ENTER);
    }
}