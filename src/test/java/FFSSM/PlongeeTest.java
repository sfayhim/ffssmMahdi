package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class PlongeeTest {

    private Site site;
    private DiplomeDeMoniteur chef;
    private Plongee plongee;
    private LocalDate datePlongee;
    private Club club;

    @BeforeEach
    void setUp() {
        site = new Site("Belle île", "Plongée au large de Belle île");
        Plongeur chefPlongeur = new Plongeur("Chef", 4, GroupeSanguin.APLUS);
        chef = new DiplomeDeMoniteur(chefPlongeur, 100);
        datePlongee = LocalDate.of(2024, 6, 15);
        plongee = new Plongee(site, chef, datePlongee, 25, 45);
        
        Plongeur president = new Plongeur("President");
        DiplomeDeMoniteur diplomePresident = new DiplomeDeMoniteur(president, 99);
        club = new Club(diplomePresident, "Club Test");
    }

    @Test
    void testConstructeur() {
        assertNotNull(plongee);
        assertEquals(site, plongee.lieu);
        assertEquals(chef, plongee.chefDePalanquee);
        assertEquals(datePlongee, plongee.date);
        assertEquals(25, plongee.profondeur);
        assertEquals(45, plongee.duree);
        assertNotNull(plongee.getParticipants());
        assertTrue(plongee.getParticipants().isEmpty());
    }

    @Test
    void testAjouteParticipant() {
        Plongeur plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        plongee.ajouteParticipant(plongeur);
        
        assertEquals(1, plongee.getParticipants().size());
        assertTrue(plongee.getParticipants().contains(plongeur));
    }

    @Test
    void testAjouteParticipantNull() {
        plongee.ajouteParticipant(null);
        assertTrue(plongee.getParticipants().isEmpty(), 
            "Ajouter un participant null ne doit pas modifier la liste");
    }

    @Test
    void testAjouteParticipantDoublon() {
        Plongeur plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        plongee.ajouteParticipant(plongeur);
        plongee.ajouteParticipant(plongeur);
        
        assertEquals(1, plongee.getParticipants().size(), 
            "Un même participant ne doit pas être ajouté deux fois");
    }

    @Test
    void testAjoutePlusieursParticipants() {
        Plongeur plongeur1 = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        Plongeur plongeur2 = new Plongeur("Marie Martin", 3, GroupeSanguin.OPLUS);
        Plongeur plongeur3 = new Plongeur("Paul Durant", 1, GroupeSanguin.BPLUS);
        
        plongee.ajouteParticipant(plongeur1);
        plongee.ajouteParticipant(plongeur2);
        plongee.ajouteParticipant(plongeur3);
        
        assertEquals(3, plongee.getParticipants().size());
    }

    @Test
    void testEstConformeSansParticipants() {
        assertTrue(plongee.estConforme(), 
            "Une plongée sans participants doit être conforme");
    }

    @Test
    void testEstConformeAvecParticipantsLicencesValides() {
        Plongeur plongeur1 = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        Plongeur plongeur2 = new Plongeur("Marie Martin", 3, GroupeSanguin.OPLUS);
        
        // Licences valides à la date de la plongée (15 juin 2024)
        Licence licence1 = new Licence(plongeur1, "LIC001", LocalDate.of(2024, 1, 1), club);
        Licence licence2 = new Licence(plongeur2, "LIC002", LocalDate.of(2023, 12, 1), club);
        
        plongeur1.ajouteLicence(licence1);
        plongeur2.ajouteLicence(licence2);
        
        plongee.ajouteParticipant(plongeur1);
        plongee.ajouteParticipant(plongeur2);
        
        assertTrue(plongee.estConforme(), 
            "Une plongée avec des licences valides doit être conforme");
    }

    @Test
    void testEstNonConformeAvecUneLicenceExpiree() {
        Plongeur plongeur1 = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        Plongeur plongeur2 = new Plongeur("Marie Martin", 3, GroupeSanguin.OPLUS);
        
        // Licence valide
        Licence licenceValide = new Licence(plongeur1, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur1.ajouteLicence(licenceValide);
        
        // Licence expirée (délivrée plus d'un an avant la plongée)
        Licence licenceExpiree = new Licence(plongeur2, "LIC002", LocalDate.of(2022, 1, 1), club);
        plongeur2.ajouteLicence(licenceExpiree);
        
        plongee.ajouteParticipant(plongeur1);
        plongee.ajouteParticipant(plongeur2);
        
        assertFalse(plongee.estConforme(), 
            "Une plongée avec au moins une licence expirée n'est pas conforme");
    }

    @Test
    void testEstNonConformeAvecPlongeurSansLicence() {
        Plongeur plongeur1 = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        Plongeur plongeurSansLicence = new Plongeur("Marie Martin", 3, GroupeSanguin.OPLUS);
        
        Licence licence = new Licence(plongeur1, "LIC001", LocalDate.of(2024, 1, 1), club);
        plongeur1.ajouteLicence(licence);
        
        plongee.ajouteParticipant(plongeur1);
        plongee.ajouteParticipant(plongeurSansLicence);
        
        assertFalse(plongee.estConforme(), 
            "Une plongée avec un plongeur sans licence n'est pas conforme");
    }

    @Test
    void testEstConformeAvecPlusieurslicences() {
        Plongeur plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        
        // Ajouter plusieurs licences, dont une ancienne expirée
        Licence licenceAncienne = new Licence(plongeur, "LIC001", LocalDate.of(2022, 1, 1), club);
        Licence licenceRecente = new Licence(plongeur, "LIC002", LocalDate.of(2024, 1, 1), club);
        
        plongeur.ajouteLicence(licenceAncienne);
        plongeur.ajouteLicence(licenceRecente);
        
        plongee.ajouteParticipant(plongeur);
        
        assertTrue(plongee.estConforme(), 
            "Si la dernière licence est valide, la plongée est conforme");
    }

    @Test
    void testEstNonConformeAvecDerniereLicenceExpiree() {
        Plongeur plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        
        // Ajouter plusieurs licences, la plus récente est expirée
        Licence licenceAncienne = new Licence(plongeur, "LIC001", LocalDate.of(2021, 1, 1), club);
        Licence licenceExpiree = new Licence(plongeur, "LIC002", LocalDate.of(2022, 6, 1), club);
        
        plongeur.ajouteLicence(licenceAncienne);
        plongeur.ajouteLicence(licenceExpiree);
        
        plongee.ajouteParticipant(plongeur);
        
        assertFalse(plongee.estConforme(), 
            "Si la dernière licence est expirée, la plongée n'est pas conforme");
    }

    @Test
    void testGetParticipants() {
        List<Plongeur> participants = plongee.getParticipants();
        assertNotNull(participants);
        assertTrue(participants.isEmpty());
        
        Plongeur plongeur = new Plongeur("Jean Dupont", 2, GroupeSanguin.APLUS);
        plongee.ajouteParticipant(plongeur);
        
        assertEquals(1, participants.size());
        assertTrue(participants.contains(plongeur));
    }
}
