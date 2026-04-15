package com.isep.airline.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant un équipage affecté à un vol.
 * Un équipage est composé d'un pilote et d'une liste de personnel de cabine.
 * Composition : un équipage appartient à un vol.
 */
public class Equipage {
    private String idEquipage;
    private Pilote pilote;
    private List<PersonnelCabine> personnelCabine;

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
     *
     * @param membre le personnel de cabine à ajouter
     */
    public void ajouterPersonnelCabine(PersonnelCabine membre) {
        if (membre != null && !personnelCabine.contains(membre)) {
            personnelCabine.add(membre);
            System.out.println(membre.getNom() + " " + membre.getPrenom() + " ajouté(e) à l'équipage " + idEquipage);
        }
    }

    /**
     * Retire un membre du personnel de cabine de l'équipage.
     *
     * @param membre le personnel de cabine à retirer
     */
    public void retirerPersonnelCabine(PersonnelCabine membre) {
        if (personnelCabine.remove(membre)) {
            System.out.println(membre.getNom() + " " + membre.getPrenom() + " retiré(e) de l'équipage " + idEquipage);
        }
    }

    /**
     * Vérifie si l'équipage est complet (a un pilote et au moins un personnel de cabine).
     *
     * @return true si l'équipage est complet
     */
    public boolean estComplet() {
        return pilote != null && !personnelCabine.isEmpty();
    }

    public void obtenirInfos() {
        System.out.println("===== Informations Équipage =====");
        System.out.println("ID Équipage : " + idEquipage);
        if (pilote != null) {
            System.out.println("Pilote      : " + pilote.getNom() + " " + pilote.getPrenom());
        } else {
            System.out.println("Pilote      : Non assigné");
        }
        System.out.println("Personnel cabine (" + personnelCabine.size() + ") :");
        for (PersonnelCabine pc : personnelCabine) {
            System.out.println("  - " + pc.getNom() + " " + pc.getPrenom() + " (" + pc.getQualification() + ")");
        }
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

    @Override
    public String toString() {
        return "Equipage{" +
                "idEquipage='" + idEquipage + '\'' +
                ", pilote=" + (pilote != null ? pilote.getNom() + " " + pilote.getPrenom() : "N/A") +
                ", personnelCabine=" + personnelCabine.size() + " membres" +
                '}';
    }
}
