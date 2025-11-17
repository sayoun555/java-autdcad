package com.example.javaautocad.AutoCad.view;

import java.util.Objects;
import java.util.Scanner;

public class InputView {
    private final Scanner scanner = new Scanner(System.in);
    private final String ENTER = "";
    private final String STOP = "stop";

    public String input() {
        return scanner.nextLine();
    }

//    public boolean stopInput() {
//        String check = scanner.nextLine();
//        return Objects.equals(check, STOP);
//    }
//
//    public boolean exitInput() {
//        String check = scanner.nextLine();
//        return Objects.equals(check, ENTER);
//    }

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