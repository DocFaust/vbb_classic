SET FOREIGN_KEY_CHECKS = 0;
drop table if exists
BUCHUNG, SPIELER, SPIEL, SEASON, USER_GROUP, USERS, ROLES, CONFIG, MAILS CASCADE;
drop view if exists V_USER_ROLE;
SET FOREIGN_KEY_CHECKS = 1;

create table SEASON (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	DESCRIPTION VARCHAR(255),
	STARTDATE DATE, 
	ENDDATE DATE, 
	PRICE DECIMAL(4,2),
	LKZ CHAR DEFAULT 'N',
	PRIMARY KEY (ID)
	
);

create table SPIEL (
	ID INTEGER NOT NULL AUTO_INCREMENT, 
	DATUM DATE,
	SEASON_ID INTEGER,
	LKZ CHAR DEFAULT 'N',
	PRIMARY KEY (ID),
	FOREIGN KEY (SEASON_ID) REFERENCES SEASON(ID)
);

create table SPIELER (
	ID INTEGER NOT NULL AUTO_INCREMENT, 
	NAME VARCHAR(255) unique,
	PRIMARY KEY (ID)
);

create table BUCHUNG (
	ID INTEGER NOT NULL AUTO_INCREMENT, 
	SPIEL_ID INTEGER,
	DATUM TIMESTAMP,
	SPIELER_ID INTEGER,
	DESCRIPTION VARCHAR(255),
	PRICE DECIMAL(10,5),
	LKZ CHAR NOT NULL DEFAULT 'N',
	PRIMARY KEY (ID),
	FOREIGN KEY (SPIEL_ID) REFERENCES SPIEL(ID),
	FOREIGN KEY (SPIELER_ID) REFERENCES SPIELER(ID)
);

create table USERS (
	ID INTEGER NOT NULL AUTO_INCREMENT, 
	USERID VARCHAR(255) UNIQUE NOT NULL,
	USERNAME VARCHAR (255),
	EMAIL VARCHAR (255) UNIQUE NOT NULL,
	PASSWORD char(32) NOT NULL,
	STATE VARCHAR(20),
	REGID VARCHAR(255) UNIQUE,
	PRIMARY KEY (ID)
);

create table GROUPS (
	ID INTEGER NOT NULL AUTO_INCREMENT, 
	NAME VARCHAR(20) UNIQUE NOT NULL,
	DESCRIPTION VARCHAR (255),
	PRIMARY KEY (ID)
);

