package com.isep.airline.model;

/**
 * Classe abstraite représentant une personne.
 *
 * <p>Classe mère de {@link Passager} et {@link Employe}.
 * Implémente l'interface {@link ObtenirInformation}.</p>
 *
 * <p><b>Attributs en String</b> (conformément aux attentes pédagogiques) :
 * id, nom, prénom, email, téléphone.</p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 * @see     Passager
 * @see     Employe
 * @see     ObtenirInformation
 */
public abstract class Personne implements ObtenirInformation {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;

    /**
     * Constructeur par défaut.
     */
    public Personne() {
    }

    /**
     * Constructeur complet.
     *
     * @param id        identifiant unique de la personne (ex : {@code "P001"})
     * @param nom       nom de famille
     * @param prenom    prénom
     * @param email     adresse e-mail
     * @param telephone numéro de téléphone
     */
    public Personne(String id, String nom, String prenom, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    // ==================== Interface ObtenirInformation ====================

    /**
     * Retourne les informations de la personne sous forme lisible.
     *
     * <p>Les sous-classes ({@link Passager}, {@link Employe}…) surchargent cette méthode
     * via {@code super.obtenirInformation()} pour enrichir l'affichage.</p>
     *
     * @return une {@link String} contenant id, nom, prénom, email et téléphone
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
     * Délègue à {@link #obtenirInformation()} pour l'affichage console.
     *
     * @return la représentation textuelle de la personne
     */
    @Override
    public String toString() {
        return obtenirInformation();
    }

    // ==================== Getters & Setters ====================

    /**
     * Retourne l'identifiant unique de la personne.
     *
     * @return l'identifiant (ex : {@code "P001"})
     */
    public String getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de la personne.
     *
     * @param id le nouvel identifiant (ne doit pas être {@code null})
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Retourne le nom de famille.
     *
     * @return le nom de famille
     */
    public String getNom() {
        return nom;
    }

    /**
     * Définit le nom de famille.
     *
     * @param nom le nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Retourne le prénom.
     *
     * @return le prénom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Définit le prénom.
     *
     * @param prenom le nouveau prénom
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Retourne l'adresse e-mail.
     *
     * @return l'adresse e-mail
     */
    public String getEmail() {
        return email;
    }

    /**
     * Définit l'adresse e-mail.
     *
     * @param email la nouvelle adresse e-mail
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retourne le numéro de téléphone.
     *
     * @return le numéro de téléphone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Définit le numéro de téléphone.
     *
     * @param telephone le nouveau numéro de téléphone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
