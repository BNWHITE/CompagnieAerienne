package com.isep.airline.model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Classe représentant un aéroport.
 * Association bidirectionnelle avec Vol : un Aeroport contient une Deque de Vols,
 * et un Vol référence ses Aeroports de départ et d'arrivée.
 *
 * Utilisation d'un Deque (Double-Ended Queue) plutôt qu'un ArrayList :
 * - Permet d'ajouter/retirer des vols en tête ou en queue efficacement
 * - Représente mieux la file des vols (prochain vol au départ = premier de la Deque)
 */
public class Aeroport implements ObtenirInformation {
    private String codeIATA;       // Ex: "CDG", "JFK"
    private String nom;            // Ex: "Charles de Gaulle"
    private String ville;          // Ex: "Paris"
    private String pays;           // Ex: "France"

    // Association : un aéroport gère une file de vols au départ et à l'arrivée
    private Deque<Vol> volsDepart;
    private Deque<Vol> volsArrivee;

    public Aeroport() {
        this.volsDepart = new ArrayDeque<>();
        this.volsArrivee = new ArrayDeque<>();
    }

    public Aeroport(String codeIATA, String nom, String ville, String pays) {
        this.codeIATA = codeIATA;
        this.nom = nom;
        this.ville = ville;
        this.pays = pays;
        this.volsDepart = new ArrayDeque<>();
        this.volsArrivee = new ArrayDeque<>();
    }

    // ==================== Méthodes métier ====================

    /**
     * Ajoute un vol au départ de cet aéroport (en fin de Deque).
     */
    public void ajouterVolDepart(Vol vol) {
        if (vol != null && !volsDepart.contains(vol)) {
            volsDepart.addLast(vol);
        }
    }

    /**
     * Ajoute un vol à l'arrivée de cet aéroport (en fin de Deque).
     */
    public void ajouterVolArrivee(Vol vol) {
        if (vol != null && !volsArrivee.contains(vol)) {
            volsArrivee.addLast(vol);
        }
    }

    /**
     * Retire un vol du départ.
     */
    public void retirerVolDepart(Vol vol) {
        volsDepart.remove(vol);
    }

    /**
     * Retire un vol de l'arrivée.
     */
    public void retirerVolArrivee(Vol vol) {
        volsArrivee.remove(vol);
    }

    /**
     * Retourne le prochain vol au départ (tête de la Deque, sans le retirer).
     */
    public Vol prochainVolDepart() {
        return volsDepart.peekFirst();
    }

    /**
     * Retourne le prochain vol à l'arrivée (tête de la Deque, sans le retirer).
     */
    public Vol prochainVolArrivee() {
        return volsArrivee.peekFirst();
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return "===== Informations Aéroport =====\n"
                + "Code IATA       : " + codeIATA + "\n"
                + "Nom             : " + nom + "\n"
                + "Ville           : " + ville + "\n"
                + "Pays            : " + pays + "\n"
                + "Vols au départ  : " + volsDepart.size() + "\n"
                + "Vols à l'arrivée: " + volsArrivee.size();
    }

    /**
     * toString() délègue à obtenirInformation() — conformément aux attentes du prof.
     */
    @Override
    public String toString() {
        return obtenirInformation();
    }

    // ==================== Getters & Setters ====================

    public String getCodeIATA() {
        return codeIATA;
    }

    public void setCodeIATA(String codeIATA) {
        this.codeIATA = codeIATA;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public Deque<Vol> getVolsDepart() {
        return volsDepart;
    }

    public void setVolsDepart(Deque<Vol> volsDepart) {
        this.volsDepart = volsDepart;
    }

    public Deque<Vol> getVolsArrivee() {
        return volsArrivee;
    }

    public void setVolsArrivee(Deque<Vol> volsArrivee) {
        this.volsArrivee = volsArrivee;
    }
}
