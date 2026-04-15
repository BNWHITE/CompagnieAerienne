package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un avion de la compagnie aérienne.
 * Implémente ObtenirInformation.
 */
public class Avion implements ObtenirInformation {
    private String immatriculation;
    private String modele;
    private int capacite;
    private boolean disponible;
    private List<Vol> volsAffectes; // Déclaré avec List (pas ArrayList)

    public Avion() {
        this.disponible = true;
        this.volsAffectes = new ArrayList<>();
    }

    public Avion(String immatriculation, String modele, int capacite) {
        this.immatriculation = immatriculation;
        this.modele = modele;
        this.capacite = capacite;
        this.disponible = true;
        this.volsAffectes = new ArrayList<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Vérifie la disponibilité de l'avion pour un horaire donné.
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
