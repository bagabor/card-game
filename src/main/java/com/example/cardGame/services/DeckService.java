package com.example.cardGame.services;

import com.example.cardGame.dao.models.Deck;
import com.example.cardGame.dao.repositories.DeckRepository;
import com.example.cardGame.resources.dtos.CardDto;
import com.example.cardGame.resources.dtos.DeckDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    public DeckDto createDeck() {

        Deck deck = Deck.builder().build();
        addCards(deck);
        Deck persistedDeck = deckRepository.save(deck);

        return DeckDto.builder()
                .id(persistedDeck.getId())
                .cards(deck.getCards()
                        .stream()
                        .map(card -> CardDto.builder()
                                .id(card.getId())
                                .cardType(card.getCardType())
                                .value(card.getValue())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }

    private void addCards(Deck deck) {

    }
}
