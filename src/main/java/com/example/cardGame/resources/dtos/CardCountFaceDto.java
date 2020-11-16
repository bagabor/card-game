package com.example.cardGame.resources.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CardCountFaceDto {

    private String name;
    private int count;
}
