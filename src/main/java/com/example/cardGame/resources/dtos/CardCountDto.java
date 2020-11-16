package com.example.cardGame.resources.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CardCountDto {

    private String typeName;
    private List<CardCountFaceDto> cardTypes;
}
