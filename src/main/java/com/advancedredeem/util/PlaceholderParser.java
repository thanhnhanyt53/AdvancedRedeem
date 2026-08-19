package com.advancedredeem.util;

import com.advancedredeem.condition.PlaceholderCondition;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class PlaceholderConditionParser {

    private static final Pattern PATTERN =
            Pattern.compile(
                    "^(.+?)\\s*(==|!=|>=|<=|>|<|contains)\\s*(.+)$",
                    Pattern.CASE_INSENSITIVE
            );

    private PlaceholderConditionParser() {
    }

    public static PlaceholderCondition parse(
            String input
    ) {

        Matcher matcher =
                PATTERN.matcher(
                        input.trim()
                );

        if (!matcher.matches()) {
            throw new IllegalArgumentException(
                    "Format: %placeholder% >= value"
            );
        }

        String placeholder =
                matcher.group(1).trim();

        String operator =
                matcher.group(2).trim();

        String expected =
                matcher.group(3).trim();

        PlaceholderCondition.Operator op =
                switch (operator.toLowerCase()) {

                    case "==" ->
                            PlaceholderCondition.Operator
                                    .EQUALS;

                    case "!=" ->
                            PlaceholderCondition.Operator
                                    .NOT_EQUALS;

                    case ">" ->
                            PlaceholderCondition.Operator
                                    .GREATER;

                    case ">=" ->
                            PlaceholderCondition.Operator
                                    .GREATER_OR_EQUAL;

                    case "<" ->
                            PlaceholderCondition.Operator
                                    .LESS;

                    case "<=" ->
                            PlaceholderCondition.Operator
                                    .LESS_OR_EQUAL;

                    case "contains" ->
                            PlaceholderCondition.Operator
                                    .CONTAINS;

                    default ->
                            throw new IllegalArgumentException(
                                    "Unknown operator"
                            );
                };

        return new PlaceholderCondition(
                placeholder,
                op,
                expected
        );
    }
}