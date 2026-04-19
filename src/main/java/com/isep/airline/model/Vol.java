package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe abstraite représentant un vol de la compagnie aérienne.
 *
 * <p>Implémente {@link ObtenirInformation}. Trois sous-classes concrètes :
 * {@link CourtCourrier}, {@link MoyenCourrier}, {@link LongCourrier}.</p>
 *
 * <p><b>Relations :</b>
 * <ul>
 *   <li>Agrégation avec {@link Aeroport} (départ / arrivée)</li>
 *   <li>Composition avec {@link Avion} et {@link Equipage}</li>
 *   <li>Association avec {@link Passager} et {@link Employe}</li>
 * </ul></p>
 *
 * <p><b>Statuts possibles :</b> {@code PLANIFIE}, {@code EN_COURS}, {@code TERMINE},
 * {@code ANNULE}.</p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 * @see     CourtCourrier
 * @see     MoyenCourrier
 * @see     LongCourrier
 */
public abstract class Vol implements ObtenirInformation {
    private String numeroVol;
    private Aeroport aeroportDepart;    // Agregation / Association avec Aeroport
    private Aeroport aeroportArrivee;   // Agregation / Association avec Aeroport
    private String dateDepart;
    private String dateArrivee;
    private String heureDepart;
    private String heureArrivee;
    private String prix;   // En String conformement aux attentes du prof
    private String statut; // PLANIFIE, EN_COURS, TERMINE, ANNULE
    private Avion avion;   // Composition : un vol est opere par un avion
    private Equipage equipage; // Composition : un vol necessite un equipage
    private List<Passager> passagers;   // Association : passagers sur ce vol
    private List<Employe> employes;     // Relation directe Vol <-> Employe

    public Vol() {
        this.passagers = new ArrayList<>();
        this.employes = new ArrayList<>();
        this.statut = "PLANIFIE";
    }

    public Vol(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
               String dateDepart, String dateArrivee, String heureDepart,
               String heureArrivee, String prix) {
        this.numeroVol = numeroVol;
        this.aeroportDepart = aeroportDepart;
        this.aeroportArrivee = aeroportArrivee;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.heureDepart = heureDepart;
        this.heureArrivee = heureArrivee;
        this.prix = prix;
        this.statut = "PLANIFIE";
        this.passagers = new ArrayList<>();
        this.employes = new ArrayList<>();

        // Association bidirectionnelle : ajouter ce vol aux Deque de l aeroport
        if (aeroportDepart != null) {
            aeroportDepart.ajouterVolDepart(this);
        }
        if (aeroportArrivee != null) {
            aeroportArrivee.ajouterVolArrivee(this);
        }
    }

    // ==================== Methode abstraite (difference de structure) ====================

    /**
     * Retourne le type de vol (Court Courrier, Moyen Courrier, Long Courrier).
     * Chaque sous-classe implemente cette methode.
     */
    public abstract String getTypeVol();

    // ==================== Methodes metier ====================

    /**
     * Affecter un equipage au vol.
     */
    public boolean affecterEquipage(Equipage equipage) {
        if (equipage == null) {
            System.out.println("Erreur : l equipage specifie est null.");
            return false;
        }
        if (!equipage.estComplet()) {
            System.out.println("Erreur : l equipage doit avoir un pilote et au moins un personnel de cabine.");
            return false;
        }
        this.equipage = equipage;
        // Ajouter les employes de l equipage a la relation directe Employe <-> Vol
        if (equipage.getPilote() != null) {
            ajouterEmploye(equipage.getPilote());
        }
        for (PersonnelCabine pc : equipage.getPersonnelCabine()) {
            ajouterEmploye(pc);
        }
        System.out.println("Equipage " + equipage.getIdEquipage() + " affecte au vol " + numeroVol);
        return true;
    }

    /**
     * Affecter un avion au vol.
     */
    public boolean affecterAvion(Avion avion) {
        if (avion == null) {
            System.out.println("Erreur : l avion specifie est null.");
            return false;
        }
        if (!avion.verifierDisponibilite(this.dateDepart, this.dateArrivee)) {
            System.out.println("Erreur : l avion " + avion.getImmatriculation() + " n est pas disponible.");
            return false;
        }
        this.avion = avion;
        avion.getVolsAffectes().add(this);
        System.out.println("Avion " + avion.getImmatriculation() + " affecte au vol " + numeroVol);
        return true;
    }

