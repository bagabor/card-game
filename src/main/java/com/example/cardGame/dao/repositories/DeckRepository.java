package com.example.cardGame.dao.repositories;

import com.example.cardGame.dao.models.Deck;
import com.example.cardGame.dao.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    Optional<List<Deck>> findAllByGame(Game game);
}
