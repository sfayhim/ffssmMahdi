/**
 * @(#) Moniteur.java
 */
package FFSSM;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class DiplomeDeMoniteur {

    private final int numeroDiplome;
    private final Plongeur possesseur;
    private final List<Embauche> emplois = new ArrayList<>();

    public DiplomeDeMoniteur(Plongeur possesseur, int numeroDiplome) {
        this.numeroDiplome = numeroDiplome;
        this.possesseur = possesseur;
    }

    /**
     * Si ce moniteur n'a pas d'embauche, ou si sa dernière embauche est terminée,
     * ce moniteur n'a pas d'employeur.
     * 
     * @return l'employeur actuel de ce moniteur ou null s'il n'en a pas
     */
    public Club employeurActuel() {
        if (emplois.isEmpty()) {
            return null;
        }
        Embauche derniere = emplois.get(emplois.size() - 1);
        if (derniere.getFin() == null) {
            return derniere.getEmployeur();
        }
        return null;
    }

    /**
     * Enregistrer une nouvelle embauche pour cet employeur
     * 
     * @param employeur     le club employeur
     * @param debutNouvelle la date de début de l'embauche
     */
    public void nouvelleEmbauche(Club employeur, LocalDate debutNouvelle) {
        Embauche e = new Embauche(debutNouvelle, this, employeur);
        emplois.add(e);

    }

    public List<Embauche> emplois() {
        return emplois;
    }

}
