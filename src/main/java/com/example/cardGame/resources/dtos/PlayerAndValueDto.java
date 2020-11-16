package com.example.cardGame.resources.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerAndValueDto {
    private String username;
    private int value;
}
