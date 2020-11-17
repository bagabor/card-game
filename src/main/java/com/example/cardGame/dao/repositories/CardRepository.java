package com.example.cardGame.dao.repositories;

import com.example.cardGame.dao.models.Card;
import com.example.cardGame.dao.models.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findAllByDeck(Deck deck);

    void deleteAllByDeck(Deck deck);
}
