delete from season;

insert into SEASON (DESCRIPTION, STARTDATE, ENDDATE, PRICE) values 
('Sommer 2019', '2019-05-01', '2019-09-30', 72),
('Winter 2019/2020', '2019-10-01', '2020-04-30', 84),
('Sommer 2020', '2020-05-01', '2020-09-30', 72),
('Winter 2020/2021', '2020-10-01', '2021-04-30', 84),
('Sommer 2021', '2021-05-01', '2021-09-30', 72);

delete from spieler;

insert into SPIELER (NAME, EMAIL, ACTIVITYLEVEL) values 
('Werner Faust', 'wfaust@gmx.de', '10'),
('Markus Pfeiffer', '', '10'),
('Anita Goldmann', '', '10'),
('Katja Mann', '', '10'),
('Matthias Matzka', '', '10'),
('Fabian Goldmann', '', '10'),
('Christian DiIorio', '', '10'),
('Thomas Haimerl', '', '1'),
('Martin Putz', '', '1');
