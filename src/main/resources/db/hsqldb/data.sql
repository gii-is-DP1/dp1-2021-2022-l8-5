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

-- One player MyPlayer, named PC1 with passwor pass
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('TheNeoStormZ','pass',TRUE,42343234);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('Vell','pass',TRUE,322223343);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('BetrayalGD','pass',TRUE,4543656);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('DRG64','pass',TRUE,32321243);
INSERT INTO players(username,password,enabled,totalPoints) VALUES ('xiscomigueles','pass',TRUE,45454354);





INSERT INTO player2 VALUES (1, 'Pablo', 'Marin', 'pabmargom3');
INSERT INTO player2 VALUES (2, 'David', 'Zamora', 'davzamfer');


