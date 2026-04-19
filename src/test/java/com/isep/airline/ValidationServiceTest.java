package com.isep.airline;

import com.isep.airline.model.*;
import com.isep.airline.service.ValidationService;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour {@link ValidationService}.
 *
 * @author Kahina Medjkoune
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ValidationServiceTest {

    private static ValidationService service;

    @BeforeAll
    static void init() {
        service = new ValidationService();
    }

    // ========================= TESTS EMAIL =========================

    @Test
    @Order(1)
    void testEmailValide() {
        assertTrue(service.validerEmail("jean.dupont@example.com"));
        assertTrue(service.validerEmail("test@isep.fr"));
    }

    @Test
    @Order(2)
    void testEmailInvalide() {
        assertFalse(service.validerEmail(null));
        assertFalse(service.validerEmail(""));
        assertFalse(service.validerEmail("pasunmail"));
        assertFalse(service.validerEmail("@domaine.com"));
        assertFalse(service.validerEmail("test@.com"));
    }

    // ========================= TESTS TELEPHONE =========================

    @Test
    @Order(3)
    void testTelephoneValide() {
        assertTrue(service.validerTelephone("+33612345678"));
        assertTrue(service.validerTelephone("0612345678"));
    }

    @Test
    @Order(4)
    void testTelephoneInvalide() {
        assertFalse(service.validerTelephone(null));
        assertFalse(service.validerTelephone("123"));
        assertFalse(service.validerTelephone("abcdefgh"));
    }

    // ========================= TESTS CODE IATA =========================

    @Test
    @Order(5)
    void testCodeIATAAeroportValide() {
        assertTrue(service.validerCodeIATAAeroport("CDG"));
        assertTrue(service.validerCodeIATAAeroport("jfk"));
    }

    @Test
    @Order(6)
    void testCodeIATAAeroportInvalide() {
        assertFalse(service.validerCodeIATAAeroport(null));
        assertFalse(service.validerCodeIATAAeroport("AB"));
        assertFalse(service.validerCodeIATAAeroport("ABCD"));
        assertFalse(service.validerCodeIATAAeroport("12A"));
    }

    @Test
    @Order(7)
    void testCodeIATACompagnieValide() {
        assertTrue(service.validerCodeIATACompagnie("AF"));
        assertTrue(service.validerCodeIATACompagnie("si"));
    }

    // ========================= TESTS NUMERO VOL =========================

    @Test
    @Order(8)
    void testNumeroVolValide() {
        assertTrue(service.validerNumeroVol("AF123"));
        assertTrue(service.validerNumeroVol("SI1"));
    }

    @Test
    @Order(9)
    void testNumeroVolInvalide() {
        assertFalse(service.validerNumeroVol(null));
        assertFalse(service.validerNumeroVol(""));
        assertFalse(service.validerNumeroVol("123AF"));
        assertFalse(service.validerNumeroVol("A1234"));
    }

    // ========================= TESTS PASSEPORT =========================

    @Test
    @Order(10)
    void testPasseportValide() {
        assertTrue(service.validerPasseport("AB123456"));
        assertTrue(service.validerPasseport("FR1234567890"));
    }

    @Test
    @Order(11)
    void testPasseportInvalide() {
        assertFalse(service.validerPasseport(null));
        assertFalse(service.validerPasseport("AB12"));
        assertFalse(service.validerPasseport("AB-123456"));
    }

    // ========================= TESTS DATE ET HEURE =========================

    @Test
    @Order(12)
    void testDateValide() {
        assertTrue(service.validerDate("25/12/2025"));
        assertTrue(service.validerDate("01/01/2026"));
    }

    @Test
    @Order(13)
    void testDateInvalide() {
        assertFalse(service.validerDate(null));
        assertFalse(service.validerDate(""));
        assertFalse(service.validerDate("2025-12-25"));
        assertFalse(service.validerDate("32/13/2025"));
    }

    @Test
    @Order(14)
    void testHeureValide() {
        assertTrue(service.validerHeure("08:30"));
        assertTrue(service.validerHeure("23:59"));
        assertTrue(service.validerHeure("00:00"));
    }

    @Test
    @Order(15)
    void testHeureInvalide() {
        assertFalse(service.validerHeure(null));
        assertFalse(service.validerHeure("25:00"));
        assertFalse(service.validerHeure("8:30"));
    }

    @Test
    @Order(16)
    void testCoherenceDates() {
        assertTrue(service.validerCoherenceDates("01/01/2026", "15/01/2026"));
        assertTrue(service.validerCoherenceDates("01/01/2026", "01/01/2026"));
        assertFalse(service.validerCoherenceDates("15/01/2026", "01/01/2026"));
        assertFalse(service.validerCoherenceDates("invalide", "01/01/2026"));
    }

    // ========================= TESTS PRIX ET CAPACITÉ =========================

    @Test
    @Order(17)
    void testPrixValide() {
        assertTrue(service.validerPrix("150.50"));
        assertTrue(service.validerPrix("1"));
    }

    @Test
    @Order(18)
    void testPrixInvalide() {
        assertFalse(service.validerPrix(null));
        assertFalse(service.validerPrix(""));
        assertFalse(service.validerPrix("-10"));
        assertFalse(service.validerPrix("abc"));
        assertFalse(service.validerPrix("0"));
    }

    @Test
    @Order(19)
    void testCapacite() {
        assertTrue(service.validerCapacite(180));
        assertFalse(service.validerCapacite(0));
        assertFalse(service.validerCapacite(-5));
    }

    // ========================= TESTS VALIDATION PASSAGER =========================

    @Test
    @Order(20)
    void testValiderPassagerComplet() {
        Passager p = new Passager("P001", "Dupont", "Jean", "jean@example.com", "+33612345678", "AB123456");
        List<String> erreurs = service.validerPassager(p);
        assertTrue(erreurs.isEmpty(), "Aucune erreur attendue : " + erreurs);
    }

    @Test
    @Order(21)
    void testValiderPassagerIncomplet() {
        Passager p = new Passager("", "", "", "pasunmail", "123", "AB");
        List<String> erreurs = service.validerPassager(p);
        assertFalse(erreurs.isEmpty());
        assertTrue(erreurs.size() >= 4, "Au moins 4 erreurs attendues, trouvées : " + erreurs.size());
    }

    @Test
    @Order(22)
    void testValiderPassagerNull() {
        List<String> erreurs = service.validerPassager(null);
        assertEquals(1, erreurs.size());
        assertEquals("Le passager est null", erreurs.get(0));
    }

    // ========================= TESTS VALIDATION AEROPORT =========================

    @Test
    @Order(23)
    void testValiderAeroportComplet() {
        Aeroport a = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        List<String> erreurs = service.validerAeroport(a);
        assertTrue(erreurs.isEmpty());
    }

    @Test
    @Order(24)
    void testValiderAeroportInvalide() {
        Aeroport a = new Aeroport("XX", "", "", "");
        List<String> erreurs = service.validerAeroport(a);
        assertFalse(erreurs.isEmpty());
    }

    // ========================= TESTS STATUTS =========================

    @Test
    @Order(25)
    void testStatutVol() {
        assertTrue(service.validerStatutVol("PLANIFIE"));
        assertTrue(service.validerStatutVol("en_cours"));
        assertTrue(service.validerStatutVol("TERMINE"));
        assertTrue(service.validerStatutVol("ANNULE"));
        assertFalse(service.validerStatutVol("INCONNU"));
        assertFalse(service.validerStatutVol(null));
    }

    @Test
    @Order(26)
    void testStatutReservation() {
        assertTrue(service.validerStatutReservation("CONFIRMEE"));
        assertTrue(service.validerStatutReservation("ANNULEE"));
        assertTrue(service.validerStatutReservation("EN_ATTENTE"));
        assertFalse(service.validerStatutReservation("AUTRE"));
        assertFalse(service.validerStatutReservation(null));
    }

    // ========================= TESTS NON VIDE / IMMAT =========================

    @Test
    @Order(27)
    void testEstNonVide() {
        assertTrue(service.estNonVide("test"));
        assertFalse(service.estNonVide(null));
        assertFalse(service.estNonVide(""));
        assertFalse(service.estNonVide("   "));
    }

    @Test
    @Order(28)
    void testImmatriculation() {
        assertTrue(service.validerImmatriculation("F-GKXA"));
        assertFalse(service.validerImmatriculation("FGKXA"));
        assertFalse(service.validerImmatriculation(null));
    }
}
