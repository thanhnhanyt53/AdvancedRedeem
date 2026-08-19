package com.advancedredeem.condition;

import java.util.Map;

public interface ConditionProvider {

    String id();

    RedeemCondition create(
            Map<String, String> data
    );
}