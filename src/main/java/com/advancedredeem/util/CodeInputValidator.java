package com.advancedredeem.util;

import java.util.regex.Pattern;

public final class CodeInputValidator {

    private static final Pattern VALID =
            Pattern.compile(
                    "^[A-Za-z0-9_-]{3,64}$"
            );

    private CodeInputValidator() {
    }

    public static boolean valid(
            String code
    ) {

        return code != null
                && VALID.matcher(code).matches();
    }
}