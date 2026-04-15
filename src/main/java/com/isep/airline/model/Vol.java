package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un vol de la compagnie aérienne.
 */
public class Vol {
    private String numeroVol;
    private String villeDepart;
    private String villeArrivee;
    private String dateDepart;
    private String dateArrivee;
    private String heureDepart;
    private String heureArrivee;
    private double prix;
    private String statut; // PLANIFIE, EN_COURS, TERMINE, ANNULE
    private Avion avion;
    private Equipage equipage;
    private List<Passager> passagers;

    public Vol() {
        this.passagers = new ArrayList<>();
        this.statut = "PLANIFIE";
    }

    public Vol(String numeroVol, String villeDepart, String villeArrivee,
               String dateDepart, String dateArrivee, String heureDepart,
               String heureArrivee, double prix) {
        this.numeroVol = numeroVol;
        this.villeDepart = villeDepart;
        this.villeArrivee = villeArrivee;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.prix = prix;
        this.statut = "PLANIFIE";
        this.passagers = new ArrayList<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Obtenir les informations du vol.
     */
    public void obtenirVol() {
        System.out.println("===== Informations Vol =====");
        System.out.println("N° Vol       : " + numeroVol);
        System.out.println("Départ       : " + villeDepart + " le " + dateDepart + " à " + heureDepart);
        System.out.println("Arrivée      : " + villeArrivee + " le " + dateArrivee + " à " + heureArrivee);
        System.out.println("Prix         : " + prix + " €");
        System.out.println("Statut       : " + statut);
        System.out.println("Avion        : " + (avion != null ? avion.getImmatriculation() + " (" + avion.getModele() + ")" : "Non affecté"));
        System.out.println("Équipage     : " + (equipage != null ? equipage.getIdEquipage() : "Non affecté"));
        System.out.println("Passagers    : " + passagers.size());
        if (avion != null) {
            System.out.println("Places dispo : " + (avion.getCapacite() - passagers.size()));
        }
    }

    /**
     * Affecter un équipage au vol.
     * Un pilote et une équipe cabine doivent assurer l'acheminement du vol.
     *
     * @param equipage l'équipage à affecter
     * @return true si l'affectation a réussi
     */
    public boolean affecterEquipage(Equipage equipage) {
        if (equipage == null) {
            System.out.println("Erreur : l'équipage spécifié est null.");
            return false;
        }
        if (!equipage.estComplet()) {
            System.out.println("Erreur : l'équipage doit avoir un pilote et au moins un personnel de cabine.");
            return false;
        }
        this.equipage = equipage;
        System.out.println("Équipage " + equipage.getIdEquipage() + " affecté au vol " + numeroVol);
        return true;
    }

    /**
     * Affecter un avion au vol après vérification de disponibilité.
     *
     * @param avion l'avion à affecter
     * @return true si l'affectation a réussi
     */
    public boolean affecterAvion(Avion avion) {
        if (avion == null) {
            System.out.println("Erreur : l'avion spécifié est null.");
            return false;
        }
        if (!avion.verifierDisponibilite(this.dateDepart, this.dateArrivee)) {
            System.out.println("Erreur : l'avion " + avion.getImmatriculation() + " n'est pas disponible.");
            return false;
        }
        this.avion = avion;
        avion.getVolsAffectes().add(this);
        System.out.println("Avion " + avion.getImmatriculation() + " affecté au vol " + numeroVol);
        return true;
    }

    /**
     * Annuler ce vol.
     */
    public void annulerVol() {
        this.statut = "ANNULE";
        System.out.println("Vol " + numeroVol + " annulé.");
        // Notifier les passagers
        for (Passager passager : passagers) {
            System.out.println("  Notification envoyée à " + passager.getNom() + " " + passager.getPrenom());
        }
    }

    /**
     * Vérifier si le vol est disponible pour réservation.
     *
     * @return true si le vol accepte des réservations
     */
    public boolean isDisponible() {
        if (!statut.equals("PLANIFIE")) {
            return false;
        }
        if (avion != null && passagers.size() >= avion.getCapacite()) {
            return false;
        }
        return true;
    }

    /**
     * Ajouter un passager au vol.
     *
     * @param passager le passager à ajouter
     */
    public void ajouterPassager(Passager passager) {
        if (passager != null && !passagers.contains(passager)) {
            passagers.add(passager);
        }
    }

    /**
     * Retirer un passager du vol.
     *
     * @param passager le passager à retirer
     */
    public void retirerPassager(Passager passager) {
        passagers.remove(passager);
    }

    // ==================== Getters & Setters ====================

    public String getNumeroVol() {
        return numeroVol;
    }

    public void setNumeroVol(String numeroVol) {
        this.numeroVol = numeroVol;
    }

    public String getVilleDepart() {
        return villeDepart;
    }

    public void setVilleDepart(String villeDepart) {
        this.villeDepart = villeDepart;
    }

    public String getVilleArrivee() {
        return villeArrivee;
    }

    public void setVilleArrivee(String villeArrivee) {
        this.villeArrivee = villeArrivee;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Equipage getEquipage() {
        return equipage;
    }

    public void setEquipage(Equipage equipage) {
        this.equipage = equipage;
    }

    public List<Passager> getPassagers() {
        return passagers;
    }

    public void setPassagers(List<Passager> passagers) {
        this.passagers = passagers;
    }

    @Override
    public String toString() {
        return "Vol{" +
                "numero='" + numeroVol + '\'' +
                ", " + villeDepart + " -> " + villeArrivee +
                ", depart=" + dateDepart + " " + heureDepart +
                ", statut='" + statut + '\'' +
                ", passagers=" + passagers.size() +
                '}';
    }
}
