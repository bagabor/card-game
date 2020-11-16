package com.example.cardGame.services;

import com.example.cardGame.dao.models.Card;
import com.example.cardGame.dao.models.Deck;
import com.example.cardGame.dao.models.Game;
import com.example.cardGame.dao.models.Player;
import com.example.cardGame.dao.repositories.GameRepository;
import com.example.cardGame.dao.repositories.PlayerRepository;
import com.example.cardGame.exceptions.PlayerAlreadyAssignedToGameException;
import com.example.cardGame.resources.dtos.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

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
}
