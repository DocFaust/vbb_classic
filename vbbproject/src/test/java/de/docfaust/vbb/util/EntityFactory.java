package de.docfaust.vbb.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.Spieler;
import de.docfaust.vbb.data.entity.User;

public final class EntityFactory {
	private static String description;

	private EntityFactory() {
	}

	public static final Season createSeason(String strStartdate, String strEndDate, long lPrice, String description) throws ParseException {

		Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse(strStartdate);
		Date enddate = new SimpleDateFormat("dd.MM.yyyy").parse(strEndDate);
		BigDecimal price = BigDecimal.valueOf(lPrice);
		return createSeason(startDate, enddate, price, description);
	}

	public static final Season createSeason(Date startdate, Date enddate, BigDecimal price, String description) {
		Season season = new Season();
		season.setStartdate(startdate);
		season.setEnddate(enddate);
		season.setPrice(price);
		season.setDescription(description);
		season.setSpiele(new ArrayList<Spiel>());
		return season;
	}

	public static final Spiel createSpiel(String strDatum) throws ParseException {
		Date datum = new SimpleDateFormat("dd.MM.yyyy").parse(strDatum);
		return createSpiel(datum);
	}

	public static final Spiel createSpiel(Date datum) {
		Spiel spiel = new Spiel();
		spiel.setDatum(datum);
		spiel.setBuchungen(new ArrayList<Buchung>());
		return spiel;
	}

	public static final Buchung createBuchung(Date datum, BigDecimal price) {
		Buchung buchung = new Buchung();
		buchung.setDatum(datum);
		buchung.setDescription(description);
		buchung.setPrice(price);
		return buchung;
	}
	
	public static final Date getActualDateAddDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();

	}

	public static Spieler createSpieler(boolean bezahlt,  String name, String email, boolean anwesend) {
		Spieler model = new Spieler();
		model.setName(name);
		model.setEmail(email);
		model.setAnwesend(anwesend);
		model.setBezahlt(bezahlt);
		return model;
	}

	public static User createUser(final String userid, final String name, final String email, final String passwort, final RegistrationState state, final String regid) {
		User user = new User();
		user.setUserid(userid);
		user.setEmail(email);
		user.setPassword(PasswordUtil.encryptPassword(passwort));
		user.setUsername(name);
		user.setState(state);
		user.setRegid(regid);
		return user;
	}
}
