package com.isep.airline.model;

/**
 * Classe représentant un membre du personnel de cabine.
 * Hérite de Employe → Personne → ObtenirInformation.
 *
 * Polymorphisme : PersonnelCabine et Pilote surchargent obtenirRole() et obtenirInformation().
 * "Il y a un seul polymorphisme : juste entre pilote et personnel cabine"
 *
 * Attributs en String : "les attributs sont en String"
 */
public class PersonnelCabine extends Employe {
    private String qualification;
    private String anneesExperience; // En String conformément aux attentes du prof

    public PersonnelCabine() {
        super();
        setRole("Personnel Cabine");
    }

    public PersonnelCabine(String id, String nom, String prenom, String email, String telephone,
                           String numeroEmploye, String salaire, String qualification, String anneesExperience) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Personnel Cabine", salaire);
        this.qualification = qualification;
        this.anneesExperience = anneesExperience;
    }

    // ==================== Polymorphisme ====================

    /**
     * Surcharge de obtenirRole() — polymorphisme avec Pilote.
     */
    @Override
    public String obtenirRole() {
        return "Personnel Cabine (Qualification: " + qualification + ", Expérience: " + anneesExperience + " ans)";
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Qualification: " + qualification + "\n"
                + "Expérience   : " + anneesExperience + " ans";
    }

    // ==================== Getters & Setters ====================

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAnneesExperience() {
        return anneesExperience;
    }

    public void setAnneesExperience(String anneesExperience) {
        this.anneesExperience = anneesExperience;
    }
}
