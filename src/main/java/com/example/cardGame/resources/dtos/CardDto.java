package com.example.cardGame.resources.dtos;

import com.example.cardGame.utils.CardType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardDto {

    private Long id;
    private CardType cardType;
    private int value;
}
