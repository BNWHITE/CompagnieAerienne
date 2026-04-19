package com.isep.airline;

import com.isep.airline.model.*;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link CompagnieAerienne}.
 *
 * <p>Vérifie les opérations CRUD sur les entités gérées par la compagnie.</p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 */
@DisplayName("Tests CompagnieAerienne")
class CompagnieAerienneTest {

    private CompagnieAerienne compagnie;

    @BeforeEach
    void setUp() {
        compagnie = new CompagnieAerienne("SkyISEP Test", "ST");
    }

    // ── Constructeur ────────────────────────────────────────────────────────

    @Test
    @DisplayName("La compagnie est correctement initialisée")
    void testConstructeur() {
        assertEquals("SkyISEP Test", compagnie.getNom());
        assertEquals("ST", compagnie.getCodeIATA());
        assertNotNull(compagnie.getVols());
        assertTrue(compagnie.getVols().isEmpty());
    }

    // ── CRUD Aéroports ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Ajout et recherche d'un aéroport")
    void testAjouterEtRechercherAeroport() {
        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        compagnie.ajouterAeroport(cdg);

        Aeroport trouve = compagnie.rechercherAeroport("CDG");
        assertNotNull(trouve);
        assertEquals("Charles de Gaulle", trouve.getNom());
    }

    @Test
    @DisplayName("Suppression d'un aéroport")
    void testSupprimerAeroport() {
        compagnie.ajouterAeroport(new Aeroport("ORY", "Orly", "Paris", "France"));
        assertTrue(compagnie.supprimerAeroport("ORY"));
        assertNull(compagnie.rechercherAeroport("ORY"));
    }

    // ── CRUD Passagers ──────────────────────────────────────────────────────

    @Test
    @DisplayName("Ajout d'un passager et vérification dans la liste")
    void testAjouterPassager() {
        Passager p = new Passager("P001", "Dupont", "Jean", "j@mail.com", "06", "FR123");
        compagnie.ajouterPassager(p);

        assertEquals(1, compagnie.getPassagers().size());
    }

    @Test
    @DisplayName("Recherche d'un passager par ID")
    void testRechercherPassager() {
        Passager p = new Passager("P002", "Martin", "Luc", "luc@mail.com", "07", "FR456");
        compagnie.ajouterPassager(p);

        Passager trouve = compagnie.rechercherPassager("P002");
        assertNotNull(trouve);
        assertEquals("Martin", trouve.getNom());
    }

    // ── CRUD Employés ───────────────────────────────────────────────────────

    @Test
    @DisplayName("Ajout d'un pilote et d'un personnel de cabine")
    void testAjouterEmployes() {
        Pilote pilote = new Pilote("E001", "Lefèvre", "Marc", "marc@mail.com", "06", "EMP001", "5000", "ATPL-001", "3000");
        PersonnelCabine pc = new PersonnelCabine("E002", "Durand", "Marie", "marie@mail.com", "07", "EMP002", "3000", "Hôtesse", "5");

        compagnie.ajouterEmploye(pilote);
        compagnie.ajouterEmploye(pc);

        assertEquals(2, compagnie.getEmployes().size());
    }

    @Test
    @DisplayName("Polymorphisme — obtenirRole() diffère entre Pilote et PersonnelCabine")
    void testPolymorphismeRole() {
        Pilote pilote = new Pilote("E001", "A", "B", "a@b.com", "06", "EMP1", "5000", "ATPL", "1000");
        PersonnelCabine pc = new PersonnelCabine("E002", "C", "D", "c@d.com", "07", "EMP2", "3000", "Hôtesse", "3");

        assertTrue(pilote.obtenirRole().contains("Pilote"));
        assertTrue(pc.obtenirRole().contains("Personnel Cabine"));
        assertNotEquals(pilote.obtenirRole(), pc.obtenirRole());
    }

    // ── CRUD Avions ─────────────────────────────────────────────────────────

    @Test
    @DisplayName("Ajout d'un avion")
    void testAjouterAvion() {
        Avion avion = new Avion("F-GKXA", "A320", 180);
        compagnie.ajouterAvion(avion);
        assertEquals(1, compagnie.getAvions().size());
    }

    // ── obtenirInformation ──────────────────────────────────────────────────

    @Test
    @DisplayName("obtenirInformation() contient le nom de la compagnie")
    void testObtenirInformation() {
        String info = compagnie.obtenirInformation();
        assertNotNull(info);
        assertTrue(info.contains("SkyISEP Test"));
    }

    // ── Lister ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("listerAeroports() retourne une copie défensive")
    void testListerAeroports() {
        compagnie.ajouterAeroport(new Aeroport("CDG", "CDG", "Paris", "France"));
        List<Aeroport> liste = compagnie.listerAeroports();
        assertEquals(1, liste.size());
        // Modifier la copie ne doit pas modifier l'original
        liste.clear();
        assertEquals(1, compagnie.listerAeroports().size());
    }
}
