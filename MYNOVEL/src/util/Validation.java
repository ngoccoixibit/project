package util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static Pattern pattern;
    private static final String NAME_REGEX = "^(?=.*[a-zA-Z]).{3,}$";
    private static final String PHONE_REGEX = "^\\+?(?:\\d{2})?[0]\\d{9}$";
    private static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@])[A-Za-z\\d@]{8,}$";
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9\\._-]+@[a-zA-Z0-9\\.-]+\\.[a-zA-Z]{2,4}$";
    private static final String DATE_REGEX = "^([0]?[0-9]|[12][0-9]|[3][01])[-]([0]?[1-9]|[1][0-2])[-]([0-9]{4}|[0-9]{2})$";
    public static boolean validate(String regex, String regexPattern) {
        switch (regexPattern) {
            case "NAME" -> pattern = Pattern.compile(NAME_REGEX);
            case "PHONE" -> pattern = Pattern.compile(PHONE_REGEX);
            case "PASSWORD" -> pattern = Pattern.compile(PASSWORD_REGEX);
            case "EMAIL" -> pattern = Pattern.compile(EMAIL_REGEX);
            case "DATE" -> pattern = Pattern.compile(DATE_REGEX);
        }
        Matcher matcher = pattern.matcher(regex);
        return !matcher.matches();
    }
}
