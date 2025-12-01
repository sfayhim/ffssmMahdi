/**
 * @(#) Plongee.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Plongee {

	public Site lieu;

	public DiplomeDeMoniteur chefDePalanquee;

	public LocalDate date;

	public int profondeur;

	public int duree;

	public Plongee(Site lieu, DiplomeDeMoniteur chefDePalanquee, LocalDate date, int profondeur, int duree) {
		this.lieu = lieu;
		this.chefDePalanquee = chefDePalanquee;
		this.date = date;
		this.profondeur = profondeur;
		this.duree = duree;
		this.participants = new ArrayList<>();
	}

	public void ajouteParticipant(Plongeur participant) {
		if (participant == null) {
			return;
		}
		if (!participants.contains(participant)) {
			participants.add(participant);
		}
	}

	/**
	 * Détermine si la plongée est conforme.
	 * Une plongée est conforme si tous les plongeurs de la palanquée ont une
	 * licence valide à la date de la plongée
	 * 
	 * @return vrai si la plongée est conforme
	 */
	public boolean estConforme() {
		if (participants == null) {
			return true;
		}
		for (Plongeur p : participants) {
			Licence l = p.derniereLicence();
			if (l == null || !l.estValide(date)) {
				return false;
			}
		}
		return true;
	}

	private List<Plongeur> participants;

	public List<Plongeur> getParticipants() {
		return participants;
	}

}
