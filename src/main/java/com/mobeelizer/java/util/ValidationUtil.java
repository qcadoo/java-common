package com.mobeelizer.java.util;

import java.util.regex.Pattern;

public final class ValidationUtil {

    private ValidationUtil() {

    }

    private static final Pattern namePattern = Pattern.compile("([0-9a-zA-Z_])*");

    private static final Pattern uuidPattern = Pattern
            .compile("^([0-9a-fA-F]){8}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){4}-([0-9a-fA-F]){12}");

    public static boolean isValidName(final String name) {
        if (name.length() > 40) {
            return false;
        }
        return namePattern.matcher(name).matches();
    }

    public static boolean isValidGuid(final String guid) {
        return uuidPattern.matcher(guid).matches();
    }

}
