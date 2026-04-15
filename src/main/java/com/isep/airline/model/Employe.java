package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un employé de la compagnie aérienne.
 * Hérite de Personne (qui implémente ObtenirInformation).
 * Classe mère de Pilote et PersonnelCabine.
 *
 * Remarque du prof : "les attributs sont en String" et
 * "il faut ajouter des relations entre par exemple Vol et Employé meme si ce n'est pas fait dans l'énoncé"
 * → salaire est en String, et on ajoute une relation directe List<Vol>.
 */
public class Employe extends Personne {
    private String numeroEmploye;
    private String role;
    private String salaire; // En String conformément aux attentes du prof

    // Association directe avec Vol — "Mieux vaut créer un lien direct c'est mieux"
    private List<Vol> vols;

    public Employe() {
        super();
        this.vols = new ArrayList<>();
    }

    public Employe(String id, String nom, String prenom, String email, String telephone,
                   String numeroEmploye, String role, String salaire) {
        super(id, nom, prenom, email, telephone);
        this.numeroEmploye = numeroEmploye;
        this.role = role;
        this.salaire = salaire;
        this.vols = new ArrayList<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Retourne le rôle d'un employé.
     * Cette méthode est surchargée (polymorphisme) dans Pilote et PersonnelCabine.
     *
     * @return le rôle de l'employé (pilote, personnel cabine, etc.)
     */
    public String obtenirRole() {
        return this.role;
    }

    /**
     * Affecter un vol à cet employé (relation directe Employe ↔ Vol).
     */
    public void ajouterVol(Vol vol) {
        if (vol != null && !vols.contains(vol)) {
            vols.add(vol);
        }
    }

    /**
     * Retirer un vol de cet employé.
     */
    public void retirerVol(Vol vol) {
        vols.remove(vol);
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "N° Employé  : " + numeroEmploye + "\n"
                + "Rôle        : " + role + "\n"
                + "Salaire     : " + salaire + " €\n"
                + "Nb vols     : " + vols.size();
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

    public String getSalaire() {
        return salaire;
    }

    public void setSalaire(String salaire) {
        this.salaire = salaire;
    }

    public List<Vol> getVols() {
        return vols;
    }

    public void setVols(List<Vol> vols) {
        this.vols = vols;
    }
}
