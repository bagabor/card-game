package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.DeckDto;
import com.example.cardGame.services.DeckService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

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

    @PostMapping(path = "/decks/game/add")
    public ResponseEntity addDeck(@RequestParam(name = "deckId") Long deckId,
                                  @RequestParam(name = "gameId") Long gameId) {
        try {
            deckService.addDeckToGame(deckId, gameId);
            return ResponseEntity.status(OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