    /**
     * Annuler ce vol.
     */
    public void annulerVol() {
        this.statut = "ANNULE";
        System.out.println("Vol " + numeroVol + " annule.");
        for (Passager passager : passagers) {
            System.out.println("  Notification envoyee a " + passager.getNom() + " " + passager.getPrenom());
        }
    }

    /**
     * Verifier si le vol est disponible pour reservation.
     */
    public boolean isDisponible() {
        if (!statut.equals("PLANIFIE")) {
            return false;
        }
        if (avion != null && passagers.size() >= avion.getCapacite()) {
            return false;
        }
        return true;
    }

    /**
     * Ajouter un passager au vol.
     */
    public void ajouterPassager(Passager passager) {
        if (passager != null && !passagers.contains(passager)) {
            passagers.add(passager);
        }
    }

    /**
     * Retirer un passager du vol.
     */
    public void retirerPassager(Passager passager) {
        passagers.remove(passager);
    }

    /**
     * Ajouter un employe au vol (relation directe Vol <-> Employe).
     */
    public void ajouterEmploye(Employe employe) {
        if (employe != null && !employes.contains(employe)) {
            employes.add(employe);
            employe.ajouterVol(this); // bidirectionnel
        }
    }

    /**
     * Retirer un employe du vol.
     */
    public void retirerEmploye(Employe employe) {
        if (employes.remove(employe)) {
            employe.retirerVol(this);
        }
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        String departInfo = (aeroportDepart != null)
                ? aeroportDepart.getCodeIATA() + " (" + aeroportDepart.getVille() + ")"
                : "Non defini";
        String arriveeInfo = (aeroportArrivee != null)
                ? aeroportArrivee.getCodeIATA() + " (" + aeroportArrivee.getVille() + ")"
                : "Non defini";

        return "===== Informations Vol =====\n"
                + "N Vol        : " + numeroVol + "\n"
                + "Type         : " + getTypeVol() + "\n"
                + "Depart       : " + departInfo + " le " + dateDepart + " a " + heureDepart + "\n"
                + "Arrivee      : " + arriveeInfo + " le " + dateArrivee + " a " + heureArrivee + "\n"
                + "Prix         : " + prix + " EUR\n"
                + "Statut       : " + statut + "\n"
                + "Avion        : " + (avion != null ? avion.getImmatriculation() + " (" + avion.getModele() + ")" : "Non affecte") + "\n"
                + "Equipage     : " + (equipage != null ? equipage.getIdEquipage() : "Non affecte") + "\n"
                + "Passagers    : " + passagers.size() + "\n"
                + "Employes     : " + employes.size()
                + (avion != null ? "\nPlaces dispo : " + (avion.getCapacite() - passagers.size()) : "");
    }

    /**
     * toString() delegue a obtenirInformation().
     */
    @Override
    public String toString() {
        return obtenirInformation();
    }

    // ==================== Getters & Setters ====================

    public String getNumeroVol() {
        return numeroVol;
    }

    public void setNumeroVol(String numeroVol) {
        this.numeroVol = numeroVol;
    }

    public Aeroport getAeroportDepart() {
        return aeroportDepart;
    }

    public void setAeroportDepart(Aeroport aeroportDepart) {
        this.aeroportDepart = aeroportDepart;
    }

    public Aeroport getAeroportArrivee() {
        return aeroportArrivee;
    }

    public void setAeroportArrivee(Aeroport aeroportArrivee) {
        this.aeroportArrivee = aeroportArrivee;
    }

    public String getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    public String getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(String dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public String getHeureDepart() {
        return heureDepart;
    }

    public void setHeureDepart(String heureDepart) {
        this.heureDepart = heureDepart;
    }

    public String getHeureArrivee() {
        return heureArrivee;
    }

    public void setHeureArrivee(String heureArrivee) {
        this.heureArrivee = heureArrivee;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Avion getAvion() {
        return avion;
    }

    public void setAvion(Avion avion) {
        this.avion = avion;
    }

    public Equipage getEquipage() {
        return equipage;
    }

    public void setEquipage(Equipage equipage) {
        this.equipage = equipage;
    }

    public List<Passager> getPassagers() {
        return passagers;
    }

    public void setPassagers(List<Passager> passagers) {
        this.passagers = passagers;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
}
