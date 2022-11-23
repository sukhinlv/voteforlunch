package ru.jsft.voteforlunch.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VoteDistribution {
    private long restaurantId;
    private String restaurantName;
    private long voteCount;
}