create table USER_GROUP (
	USER_ID INTEGER NOT NULL,
	GROUP_ID INTEGER NOT NULL,
	PRIMARY KEY(USER_ID, GROUP_ID),
	KEY fk_user_has_groups_groups1 (GROUP_ID),
	KEY fk_user_has_groups_users (USER_ID),
	CONSTRAINT fk_groups FOREIGN KEY (GROUP_ID) REFERENCES GROUPS (ID) ON DELETE NO ACTION ON UPDATE NO ACTION,
	CONSTRAINT fk_users FOREIGN KEY (USER_ID) REFERENCES USERS (ID) ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE TOKEN (
	ID INTEGER AUTO_INCREMENT NOT NULL, 
	LASTUSED DATETIME NOT NULL, 
	TOKEN VARCHAR(255) NOT NULL UNIQUE, 
	USER_ID INTEGER, 
	PRIMARY KEY (ID),
	FOREIGN KEY (USER_ID) REFERENCES USERS (ID)
);


CREATE VIEW V_USER_ROLE AS
SELECT  u.USERID, u.PASSWORD, g.NAME
 FROM USER_GROUP ug
 INNER JOIN USERS u ON u.ID = ug.USER_ID
 INNER JOIN GROUPS g ON g.ID =  ug.GROUP_ID; 

create table CONFIG (
	ID INTEGER NOT NULL AUTO_INCREMENT, 
	CONFIGKEY VARCHAR(255) UNIQUE,
	CONFIGVALUE VARCHAR (512),
	PRIMARY KEY (ID)
);

create table MAILS (
	ID INTEGER NOT NULL AUTO_INCREMENT,
	RECIPIENT VARCHAR (255) NOT NULL,
	SUBJECT VARCHAR(255) NOT NULL,
	TEXT VARCHAR (1024) NOT NULL,
	ATTEMPT INTEGER,
	PRIMARY KEY(ID)
);

insert into SEASON (DESCRIPTION, STARTDATE, ENDDATE, PRICE) values 
('Sommer 2015', '2015-05-01', '2015-09-30', 63),
('Winter 2014/2015', '2014-10-01', '2015-04-30', 72),
('Sommer 2014', '2014-05-01', '2014-09-30', 63),
('Winter 2013/2014', '2013-10-01', '2014-04-30', 72),
('Sommer 2013', '2013-05-01', '2013-09-30', 63);

insert into SPIELER (NAME) values 
('Werner Faust'),
('Markus Pfeiffer'),
('Anita Goldmann'),
('Michael Goldmann'),
('Christian DiIorio'),
('Thomas Haimerl'),
('Martin Putz');

SET @DATUM='2014-06-28';
set @SEASON_ID=(select ID from SEASON where STARTDATE<=@DATUM and ENDDATE>=@DATUM);
INSERT INTO SPIEL(DATUM, SEASON_ID) VALUES(@DATUM, @SEASON_ID);
set @SPIEL_ID=(select ID from SPIEL where DATUM=@DATUM);

INSERT INTO BUCHUNG (DATUM, SPIEL_ID, DESCRIPTION, PRICE, SPIELER_ID) VALUES 
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Werner Faust')),
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Markus Pfeiffer')),
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Anita Goldmann')),
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Michael Goldmann')),
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Christian DiIorio')),
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Thomas Haimerl')),
(@DATUM, @SPIEL_ID, 'Spieleinsatz', -9, (select ID from SPIELER where NAME='Martin Putz')),
(@DATUM, @SPIEL_ID, 'Bezahlung', 63, (select ID from SPIELER where NAME='Martin Putz'));

INSERT  INTO GROUPS(ID, NAME, DESCRIPTION) VALUES
  (1,'USER','Benutzer'),
  (2,'ADMIN','Administratoren');

INSERT INTO USERS (USERID, USERNAME, EMAIL, PASSWORD, STATE, REGID) VALUES 
('docfaust', 'Werner Faust', 'wfaust@gmx.de', MD5('renate'), 'PROOFED', 0);
INSERT INTO USERS (USERID, USERNAME, EMAIL, PASSWORD, STATE, REGID) VALUES 
('wfaust', 'Werner Faust', 'docfaust75@gmail.com', MD5('renate'), 'OPEN', 1);
   
set @USER_ID=(select ID from USERS where USERID='docfaust');
set @G_ADMIN_ID=(select ID from GROUPS where NAME='ADMIN');
set @G_USER_ID=(select ID from GROUPS where NAME='USER');

INSERT INTO USER_GROUP
	(USER_ID, GROUP_ID)
VALUES
	(@USER_ID,@G_ADMIN_ID),
	(@USER_ID,@G_USER_ID);


insert into TOKEN
	(TOKEN, LASTUSED, USER_ID)
values
	('1', NOW(), 1);
	
INSERT INTO CONFIG (CONFIGKEY, CONFIGVALUE) VALUES 
('mail.smtp.host', 'smtp.gmail.com'),
('mail.smtp.auth', 'true'),
('mail.smtp.starttls.enable', 'true'),
('mail.smtp.port', '25'),
('mail.smtp.connectiontimeout', '30000'),
('mail.smtp.timeout', '30000'),
('sender.address', 'Docfaust75@gmail.com'),
('sender.password', 'BDnmeS&H'),
('subject', 'Registrierung bei der Volleyballbuchung'),
('text', 'Hallo %s,\ndu hast Dich bei der Volleyballbuchung registriert. Um zu verifizieren, dass diese Emailadresse auch wirklich Dir gehört, klicke bitte auf folgenden Link: \n\nhttp://fytdsrtxej5k3z8x.myfritz.net:8082/vbb2test/register?regid=%s&userid=%s\n\nVielen Dank\n Ciao\nDie Volleyballbuchung');

COMMIT;