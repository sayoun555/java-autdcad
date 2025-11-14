package com.example.javaautocad.AutoCad.view;

import java.util.Objects;
import java.util.Scanner;

public class InputView {
    private final String START = "시작 하시겠습니까?";

    public boolean input() {
        System.out.println(START);
        Scanner scanner = new Scanner(System.in);
        String check = scanner.nextLine();
        return Objects.equals(check, "");
    }
}