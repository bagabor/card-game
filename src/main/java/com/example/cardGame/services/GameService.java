package com.example.cardGame.services;

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
import java.util.Optional;

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

    private long checkPlayer(Game game, String username) {
        return game.getListOfPlayers()
                .stream()
                .filter(player -> player.getUsername().equals(username))
                .count();
    }

    public void removePlayerFromAGame(Long gameId, String username) throws EntityNotFoundException{
        gameRepository.findById(gameId).orElseThrow(EntityNotFoundException::new);
        Player player = playerRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        playerRepository.delete(player);
    }
}
