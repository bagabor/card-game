drop table if exists CARD cascade ALL;
drop table if exists DECK cascade ALL;
drop table if exists PLAYER cascade ALL;
drop table if exists GAME cascade ALL;

create table GAME(
    ID int not null AUTO_INCREMENT,
    PRIMARY KEY(ID)
);

create table DECK(
    ID int not null AUTO_INCREMENT,
    GAME_ID int,
    PRIMARY KEY(ID),
    CONSTRAINT deck_game_fk FOREIGN KEY (GAME_ID) REFERENCES DECK (ID)
);

create table PLAYER(
    ID int not null AUTO_INCREMENT,
    USERNAME varchar(50),
    GAME_ID int,
    PRIMARY KEY(ID),
    CONSTRAINT player_game_fk FOREIGN KEY (GAME_ID) REFERENCES DECK (ID)
);

create table CARD(
    ID int not null AUTO_INCREMENT,
    CARD_TYPE varchar(10),
    VALUE int,
    DECK_ID int,
    PRIMARY KEY(ID),
    CONSTRAINT card_deck_fk FOREIGN KEY (DECK_ID) REFERENCES DECK (ID)
);

-- insert into DECK (ID) values (1);
-- insert into CARD (CARD_TYPE, VALUE, DECK_ID) values ('DIAMONDS', 2, 1);