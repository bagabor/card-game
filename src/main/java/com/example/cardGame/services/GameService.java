package com.example.cardGame.services;

import com.example.cardGame.dao.models.Card;
import com.example.cardGame.dao.models.Deck;
import com.example.cardGame.dao.models.Game;
import com.example.cardGame.dao.models.Player;
import com.example.cardGame.dao.repositories.DeckRepository;
import com.example.cardGame.dao.repositories.GameRepository;
import com.example.cardGame.dao.repositories.PlayerRepository;
import com.example.cardGame.exceptions.PlayerAlreadyAssignedToGameException;
import com.example.cardGame.resources.dtos.*;
import com.example.cardGame.utils.CardType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.*;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final DeckRepository deckRepository;

    public GameDto createGame() {
        Game game = gameRepository.save(Game.builder().build());
        return GameDto.builder()
                .id(game.getId())
                .build();
    }

    //TODO: add better exception handling...
    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }

    @Transactional
    public void addPlayerToGame(Long gameId, String username) throws EntityNotFoundException, PlayerAlreadyAssignedToGameException {
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);

        if (checkPlayer(game, username) > 0) {
            throw new PlayerAlreadyAssignedToGameException();
        } else {
            Player player = playerRepository.save(Player.builder()
                    .username(username)
                    .game(game)
                    .build());
            game.getListOfPlayers().add(player);
        }
    }

    @Transactional
    public void dealCardsForPlayers(Long gameId, String username, int numberOfDeals) throws RuntimeException {
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        Player player = playerRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        if (numberOfDeals < 1) {
            throw new RuntimeException("Deal number must be bigger than 0!");
        }
        dealCards(game, player, numberOfDeals);
    }

    private void dealCards(Game game, Player player, int numberOfDeals) {

//        TODO: SHUFFLE IS NEEDED
        List<Card> allCards = new ArrayList<>();
        game.getListOfDecks().forEach(deck -> allCards.addAll(deck.getCards().stream()
                .filter(card -> card.getPlayer() == null)
                .collect(toList())));

        Collections.shuffle(allCards);

        for (int j = 0; j < numberOfDeals; j++) {
            Card card = allCards.get(0);
            player.getCards().add(card);
            card.setPlayer(player);
            allCards.remove(0);
        }
    }

    //    TODO: NEEDS TO BE IMPLEMENTED
    private void shuffleCards(List<Card> allCards) {

    }

    private long checkPlayer(Game game, String username) {
        return game.getListOfPlayers()
                .stream()
                .filter(player -> player.getUsername().equals(username))
                .count();
    }

    public void removePlayerFromAGame(Long gameId, String username) throws EntityNotFoundException {
        gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        Player player = playerRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        playerRepository.delete(player);
    }

    @Transactional(readOnly = true)
    public List<PlayerAndValueDto> getPLayersWithTheirPoints(Long gameId) throws EntityNotFoundException {
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        List<PlayerAndValueDto> playersWithPoints = game.getListOfPlayers().stream()
                .map(player -> {
                    int value = 0;
                    for (int i = 0; i < player.getCards().size(); i++) {
                        value = value + player.getCards().get(i).getValue();
                    }
                    return PlayerAndValueDto.builder()
                            .username(player.getUsername())
                            .value(value)
                            .build();
                })
                .collect(toList());
        playersWithPoints.sort(comparingInt(PlayerAndValueDto::getValue).reversed());
        return playersWithPoints;
    }

    @Transactional(readOnly = true)
    public List<RemainingCardDto> getRemainingCards(Long gameId) throws EntityNotFoundException {
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        List<Deck> listOfDecks = deckRepository.findByGame(game).orElseThrow(EntityNotFoundException::new);

        List<Card> allCards = new ArrayList<>();
        listOfDecks.forEach(deck -> allCards.addAll(deck.getCards().stream()
                .filter(card -> card.getPlayer() == null)
                .collect(toList())));
        List<RemainingCardDto> listOfRemainingCards = new ArrayList<>();

        for (CardType type : CardType.values()) {
            long numberOfCards = allCards.stream()
                    .filter(card -> type.equals(card.getCardType()))
                    .count();
            listOfRemainingCards.add(RemainingCardDto.builder()
                    .cardType(type.name())
                    .numberOfRemainingCards(numberOfCards)
                    .build());
        }
        return listOfRemainingCards;
    }

    @Transactional(readOnly = true)
    public List<CardCountDto> getCountOfEachCard(Long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        List<Deck> listOfDecks = deckRepository.findByGame(game).orElseThrow(EntityNotFoundException::new);
        List<Card> allCards = new ArrayList<>();
        listOfDecks.forEach(deck -> allCards.addAll(deck.getCards().stream()
                .filter(card -> card.getPlayer() == null)
                .collect(toList())));

        List<CardCountDto> listOfCardCountDtos = new ArrayList<>();
        Map<Integer, String> cardNames = getCardNames();

        for (CardType type : CardType.values()) {
            List<CardCountFaceDto> cardTypes = new ArrayList<>();

//        type filtering
            List<Card> listOfCardsFromTheSameType = allCards.stream()
                    .filter(card -> type.equals(card.getCardType()))
                    .collect(toList());

//            card filtering based on their values 1-13 values are used
            for (int i = 13; i >= 1; i--) {
                List<Card> listOfSameCardFaces = new ArrayList<>();
                for (int j = 0; j < listOfCardsFromTheSameType.size(); j++) {
                    if (listOfCardsFromTheSameType.get(j).getValue() == i) {
                        listOfSameCardFaces.add(listOfCardsFromTheSameType.get(j));
                    }
                }
                cardTypes.add(CardCountFaceDto.builder()
                        .name(cardNames.get(i))
                        .count(listOfSameCardFaces.size())
                        .build());
            }

            listOfCardCountDtos.add(CardCountDto.builder()
                    .typeName(type.name())
                    .cardTypes(cardTypes)
                    .build());
        }
        return listOfCardCountDtos;
    }

    private Map<Integer, String> getCardNames() {
        String[] cardNames = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        Map<Integer, String> mapOfCardNames = new HashMap<>();
        for (int i = 1; i <= 13; i++) {
            mapOfCardNames.put(i, cardNames[i - 1]);
        }
        return mapOfCardNames;
    }
}
