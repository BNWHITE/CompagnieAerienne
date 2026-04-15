package com.isep.airline.model;

/**
 * Classe abstraite représentant une personne.
 * Classe mère de Passager et Employe.
 * Implémente l'interface ObtenirInformation.
 *
 * Remarque du prof : "les attributs sont en String"
 * → Tous les attributs de Personne sont de type String.
 */
public abstract class Personne implements ObtenirInformation {
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

    // ==================== Interface ObtenirInformation ====================

    /**
     * Retourne les informations de la personne sous forme de String.
     * Les sous-classes (Passager, Employe…) peuvent surcharger cette méthode
     * pour ajouter leurs propres informations.
     *
     * Remarque du prof : "Obtenir des infos : toString (afficher les infos du proprio)"
     */
    @Override
    public String obtenirInformation() {
        return "===== Informations Personne =====\n"
                + "ID          : " + id + "\n"
                + "Nom         : " + nom + "\n"
                + "Prénom      : " + prenom + "\n"
                + "Email       : " + email + "\n"
                + "Téléphone   : " + telephone;
    }

    /**
     * toString() délègue à obtenirInformation() — conformément aux attentes du prof.
     * "Obtenir des infos : toString (afficher les infos du proprio)"
     */
    @Override
    public String toString() {
        return obtenirInformation();
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
}
