package com.example.cardGame.dao.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Player {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String username;

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "game_id", referencedColumnName = "id")
    @JsonIgnore
    private Game game;
}
