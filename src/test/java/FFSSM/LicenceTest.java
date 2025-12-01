package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class LicenceTest {

    private Plongeur plongeur;
    private Club club;
    private DiplomeDeMoniteur president;
    private Licence licence;
    private LocalDate dateDelivrance;

    @BeforeEach
    void setUp() {
        plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        president = new DiplomeDeMoniteur(new Plongeur("President"), 123);
        club = new Club(president, "Club de plongée de Paris");
        dateDelivrance = LocalDate.of(2024, 1, 1);
        licence = new Licence(plongeur, "LIC001", dateDelivrance, club);
    }

    @Test
    void testConstructeur() {
        assertNotNull(licence);
        assertEquals(plongeur, licence.getPossesseur());
        assertEquals("LIC001", licence.numero);
        assertEquals(dateDelivrance, licence.getDelivrance());
        assertEquals(club, licence.getClub());
    }

    @Test
    void testEstValideJourDelivrance() {
        assertTrue(licence.estValide(dateDelivrance), 
            "La licence doit être valide le jour de délivrance");
    }

    @Test
    void testEstValideDansLAnnee() {
        LocalDate dateDansLAnnee = dateDelivrance.plusMonths(6);
        assertTrue(licence.estValide(dateDansLAnnee), 
            "La licence doit être valide 6 mois après la délivrance");
    }

    @Test
    void testEstValideDernierJour() {
        LocalDate dernierJour = dateDelivrance.plusYears(1);
        assertTrue(licence.estValide(dernierJour), 
            "La licence doit être valide exactement un an après la délivrance");
    }

    @Test
    void testEstNonValideApresUnAn() {
        LocalDate apresUnAn = dateDelivrance.plusYears(1).plusDays(1);
        assertFalse(licence.estValide(apresUnAn), 
            "La licence ne doit plus être valide après un an et un jour");
    }

    @Test
    void testEstNonValideAvantDelivrance() {
        LocalDate avantDelivrance = dateDelivrance.minusDays(1);
        assertFalse(licence.estValide(avantDelivrance), 
            "La licence ne doit pas être valide avant la date de délivrance");
    }

    @Test
    void testEstNonValideAvecDateNull() {
        assertFalse(licence.estValide(null), 
            "La licence ne doit pas être valide avec une date null");
    }

    @Test
    void testEstNonValideAvecDelivranceNull() {
        Licence licenceSansDate = new Licence(plongeur, "LIC002", null, club);
        assertFalse(licenceSansDate.estValide(LocalDate.now()), 
            "Une licence sans date de délivrance ne doit pas être valide");
    }

    @Test
    void testSettersEtGetters() {
        Plongeur nouveauPlongeur = new Plongeur("Marie Martin");
        Club nouveauClub = new Club(president, "Nouveau Club");
        LocalDate nouvelleDate = LocalDate.of(2025, 6, 1);

        licence.setPossesseur(nouveauPlongeur);
        licence.setDelivrance(nouvelleDate);
        licence.setClub(nouveauClub);

        assertEquals(nouveauPlongeur, licence.getPossesseur());
        assertEquals(nouvelleDate, licence.getDelivrance());
        assertEquals(nouveauClub, licence.getClub());
    }
}
