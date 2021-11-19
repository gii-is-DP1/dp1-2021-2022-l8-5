-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,email) VALUES ('admin1','4dm1n',TRUE,'test9@test.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled,email) VALUES ('owner1','0wn3r',TRUE,'test@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('pabmargom3','0wn3r',TRUE,'test2@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('davzamfer','0wn3r',TRUE,'test3@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('dieruigil','0wn3r',TRUE,'test4@test.com');
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
INSERT INTO authorities(id,username,authority) VALUES (3,'pabmargom3','owner');
INSERT INTO authorities(id,username,authority) VALUES (4,'davzamfer','owner');

-- Players
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES  (1, 'Pablo', 'Marin' ,'pabmargom3','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (2, 'David', 'Zamora', 'davzamfer','https://www.w3schools.com/w3images/avatar2.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES  (3, 'Diego', 'Ruiz' ,'dieruigil','https://www.w3schools.com/w3images/avatar1.png');


INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,secondPlayer,thirdPlayer,startDate,finishDate) VALUES (2,'ACTION_SELECTION',2,3,1,2,'2021-11-12 16:42:00', '2021-11-12 17:42:00');


 -- Card sheet 1
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 0, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 0, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 0, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 1, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 1, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 1, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Gold Seam', 'Take 1 gold from the supply', 1, 2, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 2, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Gold Seam', 'Take 1 gold from the supply', 3, 2, 'MINE', 1);
 
 -- Card sheet 2
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 1, 0, 'MINE', 2);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 2, 0, 'DEFEND', 2);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Forge Sword', 'Return 3 steel to the supply then take 1 item', 3, 0, 'CRAFT', 3);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Great Dragon', 'If undefended at the end of the round, each player must return all gold they possess to the supply', 1, 1, 'DEFEND', 2);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 2, 1, 'DEFEND', 3);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 3, 1, 'MINE', 3);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Forge Mace', 'Return 2 steel and 1 gold to the supply then take 1 item', 1, 2, 'CRAFT', 2);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Gold Seam', 'Take 1 gold from the supply', 2, 2, 'MINE', 2);
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 2, 'MINE', 2);
 
 -- Card sheet 3

 -- Card sheet 4
 
 -- Card sheet 5
 
 -- Card sheet 6
 
-- Special cards (card sheet 7 front & back)
 INSERT INTO special_cards(name, description, back_card) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 1);
 INSERT INTO special_decks(xposition, yposition) VALUES (0,0);
 INSERT INTO SPECIAL_DECKS_SPECIAL_CARD(SPECIAL_DECK_ID, SPECIAL_CARD_ID) VALUES (1,1);
-- INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 0, 'MINE', 0);

INSERT INTO mountain_decks(xposition, yposition) VALUES (0,0);
 
INSERT INTO boards(background,width,height,mountaindeck) VALUES ('resources/images/oro_erebor.jpg',750,600,1);

INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,1);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,2);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,3);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,4);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,5);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,6);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,7);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,8);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,9);
