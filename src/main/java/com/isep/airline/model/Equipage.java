package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un équipage affecté à un vol.
 * Un équipage est composé d'un pilote et d'une liste de personnel de cabine.
 * Composition : un équipage appartient à un vol.
 * Implémente ObtenirInformation.
 */
public class Equipage implements ObtenirInformation {
    private String idEquipage;
    private Pilote pilote;
    private List<PersonnelCabine> personnelCabine; // Déclaré avec List (pas ArrayList)

    public Equipage() {
        this.personnelCabine = new ArrayList<>();
    }

    public Equipage(String idEquipage, Pilote pilote) {
        this.idEquipage = idEquipage;
        this.pilote = pilote;
        this.personnelCabine = new ArrayList<>();
    }

    public Equipage(String idEquipage, Pilote pilote, List<PersonnelCabine> personnelCabine) {
        this.idEquipage = idEquipage;
        this.pilote = pilote;
        this.personnelCabine = personnelCabine != null ? personnelCabine : new ArrayList<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Ajoute un membre du personnel de cabine à l'équipage.
     */
    public void ajouterPersonnelCabine(PersonnelCabine membre) {
        if (membre != null && !personnelCabine.contains(membre)) {
            personnelCabine.add(membre);
            System.out.println(membre.getNom() + " " + membre.getPrenom() + " ajouté(e) à l'équipage " + idEquipage);
        }
    }

    /**
     * Retire un membre du personnel de cabine de l'équipage.
     */
    public void retirerPersonnelCabine(PersonnelCabine membre) {
        if (personnelCabine.remove(membre)) {
            System.out.println(membre.getNom() + " " + membre.getPrenom() + " retiré(e) de l'équipage " + idEquipage);
        }
    }

    /**
     * Vérifie si l'équipage est complet (a un pilote et au moins un personnel de cabine).
     */
    public boolean estComplet() {
        return pilote != null && !personnelCabine.isEmpty();
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== Informations Équipage =====\n");
        sb.append("ID Équipage : ").append(idEquipage).append("\n");
        if (pilote != null) {
            sb.append("Pilote      : ").append(pilote.getNom()).append(" ").append(pilote.getPrenom()).append("\n");
        } else {
            sb.append("Pilote      : Non assigné\n");
        }
        sb.append("Personnel cabine (").append(personnelCabine.size()).append(") :");
        for (PersonnelCabine pc : personnelCabine) {
            sb.append("\n  - ").append(pc.getNom()).append(" ").append(pc.getPrenom())
              .append(" (").append(pc.getQualification()).append(")");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return obtenirInformation();
    }

    // ==================== Getters & Setters ====================

    public String getIdEquipage() {
        return idEquipage;
    }

    public void setIdEquipage(String idEquipage) {
        this.idEquipage = idEquipage;
    }

    public Pilote getPilote() {
        return pilote;
    }

    public void setPilote(Pilote pilote) {
        this.pilote = pilote;
    }

    public List<PersonnelCabine> getPersonnelCabine() {
        return personnelCabine;
    }

    public void setPersonnelCabine(List<PersonnelCabine> personnelCabine) {
        this.personnelCabine = personnelCabine;
    }
}
