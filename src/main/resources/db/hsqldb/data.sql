-- USERS
INSERT INTO users(username,password,enabled,email) VALUES ('pabmargom3','admin1',TRUE,'test2@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('davzamfer','admin1',TRUE,'test3@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('dieruigil','admin1',TRUE,'test4@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('test','admin1',TRUE,'test5@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('framigdom','admin1',TRUE,'test6@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('pabalvcar','admin1',TRUE,'test7@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('josgarboh','admin1',TRUE,'test8@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('test2','admin1',TRUE,'test10@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('test3','admin1',TRUE,'test11@test.com');
INSERT INTO users(username,password,enabled,email) VALUES ('DELETED','P5xEr6oez!AH6vHBhVE8ZAeBo9&FIHdEfdB9X19fA4yPrhrK%',FALSE,'Yh&as2FHef1O84Tfycp7ZcG@Yh&as2FHef1O84Tfycp7ZcG.n&GdiH');
INSERT INTO authorities(id,username,authority) VALUES (1,'pabmargom3','admin');
INSERT INTO authorities(id,username,authority) VALUES (2,'davzamfer','admin');
INSERT INTO authorities(id,username,authority) VALUES (3,'dieruigil','admin');
INSERT INTO authorities(id,username,authority) VALUES (4,'test','player');
INSERT INTO authorities(id,username,authority) VALUES (5,'framigdom','player');
INSERT INTO authorities(id,username,authority) VALUES (6,'pabalvcar','player');
INSERT INTO authorities(id,username,authority) VALUES (7,'josgarboh','player');
INSERT INTO authorities(id,username,authority) VALUES (8,'test2','player');
INSERT INTO authorities(id,username,authority) VALUES (9,'test3','player');


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
INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,secondPlayer,thirdPlayer,startDate,canResolveActions, musterAnArmyEffect) VALUES (4,'MINERAL_EXTRACTION',1,4,5,2,'2021-11-12 16:42:00',true,false);

 -- Finished games
INSERT INTO games(currentPlayer,currentPhase,currentRound,firstPlayer,secondPlayer,thirdPlayer,startDate,finishDate,canResolveActions) VALUES (2,'ACTION_SELECTION',6,3,1,2,'2021-11-12 16:42:00', '2021-11-12 17:42:00',true);


 -- Mountain cards
 -- SEAM,FORGES_ALLOY,GET_HELP,ORC_RAIDERS,DRAGONS_KNOCKERS,SHIDE

 -- Card sheet 1
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 0, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam1.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 0, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam2.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 0, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam3.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 1, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam4.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 1, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam5.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 1, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam6.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 1, 2, 'MINE', 1, '/resources/images/mountainCards/goldSeams/goldSeam.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 2, 2, 'MINE', 1, '/resources/images/mountainCards/ironSeams/ironSeam7.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 3, 2, 'MINE', 1, '/resources/images/mountainCards/goldSeams/goldSeam2.png', 'SEAM');
 
 -- Card sheet 2
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 1, 0, 'MINE', 2, '/resources/images/mountainCards/alloySteels/alloySteel8.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 2, 0, 'DEFEND', 2, '/resources/images/mountainCards/orcRaiders/orcRaiders.png', 'ORC_RAIDERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Sword', 'Return 3 steel to the supply then take 1 item', 3, 0, 'CRAFT', 3, '/resources/images/mountainCards/forges/forgeSword.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Great Dragon', 'If undefended at the end of the round, each player must return all gold they possess to the supply', 1, 1, 'DEFEND', 2, '/resources/images/mountainCards/greatDragons/greatDragon.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 2, 1, 'DEFEND', 3, '/resources/images/mountainCards/knockers/knockers.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 3, 1, 'MINE', 3, '/resources/images/mountainCards/alloySteels/alloySteel5.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Mace', 'Return 2 steel and 1 gold to the supply then take 1 item', 1, 2, 'CRAFT', 2, '/resources/images/mountainCards/forges/forgeMace.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 2, 2, 'MINE', 2, '/resources/images/mountainCards/goldSeams/goldSeam3.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 2, 'MINE', 2, '/resources/images/mountainCards/ironSeams/ironSeam8.png', 'SEAM');
 
 -- Card sheet 3
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 1, 0, 'MINE', 3, '/resources/images/mountainCards/alloySteels/alloySteel.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 2, 0, 'DEFEND', 3, '/resources/images/mountainCards/orcRaiders/orcRaiders2.png', 'ORC_RAIDERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 3, 0, 'CRAFT', 2, '/resources/images/mountainCards/alloySteels/alloySteel3.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Dragon', 'If undefended at the end of the round, each player must return 1 gold to the supply', 1, 1, 'DEFEND', 3, '/resources/images/mountainCards/dragons/dragon.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 2, 1, 'MINE', 3, '/resources/images/mountainCards/alloySteels/alloySteel.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Get Help', 'You may place 2 adittional workers this round. If you are the first player, the next player will become the first one', 3, 1, 'AID', 3, '/resources/images/mountainCards/getHelps/getHelp.png', 'GET_HELP');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Diadem', 'Return 1 iron, 1 steel and 1 gold to the supply then take 1 item', 1, 2, 'CRAFT', 3, '/resources/images/mountainCards/forges/forgeDiadem.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Helm', 'Return 1 steel and 2 gold to the supply then take 1 item', 2, 2, 'CRAFT', 3, '/resources/images/mountainCards/forges/forgeHelm.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Dragon', 'If undefended at the end of the round, each player must return 1 gold to the supply', 3, 2, 'MINE', 3, '/resources/images/mountainCards/dragons/dragon2.png', 'DRAGONS_KNOCKERS');

 -- Card sheet 4
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 1, 0, 'MINE', 2, '/resources/images/mountainCards/goldSeams/goldSeam4.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 2, 0, 'MINE', 2, '/resources/images/mountainCards/alloySteels/alloySteel2.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 3, 0, 'DEFEND', 3, '/resources/images/mountainCards/orcRaiders/orcRaiders3.png', 'ORC_RAIDERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Axe', 'Return 2 steel to the supply then take 1 item', 1, 1, 'CRAFT', 2, '/resources/images/mountainCards/forges/forgeAxe.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Get Help', 'You may place 2 adittional workers this round. If you are the first player, the next player will become the first one', 2, 1, 'AID', 3, '/resources/images/mountainCards/getHelps/getHelp2.png', 'GET_HELP');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Crown', 'Return 3 gold to the supply then take 1 item', 3, 1, 'CRAFT', 3, '/resources/images/mountainCards/forges/forgeCrown.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 1, 2, 'MINE', 2, '/resources/images/mountainCards/ironSeams/ironSeam9.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Dragon', 'If undefended at the end of the round, each player must return 1 gold to the supply', 2, 2, 'DEFEND', 2, '/resources/images/mountainCards/dragons/dragon3.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Iron Seam', 'Take 3 iron from the supply', 3, 2, 'MINE', 2, '/resources/images/mountainCards/ironSeams/ironSeam10.png', 'SEAM');
 
 -- Card sheet 5
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Sidhe', 'If undefended at the end of the round, each player must replace two gold they posesss with two iron', 1, 0, 'DEFEND', 2, '/resources/images/mountainCards/sidhes/sidhe.png', 'SHIDE');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Sidhe', 'If undefended at the end of the round, each player must replace two gold they posesss with two iron', 2, 0, 'DEFEND', 2, '/resources/images/mountainCards/sidhes/sidhe2.png', 'SHIDE');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Get Help', 'You may place 2 adittional workers this round. If you are the first player, the next player will become the first one', 3, 0, 'AID', 3, '/resources/images/mountainCards/getHelps/getHelp3.png', 'GET_HELP');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 1, 1, 'MINE', 3, '/resources/images/mountainCards/goldSeams/goldSeam5.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 2, 1, 'MINE', 2, '/resources/images/mountainCards/goldSeams/goldSeam6.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 3, 1, 'DEFEND', 2, '/resources/images/mountainCards/knockers/knockers2.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 1, 2, 'DEFEND', 2, '/resources/images/mountainCards/knockers/knockers3.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 2, 2, 'MINE', 3, '/resources/images/mountainCards/alloySteels/alloySteel6.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge dagger', 'Return 1 iron and 2 steel to the supply then take 1 item', 3, 2, 'CRAFT', 2, '/resources/images/mountainCards/forges/forgeDagger.png', 'FORGES_ALLOY');
 
 -- Card sheet 6
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 1, 0, 'MINE', 3, '/resources/images/mountainCards/goldSeams/goldSeam7.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Sidhe', 'If undefended at the end of the round, each player must replace two gold they posesss with two iron', 2, 0, 'DEFEND', 3, '/resources/images/mountainCards/sidhes/sidhe3.png', 'SHIDE');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Great Dragon', 'If undefended at the end of the round, each player must return all gold they possess to the supply', 3, 0, 'DEFEND', 3, '/resources/images/mountainCards/greatDragons/greatDragon2.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Orc Raiders', 'If undefended at the end of the round, players cannot take any MINE actions', 1, 1, 'DEFEND', 2, '/resources/images/mountainCards/orcRaiders/orcRaiders4.png', 'ORC_RAIDERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Gold Seam', 'Take 1 gold from the supply', 2, 1, 'MINE', 3, '/resources/images/mountainCards/goldSeams/goldSeam8.png', 'SEAM');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Forge Armour', 'Return 2 steel and 1 gold to the supply then take 1 item', 3, 1, 'CRAFT', 3, '/resources/images/mountainCards/forges/forgeArmour.png', 'FORGES_ALLOY');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Get Help', 'You may place 2 adittional workers this round. If you are the first player, the next player will become the first one', 1, 2, 'AID', 2, '/resources/images/mountainCards/getHelps/getHelp4.png', 'GET_HELP');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Knockers', 'If undefended at the end of the round, each player must return 1 iron to the supply', 2, 2, 'DEFEND', 3, '/resources/images/mountainCards/knockers/knockers4.png', 'DRAGONS_KNOCKERS');
 INSERT INTO mountain_cards(name, description, xposition, yposition, type, cardgroup, image, actiontype) VALUES ('Alloy Steel', 'Return 3 iron to the supply then take 2 steel', 3, 2, 'MINE', 2, '/resources/images/mountainCards/alloySteels/alloySteel7.png', 'FORGES_ALLOY');
 
 
 -- Special cards
 -- MUSERT_ARMY,HOLD_COUNCIL,SELL_ITEM,PAST_GLORIES,SPECIAL_ORDER,TURN_BACK,APPRENTICE,COLLAPSE_SHAFTS,RUN_AMOK
 
