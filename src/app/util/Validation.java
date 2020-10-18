package app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Validation {

    Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\" +
            ".[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    Pattern namePattern = Pattern.compile("^(?=.{4,20}$)(?![_.])"+
            "(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$", Pattern.CASE_INSENSITIVE);


    default boolean isValidEmail(String input) {
        Matcher matcher = emailPattern.matcher(input);
        return matcher.matches();
    }

    default boolean isValidPassword(String input) {
        return input.length() >= 6;
    }

    default boolean isValidName(String input) {
        Matcher matcher = namePattern.matcher(input);
        return matcher.matches();
    }

}