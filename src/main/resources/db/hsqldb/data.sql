-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('pabmargom3','0wn3r',TRUE);
INSERT INTO users(username,password,enabled) VALUES ('davzamfer','0wn3r',TRUE);
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
INSERT INTO authorities(id,username,authority) VALUES (3,'pabmargom3','owner');
INSERT INTO authorities(id,username,authority) VALUES (4,'davzamfer','owner');

-- Players of the app
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('TheNeoStormZ','pass',TRUE,42343234);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('Vell','pass',TRUE,322223343);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('BetrayalGD','pass',TRUE,4543656);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('DRG64','pass',TRUE,32321243);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('xiscomigueles','pass',TRUE,45454354);


 -- Card sheet 1
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 0, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 4, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 5, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Gold Seam', 'Take 1 gold from the supply', 6, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 7, 'MINE', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Gold Seam', 'Take 1 gold from the supply', 8, 'MINE', 1);
 
 -- Card sheet 2
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 0, 'MINE', 2);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 1, 'DEFEND', 2);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Forge Sword', 'Return 3 steel to the supply then take 1 item', 2, 'CRAFT', 3);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Great Dragon', 'If undefended at the end of the round, each player must return all gold they possess to the supply', 3, 'DEFEND', 2);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 4, 'DEFEND', 3);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 5, 'MINE', 3);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Forge Mace', 'Return 2 steel and 1 gold to the supply then take 1 item', 6, 'CRAFT', 2);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Gold Seam', 'Take 1 gold from the supply', 7, 'MINE', 2);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 8, 'MINE', 2);
 
 -- Card sheet 3

 -- Card sheet 4
 
 -- Card sheet 5
 
 -- Card sheet 6
 
-- Special cards (card sheet 7 front & back)
 INSERT INTO special_cards(name, description, back_card) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 1);
 INSERT INTO mountain_cards(name, description, position, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 0, 'MINE', 0);




-- Players2
INSERT INTO player2 (id,first_name,last_name,username,avatar_url) VALUES  (1, 'Pablo', 'Marin' ,'pabmargom3','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player2  (id,first_name,last_name,username,avatar_url) VALUES (2, 'David', 'Zamora', 'davzamfer','https://www.w3schools.com/w3images/avatar2.png');

INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,secondPlayer,thirdPlayer,startDate,finishDate) VALUES (2,'ACTION_SELECTION',2,5,1,2,'2021-11-12 16:42:00', '2021-11-12 17:42:00');