-- Special cards (card sheet 7 front & back)
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 1,'/resources/images/specialCards/musterAnArmy.png', 'MUSTER_AN_ARMY');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Hold a Council', 'Remove the top card from each location (leaving at least one card) and shuffle them back into The Mountain', 2,'/resources/images/specialCards/holdACouncil.png', 'HOLD_A_COUNCIL');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Sell an Item', 'Exchange 1 of your items for 5 resources of your choice', 3,'/resources/images/specialCards/sellAnItem.png', 'SELL_AN_ITEM');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Past Glories', 'Name a card previously on top in any location and return it to the top of that location', 4,'/resources/images/specialCards/pastGlories.png', 'PAST_GLORIES');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Special Order', 'Return 5 resources of your choice, including at least 1 of each type, to the supply and take 1 item', 5,'/resources/images/specialCards/specialOrder.png', 'SPECIAL_ORDER');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Turn Back', 'Remove the top card from 1 location and shuffle it back into the mountain. Immediatly place 1 worker on that location', 6,'/resources/images/specialCards/turnBack.png', 'TURN_BACK');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Apprentice', 'Place one worker on a location occupied by other player', 7,'/resources/images/specialCards/apprentice.png', 'APPRENTICE');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Collapse the Shafts', 'Remove the top card from each location and place it on the bottom of that stack', 8,'/resources/images/specialCards/collapseTheShafts.png', 'COLLAPSE_THE_SHAFTS');
 INSERT INTO special_cards(name, description, back_card, image, actiontype) VALUES ('Run Amok', 'Collect all of the cards from each location, in turn, shuffle them and return them to that location', 9,'/resources/images/specialCards/runAmok.png', 'RUN_AMOK');

 INSERT INTO special_decks(xposition, yposition) VALUES (0,0);
 INSERT INTO special_decks(xposition, yposition) VALUES (0,1);
 
 INSERT INTO SPECIAL_DECKS_SPECIAL_CARD(SPECIAL_DECK_ID, SPECIAL_CARD_ID) VALUES (1,1);

