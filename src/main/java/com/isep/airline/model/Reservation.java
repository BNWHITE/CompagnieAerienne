package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant une réservation.
 * Association entre un Passager et un ou plusieurs Vols.
 */
public class Reservation {
    private String numeroReservation;
    private Passager passager;
    private Vol vol;
    private List<Vol> volsReserves;
    private String dateReservation;
    private String statut; // CONFIRMEE, ANNULEE, EN_ATTENTE
    private double montantTotal;

    public Reservation() {
        this.volsReserves = new ArrayList<>();
        this.statut = "CONFIRMEE";
        this.dateReservation = java.time.LocalDate.now().toString();
    }

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
     * Ajouter un vol à la réservation.
     *
     * @param vol le vol à ajouter
     */
    public void ajouterVol(Vol vol) {
        if (vol != null && !volsReserves.contains(vol)) {
            volsReserves.add(vol);
            montantTotal += vol.getPrix();
            System.out.println("Vol " + vol.getNumeroVol() + " ajouté à la réservation " + numeroReservation);
        }
    }

    /**
     * Obtenir les informations de la réservation.
     */
    public void obtenirReservation() {
        System.out.println("===== Informations Réservation =====");
        System.out.println("N° Réservation : " + numeroReservation);
        System.out.println("Passager       : " + passager.getNom() + " " + passager.getPrenom());
        System.out.println("Date réserv.   : " + dateReservation);
        System.out.println("Statut         : " + statut);
        System.out.println("Montant total  : " + montantTotal + " €");
        System.out.println("Vols réservés  :");
        for (Vol v : volsReserves) {
            System.out.println("  - " + v.getNumeroVol() + " : " + v.getVilleDepart() + " -> " + v.getVilleArrivee());
        }
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

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "numero='" + numeroReservation + '\'' +
                ", passager=" + passager.getNom() + " " + passager.getPrenom() +
                ", statut='" + statut + '\'' +
                ", montant=" + montantTotal + "€" +
                ", nbVols=" + volsReserves.size() +
                '}';
    }
}
