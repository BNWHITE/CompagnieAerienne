package com.isep.airline;

import com.isep.airline.model.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour les classes {@link Vol}, {@link CourtCourrier},
 * {@link MoyenCourrier} et {@link LongCourrier}.
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 */
@DisplayName("Tests Vol (Court/Moyen/Long Courrier)")
class VolTest {

    private Aeroport cdg;
    private Aeroport jfk;
    private Aeroport bcn;

    @BeforeEach
    void setUp() {
        cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        jfk = new Aeroport("JFK", "JFK International", "New York", "USA");
        bcn = new Aeroport("BCN", "El Prat", "Barcelone", "Espagne");
    }

    // ── CourtCourrier ───────────────────────────────────────────────────────

    @Test
    @DisplayName("CourtCourrier — getTypeVol() retourne 'Court Courrier'")
    void testTypeCourtCourrier() {
        Vol vol = new CourtCourrier("SK101", cdg, bcn, "2026-06-01", "2026-06-01", "08:00", "10:00", "120", "1000");
        assertEquals("Court Courrier", vol.getTypeVol());
    }

    @Test
    @DisplayName("CourtCourrier — statut initial est PLANIFIE")
    void testStatutInitial() {
        Vol vol = new CourtCourrier("SK101", cdg, bcn, "2026-06-01", "2026-06-01", "08:00", "10:00", "120", "1000");
        assertEquals("PLANIFIE", vol.getStatut());
    }

    // ── Aéroports (association bidirectionnelle) ────────────────────────────

    @Test
    @DisplayName("La création d'un vol ajoute le vol aux Deque de l'aéroport")
    void testAssociationBidirectionnelle() {
        Vol vol = new CourtCourrier("SK102", cdg, bcn, "2026-06-01", "2026-06-01", "08:00", "10:00", "150", "1050");
        assertTrue(cdg.getVolsDepart().contains(vol), "Le vol doit être dans les départs de CDG");
        assertTrue(bcn.getVolsArrivee().contains(vol), "Le vol doit être dans les arrivées de BCN");
    }

    // ── Passagers ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("ajouterPassager() / retirerPassager() fonctionnent correctement")
    void testGestionPassagers() {
        Vol vol = new CourtCourrier("SK103", cdg, bcn, "2026-06-15", "2026-06-15", "12:00", "14:00", "100", "1000");
        Passager p = new Passager("P001", "Doe", "John", "john@mail.com", "06", "US000");

        vol.ajouterPassager(p);
        assertEquals(1, vol.getPassagers().size());
        assertTrue(vol.getPassagers().contains(p));

        vol.retirerPassager(p);
        assertEquals(0, vol.getPassagers().size());
    }

    // ── Disponibilité ───────────────────────────────────────────────────────

    @Test
    @DisplayName("isDisponible() retourne true pour un vol PLANIFIE")
    void testDisponibilite() {
        Vol vol = new CourtCourrier("SK104", cdg, bcn, "2026-07-01", "2026-07-01", "09:00", "11:00", "80", "1000");
        assertTrue(vol.isDisponible());
    }

    @Test
    @DisplayName("isDisponible() retourne false pour un vol ANNULE")
    void testDisponibiliteAnnule() {
        Vol vol = new CourtCourrier("SK105", cdg, bcn, "2026-07-01", "2026-07-01", "09:00", "11:00", "80", "1000");
        vol.setStatut("ANNULE");
        assertFalse(vol.isDisponible());
    }

    // ── Polymorphisme ───────────────────────────────────────────────────────

    @Test
    @DisplayName("Polymorphisme — getTypeVol() varie selon la sous-classe")
    void testPolymorphismeTypeVol() {
        Vol court = new CourtCourrier("SK201", cdg, bcn, "2026-08-01", "2026-08-01", "08:00", "10:00", "100", "900");
        Vol moyen = new MoyenCourrier("SK202", cdg, bcn, "2026-08-01", "2026-08-01", "08:00", "12:00", "250", "2500");
        Vol longC = new LongCourrier("SK203", cdg, jfk, "2026-08-01", "2026-08-01", "10:00", "22:00", "600", "5800");

        assertEquals("Court Courrier", court.getTypeVol());
        assertEquals("Moyen Courrier", moyen.getTypeVol());
        assertEquals("Long Courrier", longC.getTypeVol());
    }

    // ── obtenirInformation ──────────────────────────────────────────────────

    @Test
    @DisplayName("obtenirInformation() contient le numéro de vol et le type")
    void testObtenirInformation() {
        Vol vol = new CourtCourrier("SK300", cdg, bcn, "2026-09-01", "2026-09-01", "08:00", "10:00", "120", "1050");
        String info = vol.obtenirInformation();
        assertTrue(info.contains("SK300"));
        assertTrue(info.contains("Court Courrier"));
    }
}
