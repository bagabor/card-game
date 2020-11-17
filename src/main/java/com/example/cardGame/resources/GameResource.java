package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.*;
import com.example.cardGame.services.GameService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
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
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteGame(@PathVariable("id") Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.status(OK).body("Game has been deleted with id: " + id);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @PostMapping(path = "/player/add")
    public ResponseEntity addPlayerToGame(@RequestParam(name = "gameId") Long gameId,
                                          @RequestParam(name = "username") String username) {
        try {
            gameService.addPlayerToGame(gameId, username);
            return ResponseEntity.status(CREATED).body("Player has been added to game, game id is: " + gameId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @DeleteMapping(path = "/player/delete")
    public ResponseEntity removePlayerFromAGame(@RequestParam(name = "gameId") Long gameId,
                                                @RequestParam(name = "username") String username) {
        try {
            gameService.removePlayerFromAGame(gameId, username);
            return ResponseEntity.status(OK).body("Player has been removed");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @PostMapping(path = "/player/deal")
    public ResponseEntity dealCardsForPlayers(@RequestParam(name = "gameId") Long gameId,
                                              @RequestParam(name = "username") String username,
                                              @RequestParam(name = "numberOfDeals") int numberOfDeals) {
        try {
            gameService.shuffleCards(gameId);
            PlayerDto player = gameService.dealCardsForPlayers(gameId, username, numberOfDeals);
            return ResponseEntity.status(OK).body(player);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @GetMapping(path = "/{id}/players")
    public ResponseEntity getPlayersWithPoints(@PathVariable(name = "id") Long gameId) {
        try {
            List<PlayerAndValueDto> cards = gameService.getPLayersWithTheirPoints(gameId);
            return ResponseEntity.status(OK).body(cards);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @GetMapping(path = "/{id}/cards")
    public ResponseEntity getRemainingCards(@PathVariable(name = "id") Long gameId) {
        try {
            List<RemainingCardDto> cards = gameService.getRemainingCards(gameId);
            return ResponseEntity.status(OK).body(cards);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @GetMapping(path = "/{id}/cards/count")
    public ResponseEntity getCountOfEachCard(@PathVariable(name = "id") Long gameId) {
        try {
            List<CardCountDto> cards = gameService.getCountOfEachCard(gameId);
            return ResponseEntity.status(OK).body(cards);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

    @PostMapping(path = "/{id}/shuffle")
    public ResponseEntity shuffle(@PathVariable(name = "id") Long gameId) {
        try {
            gameService.shuffleCards(gameId);
            return ResponseEntity.status(OK).body("Shuffle has been done");
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body("Some error has been occurred");
        }
    }

}
