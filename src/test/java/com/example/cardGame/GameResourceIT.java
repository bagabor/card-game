package com.example.cardGame;

import com.example.cardGame.services.DeckService;
import com.example.cardGame.services.GameService;
import com.example.cardGame.services.PlayerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class GameResourceIT {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeckService deckService;

    @MockBean
    private GameService gameService;

    @MockBean
    private PlayerService playerService;

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(post("/games/create"))
                .andDo(print())
                .andExpect(status().isCreated());
    }
}
