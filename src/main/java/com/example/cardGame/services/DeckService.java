package com.example.cardGame.services;

import com.example.cardGame.dao.models.Card;
import com.example.cardGame.dao.models.Deck;
import com.example.cardGame.dao.models.Game;
import com.example.cardGame.dao.repositories.DeckRepository;
import com.example.cardGame.dao.repositories.GameRepository;
import com.example.cardGame.resources.dtos.CardDto;
import com.example.cardGame.resources.dtos.DeckDto;
import com.example.cardGame.utils.CardType;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final GameRepository gameRepository;

    @Transactional
    public DeckDto createDeck() {
        Deck deck = Deck.builder()
                .cards(new ArrayList<>())
                .build();
        Deck persistedDeck = deckRepository.save(deck);
        log.info("deck has been saved with id: " + deck.getId());

        addCards(deck);
        log.info("cards have been saved for deck with id: " + deck.getId());
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

    @Transactional
    public void addDeckToGame(Long deckId, Long gameId) throws EntityNotFoundException{
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        Deck deck = deckRepository.findById(deckId).orElseThrow(EntityNotFoundException::new);
        deck.setGame(game);
        log.info("deck : " + deck.getId() + " has been added to game: " + game.getId());
    }

    private void addCards(Deck deck) {
        for (CardType type : CardType.values()) {
            for (int i = 1; i < 14; i++) {
                deck.getCards().add(Card.builder()
                        .cardType(type)
                        .value(i)
                        .deck(deck)
                        .build());
            }
        }
    }
}
