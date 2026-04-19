package com.isep.airline;

import com.isep.airline.model.*;
import com.isep.airline.service.StatistiquesService;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link StatistiquesService}.
 *
 * @author Kahina Medjkoune
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StatistiquesServiceTest {

    private static StatistiquesService service;
    private static List<Vol> vols;
    private static List<Passager> passagers;
    private static List<Avion> avions;
    private static List<Reservation> reservations;

    @BeforeAll
    static void init() {
        service = new StatistiquesService();

        // Créer des aéroports
        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        Aeroport jfk = new Aeroport("JFK", "John F. Kennedy", "New York", "USA");
        Aeroport lhr = new Aeroport("LHR", "Heathrow", "Londres", "UK");

        // Créer des avions
        Avion a1 = new Avion("F-GKXA", "Airbus A320", 180);
        Avion a2 = new Avion("F-GKXB", "Boeing 777", 350);
        a2.setDisponible(false);
        avions = List.of(a1, a2);

        // Créer des passagers
        Passager p1 = new Passager("P001", "Dupont", "Jean", "jean@test.com", "+33612345678", "AB123456");
        Passager p2 = new Passager("P002", "Martin", "Alice", "alice@test.com", "+33698765432", "CD789012");
        Passager p3 = new Passager("P003", "Durand", "Pierre", "pierre@test.com", "+33611111111", "EF345678");
        passagers = List.of(p1, p2, p3);

        // Créer des vols (utilise CourtCourrier comme implémentation concrète)
        CourtCourrier v1 = new CourtCourrier();
        v1.setNumeroVol("AF100");
        v1.setAeroportDepart(cdg);
        v1.setAeroportArrivee(lhr);
        v1.setPrix("150.0");
        v1.setStatut("TERMINE");
        v1.setAvion(a1);
        v1.getPassagers().add(p1);
        v1.getPassagers().add(p2);

        CourtCourrier v2 = new CourtCourrier();
        v2.setNumeroVol("AF200");
        v2.setAeroportDepart(cdg);
        v2.setAeroportArrivee(jfk);
        v2.setPrix("500.0");
        v2.setStatut("PLANIFIE");
        v2.setAvion(a2);
        v2.getPassagers().add(p3);

        CourtCourrier v3 = new CourtCourrier();
        v3.setNumeroVol("AF300");
        v3.setAeroportDepart(lhr);
        v3.setAeroportArrivee(jfk);
        v3.setPrix("400.0");
        v3.setStatut("ANNULE");

        vols = List.of(v1, v2, v3);

        // Réservations
        Reservation r1 = new Reservation("RES-001", p1, v1);
        Reservation r2 = new Reservation("RES-002", p2, v1);
        r2.setStatut("ANNULEE");
        Reservation r3 = new Reservation("RES-003", p3, v2);
        reservations = List.of(r1, r2, r3);
    }

    // ========================= TESTS VOLS =========================

    @Test
    @Order(1)
    void testCompterVolsParStatut() {
        Map<String, Long> result = service.compterVolsParStatut(vols);
        assertEquals(1L, result.get("TERMINE"));
        assertEquals(1L, result.get("PLANIFIE"));
        assertEquals(1L, result.get("ANNULE"));
    }

    @Test
    @Order(2)
    void testTauxAnnulation() {
        double taux = service.calculerTauxAnnulation(vols);
        assertEquals(33.33, taux, 0.5);
    }

    @Test
    @Order(3)
    void testTauxAnnulationListeVide() {
        assertEquals(0.0, service.calculerTauxAnnulation(Collections.emptyList()));
        assertEquals(0.0, service.calculerTauxAnnulation(null));
    }

    @Test
    @Order(4)
    void testTopDestinations() {
        List<Map.Entry<String, Long>> top = service.topDestinations(vols, 2);
        assertFalse(top.isEmpty());
        assertEquals("New York", top.get(0).getKey());
        assertEquals(2L, top.get(0).getValue());
    }

    @Test
    @Order(5)
    void testTopOrigines() {
        List<Map.Entry<String, Long>> top = service.topOrigines(vols, 1);
        assertFalse(top.isEmpty());
        assertEquals("Paris", top.get(0).getKey());
    }

    @Test
    @Order(6)
    void testPrixMoyen() {
        double prix = service.calculerPrixMoyen(vols);
        assertEquals(350.0, prix, 0.01);
    }

    @Test
    @Order(7)
    void testRevenuTotal() {
        double revenu = service.calculerRevenuTotal(vols);
        // v1 : TERMINE, prix 150.0, 2 passagers → 300.0
        assertEquals(300.0, revenu, 0.01);
    }

    // ========================= TESTS PASSAGERS =========================

    @Test
    @Order(8)
    void testCompterPassagersUniques() {
        int count = service.compterPassagersUniques(vols);
        assertEquals(3, count);
    }

    @Test
    @Order(9)
    void testTauxOccupationMoyen() {
        double taux = service.calculerTauxOccupationMoyen(vols);
        // v1 : 2/180 = 1.11%, v2 : 1/350 = 0.29% → moyenne ≈ 0.70%
        assertTrue(taux > 0 && taux < 5);
    }

    @Test
    @Order(10)
    void testPassagersFrequents() {
        // Aucun passager n'a >= 5 réservations
        List<Passager> frequents = service.trouverPassagersFrequents(passagers, 5);
        assertTrue(frequents.isEmpty());
    }

    // ========================= TESTS RESERVATIONS =========================

    @Test
    @Order(11)
    void testCompterReservationsParStatut() {
        Map<String, Long> result = service.compterReservationsParStatut(reservations);
        assertEquals(2L, result.get("CONFIRMEE"));
        assertEquals(1L, result.get("ANNULEE"));
    }

    @Test
    @Order(12)
    void testMontantTotalReservations() {
        double montant = service.calculerMontantTotalReservations(reservations);
        // r1 (CONFIRMEE, prix=150.0) + r3 (CONFIRMEE, prix=500.0) = 650.0
        assertTrue(montant > 0);
    }

    // ========================= TESTS FLOTTE =========================

    @Test
    @Order(13)
    void testAvionsParDisponibilite() {
        Map<String, Long> result = service.compterAvionsParDisponibilite(avions);
        assertEquals(1L, result.get("Disponible"));
        assertEquals(1L, result.get("Indisponible"));
    }

    @Test
    @Order(14)
    void testCapaciteTotaleFlotte() {
        int capacite = service.calculerCapaciteTotaleFlotte(avions);
        assertEquals(530, capacite);
    }

    @Test
    @Order(15)
    void testAvionsParModele() {
        Map<String, Long> result = service.compterAvionsParModele(avions);
        assertEquals(1L, result.get("Airbus A320"));
        assertEquals(1L, result.get("Boeing 777"));
    }

    // ========================= TEST RAPPORT =========================

    @Test
    @Order(16)
    void testGenererRapport() {
        CompagnieAerienne compagnie = new CompagnieAerienne("SkyISEP Airlines", "SI");
        for (Vol v : vols) compagnie.planifierVol(v);
        for (Passager p : passagers) compagnie.ajouterPassager(p);
        for (Avion a : avions) compagnie.ajouterAvion(a);
        for (Reservation r : reservations) compagnie.getReservations().add(r);

        String rapport = service.genererRapport(compagnie);
        assertNotNull(rapport);
        assertTrue(rapport.contains("SkyISEP Airlines"));
        assertTrue(rapport.contains("VOLS"));
        assertTrue(rapport.contains("PASSAGERS"));
        assertTrue(rapport.contains("FLOTTE"));
        assertTrue(rapport.contains("RÉSERVATIONS"));
    }

    // ========================= TESTS CAS LIMITES =========================

    @Test
    @Order(17)
    void testListesNulles() {
        assertEquals(Collections.emptyMap(), service.compterVolsParStatut(null));
        assertEquals(0.0, service.calculerPrixMoyen(null));
        assertEquals(0, service.compterPassagersUniques(null));
        assertEquals(0.0, service.calculerTauxOccupationMoyen(null));
        assertEquals(0, service.calculerCapaciteTotaleFlotte(null));
    }
}
