package com.example.cardGame.dao.models;

import com.example.cardGame.utils.CardType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@Table
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    @Enumerated(STRING)
    private CardType cardType;

    @Column
    private int value;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "deck_id", nullable = false, referencedColumnName = "id")
    @JsonIgnore
    private Deck deck;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "player_id", referencedColumnName = "id")
    @JsonIgnore
    private Player player;
}
