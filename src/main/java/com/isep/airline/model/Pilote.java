package com.isep.airline.model;

/**
 * Classe représentant un pilote.
 * Hérite de Employe → Personne → ObtenirInformation.
 *
 * Polymorphisme : Pilote et PersonnelCabine surchargent obtenirRole() et obtenirInformation().
 * "Il y a un seul polymorphisme : juste entre pilote et personnel cabine"
 *
 * Attributs en String : "les attributs sont en String"
 */
public class Pilote extends Employe {
    private String licence;
    private String heuresDeVol; // En String conformément aux attentes du prof

    public Pilote() {
        super();
        setRole("Pilote");
    }

    public Pilote(String id, String nom, String prenom, String email, String telephone,
                  String numeroEmploye, String salaire, String licence, String heuresDeVol) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Pilote", salaire);
        this.licence = licence;
        this.heuresDeVol = heuresDeVol;
    }

    // ==================== Polymorphisme ====================

    /**
     * Surcharge de obtenirRole() — polymorphisme avec PersonnelCabine.
     */
    @Override
    public String obtenirRole() {
        return "Pilote (Licence: " + licence + ", Heures de vol: " + heuresDeVol + ")";
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Licence     : " + licence + "\n"
                + "Heures vol  : " + heuresDeVol;
    }

    // ==================== Getters & Setters ====================

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getHeuresDeVol() {
        return heuresDeVol;
    }

    public void setHeuresDeVol(String heuresDeVol) {
        this.heuresDeVol = heuresDeVol;
    }
}
