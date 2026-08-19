package com.advancedredeem.condition;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.Map;

public final class PlaceholderCondition
        implements RedeemCondition {

    public enum Operator {
        EQUALS,
        NOT_EQUALS,
        GREATER,
        GREATER_OR_EQUAL,
        LESS,
        LESS_OR_EQUAL,
        CONTAINS
    }

    private final String placeholder;
    private final Operator operator;
    private final String expected;

    public PlaceholderCondition(
            String placeholder,
            Operator operator,
            String expected
    ) {
        this.placeholder = placeholder;
        this.operator = operator;
        this.expected = expected;
    }

    @Override
    public String type() {
        return "placeholder";
    }

    @Override
    public boolean check(Player player) {

        String actual =
                PlaceholderAPI.setPlaceholders(
                        player,
                        placeholder
                );

        return compare(
                actual,
                expected,
                operator
        );
    }

    private boolean compare(
            String actual,
            String expected,
            Operator operator
    ) {

        if (operator == Operator.CONTAINS) {
            return actual.contains(expected);
        }

        if (operator == Operator.EQUALS) {
            return actual.equals(expected);
        }

        if (operator == Operator.NOT_EQUALS) {
            return !actual.equals(expected);
        }

        Double a = parseDouble(actual);
        Double b = parseDouble(expected);

        if (a != null && b != null) {

            return switch (operator) {
                case GREATER -> a > b;
                case GREATER_OR_EQUAL -> a >= b;
                case LESS -> a < b;
                case LESS_OR_EQUAL -> a <= b;
                default -> false;
            };
        }

        int comparison =
                actual.compareToIgnoreCase(expected);

        return switch (operator) {
            case GREATER -> comparison > 0;
            case GREATER_OR_EQUAL -> comparison >= 0;
            case LESS -> comparison < 0;
            case LESS_OR_EQUAL -> comparison <= 0;
            default -> false;
        };
    }

    private Double parseDouble(String value) {
        try {
            return Double.parseDouble(
                    value.trim()
            );
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    @Override
    public String description() {
        return placeholder
                + " "
                + operator.name()
                + " "
                + expected;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "type",
                type(),
                "placeholder",
                placeholder,
                "operator",
                operator.name(),
                "expected",
                expected
        );
    }
}