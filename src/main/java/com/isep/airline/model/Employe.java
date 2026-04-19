package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un employé de la compagnie aérienne.
 *
 * <p>Hérite de {@link Personne} (qui implémente {@link ObtenirInformation}).
 * Classe mère de {@link Pilote} et {@link PersonnelCabine}.</p>
 *
 * <p><b>Attributs en String</b> : salaire (conformément aux attentes pédagogiques).<br>
 * <b>Relation directe</b> : {@code Employe} → {@code List<Vol>}.</p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 * @see     Pilote
 * @see     PersonnelCabine
 * @see     Vol
 */
public class Employe extends Personne {
    private String numeroEmploye;
    private String role;
    private String salaire; // En String conformément aux attentes du prof

    // Association directe avec Vol
    private List<Vol> vols;

    /**
     * Constructeur par défaut.
     */
    public Employe() {
        super();
        this.vols = new ArrayList<>();
    }

    /**
     * Constructeur complet.
     *
     * @param id             identifiant unique
     * @param nom            nom de famille
     * @param prenom         prénom
     * @param email          adresse e-mail
     * @param telephone      numéro de téléphone
     * @param numeroEmploye  matricule de l'employé
     * @param role           rôle dans la compagnie (ex : {@code "Pilote"})
     * @param salaire        salaire mensuel brut (en String)
     */
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
     * Retourne le rôle de l'employé.
     *
     * <p>Cette méthode est surchargée (polymorphisme) dans {@link Pilote} et
     * {@link PersonnelCabine} pour fournir une description enrichie.</p>
     *
     * @return le rôle de l'employé
     */
    public String obtenirRole() {
        return this.role;
    }

    /**
     * Affecte un vol à cet employé (relation directe {@code Employe} ↔ {@code Vol}).
     *
     * @param vol le vol à affecter (ignoré s'il est {@code null} ou déjà présent)
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
