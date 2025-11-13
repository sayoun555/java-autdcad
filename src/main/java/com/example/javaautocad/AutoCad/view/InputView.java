package com.example.javaautocad.AutoCad.view;

import com.example.javaautocad.AutoCad.message.ErrorMessage;

import java.util.Objects;
import java.util.Scanner;

public class InputView {
    private final String START = "시작 하시겠습니까?";

    public String input() {
        System.out.println(START);
        Scanner scanner = new Scanner(System.in);
        String check = scanner.nextLine();
        if (!Objects.equals(check, "")) {
            throw new IllegalArgumentException(ErrorMessage.INPUT_ERROR.getMessage());
        }
        return check;
    }
}
