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



INSERT INTO owners VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023', 'owner1');
INSERT INTO owners VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749', 'owner1');
INSERT INTO owners VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763', 'owner1');
INSERT INTO owners VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198', 'owner1');
INSERT INTO owners VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765', 'owner1');
INSERT INTO owners VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654', 'owner1');
INSERT INTO owners VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387', 'owner1');
INSERT INTO owners VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683', 'owner1');
INSERT INTO owners VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435', 'owner1');
INSERT INTO owners VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487', 'owner1');
INSERT INTO owners VALUES (11, 'Pablo', 'Marin', '64  Lees Gwynno.', 'Lawson', '636709741', 'pabmargom3');
INSERT INTO owners VALUES (12, 'David', 'Zamora', '64 Lord Llanio.', 'Teilo', '613880809', 'davzamfer');


