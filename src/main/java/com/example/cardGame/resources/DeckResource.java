package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.DeckDto;
import com.example.cardGame.services.DeckService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/decks")
public class DeckResource {

    private final DeckService deckService;

    @PostMapping(path = "/create")
    public ResponseEntity createDeck() {
        try {
            DeckDto deckDto = deckService.createDeck();
            return ResponseEntity.status(CREATED).body(deckDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred.");
        }
    }

    @PostMapping(path = "/game/add")
    public ResponseEntity addDeck(@RequestParam(name = "deckId") Long deckId,
                                  @RequestParam(name = "gameId") Long gameId) {
        try {
            deckService.addDeckToGame(deckId, gameId);
            return ResponseEntity.status(OK).body("Deck has been added to the game.");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred.");
        }
    }
}
