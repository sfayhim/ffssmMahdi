package FFSSM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Plongeur extends Personne {

    private int niveau;
    private GroupeSanguin groupe;

    private List<Licence> licences;

    public Plongeur(String nom) {
        super(nom);
        this.licences = new ArrayList<>();
    }

    public Plongeur(String nom, int niveau, GroupeSanguin groupe) {
        super(nom);
        this.niveau = niveau;
        this.groupe = groupe;
        this.licences = new ArrayList<>();
    }

    public void ajouteLicence(Licence l) {
        if (l == null) {
            return;
        }
        licences.add(l);
    }

    public Licence derniereLicence() {
        if (licences.isEmpty()) {
            return null;
        }
        return licences.stream().max(Comparator.comparing(Licence::getDelivrance)).orElse(null);
    }

    public List<Licence> getLicences() {
        return licences;
    }

}
