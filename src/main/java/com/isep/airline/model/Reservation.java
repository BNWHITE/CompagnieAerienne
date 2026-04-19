package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une réservation de vol.
 *
 * <p>
 * <b>Association</b> entre un {@link Passager} et un ou plusieurs {@link Vol}s.
 * Implémente {@link ObtenirInformation}.
 * </p>
 *
 * <p>
 * Statuts possibles : {@code CONFIRMEE}, {@code ANNULEE}, {@code EN_ATTENTE}.
 * </p>
 *
 * @author Équipe SkyISEP
 * @author Kahina Medjkoune
 * @version 1.0
 * @since 2025
 * @see Passager
 * @see Vol
 */
public class Reservation implements ObtenirInformation {
    /** Numéro unique de la réservation. */
    private String numeroReservation;
    /** Passager ayant effectué la réservation. */
    private Passager passager;
    /** Vol principal de la réservation. */
    private Vol vol;
    /** Liste de tous les vols réservés (multi-segments). */
    private List<Vol> volsReserves;
    /** Date de la réservation au format JJ/MM/AAAA. */
    private String dateReservation;
    /** Statut : CONFIRMEE, ANNULEE ou EN_ATTENTE. */
    private String statut;
    /** Montant total de la réservation (format String). */
    private String montantTotal;

    /**
     * Constructeur par défaut. Statut initialisé à {@code "CONFIRMEE"}.
     */
    public Reservation() {
        this.volsReserves = new ArrayList<>();
        this.statut = "CONFIRMEE";
        this.dateReservation = java.time.LocalDate.now().toString();
    }

    /**
     * Constructeur complet.
     *
     * @param numeroReservation numéro unique de réservation (ex :
     *                          {@code "RES-001"})
     * @param passager          le passager qui effectue la réservation
     * @param vol               le vol principal réservé
     */
    public Reservation(String numeroReservation, Passager passager, Vol vol) {
        this.numeroReservation = numeroReservation;
        this.passager = passager;
        this.vol = vol;
        this.volsReserves = new ArrayList<>();
        this.volsReserves.add(vol);
        this.statut = "CONFIRMEE";
        this.dateReservation = java.time.LocalDate.now().toString();
        this.montantTotal = vol.getPrix();
    }

    // ==================== Méthodes métier ====================

    /**
     * Ajoute un vol supplémentaire à la réservation et met à jour le montant total.
     *
     * @param vol le vol à ajouter (ignoré s'il est {@code null} ou déjà présent)
     */
    public void ajouterVol(Vol vol) {
        if (vol != null && !volsReserves.contains(vol)) {
            volsReserves.add(vol);
            // Calcul du montant total
            try {
                double total = Double.parseDouble(this.montantTotal) + Double.parseDouble(vol.getPrix());
                this.montantTotal = String.valueOf(total);
            } catch (NumberFormatException e) {
                // Si le prix n'est pas un nombre valide, on garde l'ancien montant
            }
            System.out.println("Vol " + vol.getNumeroVol() + " ajouté à la réservation " + numeroReservation);
        }
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Informations Réservation =====\n");
        sb.append("N° Réservation : ").append(numeroReservation).append("\n");
        sb.append("Passager       : ").append(passager.getNom()).append(" ").append(passager.getPrenom()).append("\n");
        sb.append("Date réserv.   : ").append(dateReservation).append("\n");
        sb.append("Statut         : ").append(statut).append("\n");
        sb.append("Montant total  : ").append(montantTotal).append(" €\n");
        sb.append("Vols réservés  :");
        for (Vol v : volsReserves) {
            String depart = (v.getAeroportDepart() != null) ? v.getAeroportDepart().getCodeIATA() : "?";
            String arrivee = (v.getAeroportArrivee() != null) ? v.getAeroportArrivee().getCodeIATA() : "?";
            sb.append("\n  - ").append(v.getNumeroVol()).append(" : ").append(depart).append(" -> ").append(arrivee);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return obtenirInformation();
    }

    // ==================== Getters & Setters ====================

    public String getNumeroReservation() {
        return numeroReservation;
    }

    public void setNumeroReservation(String numeroReservation) {
        this.numeroReservation = numeroReservation;
    }

    public Passager getPassager() {
        return passager;
    }

    public void setPassager(Passager passager) {
        this.passager = passager;
    }

    public Vol getVol() {
        return vol;
    }

    public void setVol(Vol vol) {
        this.vol = vol;
    }

    public List<Vol> getVolsReserves() {
        return volsReserves;
    }

    public void setVolsReserves(List<Vol> volsReserves) {
        this.volsReserves = volsReserves;
    }

    public String getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(String montantTotal) {
        this.montantTotal = montantTotal;
    }
}
