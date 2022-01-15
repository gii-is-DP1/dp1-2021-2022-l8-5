-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled,email) VALUES ('admin1','4dm1n',TRUE,'test9@test.com');
INSERT INTO authorities(id,username,authority) VALUES (1,'admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled,email) VALUES ('owner1','0wn3r',TRUE,'test@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('pabmargom3','1',TRUE,'test2@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('davzamfer','1',TRUE,'test3@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('dieruigil','1',TRUE,'test4@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('test','1',TRUE,'test5@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('framigdom','1',TRUE,'test6@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('pabalvcar','1',TRUE,'test7@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('josgarboh','1',TRUE,'test8@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('test2','1',TRUE,'test10@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('test3','1',TRUE,'test11@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('DELETED','P5xEr6oez!AH6vHBhVE8ZAeBo9&FIHdEfdB9X19fA4yPrhrKG%',FALSE,'Yh&as2FHef1O84Tfycp7ZcG@Yh&as2FHef1O84Tfycp7ZcG.n&GdiH');
INSERT INTO authorities(id,username,authority) VALUES (2,'owner1','owner');
INSERT INTO authorities(id,username,authority) VALUES (3,'pabmargom3','admin');
INSERT INTO authorities(id,username,authority) VALUES (4,'davzamfer','admin');
INSERT INTO authorities(id,username,authority) VALUES (5,'dieruigil','admin');
INSERT INTO authorities(id,username,authority) VALUES (6,'test','player');
INSERT INTO authorities(id,username,authority) VALUES (7,'framigdom','player');
INSERT INTO authorities(id,username,authority) VALUES (8,'pabalvcar','player');
INSERT INTO authorities(id,username,authority) VALUES (9,'josgarboh','player');
INSERT INTO authorities(id,username,authority) VALUES (10,'test2','player');
INSERT INTO authorities(id,username,authority) VALUES (11,'test3','player');



-- Players
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (0, 'Deleted', 'User' ,'DELETED','https://cdn-icons-png.flaticon.com/512/747/747969.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (1, 'Pablo', 'Marin' ,'pabmargom3','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (2, 'David', 'Zamora', 'davzamfer','https://www.w3schools.com/w3images/avatar2.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (3, 'Diego', 'Ruiz' ,'dieruigil','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (4, 'Player', 'Test' ,'test','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (5, 'Francisco Javier', 'Migueles' ,'framigdom','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (6, 'Pablo', 'Alvarez' ,'pabalvcar','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (7, 'Jose', 'Ignacio' ,'josgarboh','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (8, 'Player2', 'Test2' ,'test2','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player(id,first_name,last_name,username,avatar_url) VALUES (9, 'Player3', 'Test3' ,'test3','https://www.w3schools.com/w3images/avatar1.png');

-- Looking for players games
INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,startDate,canResolveActions) VALUES (6,'MINERAL_EXTRACTION',1,6,'2021-11-12 16:42:00',true);
INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,secondPlayer,thirdPlayer,startDate,canResolveActions) VALUES (4,'MINERAL_EXTRACTION',1,4,5,2,'2021-11-12 16:42:00',true);

 -- Finished games
INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,secondPlayer,thirdPlayer,startDate,finishDate,canResolveActions) VALUES (2,'ACTION_SELECTION',6,3,1,2,'2021-11-12 16:42:00', '2021-11-12 17:42:00',true);


 -- Mountain cards
 -- SEAM,FORGES_ALLOY,GET_HELP,ORC_RAIDERS,DRAGONS_KNOCKERS,SHIDE,

 -- Card sheet 1
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 0, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam1.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 0, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam2.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 0, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam3.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 1, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam4.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 1, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam5.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 1, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam6.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 1, 2, 'MINE', 1, '/resources/images/mountainCards/goldSeams/goldSeam.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 2, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam7.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 3, 2, 'MINE', 1, '/resources/images/mountainCards/goldSeams/goldSeam.png', 'SEAM');
 
 -- Card sheet 2
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 1, 0, 'MINE', 2, '/resources/images/mountainCards/alloySteels/alloySteel.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 2, 0, 'DEFEND', 2, '/resources/images/mountainCards/orcRaiders/orcRaiders.png', 'ORC_RAIDERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Sword', 'Return 3 steel to the supply then take 1 item', 3, 0, 'CRAFT', 3, '/resources/images/mountainCards/forges/forgeSword.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Great Dragon', 'If undefended at the end of the round, each player must return all gold they possess to the supply', 1, 1, 'DEFEND', 2, '/resources/images/mountainCards/dragons/dragon.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 2, 1, 'DEFEND', 3, '/resources/images/mountainCards/knockers/knockers.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 1, 0, 'MINE', 3, '/resources/images/mountainCards/alloySteels/alloySteel.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Mace', 'Return 2 steel and 1 gold to the supply then take 1 item', 1, 2, 'CRAFT', 2, '/resources/images/mountainCards/forges/ForgeMace.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 3, 2, 'MINE', 2, '/resources/images/mountainCards/goldSeams/goldSeam2.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 2, 'MINE', 2, '/resources/images/mountainCards/ironSeams/ironSeam7.png', 'SEAM');
 
 -- Card sheet 3

 -- Card sheet 4
 
 -- Card sheet 5
 
 -- Card sheet 6
 
 
 -- Special cards
 -- MUSERT_ARMY,HOLD_COUNCIL,SELL_ITEM,PAST_GLORIES,SPECIAL_ORDER,TURN_BACK,APPRENTICE,COLLAPSE_SHAFTS,RUN_AMOK
 
-- Special cards (card sheet 7 front & back)
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 1,'/resources/images/specialCards/musterAnArmy.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 2,'/resources/images/specialCards/holdACouncil.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 3,'/resources/images/specialCards/sellAnItem.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 4,'/resources/images/specialCards/pastGlories.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 5,'/resources/images/specialCards/specialOrder.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 6,'/resources/images/specialCards/turnBack.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 7,'/resources/images/specialCards/apprentice.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 8,'/resources/images/specialCards/collapseTheShafts.png');
 INSERT INTO special_cards(name, description, back_card, image) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 9,'/resources/images/specialCards/runAmok.png');

 INSERT INTO special_decks(xposition, yposition) VALUES (0,0);
 INSERT INTO special_decks(xposition, yposition) VALUES (0,1);
 
 INSERT INTO SPECIAL_DECKS_SPECIAL_CARD(SPECIAL_DECK_ID, SPECIAL_CARD_ID) VALUES (1,1);

-- Froge and alloy card resources
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Alloy Steel', 'STEEL', 2);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(1, 'IRON', 3);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Sword', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(2, 'STEEL', 3);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Mace', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(3, 'STEEL', 2);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(3, 'GOLD', 1);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Diadem', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(4, 'IRON', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(4, 'STEEL', 1);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Helm', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(5, 'STEEL', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(5, 'GOLD', 2);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Axe', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(6, 'STEEL', 2);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Crown', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(7, 'GOLD', 3);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Dagger', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(8, 'IRON', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(8, 'STEEL', 2);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Armour', 'ITEM', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(9, 'STEEL', 2);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(9, 'GOLD', 1);
 
 
-- INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup) VALUES ('Iron Seam', 'Take 3 iron from the supply', 0, 'MINE', 0);

INSERT INTO mountain_decks(xposition, yposition, image) VALUES (0,0,'resources/images/mountainDekc_img.png');

 -- Test para borrar
INSERT INTO mountain_decks(xposition, yposition, image) VALUES (1,1,'resources/images/mountainDekc_img.png');


 --Boards 
INSERT INTO boards(background,width,height,mountaindeck,game) VALUES ('resources/images/oro_erebor.jpg',750,600,1,1);
INSERT INTO boards(background,width,height,mountaindeck,game) VALUES ('resources/images/oro_erebor.jpg',750,600,1,3);

INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,1);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,2);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,3);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,4);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,5);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,6);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,7);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,8);
INSERT INTO MOUNTAIN_DECKS_MOUNTAIN_CARDS(MOUNTAIN_DECK_ID, MOUNTAIN_CARDS_ID) VALUES (1,9);


-- WORKERS

INSERT INTO Workers(id, xposition, yposition, status, gameId, playerId) VALUES (1, 1, 1, FALSE, 1, 1);

-- RESOURCES

INSERT INTO resources(id,badges,gold,iron,items,steel,gameId,playerId)  VALUES (1,2,3,6,2,1,1,1);