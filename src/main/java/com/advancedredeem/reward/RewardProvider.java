package com.advancedredeem.reward;

import java.util.Map;

@FunctionalInterface
public interface RewardProvider {

    Reward create(
            Map<String, String> data
    );
}