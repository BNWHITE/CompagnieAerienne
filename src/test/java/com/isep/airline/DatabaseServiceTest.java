package com.isep.airline;

import com.isep.airline.model.Passager;
import com.isep.airline.service.DatabaseService;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le {@link DatabaseService} (bonus — JDBC + H2).
 *
 * <p>
 * Utilise une base H2 embarquée en mémoire pour éviter de polluer le disque.
 * </p>
 *
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 */
@DisplayName("Tests DatabaseService (JDBC + H2)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DatabaseServiceTest {

    private static DatabaseService db;

    /**
     * Ouvre la connexion et initialise les tables une seule fois avant tous les
     * tests.
     */
    @BeforeAll
    static void setUpAll() throws SQLException {
        db = new DatabaseService();
        db.ouvrirConnexion();
        db.initialiserBase();
        db.viderToutesLesTables();
    }

    /**
     * Ferme la connexion après tous les tests.
     */
    @AfterAll
    static void tearDownAll() throws SQLException {
        if (db != null) {
            db.viderToutesLesTables();
            db.fermerConnexion();
        }
    }

    // ── CREATE ──────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("Insérer un passager dans la base")
    void testInsererPassager() throws SQLException {
        Passager p = new Passager("P001", "Dupont", "Jean", "jean@mail.com", "0600000000", "FR123");
        boolean ok = db.insererPassager(p);
        assertTrue(ok, "L'insertion doit réussir");
    }

    @Test
    @Order(2)
    @DisplayName("Insérer un vol dans la base")
    void testInsererVol() throws SQLException {
        boolean ok = db.insererVol("SK101", "Court Courrier", "2026-06-01", "2026-06-01", "120");
        assertTrue(ok);
    }

    // ── READ ────────────────────────────────────────────────────────────────

    @Test
    @Order(3)
    @DisplayName("Lister les passagers retourne les données insérées")
    void testListerPassagers() throws SQLException {
        List<Passager> passagers = db.listerPassagers();
        assertFalse(passagers.isEmpty(), "Il doit y avoir au moins 1 passager");
        assertEquals("Dupont", passagers.get(0).getNom());
    }

    @Test
    @Order(4)
    @DisplayName("Rechercher un passager par ID")
    void testRechercherPassager() throws SQLException {
        Passager p = db.rechercherPassager("P001");
        assertNotNull(p);
        assertEquals("Jean", p.getPrenom());
        assertEquals("FR123", p.getNumeroPasseport());
    }

    @Test
    @Order(5)
    @DisplayName("Rechercher un passager inexistant retourne null")
    void testRechercherPassagerInexistant() throws SQLException {
        assertNull(db.rechercherPassager("P_INEXISTANT"));
    }

    @Test
    @Order(6)
    @DisplayName("Lister les vols retourne les données insérées")
    void testListerVols() throws SQLException {
        List<String> vols = db.listerVols();
        assertFalse(vols.isEmpty());
        assertTrue(vols.get(0).contains("SK101"));
    }

    @Test
    @Order(7)
    @DisplayName("Compter les passagers")
    void testCompterPassagers() throws SQLException {
        int count = db.compterPassagers();
        assertTrue(count >= 1);
    }

    // ── UPDATE ──────────────────────────────────────────────────────────────

    @Test
    @Order(8)
    @DisplayName("Mettre à jour un passager")
    void testMettreAJourPassager() throws SQLException {
        Passager p = new Passager("P001", "Dupont-Martin", "Jean", "jean.new@mail.com", "0600000000", "FR123");
        boolean ok = db.mettreAJourPassager(p);
        assertTrue(ok);

        Passager maj = db.rechercherPassager("P001");
        assertEquals("Dupont-Martin", maj.getNom());
        assertEquals("jean.new@mail.com", maj.getEmail());
    }

    // ── DELETE ───────────────────────────────────────────────────────────────

    @Test
    @Order(9)
    @DisplayName("Supprimer un passager")
    void testSupprimerPassager() throws SQLException {
        boolean ok = db.supprimerPassager("P001");
        assertTrue(ok);
        assertNull(db.rechercherPassager("P001"));
    }

    @Test
    @Order(10)
    @DisplayName("Supprimer un passager inexistant retourne false")
    void testSupprimerPassagerInexistant() throws SQLException {
        assertFalse(db.supprimerPassager("P_NOPE"));
    }
}
