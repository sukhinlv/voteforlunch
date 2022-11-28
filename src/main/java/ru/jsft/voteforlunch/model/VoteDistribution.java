package ru.jsft.voteforlunch.model;

public record VoteDistribution (
    long restaurantId,
    String restaurantName,
    long voteCount
) {}
