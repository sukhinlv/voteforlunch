package ru.jsft.voteforlunch.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class VoteDistribution {
    private long restaurantId;
    private String restaurantName;
    private long voteCount;
}
