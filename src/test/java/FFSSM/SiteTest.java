package FFSSM;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SiteTest {

    @Test
    void testConstructeur() {
        Site site = new Site("Belle île", "Plongée au large de Belle île");
        
        assertNotNull(site);
        assertEquals("Belle île", site.getNom());
        assertEquals("Plongée au large de Belle île", site.getDetails());
    }

    @Test
    void testGetters() {
        Site site = new Site("Épave du Donator", "Épave mythique en Méditerranée");
        
        assertEquals("Épave du Donator", site.getNom());
        assertEquals("Épave mythique en Méditerranée", site.getDetails());
    }

    @Test
    void testSetters() {
        Site site = new Site("Site 1", "Description 1");
        
        site.setNom("Mont Saint Michel");
        site.setDetails("Plongée autour du Mont Saint Michel");
        
        assertEquals("Mont Saint Michel", site.getNom());
        assertEquals("Plongée autour du Mont Saint Michel", site.getDetails());
    }

    @Test
    void testToString() {
        Site site = new Site("Calanques", "Plongée dans les calanques de Marseille");
        String str = site.toString();
        
        assertNotNull(str);
        assertTrue(str.contains("Calanques"));
        assertTrue(str.contains("Plongée dans les calanques de Marseille"));
    }

    @Test
    void testConstructeurAvecNomsLongs() {
        String nomLong = "Site de plongée exceptionnel avec de nombreuses espèces marines";
        String detailsLongs = "Ce site offre une biodiversité remarquable avec des tombants magnifiques, "
                + "des grottes sous-marines et une visibilité exceptionnelle toute l'année";
        
        Site site = new Site(nomLong, detailsLongs);
        
        assertEquals(nomLong, site.getNom());
        assertEquals(detailsLongs, site.getDetails());
    }

    @Test
    void testConstructeurAvecCaracteresSpeciaux() {
        Site site = new Site("L'Île d'Yeu", "Côte atlantique - Épaves & récifs");
        
        assertEquals("L'Île d'Yeu", site.getNom());
        assertEquals("Côte atlantique - Épaves & récifs", site.getDetails());
    }

    @Test
    void testEquals() {
        Site site1 = new Site("Belle île", "Description");
        Site site2 = new Site("Belle île", "Description");
        
        // Avec Lombok @Data, equals est généré automatiquement
        assertEquals(site1, site2);
    }

    @Test
    void testHashCode() {
        Site site1 = new Site("Belle île", "Description");
        Site site2 = new Site("Belle île", "Description");
        
        // Avec Lombok @Data, hashCode est généré automatiquement
        assertEquals(site1.hashCode(), site2.hashCode());
    }
}
