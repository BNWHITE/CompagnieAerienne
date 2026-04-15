package com.isep.airline.model;

/**
 * Classe abstraite représentant une personne.
 * Classe mère de Passager et Employe.
 */
public abstract class Personne {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    public Personne() {
    }

    public Personne(String id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // ==================== CRUD ====================

    /**
     * Affiche les différentes informations d'une personne.
     */
    public void obtenirInfos() {
        System.out.println("===== Informations Personne =====");
        System.out.println("ID          : " + id);
        System.out.println("Nom         : " + nom);
        System.out.println("Prénom      : " + prenom);
        System.out.println("Email       : " + email);
        System.out.println("Téléphone   : " + telephone);
    }

    // ==================== Getters & Setters ====================

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Personne{" +
                "id='" + id + '\'' +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
