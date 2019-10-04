package de.docfaust.vbb.jsfbeans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.docfaust.vbb.data.entity.Season;
import de.docfaust.vbb.service.SeasonService;
import de.docfaust.vbb.util.messages.MessageConstants;
import de.docfaust.vbb.util.messages.UIMessages;
import de.docfaust.vbb.util.statusliste.Statusliste;

/**
 * Managed Bean für die Saisonverwaltung.
 * 
 * @author xhu1011
 *
 */
@ViewScoped
@Named
public class EditSeasonBean extends AbstractJSFBean {
	private static final long serialVersionUID = -2117558184786615638L;
	@Inject
	private Logger logger;

	private List<Season> seasons = null;
	private Season selectedSeason;
	@Inject
	private SeasonService seasonService;

	/**
	 * Konstruktor ohne EJB Kontext.
	 * 
	 * @param uiMessages
	 *            UIMessages
	 */
	public EditSeasonBean(final UIMessages uiMessages, final SeasonService seasonService) {
		super(uiMessages);
		this.seasonService = seasonService;
		logger = LoggerFactory.getLogger(getClass());
	}

	/**
	 * Konstruktor mit EJB Kontext.
	 * 
	 */
	public EditSeasonBean() {
		super();
	}

	/**
	 * Initialisiert das Bean mit den Werten aus der Datenbank.
	 */
	@PostConstruct
	public void init() {
		logger.debug("Hole Seasons");
		setSeasons(seasonService.getSeasons());
		if (seasons != null && seasons.size() > 0) {
			setSelectedSeason(seasons.get(0));
		}
	}

	public Season getSelectedSeason() {
		return selectedSeason;
	}

	public void setSelectedSeason(final Season selectedSeason) {
		this.selectedSeason = selectedSeason;
	}

	public List<Season> getSeasons() {
		return seasons;
	}

	public void setSeasons(final List<Season> seasons) {
		this.seasons = seasons;
	}

	/**
	 * Löscht die ausgewählte Saison.
	 */
	public void delete() {
		if (selectedSeason != null) {
			logger.info(selectedSeason.getDescription());
			Statusliste statusliste = seasonService.deleteSaison(selectedSeason);
			showMessages(statusliste);

		} else {
			logger.warn("Keine Saison ausgewählt");
			showUIMessage(MessageConstants.NO_SEASON_CHOSEN);
		}
		init();
	}

	/**
	 * fügt eine Saison hinzu.
	 */
	public void addSeason() {
		Season season = new Season();
		selectedSeason = season;
		seasons.add(season);
	}

	/**
	 * Speichert die ausgewählte Saison.
	 */
	public void saveSeason() {
		logger.debug("Speichere Saison: " + ToStringBuilder.reflectionToString(selectedSeason));
		if (selectedSeason.getStartdate() != null && selectedSeason.getEnddate() != null
				&& selectedSeason.getPrice() != null) {
			seasonService.saveSeason(selectedSeason);
			logger.info("Saison gespeichert");
			showUIMessage(MessageConstants.SEASON_SAVED);
		} else {
			logger.warn("Keine Auswahl getätigt");
			showUIMessage(MessageConstants.NO_INPUT);
			seasons.remove(selectedSeason);
		}
	}
}
