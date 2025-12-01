package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Set;

class ClubTest {

    private Club club;
    private DiplomeDeMoniteur president;
    private Plongeur plongeur1;
    private Plongeur plongeur2;

    @BeforeEach
    void setUp() {
        Plongeur presidentPlongeur = new Plongeur("President");
        president = new DiplomeDeMoniteur(presidentPlongeur, 100);
        club = new Club(president, "Club de plongée Paris");
        
        plongeur1 = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        plongeur2 = new Plongeur("Marie Martin", 3, GroupeSanguin.OPLUS);
    }

    @Test
    void testConstructeur() {
        assertNotNull(club);
        assertEquals(president, club.getPresident());
        assertEquals("Club de plongée Paris", club.getNom());
        assertNotNull(club.getPlongees());
        assertTrue(club.getPlongees().isEmpty());
    }

    @Test
    void testSettersEtGetters() {
        club.setNom("Nouveau Club");
        club.setAdresse("123 Rue de la Mer");
        club.setTelephone("0123456789");
        
        assertEquals("Nouveau Club", club.getNom());
        assertEquals("123 Rue de la Mer", club.getAdresse());
        assertEquals("0123456789", club.getTelephone());
    }

    @Test
    void testOrganisePlongee() {
        Site site = new Site("Belle île", "Plongée au large");
        Plongee plongee = new Plongee(site, president, LocalDate.now(), 20, 45);
        
        club.organisePlongee(plongee);
        
        assertEquals(1, club.getPlongees().size());
        assertTrue(club.getPlongees().contains(plongee));
    }

    @Test
    void testOrganisePlongeeNull() {
        club.organisePlongee(null);
        assertTrue(club.getPlongees().isEmpty(), 
            "Organiser une plongée null ne doit pas modifier la liste");
    }

    @Test
    void testOrganisePlusieurslongees() {
        Site site1 = new Site("Site 1", "Description 1");
        Site site2 = new Site("Site 2", "Description 2");
        
        Plongee plongee1 = new Plongee(site1, president, LocalDate.of(2024, 6, 1), 20, 45);
        Plongee plongee2 = new Plongee(site2, president, LocalDate.of(2024, 7, 1), 30, 50);
        
        club.organisePlongee(plongee1);
        club.organisePlongee(plongee2);
        
        assertEquals(2, club.getPlongees().size());
    }

    @Test
    void testPlongeesNonConformesSansPlongees() {
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        assertNotNull(nonConformes);
        assertTrue(nonConformes.isEmpty());
    }

    @Test
    void testPlongeesNonConformesAvecPlongeeConforme() {
        Site site = new Site("Site Test", "Description");
        LocalDate datePlongee = LocalDate.of(2024, 6, 1);
        Plongee plongee = new Plongee(site, president, datePlongee, 20, 45);
        
        // Ajouter un plongeur avec une licence valide
        Licence licence = new Licence(plongeur1, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur1.ajouteLicence(licence);
        plongee.ajouteParticipant(plongeur1);
        
        club.organisePlongee(plongee);
        
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        assertTrue(nonConformes.isEmpty(), 
            "Une plongée conforme ne doit pas être dans la liste des non conformes");
    }

    @Test
    void testPlongeesNonConformesAvecPlongeeNonConforme() {
        Site site = new Site("Site Test", "Description");
        LocalDate datePlongee = LocalDate.of(2025, 6, 1);
        Plongee plongee = new Plongee(site, president, datePlongee, 20, 45);
        
        // Ajouter un plongeur avec une licence expirée
        Licence licenceExpiree = new Licence(plongeur1, "LIC001", LocalDate.of(2023, 1, 1), club);
        plongeur1.ajouteLicence(licenceExpiree);
        plongee.ajouteParticipant(plongeur1);
        
        club.organisePlongee(plongee);
        
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        assertEquals(1, nonConformes.size(), 
            "Une plongée avec licence expirée doit être non conforme");
        assertTrue(nonConformes.contains(plongee));
    }

    @Test
    void testPlongeesNonConformesAvecPlongeurSansLicence() {
        Site site = new Site("Site Test", "Description");
        LocalDate datePlongee = LocalDate.of(2024, 6, 1);
        Plongee plongee = new Plongee(site, president, datePlongee, 20, 45);
        
        // Ajouter un plongeur sans licence
        plongee.ajouteParticipant(plongeur1);
        
        club.organisePlongee(plongee);
        
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        assertEquals(1, nonConformes.size(), 
            "Une plongée avec un plongeur sans licence doit être non conforme");
    }

    @Test
    void testPlongeesNonConformesMixte() {
        Site site1 = new Site("Site 1", "Description 1");
        Site site2 = new Site("Site 2", "Description 2");
        
        LocalDate datePlongee = LocalDate.of(2024, 6, 1);
        
        // Plongée conforme
        Plongee plongeeConforme = new Plongee(site1, president, datePlongee, 20, 45);
        Licence licenceValide = new Licence(plongeur1, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur1.ajouteLicence(licenceValide);
        plongeeConforme.ajouteParticipant(plongeur1);
        
        // Plongée non conforme
        Plongee plongeeNonConforme = new Plongee(site2, president, datePlongee, 30, 50);
        plongeeNonConforme.ajouteParticipant(plongeur2); // Sans licence
        
        club.organisePlongee(plongeeConforme);
        club.organisePlongee(plongeeNonConforme);
        
        Set<Plongee> nonConformes = club.plongeesNonConformes();
        assertEquals(1, nonConformes.size());
        assertTrue(nonConformes.contains(plongeeNonConforme));
        assertFalse(nonConformes.contains(plongeeConforme));
    }

    @Test
    void testToString() {
        club.setAdresse("123 Rue de la Mer");
        club.setTelephone("0123456789");
        String str = club.toString();
        assertNotNull(str);
        assertTrue(str.contains("Club de plongée Paris"));
    }
}
