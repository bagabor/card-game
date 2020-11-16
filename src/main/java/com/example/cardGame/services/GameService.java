package com.example.cardGame.services;

import com.example.cardGame.dao.models.Game;
import com.example.cardGame.dao.repositories.GameRepository;
import com.example.cardGame.resources.dtos.GameDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public GameDto createGame() {
        Game game = gameRepository.save(Game.builder().build());
        return GameDto.builder()
                .id(game.getId())
                .build();
    }

    public void deleteGame(Long id) {
        gameRepository.deleteById(id);
    }
}
