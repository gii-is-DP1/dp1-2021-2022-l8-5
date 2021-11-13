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

-- Mountain cards
 INSERT INTO mountain_cards(name, description, position) VALUES ('Iron Seam', 'Take 3 iron from the supply', 0);
 
-- Special cards
 INSERT INTO special_cards(name, description, back_card) VALUES ('Muster an Army', 'When resolving actions this turn, treat all defend cards as if they are ocuppied', 1);




-- Players2
INSERT INTO player2 (id,first_name,last_name,username,avatar_url) VALUES  (1, 'Pablo', 'Marin' ,'pabmargom3','https://www.w3schools.com/w3images/avatar1.png');
INSERT INTO player2  (id,first_name,last_name,username,avatar_url) VALUES (2, 'David', 'Zamora', 'davzamfer','https://www.w3schools.com/w3images/avatar2.png');


