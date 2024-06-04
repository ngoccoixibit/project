package util;

import java.util.Scanner;

public class Input {
    private static final Scanner INPUT = new Scanner(System.in);
    public static int scanIntegerLine(String request) {
        int number;
        do {
            try {
                System.out.print(request);
                number = Integer.parseInt(INPUT.nextLine());
                break;
            } catch(NumberFormatException e) {
                System.out.println("Error! " + e.getMessage() + ". Please input number! [>v<]");
            }
        } while(true);
        return number;
    }

    public static boolean scanBooleanLine(String request) {
        boolean string;
        do {
            try {
                System.out.print(request);
                string = Boolean.parseBoolean(INPUT.nextLine());
                break;
            } catch(NumberFormatException e) {
                System.out.println("Error! " + e.getMessage() + ". Please input 'true' or 'false'! (>,<)");
            }
        } while(true);
        return string;
    }

    public static String scanStringLine(String request) {
        System.out.print(request);
        return INPUT.nextLine();
    }

    public static String scanLineRegex(String request, String regexPattern) {
        String text;
        do {
            text = scanStringLine(request);
            if (Validation.validate(text, regexPattern)) {
                System.out.println("Invalid Input! Wrong format! (>,<)");
            }
        } while (Validation.validate(text, regexPattern));
        return text;
    }
}
