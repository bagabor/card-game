package com.example.cardGame.resources.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RemainingCardDto {
    private String cardType;
    private long numberOfRemainingCards;
}
