package com.isep.airline.model;

/**
 * Classe représentant un membre du personnel de cabine.
 *
 * <p>Hiérarchie : {@code PersonnelCabine} → {@link Employe} → {@link Personne}
 * → {@link ObtenirInformation}.</p>
 *
 * <p><b>Polymorphisme :</b> {@link #obtenirRole()} est surchargée par rapport à
 * {@link Pilote#obtenirRole()}.</p>
 *
 * <p><b>Attributs en String</b> : anneesExperience (conformément aux attentes pédagogiques).</p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 * @see     Employe
 * @see     Pilote
 */
public class PersonnelCabine extends Employe {
    private String qualification;
    private String anneesExperience; // En String conformément aux attentes du prof

    /**
     * Constructeur par défaut. Définit le rôle à {@code "Personnel Cabine"}.
     */
    public PersonnelCabine() {
        super();
        setRole("Personnel Cabine");
    }

    /**
     * Constructeur complet.
     *
     * @param id               identifiant unique
     * @param nom              nom de famille
     * @param prenom           prénom
     * @param email            adresse e-mail
     * @param telephone        numéro de téléphone
     * @param numeroEmploye    matricule de l'employé
     * @param salaire          salaire mensuel brut (en String)
     * @param qualification    qualification (ex : {@code "Hôtesse de l'air"})
     * @param anneesExperience nombre d'années d'expérience (en String)
     */
    public PersonnelCabine(String id, String nom, String prenom, String email, String telephone,
                           String numeroEmploye, String salaire, String qualification, String anneesExperience) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Personnel Cabine", salaire);
        this.qualification = qualification;
        this.anneesExperience = anneesExperience;
    }

    // ==================== Polymorphisme ====================

    /**
     * Retourne la description du rôle du personnel de cabine avec sa qualification et son expérience.
     *
     * <p>Surcharge de {@link Employe#obtenirRole()} — polymorphisme avec {@link Pilote#obtenirRole()}.</p>
     *
     * @return une {@link String} décrivant le rôle, la qualification et les années d'expérience
     */
    @Override
    public String obtenirRole() {
        return "Personnel Cabine (Qualification: " + qualification + ", Expérience: " + anneesExperience + " ans)";
    }

    // ==================== Interface ObtenirInformation ====================

    /**
     * Retourne les informations complètes du personnel de cabine.
     *
     * @return informations de {@link Employe#obtenirInformation()} enrichies de la qualification et de l'expérience
     */
    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Qualification: " + qualification + "\n"
                + "Expérience   : " + anneesExperience + " ans";
    }

    // ==================== Getters & Setters ====================

    /**
     * Retourne la qualification du personnel de cabine.
     *
     * @return la qualification
     */
    public String getQualification() {
        return qualification;
    }

    /**
     * Définit la qualification du personnel de cabine.
     *
     * @param qualification la nouvelle qualification
     */
    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    /**
     * Retourne le nombre d'années d'expérience.
     *
     * @return les années d'expérience (en String)
     */
    public String getAnneesExperience() {
        return anneesExperience;
    }

    public void setAnneesExperience(String anneesExperience) {
        this.anneesExperience = anneesExperience;
    }
}
