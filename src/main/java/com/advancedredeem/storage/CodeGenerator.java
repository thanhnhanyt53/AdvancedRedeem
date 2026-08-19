package com.advancedredeem.storage;

import java.security.SecureRandom;

public final class CodeGenerator {

    private static final String ALPHABET =
            "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private static final SecureRandom RANDOM =
            new SecureRandom();

    private CodeGenerator() {
    }

    public static String generate(
            int length
    ) {

        if (length < 4) {
            throw new IllegalArgumentException(
                    "Code length must be >= 4"
            );
        }

        StringBuilder result =
                new StringBuilder(length);

        for (int i = 0; i < length; i++) {

            result.append(
                    ALPHABET.charAt(
                            RANDOM.nextInt(
                                    ALPHABET.length()
                            )
                    )
            );
        }

        return result.toString();
    }
}