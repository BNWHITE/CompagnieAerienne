package com.isep.airline.model;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Classe représentant un aéroport.
 *
 * <p>
 * <b>Association bidirectionnelle avec {@link Vol}</b> : un {@code Aeroport}
 * contient
 * une {@link java.util.Deque} de vols au départ et à l'arrivée, et un
 * {@link Vol} référence
 * ses aéroports de départ/arrivée.
 * </p>
 *
 * <p>
 * <b>Pourquoi {@link java.util.Deque} ?</b><br>
 * Permet d'ajouter/retirer des vols en tête ou en queue efficacement et
 * représente
 * mieux la file des vols (prochain vol = premier de la Deque).
 * </p>
 *
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 * @see Vol
 */
public class Aeroport implements ObtenirInformation {
    /** Code IATA de l'aéroport (ex: CDG, JFK). */
    private String codeIATA;
    /** Nom complet de l'aéroport (ex: Charles de Gaulle). */
    private String nom;
    /** Ville de l'aéroport (ex: Paris). */
    private String ville;
    /** Pays de l'aéroport (ex: France). */
    private String pays;

    /** File des vols au départ depuis cet aéroport. */
    private Deque<Vol> volsDepart;
    /** File des vols à l'arrivée vers cet aéroport. */
    private Deque<Vol> volsArrivee;

    /**
     * Constructeur par défaut. Initialise les files de vols.
     */
    public Aeroport() {
        this.volsDepart = new ArrayDeque<>();
        this.volsArrivee = new ArrayDeque<>();
    }

    /**
     * Constructeur complet.
     *
     * @param codeIATA code IATA de l'aéroport sur 3 lettres (ex : {@code "CDG"})
     * @param nom      nom officiel de l'aéroport (ex : {@code "Charles de Gaulle"})
     * @param ville    ville hôte (ex : {@code "Paris"})
     * @param pays     pays hôte (ex : {@code "France"})
     */
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
     *
     * @param vol le vol à ajouter (ignoré s'il est {@code null} ou déjà présent)
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
     * toString() délègue à obtenirInformation() — conformément aux attentes du
     * prof.
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
