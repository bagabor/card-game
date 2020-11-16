package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.*;
import com.example.cardGame.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping("/games")
public class GameResource {

    private final GameService gameService;

    @PostMapping(path = "/create")
    public ResponseEntity createGame() {
        try {
            GameDto gameDto = gameService.createGame();
            return ResponseEntity.status(CREATED).body(gameDto);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteGame(@PathVariable("id") Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.status(OK).body("Game has been deleted with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping(path = "/player/add")
    public ResponseEntity addPlayerToGame(@RequestParam(name = "gameId") Long gameId,
                                          @RequestParam(name = "username") String username) {
        try {
            gameService.addPlayerToGame(gameId, username);
            return ResponseEntity.status(CREATED).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/player/delete")
    public ResponseEntity removePlayerFromAGame(@RequestParam(name = "gameId") Long gameId,
                                                @RequestParam(name = "username") String username) {
        try {
            gameService.removePlayerFromAGame(gameId, username);
            return ResponseEntity.status(OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping(path = "/player/deal")
    public ResponseEntity dealCardsForPlayers(@RequestParam(name = "gameId") Long gameId,
                                              @RequestParam(name = "username") String username,
                                              @RequestParam(name = "numberOfDeals") int numberOfDeals) {
        try {
            gameService.dealCardsForPlayers(gameId, username, numberOfDeals);
            return ResponseEntity.status(OK).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/players")
    public ResponseEntity getPlayersWithPoints(@PathVariable(name = "id") Long gameId) {
        try {
            List<PlayerAndValueDto> cards = gameService.getPLayersWithTheirPoints(gameId);
            return ResponseEntity.status(CREATED).body(cards);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/cards")
    public ResponseEntity getRemainingCards(@PathVariable(name = "id") Long gameId) {
        try {
            List<RemainingCardDto> cards = gameService.getRemainingCards(gameId);
            return ResponseEntity.status(CREATED).body(cards);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping(path = "/{id}/cards/count")
    public ResponseEntity getCountOfEachCard(@PathVariable(name = "id") Long gameId) {
        try {
            List<CardCountDto> cards = gameService.getCountOfEachCard(gameId);
            return ResponseEntity.status(CREATED).body(cards);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
