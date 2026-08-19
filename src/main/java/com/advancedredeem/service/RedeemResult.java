package com.advancedredeem.service;

public enum RedeemResult {

    SUCCESS,

    NOT_FOUND,

    EXPIRED,

    GLOBAL_LIMIT,

    PLAYER_LIMIT,

    CONDITION_FAILED,

    REWARD_FAILED,

    INTERNAL_ERROR
}