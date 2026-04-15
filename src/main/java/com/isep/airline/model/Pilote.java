package com.isep.airline.model;

/**
 * Classe représentant un pilote.
 * Hérite de Employe.
 */
public class Pilote extends Employe {
    private String licence;
    private int heuresDeVol;

    public Pilote() {
        super();
        setRole("Pilote");
    }

    public Pilote(String id, String nom, String prenom, String email, String telephone,
                  String numeroEmploye, double salaire, String licence, int heuresDeVol) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Pilote", salaire);
        this.licence = licence;
        this.heuresDeVol = heuresDeVol;
    }

    @Override
    public String obtenirRole() {
        return "Pilote (Licence: " + licence + ", Heures de vol: " + heuresDeVol + ")";
    }

    @Override
    public void obtenirInfos() {
        super.obtenirInfos();
        System.out.println("Licence     : " + licence);
        System.out.println("Heures vol  : " + heuresDeVol);
    }

    // ==================== Getters & Setters ====================

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public int getHeuresDeVol() {
        return heuresDeVol;
    }

    public void setHeuresDeVol(int heuresDeVol) {
        this.heuresDeVol = heuresDeVol;
    }

    @Override
    public String toString() {
        return "Pilote{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", licence='" + licence + '\'' +
                ", heuresDeVol=" + heuresDeVol +
                '}';
    }
}
