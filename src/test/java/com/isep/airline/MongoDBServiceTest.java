package com.isep.airline;

import com.isep.airline.model.Passager;
import com.isep.airline.service.MongoDBService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link MongoDBService}.
 *
 * <p>Teste les opérations CRUD sur MongoDB Atlas :
 * insertion, lecture, recherche, mise à jour et suppression.</p>
 *
 * @author Kahina Medjkoune
 * @version 1.0
 * @since 2025
 */
@DisplayName("Tests MongoDBService — MongoDB Atlas")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MongoDBServiceTest {

    private static MongoDBService mongoService;

    @BeforeAll
    static void setUp() {
        mongoService = new MongoDBService();
        mongoService.ouvrirConnexion();
        mongoService.viderToutesLesCollections();
    }

    @AfterAll
    static void tearDown() {
        mongoService.viderToutesLesCollections();
        mongoService.fermerConnexion();
    }

    // ── CREATE ──────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("Insérer un passager dans MongoDB")
    void testInsererPassager() {
        Passager p = new Passager("P001", "Dupont", "Jean", "jean@mail.com", "0600000000", "FR123456");
        assertTrue(mongoService.insererPassager(p));
    }

    @Test
    @Order(2)
    @DisplayName("Insérer un vol dans MongoDB")
    void testInsererVol() {
        assertTrue(mongoService.insererVol("SK101", "Court Courrier", "25/12/2025", "25/12/2025", "150"));
    }

    @Test
    @Order(3)
    @DisplayName("Insérer une réservation dans MongoDB")
    void testInsererReservation() {
        assertTrue(mongoService.insererReservation("RES-001", "P001", "SK101", "20/12/2025", "150"));
    }

    // ── READ ────────────────────────────────────────────────────────────────

    @Test
    @Order(4)
    @DisplayName("Lister les passagers depuis MongoDB")
    void testListerPassagers() {
        List<Passager> passagers = mongoService.listerPassagers();
        assertFalse(passagers.isEmpty());
        assertEquals("Dupont", passagers.get(0).getNom());
    }

    @Test
    @Order(5)
    @DisplayName("Rechercher un passager par ID")
    void testRechercherPassager() {
        Passager p = mongoService.rechercherPassager("P001");
        assertNotNull(p);
        assertEquals("Jean", p.getPrenom());
    }

    @Test
    @Order(6)
    @DisplayName("Rechercher un passager inexistant retourne null")
    void testRechercherPassagerInexistant() {
        assertNull(mongoService.rechercherPassager("P_INEXISTANT"));
    }

    @Test
    @Order(7)
    @DisplayName("Lister les vols depuis MongoDB")
    void testListerVols() {
        List<String> vols = mongoService.listerVols();
        assertFalse(vols.isEmpty());
        assertTrue(vols.get(0).contains("SK101"));
    }

    @Test
    @Order(8)
    @DisplayName("Compter les passagers et vols")
    void testCompter() {
        assertEquals(1, mongoService.compterPassagers());
        assertEquals(1, mongoService.compterVols());
    }

    // ── UPDATE ──────────────────────────────────────────────────────────────

    @Test
    @Order(9)
    @DisplayName("Mettre à jour un passager")
    void testMettreAJourPassager() {
        Passager updated = new Passager("P001", "Dupont", "Jean-Pierre", "jp@mail.com", "0611111111", "FR654321");
        assertTrue(mongoService.mettreAJourPassager(updated));
        Passager found = mongoService.rechercherPassager("P001");
        assertEquals("Jean-Pierre", found.getPrenom());
    }

    // ── DELETE ──────────────────────────────────────────────────────────────

    @Test
    @Order(10)
    @DisplayName("Supprimer un passager")
    void testSupprimerPassager() {
        assertTrue(mongoService.supprimerPassager("P001"));
    }

    @Test
    @Order(11)
    @DisplayName("Supprimer un passager inexistant retourne false")
    void testSupprimerPassagerInexistant() {
        assertFalse(mongoService.supprimerPassager("P_NOPE"));
    }
}
