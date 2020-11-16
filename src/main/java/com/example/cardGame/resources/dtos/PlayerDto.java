package com.example.cardGame.resources.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PlayerDto {
    private Long id;
    private List<CardDto> cards;
}
