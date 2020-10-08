select 
	b.ID as BuchungsID,
    s.DATUM as Spieldatum,
	se.DESCRIPTION as Saison_Beschreibung, 
    se.STARTDATE as Startdatum, 
    se.ENDDATE as Enddatum,
    se.PRICE as Saisonpreis,
    sp.NAME as Spielername,
    b.DATUM as Buchungsdatum,
    b.DESCRIPTION as Buchungsposten,
    b.PRICE as Betrag,
    b.LKZ as Buchung_gelöscht,
    se.LKZ as Season_gelöscht,
    s.LKZ as Spiel_gelöscht
from 
	vbb.BUCHUNG b
right  join 
	vbb.SPIEL s 
on 
	b.SPIEL_ID = s.ID
left join 
	vbb.SPIELER sp 
on 
	b.SPIELER_ID = sp.ID
left outer join 
	vbb.SEASON se 
on 
	s.SEASON_ID = se.ID;

select 
    g.NAME as Gruppenname,
    g.DESCRIPTION as Gruppenbeschreibung,
	u.USERID as Benutzerkennung,
    u.USERNAME as Name,
    u.EMAIL as Emailadresse,
    u.PASSWORD as Passwort,
    u.STATE as Registrierungsstatus,
    u.REGID as RegistrierungsID
from 
        vbb.USERS u
left join 
	vbb.USER_GROUP ug 
on 
	ug.USER_ID = u.ID
left join 
	vbb.roles g 
on 
	ug.GROUP_ID = g.ID
order by 
	g.NAME;
