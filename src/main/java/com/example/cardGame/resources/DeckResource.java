package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.DeckDto;
import com.example.cardGame.resources.dtos.GameDto;
import com.example.cardGame.services.DeckService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@AllArgsConstructor
public class DeckResource {

    private final DeckService deckService;


    @PostMapping(path = "/decks/create")
    public ResponseEntity createDeck() {
        try {
            DeckDto deckDto = deckService.createDeck();
            return ResponseEntity.status(CREATED).body(deckDto);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
