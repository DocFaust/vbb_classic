delete from season;

insert into SEASON (DESCRIPTION, STARTDATE, ENDDATE, PRICE) values 
('Sommer 2019', '2019-05-01', '2019-09-30', 72),
('Winter 2019/2020', '2019-10-01', '2020-04-30', 84),
('Sommer 2020', '2020-05-01', '2020-09-30', 72),
('Winter 2020/2021', '2020-10-01', '2021-04-30', 84),
('Sommer 2021', '2021-05-01', '2021-09-30', 72);

delete from spieler;

insert into SPIELER (NAME, EMAIL, ACTIVITYLEVEL) values 
('Alex Ander', 'alex.ander@example.invalid', '10'),
('Bea Beispiel', '', '10'),
('Chris Cordt', '', '10'),
('Dana Dorn', '', '10'),
('Emil Engel', '', '10'),
('Frieda Falk', '', '10'),
('Gustav Gruen', '', '10'),
('Helene Herbst', '', '1'),
('Ida Immer', '', '1');
