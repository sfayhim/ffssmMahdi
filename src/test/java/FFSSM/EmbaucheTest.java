package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

class EmbaucheTest {

    private DiplomeDeMoniteur diplome;
    private Club club;
    private Embauche embauche;
    private LocalDate dateDebut;

    @BeforeEach
    void setUp() {
        Plongeur plongeur = new Plongeur("Jean Moniteur", 4, GroupeSanguin.APLUS);
        diplome = new DiplomeDeMoniteur(plongeur, 12345);
        
        Plongeur president = new Plongeur("President");
        DiplomeDeMoniteur diplomePresident = new DiplomeDeMoniteur(president, 100);
        club = new Club(diplomePresident, "Club Test");
        
        dateDebut = LocalDate.of(2024, 1, 1);
        embauche = new Embauche(dateDebut, diplome, club);
    }

    @Test
    void testConstructeur() {
        assertNotNull(embauche);
        assertEquals(dateDebut, embauche.getDebut());
        assertEquals(diplome, embauche.getEmploye());
        assertEquals(club, embauche.getEmployeur());
        assertNull(embauche.getFin());
        assertFalse(embauche.estTerminee());
    }

    @Test
    void testGetters() {
        assertEquals(dateDebut, embauche.getDebut());
        assertEquals(diplome, embauche.getEmploye());
        assertEquals(club, embauche.getEmployeur());
        assertNull(embauche.getFin());
    }

    @Test
    void testEstTermineeAvantTerminer() {
        assertFalse(embauche.estTerminee(), 
            "Une embauche non terminée doit retourner false");
    }

    @Test
    void testTerminer() {
        LocalDate dateFin = LocalDate.of(2024, 12, 31);
        embauche.terminer(dateFin);
        
        assertEquals(dateFin, embauche.getFin());
        assertTrue(embauche.estTerminee());
    }

    @Test
    void testEstTermineeApresTerminer() {
        LocalDate dateFin = LocalDate.of(2024, 12, 31);
        embauche.terminer(dateFin);
        
        assertTrue(embauche.estTerminee(), 
            "Une embauche terminée doit retourner true");
    }

    @Test
    void testSetFin() {
        LocalDate dateFin = LocalDate.of(2024, 6, 30);
        embauche.setFin(dateFin);
        
        assertEquals(dateFin, embauche.getFin());
        assertTrue(embauche.estTerminee());
    }

    @Test
    void testEmbaucheEnCours() {
        assertNull(embauche.getFin());
        assertFalse(embauche.estTerminee());
    }

    @Test
    void testTerminerPlusieursForis() {
        LocalDate dateFin1 = LocalDate.of(2024, 6, 30);
        LocalDate dateFin2 = LocalDate.of(2024, 12, 31);
        
        embauche.terminer(dateFin1);
        assertEquals(dateFin1, embauche.getFin());
        
        // Modifier la date de fin
        embauche.terminer(dateFin2);
        assertEquals(dateFin2, embauche.getFin());
        assertTrue(embauche.estTerminee());
    }

    @Test
    void testDateFinNull() {
        embauche.terminer(LocalDate.of(2024, 12, 31));
        assertTrue(embauche.estTerminee());
        
        // Remettre la fin à null
        embauche.setFin(null);
        assertNull(embauche.getFin());
        assertFalse(embauche.estTerminee());
    }
}
