package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un passager de la compagnie aérienne.
 *
 * <p>
 * Hérite de {@link Personne} (qui implémente {@link ObtenirInformation}).
 * Un passager possède un numéro de passeport et peut effectuer plusieurs
 * réservations.
 * </p>
 *
 * <p>
 * <b>Relation :</b> {@code Passager} → {@code List<Reservation>} (composition).
 * </p>
 *
 * @author Équipe SkyISEP
 * @author Kahina Medjkoune
 * @version 1.0
 * @since 2025
 * @see Personne
 * @see Reservation
 */
public class Passager extends Personne {
    /** Numéro de passeport unique du passager. */
    private String numeroPasseport;
    /** Liste des réservations effectuées par le passager (composition). */
    private List<Reservation> reservations;

    /**
     * Constructeur par défaut. Initialise la liste de réservations.
     */
    public Passager() {
        super();
        this.reservations = new ArrayList<>();
    }

    /**
     * Constructeur complet.
     *
     * @param id              identifiant unique du passager (ex : {@code "P001"})
     * @param nom             nom de famille
     * @param prenom          prénom
     * @param email           adresse e-mail
     * @param telephone       numéro de téléphone
     * @param numeroPasseport numéro de passeport
     */
    public Passager(String id, String nom, String prenom, String email, String telephone, String numeroPasseport) {
        super(id, nom, prenom, email, telephone);
        this.numeroPasseport = numeroPasseport;
        this.reservations = new ArrayList<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Réserve un vol pour ce passager.
     *
     * <p>
     * Crée une nouvelle {@link Reservation} et l'ajoute à la liste des réservations
     * du passager, puis ajoute le passager au vol.
     * </p>
     *
     * @param vol le vol à réserver (ne doit pas être {@code null} ni indisponible)
     * @return la {@link Reservation} créée, ou {@code null} si le vol est invalide
     */
    public Reservation reserverVol(Vol vol) {
        if (vol == null) {
            System.out.println("Erreur : le vol spécifié est null.");
            return null;
        }
        if (!vol.isDisponible()) {
            System.out.println("Erreur : le vol " + vol.getNumeroVol() + " n'est pas disponible.");
            return null;
        }

        String numReservation = "RES-" + System.currentTimeMillis();
        Reservation reservation = new Reservation(numReservation, this, vol);
        this.reservations.add(reservation);
        vol.ajouterPassager(this);
        System.out.println("Réservation effectuée avec succès ! Numéro : " + numReservation);
        return reservation;
    }

    /**
     * Annuler une réservation par son numéro.
     *
     * @param numeroReservation le numéro de la réservation à annuler
     * @return true si l'annulation a réussi, false sinon
     */
    public boolean annulerReservation(String numeroReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getNumeroReservation().equals(numeroReservation)) {
                Reservation reservation = reservations.get(i);
                reservation.getVol().retirerPassager(this);
                reservation.setStatut("ANNULEE");
                reservations.remove(i);
                System.out.println("Réservation " + numeroReservation + " annulée avec succès.");
                return true;
            }
        }
        System.out.println("Erreur : réservation " + numeroReservation + " introuvable.");
        return false;
    }

    /**
     * Obtenir les informations sur une réservation par son numéro.
     *
     * @param numeroReservation le numéro de la réservation
     * @return la réservation correspondante ou null
     */
    public Reservation obtenirReservation(String numeroReservation) {
        for (Reservation reservation : reservations) {
            if (reservation.getNumeroReservation().equals(numeroReservation)) {
                return reservation;
            }
        }
        System.out.println("Réservation " + numeroReservation + " introuvable.");
        return null;
    }

    /**
     * Obtenir toutes les réservations du passager.
     *
     * @return la liste des réservations
     */
    public List<Reservation> obtenirReservations() {
        return new ArrayList<>(reservations);
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Passeport   : " + numeroPasseport + "\n"
                + "Nb réserv.  : " + reservations.size();
    }

    // ==================== Getters & Setters ====================

    public String getNumeroPasseport() {
        return numeroPasseport;
    }

    public void setNumeroPasseport(String numeroPasseport) {
        this.numeroPasseport = numeroPasseport;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }
}
