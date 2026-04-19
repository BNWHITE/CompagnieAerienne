package com.isep.airline;

import com.isep.airline.model.*;
import com.isep.airline.service.RechercheService;
import org.junit.jupiter.api.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link RechercheService}.
 *
 * @author Kahina Medjkoune
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RechercheServiceTest {

    private static RechercheService service;
    private static List<Vol> vols;
    private static List<Passager> passagers;
    private static List<Avion> avions;
    private static List<Reservation> reservations;

    @BeforeAll
    static void init() {
        service = new RechercheService();

        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        Aeroport jfk = new Aeroport("JFK", "John F. Kennedy", "New York", "USA");
        Aeroport lhr = new Aeroport("LHR", "Heathrow", "Londres", "UK");

        Avion a1 = new Avion("F-GKXA", "Airbus A320", 180);
        Avion a2 = new Avion("F-GKXB", "Boeing 777", 350);
        Avion a3 = new Avion("F-GKXC", "Airbus A380", 500);
        a3.setDisponible(false);
        avions = List.of(a1, a2, a3);

        Passager p1 = new Passager("P001", "Dupont", "Jean", "jean@test.com", "+33612345678", "AB123456");
        Passager p2 = new Passager("P002", "Martin", "Alice", "alice@test.com", "+33698765432", "CD789012");
        passagers = List.of(p1, p2);

        CourtCourrier v1 = new CourtCourrier();
        v1.setNumeroVol("AF100");
        v1.setAeroportDepart(cdg);
        v1.setAeroportArrivee(lhr);
        v1.setDateDepart("15/06/2026");
        v1.setPrix("150.0");
        v1.setStatut("PLANIFIE");
        v1.setAvion(a1);
        v1.getPassagers().add(p1);

        CourtCourrier v2 = new CourtCourrier();
        v2.setNumeroVol("AF200");
        v2.setAeroportDepart(cdg);
        v2.setAeroportArrivee(jfk);
        v2.setDateDepart("20/07/2026");
        v2.setPrix("500.0");
        v2.setStatut("TERMINE");
        v2.setAvion(a2);
        v2.getPassagers().add(p1);
        v2.getPassagers().add(p2);

        CourtCourrier v3 = new CourtCourrier();
        v3.setNumeroVol("AF300");
        v3.setAeroportDepart(lhr);
        v3.setAeroportArrivee(jfk);
        v3.setDateDepart("15/06/2026");
        v3.setPrix("400.0");
        v3.setStatut("ANNULE");

        vols = List.of(v1, v2, v3);

        Reservation r1 = new Reservation("RES-001", p1, v1);
        Reservation r2 = new Reservation("RES-002", p2, v2);
        r2.setStatut("ANNULEE");
        reservations = List.of(r1, r2);
    }

    // ========================= RECHERCHE VOLS =========================

    @Test
    @Order(1)
    void testRechercherVolsParTrajet() {
        List<Vol> result = service.rechercherVolsParTrajet(vols, "Paris", "Londres");
        assertEquals(1, result.size());
        assertEquals("AF100", result.get(0).getNumeroVol());
    }

    @Test
    @Order(2)
    void testRechercherVolsParTrajetInexistant() {
        List<Vol> result = service.rechercherVolsParTrajet(vols, "Tokyo", "Sydney");
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(3)
    void testRechercherVolsParDate() {
        List<Vol> result = service.rechercherVolsParDate(vols, "15/06/2026");
        assertEquals(2, result.size());
    }

    @Test
    @Order(4)
    void testRechercherVolsParStatut() {
        List<Vol> result = service.rechercherVolsParStatut(vols, "ANNULE");
        assertEquals(1, result.size());
        assertEquals("AF300", result.get(0).getNumeroVol());
    }

    @Test
    @Order(5)
    void testRechercherVolsParPrix() {
        List<Vol> result = service.rechercherVolsParPrix(vols, 100, 200);
        assertEquals(1, result.size());
        assertEquals("AF100", result.get(0).getNumeroVol());
    }

    @Test
    @Order(6)
    void testRechercherVolsDisponibles() {
        List<Vol> result = service.rechercherVolsDisponibles(vols);
        // v1 (PLANIFIE, 1/180) et v2 (TERMINE, 2/350) ont des places
        // v3 (ANNULE) est exclu
        assertEquals(2, result.size());
    }

    @Test
    @Order(7)
    void testTrierVolsParPrix() {
        List<Vol> tries = service.trierVolsParPrix(vols);
        assertEquals(3, tries.size());
        assertEquals("AF100", tries.get(0).getNumeroVol()); // 150
        assertEquals("AF300", tries.get(1).getNumeroVol()); // 400
        assertEquals("AF200", tries.get(2).getNumeroVol()); // 500
    }

    // ========================= RECHERCHE PASSAGERS =========================

    @Test
    @Order(8)
    void testRechercherParPasseport() {
        Passager p = service.rechercherParPasseport(passagers, "AB123456");
        assertNotNull(p);
        assertEquals("P001", p.getId());
    }

    @Test
    @Order(9)
    void testRechercherParPasseportInexistant() {
        Passager p = service.rechercherParPasseport(passagers, "ZZ999999");
        assertNull(p);
    }

    @Test
    @Order(10)
    void testRechercherPassagersParNom() {
        List<Passager> result = service.rechercherPassagersParNom(passagers, "dup");
        assertEquals(1, result.size());
        assertEquals("Dupont", result.get(0).getNom());
    }

    @Test
    @Order(11)
    void testRechercherPassagerParEmail() {
        Passager p = service.rechercherPassagerParEmail(passagers, "alice@test.com");
        assertNotNull(p);
        assertEquals("P002", p.getId());
    }

    @Test
    @Order(12)
    void testRechercherPassagersDuVol() {
        List<Passager> result = service.rechercherPassagersDuVol(vols.get(1));
        assertEquals(2, result.size());
    }

    // ========================= RECHERCHE RESERVATIONS =========================

    @Test
    @Order(13)
    void testRechercherReservationsParPassager() {
        List<Reservation> result = service.rechercherReservationsParPassager(reservations, "P001");
        assertEquals(1, result.size());
        assertEquals("RES-001", result.get(0).getNumeroReservation());
    }

    @Test
    @Order(14)
    void testRechercherReservationsParStatut() {
        List<Reservation> result = service.rechercherReservationsParStatut(reservations, "ANNULEE");
        assertEquals(1, result.size());
    }

    @Test
    @Order(15)
    void testRechercherReservationParNumero() {
        Reservation r = service.rechercherReservationParNumero(reservations, "RES-002");
        assertNotNull(r);
        assertEquals("ANNULEE", r.getStatut());
    }

    // ========================= RECHERCHE AVIONS =========================

    @Test
    @Order(16)
    void testRechercherAvionsDisponibles() {
        List<Avion> result = service.rechercherAvionsDisponibles(avions);
        assertEquals(2, result.size());
    }

    @Test
    @Order(17)
    void testRechercherAvionsParModele() {
        List<Avion> result = service.rechercherAvionsParModele(avions, "airbus");
        assertEquals(2, result.size());
    }

    @Test
    @Order(18)
    void testRechercherAvionsParCapaciteMin() {
        List<Avion> result = service.rechercherAvionsParCapaciteMin(avions, 300);
        assertEquals(2, result.size());
    }

    // ========================= RECHERCHE GLOBALE =========================

    @Test
    @Order(19)
    void testRechercheGlobalePassagers() {
        List<Passager> result = service.rechercheGlobalePassagers(passagers, "jean");
        assertEquals(1, result.size());
        assertEquals("P001", result.get(0).getId());
    }

    @Test
    @Order(20)
    void testRechercheGlobaleParEmail() {
        List<Passager> result = service.rechercheGlobalePassagers(passagers, "alice@test");
        assertEquals(1, result.size());
    }

    // ========================= CAS LIMITES =========================

    @Test
    @Order(21)
    void testRechercheAvecParametresNull() {
        assertTrue(service.rechercherVolsParTrajet(null, "Paris", "Londres").isEmpty());
        assertTrue(service.rechercherVolsParDate(null, "15/06/2026").isEmpty());
        assertNull(service.rechercherParPasseport(null, "AB123456"));
        assertTrue(service.rechercherPassagersParNom(null, "test").isEmpty());
        assertTrue(service.rechercherAvionsDisponibles(null).isEmpty());
    }
}
