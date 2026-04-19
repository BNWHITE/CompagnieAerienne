package com.isep.airline.model;

/**
 * Classe représentant un pilote de la compagnie aérienne.
 *
 * <p>
 * Hiérarchie : {@code Pilote} → {@link Employe} → {@link Personne} →
 * {@link ObtenirInformation}.
 * </p>
 *
 * <p>
 * <b>Polymorphisme :</b> {@link #obtenirRole()} est surchargée par rapport à
 * {@link PersonnelCabine#obtenirRole()}.
 * </p>
 *
 * <p>
 * <b>Attributs en String</b> : heuresDeVol (conformément aux attentes
 * pédagogiques).
 * </p>
 *
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 * @see Employe
 * @see PersonnelCabine
 */
public class Pilote extends Employe {
    /** Numéro de licence du pilote. */
    private String licence;
    /** Nombre d'heures de vol cumulées (format String). */
    private String heuresDeVol;

    /**
     * Constructeur par défaut. Définit le rôle à {@code "Pilote"}.
     */
    public Pilote() {
        super();
        setRole("Pilote");
    }

    /**
     * Constructeur complet.
     *
     * @param id            identifiant unique
     * @param nom           nom de famille
     * @param prenom        prénom
     * @param email         adresse e-mail
     * @param telephone     numéro de téléphone
     * @param numeroEmploye matricule de l'employé
     * @param salaire       salaire mensuel brut (en String)
     * @param licence       numéro de licence de pilote (ex :
     *                      {@code "ATPL-FR-12345"})
     * @param heuresDeVol   total des heures de vol (en String, ex : {@code "3500"})
     */
    public Pilote(String id, String nom, String prenom, String email, String telephone,
            String numeroEmploye, String salaire, String licence, String heuresDeVol) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Pilote", salaire);
        this.licence = licence;
        this.heuresDeVol = heuresDeVol;
    }

    // ==================== Polymorphisme ====================

    /**
     * Retourne la description du rôle du pilote avec sa licence et ses heures de
     * vol.
     *
     * <p>
     * Surcharge de {@link Employe#obtenirRole()} — polymorphisme avec
     * {@link PersonnelCabine#obtenirRole()}.
     * </p>
     *
     * @return une {@link String} décrivant le rôle, la licence et les heures de vol
     */
    @Override
    public String obtenirRole() {
        return "Pilote (Licence: " + licence + ", Heures de vol: " + heuresDeVol + ")";
    }

    // ==================== Interface ObtenirInformation ====================

    /**
     * Retourne les informations complètes du pilote.
     *
     * @return informations de {@link Employe#obtenirInformation()} enrichies de la
     *         licence et des heures de vol
     */
    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Licence     : " + licence + "\n"
                + "Heures vol  : " + heuresDeVol;
    }

    // ==================== Getters & Setters ====================

    /**
     * Retourne le numéro de licence du pilote.
     *
     * @return le numéro de licence
     */
    public String getLicence() {
        return licence;
    }

    /**
     * Définit le numéro de licence du pilote.
     *
     * @param licence le nouveau numéro de licence
     */
    public void setLicence(String licence) {
        this.licence = licence;
    }

    /**
     * Retourne le total des heures de vol.
     *
     * @return les heures de vol (en String)
     */
    public String getHeuresDeVol() {
        return heuresDeVol;
    }

    /**
     * Définit le total des heures de vol.
     *
     * @param heuresDeVol les nouvelles heures de vol (en String)
     */
    public void setHeuresDeVol(String heuresDeVol) {
        this.heuresDeVol = heuresDeVol;
    }
}
