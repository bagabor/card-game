package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.CardDto;
import com.example.cardGame.services.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@AllArgsConstructor
public class PlayerResource {

    private final PlayerService playerService;


    @GetMapping(path = "/players/{id}/cards")
    public ResponseEntity createDeck(@PathVariable("id") Long id) {
        try {
            List<CardDto> cards = playerService.getCards(id);
            return ResponseEntity.status(CREATED).body(cards);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
