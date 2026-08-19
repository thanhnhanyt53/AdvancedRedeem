package com.advancedredeem.util;

public final class InputParser {

    private InputParser() {
    }

    public static Integer integer(
            String input
    ) {

        try {
            return Integer.parseInt(
                    input.trim()
            );
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Long longValue(
            String input
    ) {

        try {
            return Long.parseLong(
                    input.trim()
            );
        } catch (Exception ignored) {
            return null;
        }
    }

    public static Double decimal(
            String input
    ) {

        try {
            return Double.parseDouble(
                    input.trim()
            );
        } catch (Exception ignored) {
            return null;
        }
    }

    public static boolean positive(
            long value
    ) {
        return value >= 0L;
    }

    public static boolean positive(
            double value
    ) {
        return value >= 0D;
    }
}