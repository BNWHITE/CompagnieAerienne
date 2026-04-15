package com.isep.airline.model;

/**
 * Classe représentant un employé de la compagnie aérienne.
 * Hérite de Personne.
 * Classe mère de Pilote et PersonnelCabine.
 */
public class Employe extends Personne {
    private String numeroEmploye;
    private String role;
    private double salaire;

    public Employe() {
        super();
    }

    public Employe(String id, String nom, String prenom, String email, String telephone,
                   String numeroEmploye, String role, double salaire) {
        super(id, nom, prenom, email, telephone);
        this.numeroEmploye = numeroEmploye;
        this.role = role;
        this.salaire = salaire;
    }

    // ==================== Méthodes métier ====================

    /**
     * Retourne le rôle d'un employé.
     *
     * @return le rôle de l'employé (pilote, personnel cabine, etc.)
     */
    public String obtenirRole() {
        return this.role;
    }

    @Override
    public void obtenirInfos() {
        super.obtenirInfos();
        System.out.println("N° Employé  : " + numeroEmploye);
        System.out.println("Rôle        : " + role);
        System.out.println("Salaire     : " + salaire + " €");
    }

    // ==================== Getters & Setters ====================

    public String getNumeroEmploye() {
        return numeroEmploye;
    }

    public void setNumeroEmploye(String numeroEmploye) {
        this.numeroEmploye = numeroEmploye;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getSalaire() {
        return salaire;
    }

    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id='" + getId() + '\'' +
                ", nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", numeroEmploye='" + numeroEmploye + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
