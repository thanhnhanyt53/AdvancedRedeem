package com.advancedredeem.util;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DurationParser {

    private static final Pattern PATTERN =
            Pattern.compile(
                    "^(\\d+)\\s*([smhdw]?)$",
                    Pattern.CASE_INSENSITIVE
            );

    private DurationParser() {
    }

    public static long parseSeconds(
            String input
    ) {

        if (input == null) {
            throw new IllegalArgumentException(
                    "Duration is null"
            );
        }

        String value =
                input.trim()
                        .toLowerCase(Locale.ROOT);

        Matcher matcher =
                PATTERN.matcher(value);

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Invalid duration: "
                            + input
            );
        }

        long amount =
                Long.parseLong(
                        matcher.group(1)
                );

        String unit =
                matcher.group(2);

        return switch (unit) {

            case "" -> amount;

            case "s" -> amount;

            case "m" -> safeMultiply(
                    amount,
                    60L
            );

            case "h" -> safeMultiply(
                    amount,
                    3600L
            );

            case "d" -> safeMultiply(
                    amount,
                    86400L
            );

            case "w" -> safeMultiply(
                    amount,
                    604800L
            );

            default ->
                    throw new IllegalArgumentException(
                            "Unknown duration unit"
                    );
        };
    }

    private static long safeMultiply(
            long a,
            long b
    ) {

        if (a > Long.MAX_VALUE / b) {
            throw new IllegalArgumentException(
                    "Duration is too large"
            );
        }

        return a * b;
    }
}