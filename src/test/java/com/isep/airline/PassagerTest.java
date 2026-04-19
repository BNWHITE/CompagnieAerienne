package com.isep.airline;

import com.isep.airline.model.Passager;
import com.isep.airline.model.Reservation;
import com.isep.airline.model.Aeroport;
import com.isep.airline.model.CourtCourrier;
import com.isep.airline.model.Vol;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour la classe {@link Passager}.
 *
 * <p>
 * Utilise <b>JUnit 5</b> (Jupiter API) :
 * <ul>
 * <li>{@code @Test} — marque une méthode de test</li>
 * <li>{@code @BeforeEach} — initialisation avant chaque test</li>
 * <li>{@code @DisplayName} — nom lisible du test</li>
 * <li>{@code assertEquals}, {@code assertNotNull}, {@code assertTrue} —
 * assertions</li>
 * </ul>
 * </p>
 *
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 */
@DisplayName("Tests Passager")
class PassagerTest {

    private Passager passager;

    /**
     * Initialise un passager de test avant chaque test.
     */
    @BeforeEach
    void setUp() {
        passager = new Passager("P001", "Dupont", "Jean", "jean@mail.com", "0600000000", "FR123456");
    }

    // ── Constructeur & getters ──────────────────────────────────────────────

    @Test
    @DisplayName("Le constructeur initialise correctement tous les attributs")
    void testConstructeurComplet() {
        assertEquals("P001", passager.getId());
        assertEquals("Dupont", passager.getNom());
        assertEquals("Jean", passager.getPrenom());
        assertEquals("jean@mail.com", passager.getEmail());
        assertEquals("0600000000", passager.getTelephone());
        assertEquals("FR123456", passager.getNumeroPasseport());
    }

    @Test
    @DisplayName("Le constructeur par défaut crée un passager sans erreur")
    void testConstructeurParDefaut() {
        Passager p = new Passager();
        assertNotNull(p);
        assertNull(p.getId());
        assertNotNull(p.getReservations()); // La liste doit être initialisée
    }

    // ── Setters ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("Les setters modifient correctement les attributs")
    void testSetters() {
        passager.setNom("Martin");
        passager.setPrenom("Pierre");
        passager.setEmail("pierre@mail.com");
        passager.setTelephone("0700000000");
        passager.setNumeroPasseport("FR999999");

        assertEquals("Martin", passager.getNom());
        assertEquals("Pierre", passager.getPrenom());
        assertEquals("pierre@mail.com", passager.getEmail());
        assertEquals("0700000000", passager.getTelephone());
        assertEquals("FR999999", passager.getNumeroPasseport());
    }

    // ── ObtenirInformation & toString ───────────────────────────────────────

    @Test
    @DisplayName("obtenirInformation() contient le nom et le passeport")
    void testObtenirInformation() {
        String info = passager.obtenirInformation();
        assertNotNull(info);
        assertTrue(info.contains("Dupont"), "Doit contenir le nom");
        assertTrue(info.contains("FR123456"), "Doit contenir le numéro de passeport");
    }

    @Test
    @DisplayName("toString() retourne la même chose que obtenirInformation()")
    void testToString() {
        assertEquals(passager.obtenirInformation(), passager.toString());
    }

    // ── Réservation de vol ──────────────────────────────────────────────────

    @Test
    @DisplayName("reserverVol() crée une réservation valide")
    void testReserverVol() {
        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        Aeroport ory = new Aeroport("ORY", "Orly", "Paris", "France");
        Vol vol = new CourtCourrier("SK101", cdg, ory, "2026-06-01", "2026-06-01", "08:00", "09:30", "120", "350");

        Reservation reservation = passager.reserverVol(vol);

        assertNotNull(reservation, "La réservation ne doit pas être null");
        assertEquals("CONFIRMEE", reservation.getStatut());
        assertEquals(1, passager.getReservations().size());
        assertTrue(vol.getPassagers().contains(passager));
    }

    @Test
    @DisplayName("reserverVol(null) retourne null")
    void testReserverVolNull() {
        Reservation reservation = passager.reserverVol(null);
        assertNull(reservation);
    }

    // ── Annulation ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("annulerReservation() supprime la réservation de la liste")
    void testAnnulerReservation() {
        Aeroport cdg = new Aeroport("CDG", "CDG", "Paris", "France");
        Aeroport ory = new Aeroport("ORY", "Orly", "Paris", "France");
        Vol vol = new CourtCourrier("SK102", cdg, ory, "2026-07-01", "2026-07-01", "10:00", "11:30", "100", "300");
        Reservation res = passager.reserverVol(vol);
        assertNotNull(res);

        boolean resultat = passager.annulerReservation(res.getNumeroReservation());
        assertTrue(resultat);
        assertEquals(0, passager.getReservations().size());
    }

    @Test
    @DisplayName("annulerReservation() avec un numéro inconnu retourne false")
    void testAnnulerReservationInconnue() {
        boolean resultat = passager.annulerReservation("RES-INEXISTANT");
        assertFalse(resultat);
    }
}