-- Forge and alloy card resources
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Alloy Steel', 'STEEL', 2);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(1, 'IRON', 3);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Sword', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(2, 'STEEL', 3);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Mace', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(3, 'STEEL', 2);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(3, 'GOLD', 1);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Diadem', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(4, 'IRON', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(4, 'STEEL', 1);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Helm', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(5, 'STEEL', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(5, 'GOLD', 2);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Axe', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(6, 'STEEL', 2);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Crown', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(7, 'GOLD', 3);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Dagger', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(8, 'IRON', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(8, 'STEEL', 2);
 
 INSERT INTO forges_alloy_resources(cardname, resource_type_received, amount_received) VALUES ('Forge Armour', 'ITEMS', 1);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(9, 'STEEL', 2);
 INSERT INTO resources_given(forges_alloy_resources_id, resource, amount) VALUES(9, 'GOLD', 1);

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

INSERT INTO Workers(id, xposition, yposition, status, gameId, playerId,image) VALUES (1, null, null, FALSE, 1, 1,'/resources/images/epicworker4.png');
INSERT INTO Workers(id, xposition, yposition, status, gameId, playerId,image) VALUES (2, null, null, TRUE, 1, 1,'/resources/images/epicworker4.png');

-- RESOURCES

INSERT INTO resources(id,badges,gold,iron,items,steel,gameId,playerId)  VALUES (1,2,3,6,2,1,1,1);