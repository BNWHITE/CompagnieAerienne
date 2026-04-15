package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un avion de la compagnie aérienne.
 */
public class Avion {
    private String immatriculation;
    private String modele;
    private int capacite;
    private boolean disponible;
    private List<Vol> volsAffectes;

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
     * L'avion est considéré indisponible s'il est déjà affecté à un vol
     * dont les horaires chevauchent ceux demandés.
     *
     * @param dateDepart  la date de départ prévue
     * @param dateArrivee la date d'arrivée prévue
     * @return true si l'avion est disponible, false sinon
     */
    public boolean verifierDisponibilite(String dateDepart, String dateArrivee) {
        if (!disponible) {
            System.out.println("L'avion " + immatriculation + " est hors service.");
            return false;
        }
        for (Vol vol : volsAffectes) {
            if (vol.getStatut().equals("PLANIFIE") || vol.getStatut().equals("EN_COURS")) {
                // Vérification simplifiée de chevauchement de dates
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
     *
     * @param vol le vol auquel affecter l'avion
     */
    public void affecterVol(Vol vol) {
        if (vol != null) {
            volsAffectes.add(vol);
            vol.setAvion(this);
            System.out.println("Avion " + immatriculation + " affecté au vol " + vol.getNumeroVol());
        }
    }

    public void obtenirInfos() {
        System.out.println("===== Informations Avion =====");
        System.out.println("Immatriculation : " + immatriculation);
        System.out.println("Modèle          : " + modele);
        System.out.println("Capacité        : " + capacite + " places");
        System.out.println("Disponible      : " + (disponible ? "Oui" : "Non"));
        System.out.println("Vols affectés   : " + volsAffectes.size());
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

    @Override
    public String toString() {
        return "Avion{" +
                "immatriculation='" + immatriculation + '\'' +
                ", modele='" + modele + '\'' +
                ", capacite=" + capacite +
                ", disponible=" + disponible +
                '}';
    }
}
