package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un avion de la flotte de la compagnie aérienne.
 *
 * <p>
 * Implémente {@link ObtenirInformation}.<br>
 * <b>Relation :</b> {@code Avion} → {@code List<Vol>} (composition avec les
 * vols affectés).
 * </p>
 *
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 * @see Vol
 */
public class Avion implements ObtenirInformation {
    /** Immatriculation de l'avion (ex: F-GKXA). */
    private String immatriculation;
    /** Modèle de l'avion (ex: Boeing 737). */
    private String modele;
    /** Capacité maximale en nombre de passagers. */
    private int capacite;
    /** Indique si l'avion est disponible pour un vol. */
    private boolean disponible;
    /** Liste des vols affectés à cet avion (composition). */
    private List<Vol> volsAffectes;

    /**
     * Constructeur par défaut. L'avion est disponible par défaut.
     */
    public Avion() {
        this.disponible = true;
        this.volsAffectes = new ArrayList<>();
    }

    /**
     * Constructeur complet.
     *
     * @param immatriculation immatriculation de l'avion (ex : {@code "F-GKXA"})
     * @param modele          modèle de l'appareil (ex : {@code "Airbus A320"})
     * @param capacite        nombre de sièges passagers
     */
    public Avion(String immatriculation, String modele, int capacite) {
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.capacite = capacite;
        this.disponible = true;
        this.volsAffectes = new ArrayList<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Vérifie si l'avion est disponible pour une date de départ donnée.
     *
     * @param dateDepart  la date de départ souhaitée (format {@code "yyyy-MM-dd"})
     * @param dateArrivee la date d'arrivée souhaitée (non utilisée actuellement,
     *                    prévu pour extension)
     * @return {@code true} si l'avion est disponible, {@code false} sinon
     */
    public boolean verifierDisponibilite(String dateDepart, String dateArrivee) {
        if (!disponible) {
            System.out.println("L'avion " + immatriculation + " est hors service.");
            return false;
        }
        for (Vol vol : volsAffectes) {
            if (vol.getStatut().equals("PLANIFIE") || vol.getStatut().equals("EN_COURS")) {
                if (vol.getDateDepart().equals(dateDepart)) {
                    System.out.println("L'avion " + immatriculation + " est déjà affecté à un vol à cette date.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Affecte cet avion à un vol.
     */
    public void affecterVol(Vol vol) {
        if (vol != null) {
            volsAffectes.add(vol);
            vol.setAvion(this);
            System.out.println("Avion " + immatriculation + " affecté au vol " + vol.getNumeroVol());
        }
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return "===== Informations Avion =====\n"
                + "Immatriculation : " + immatriculation + "\n"
                + "Modèle          : " + modele + "\n"
                + "Capacité        : " + capacite + " places\n"
                + "Disponible      : " + (disponible ? "Oui" : "Non") + "\n"
                + "Vols affectés   : " + volsAffectes.size();
    }

    @Override
    public String toString() {
        return obtenirInformation();
    }

    // ==================== Getters & Setters ====================

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public List<Vol> getVolsAffectes() {
        return volsAffectes;
    }

    public void setVolsAffectes(List<Vol> volsAffectes) {
        this.volsAffectes = volsAffectes;
    }
}
