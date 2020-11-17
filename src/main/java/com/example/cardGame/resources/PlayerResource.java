package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.CardDto;
import com.example.cardGame.services.PlayerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
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
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

}
