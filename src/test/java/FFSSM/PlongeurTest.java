package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class PlongeurTest {

    private Plongeur plongeur;
    private Club club;
    private DiplomeDeMoniteur president;

    @BeforeEach
    void setUp() {
        president = new DiplomeDeMoniteur(new Plongeur("President"), 100);
        club = new Club(president, "Club Test");
        plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
    }

    @Test
    void testConstructeurSimple() {
        Plongeur p = new Plongeur("Marie Martin");
        assertNotNull(p);
        assertNotNull(p.getLicences());
        assertTrue(p.getLicences().isEmpty());
    }

    @Test
    void testConstructeurComplet() {
        assertNotNull(plongeur);
        assertNotNull(plongeur.getLicences());
        assertTrue(plongeur.getLicences().isEmpty());
    }

    @Test
    void testAjouteLicence() {
        Licence licence = new Licence(plongeur, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur.ajouteLicence(licence);
        
        assertEquals(1, plongeur.getLicences().size());
        assertTrue(plongeur.getLicences().contains(licence));
    }

    @Test
    void testAjouteLicenceNull() {
        plongeur.ajouteLicence(null);
        assertTrue(plongeur.getLicences().isEmpty(), 
            "Ajouter une licence null ne doit pas modifier la liste");
    }

    @Test
    void testAjoutePlusieurslicences() {
        Licence licence1 = new Licence(plongeur, "LIC001", LocalDate.of(2024, 1, 1), club);
        Licence licence2 = new Licence(plongeur, "LIC002", LocalDate.of(2024, 6, 1), club);
        Licence licence3 = new Licence(plongeur, "LIC003", LocalDate.of(2025, 1, 1), club);
        
        plongeur.ajouteLicence(licence1);
        plongeur.ajouteLicence(licence2);
        plongeur.ajouteLicence(licence3);
        
        assertEquals(3, plongeur.getLicences().size());
    }

    @Test
    void testDerniereLicenceSansLicence() {
        assertNull(plongeur.derniereLicence(), 
            "Un plongeur sans licence doit retourner null");
    }

    @Test
    void testDerniereLicenceAvecUneLicence() {
        Licence licence = new Licence(plongeur, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur.ajouteLicence(licence);
        
        assertEquals(licence, plongeur.derniereLicence());
    }

    @Test
    void testDerniereLicenceAvecPlusieurslicences() {
        Licence licence1 = new Licence(plongeur, "LIC001", LocalDate.of(2023, 1, 1), club);
        Licence licence2 = new Licence(plongeur, "LIC002", LocalDate.of(2024, 6, 1), club);
        Licence licence3 = new Licence(plongeur, "LIC003", LocalDate.of(2025, 1, 1), club);
        
        // Ajouter dans le désordre pour tester le tri
        plongeur.ajouteLicence(licence2);
        plongeur.ajouteLicence(licence1);
        plongeur.ajouteLicence(licence3);
        
        assertEquals(licence3, plongeur.derniereLicence(), 
            "Doit retourner la licence la plus récente");
    }

    @Test
    void testGetLicences() {
        List<Licence> licences = plongeur.getLicences();
        assertNotNull(licences);
        assertTrue(licences.isEmpty());
        
        Licence licence = new Licence(plongeur, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur.ajouteLicence(licence);
        
        assertEquals(1, licences.size());
    }
}
