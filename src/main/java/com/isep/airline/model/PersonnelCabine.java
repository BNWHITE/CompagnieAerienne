package com.isep.airline.model;

/**
 * Classe représentant un membre du personnel de cabine.
 * Hérite de Employe.
 */
public class PersonnelCabine extends Employe {
    private String qualification;
    private int anneesExperience;

    public PersonnelCabine() {
        super();
        setRole("Personnel Cabine");
    }

    public PersonnelCabine(String id, String nom, String prenom, String email, String telephone,
                           String numeroEmploye, double salaire, String qualification, int anneesExperience) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Personnel Cabine", salaire);
        this.qualification = qualification;
        this.anneesExperience = anneesExperience;
    }

    @Override
    public String obtenirRole() {
        return "Personnel Cabine (Qualification: " + qualification + ", Expérience: " + anneesExperience + " ans)";
    }

    @Override
    public void obtenirInfos() {
        super.obtenirInfos();
        System.out.println("Qualification: " + qualification);
        System.out.println("Expérience   : " + anneesExperience + " ans");
    }

    // ==================== Getters & Setters ====================

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getAnneesExperience() {
        return anneesExperience;
    }

    public void setAnneesExperience(int anneesExperience) {
        this.anneesExperience = anneesExperience;
    }

    @Override
    public String toString() {
        return "PersonnelCabine{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", qualification='" + qualification + '\'' +
                ", experience=" + anneesExperience +
                '}';
    }
}
