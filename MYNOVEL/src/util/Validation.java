package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static Pattern pattern;
    private static final String PHONE_REGEX = "^\\+?(?:\\d{2})?[0]\\d{9}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9\\.-]+\\.[a-zA-Z]{2,4}$";
    public static boolean validate(String regex, String regexPattern) {
        switch (regexPattern) {
            case "PHONE" -> pattern = Pattern.compile(PHONE_REGEX);
            case "PASSWORD" -> pattern = Pattern.compile(PASSWORD_REGEX);
            case "EMAIL" -> pattern = Pattern.compile(EMAIL_REGEX);
        }
        Matcher matcher = pattern.matcher(regex);
        return !matcher.matches();
    }
}
