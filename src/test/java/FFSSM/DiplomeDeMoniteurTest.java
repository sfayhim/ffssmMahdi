package FFSSM;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

class DiplomeDeMoniteurTest {

    private Plongeur plongeur;
    private DiplomeDeMoniteur diplome;
    private Club club1;
    private Club club2;

    @BeforeEach
    void setUp() {
        plongeur = new Plongeur("Jean Moniteur", 4, GroupeSanguin.APLUS);
        diplome = new DiplomeDeMoniteur(plongeur, 12345);
        
        Plongeur president1 = new Plongeur("President1");
        DiplomeDeMoniteur diplomePresident1 = new DiplomeDeMoniteur(president1, 100);
        club1 = new Club(diplomePresident1, "Club 1");
        
        Plongeur president2 = new Plongeur("President2");
        DiplomeDeMoniteur diplomePresident2 = new DiplomeDeMoniteur(president2, 101);
        club2 = new Club(diplomePresident2, "Club 2");
    }

    @Test
    void testConstructeur() {
        assertNotNull(diplome);
        assertNotNull(diplome.emplois());
        assertTrue(diplome.emplois().isEmpty());
    }

    @Test
    void testEmployeurActuelSansEmbauche() {
        assertNull(diplome.employeurActuel(), 
            "Un moniteur sans embauche n'a pas d'employeur actuel");
    }

    @Test
    void testNouvelleEmbauche() {
        LocalDate debut = LocalDate.of(2024, 1, 1);
        diplome.nouvelleEmbauche(club1, debut);
        
        assertEquals(1, diplome.emplois().size());
        assertEquals(club1, diplome.employeurActuel());
    }

    @Test
    void testEmployeurActuelAvecEmbaucheEnCours() {
        LocalDate debut = LocalDate.of(2024, 1, 1);
        diplome.nouvelleEmbauche(club1, debut);
        
        Club employeur = diplome.employeurActuel();
        assertNotNull(employeur);
        assertEquals(club1, employeur);
    }

    @Test
    void testEmployeurActuelAvecEmbaucheTerminee() {
        LocalDate debut = LocalDate.of(2024, 1, 1);
        LocalDate fin = LocalDate.of(2024, 12, 31);
        
        diplome.nouvelleEmbauche(club1, debut);
        
        // Terminer l'embauche
        List<Embauche> emplois = diplome.emplois();
        emplois.get(0).terminer(fin);
        
        assertNull(diplome.employeurActuel(), 
            "Si la dernière embauche est terminée, il n'y a pas d'employeur actuel");
    }

    @Test
    void testPlusieursEmbauchesSuccessives() {
        LocalDate debut1 = LocalDate.of(2023, 1, 1);
        LocalDate fin1 = LocalDate.of(2023, 12, 31);
        LocalDate debut2 = LocalDate.of(2024, 1, 1);
        
        // Première embauche
        diplome.nouvelleEmbauche(club1, debut1);
        diplome.emplois().get(0).terminer(fin1);
        
        // Deuxième embauche
        diplome.nouvelleEmbauche(club2, debut2);
        
        assertEquals(2, diplome.emplois().size());
        assertEquals(club2, diplome.employeurActuel(), 
            "L'employeur actuel doit être celui de la dernière embauche");
    }

    @Test
    void testPlusieursEmbauchesToutesTerminees() {
        LocalDate debut1 = LocalDate.of(2023, 1, 1);
        LocalDate fin1 = LocalDate.of(2023, 12, 31);
        LocalDate debut2 = LocalDate.of(2024, 1, 1);
        LocalDate fin2 = LocalDate.of(2024, 12, 31);
        
        diplome.nouvelleEmbauche(club1, debut1);
        diplome.emplois().get(0).terminer(fin1);
        
        diplome.nouvelleEmbauche(club2, debut2);
        diplome.emplois().get(1).terminer(fin2);
        
        assertEquals(2, diplome.emplois().size());
        assertNull(diplome.employeurActuel(), 
            "Si toutes les embauches sont terminées, il n'y a pas d'employeur actuel");
    }

    @Test
    void testEmplois() {
        assertTrue(diplome.emplois().isEmpty());
        
        diplome.nouvelleEmbauche(club1, LocalDate.of(2024, 1, 1));
        assertEquals(1, diplome.emplois().size());
        
        diplome.nouvelleEmbauche(club2, LocalDate.of(2025, 1, 1));
        assertEquals(2, diplome.emplois().size());
    }

    @Test
    void testChangementDEmployeur() {
        LocalDate debut1 = LocalDate.of(2024, 1, 1);
        LocalDate fin1 = LocalDate.of(2024, 6, 30);
        LocalDate debut2 = LocalDate.of(2024, 7, 1);
        
        // Première embauche
        diplome.nouvelleEmbauche(club1, debut1);
        assertEquals(club1, diplome.employeurActuel());
        
        // Terminer la première embauche
        diplome.emplois().get(0).terminer(fin1);
        assertNull(diplome.employeurActuel());
        
        // Nouvelle embauche
        diplome.nouvelleEmbauche(club2, debut2);
        assertEquals(club2, diplome.employeurActuel());
    }
}
