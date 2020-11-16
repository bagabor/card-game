package com.example.cardGame.services;

import com.example.cardGame.dao.models.Player;
import com.example.cardGame.dao.repositories.PlayerRepository;
import com.example.cardGame.resources.dtos.CardDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Transactional(readOnly = true)
    public List<CardDto> getCards(Long id) throws EntityNotFoundException {
        Player player = playerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return player.getCards().stream()
                .map(card -> CardDto.builder()
                        .id(card.getId())
                        .value(card.getValue())
                        .cardType(card.getCardType())
                        .build())
                .collect(toList());
    }
}
