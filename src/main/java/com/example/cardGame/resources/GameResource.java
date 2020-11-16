package com.example.cardGame.resources;

import com.example.cardGame.resources.dtos.GameDto;
import com.example.cardGame.services.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
public class GameResource {

    private final GameService gameService;

    @PostMapping(path = "/games/create")
    public ResponseEntity createGame() {
        try {
            GameDto gameDto = gameService.createGame();
            return ResponseEntity.status(CREATED).body(gameDto);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/games/delete/{id}")
    public ResponseEntity deleteGame(@PathVariable("id") Long id) {
        try {
            gameService.deleteGame(id);
            return ResponseEntity.status(OK).body("Game has been deleted with id: " + id);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping(path = "/games/player/add")
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

    @DeleteMapping(path = "/games/player/delete")
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

}
