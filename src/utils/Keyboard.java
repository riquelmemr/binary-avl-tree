package utils;

import java.util.Scanner;

public class Keyboard {

    private static final Scanner scanner = new Scanner(System.in);

    public static String readString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
}