package de.docfaust.vbb.util.templates;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.enterprise.context.Dependent;

import de.docfaust.vbb.data.entity.Buchung;
import de.docfaust.vbb.data.entity.Spiel;
import de.docfaust.vbb.data.entity.User;

/**
 * Erstellt die Mails als HTML Strings über das TemplateTool Velocity.
 * 
 * @author xhu1011
 *
 */
@Dependent
@VelocityMailTemplate
public class VelocityMailTemplates implements MailTemplates {
	private static final String REGISTER_MAIL = "registermail.vm";
	private static final String SALDO_MAIL = "saldomail.vm";
	private final DecimalFormat format = new DecimalFormat("###,###.##");

	@Override
	public String getSaldoMail(final Spiel spiel, final String name, final List<Entry<String, BigDecimal>> salden,
			final BigDecimal completesaldo) {
		Map<String, Object> values = new HashMap<String, Object>();

		values.put("name", name);
		values.put("datum", new SimpleDateFormat("dd.MM.yyyy").format(spiel.getDatum()));
		values.put("completesaldo", format.format(completesaldo));

		// Salden aufbauen
		List<Map<String, Object>> saldoList = new ArrayList<Map<String, Object>>();
		for (Entry<String, BigDecimal> saldo : salden) {
			Map<String, Object> saldoMap = new HashMap<String, Object>();
			saldoMap.put("name", saldo.getKey());
			saldoMap.put("betrag", format.format(saldo.getValue()));
			saldoList.add(saldoMap);
		}
		values.put("saldo", saldoList);

		// Buchungen Aufbauen
		List<Map<String, String>> buchungList = new ArrayList<Map<String, String>>();
		
		for (Buchung buchung : spiel.getBuchungen()) {
			Map<String, String> buchungMap = new HashMap<String, String>();
			buchungMap.put("name", buchung.getSpieler().getName());
			buchungMap.put("betrag", format.format(buchung.getPrice()));
			buchungList.add(buchungMap);
		}
		values.put("buchungen", buchungList);

		return VelocityBuilder.getInstance().buildMessage(SALDO_MAIL, values);

	}

	@Override
	public String getRegisterMail(final User user, final String domain) {
		Map<String, Object> values = new HashMap<String, Object>();
		values.put("username", user.getUsername());
		values.put("userid", user.getUserid());
		values.put("regid", user.getRegid());
		values.put("domain", domain);
		return VelocityBuilder.getInstance().buildMessage(REGISTER_MAIL, values);
	}
}
