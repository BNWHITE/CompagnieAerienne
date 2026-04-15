package com.isep.airline.model;

/**
 * Classe représentant un vol long courrier (> 4000 km).
 * Hérite de Vol.
 *
 * Différence de structure par rapport aux autres types de vol :
 * - Service repas complet (plusieurs services)
 * - Classe affaires et première classe disponibles
 * - Divertissement à bord (écrans individuels)
 * - Durée typique : > 6h
 */
public class LongCourrier extends Vol {
    private String distanceKm;              // Distance en km (String)
    private String nombreRepas;             // Nombre de repas servis (String)
    private boolean premiereClasseDisponible;
    private boolean divertissementABord;

    public LongCourrier() {
        super();
    }

    public LongCourrier(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
                        String dateDepart, String dateArrivee, String heureDepart,
                        String heureArrivee, String prix, String distanceKm) {
        super(numeroVol, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
        this.distanceKm = distanceKm;
        this.nombreRepas = "2";          // Par défaut 2 repas pour un long courrier
        this.premiereClasseDisponible = true;
        this.divertissementABord = true;
    }

    @Override
    public String getTypeVol() {
        return "Long Courrier";
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Distance     : " + distanceKm + " km\n"
                + "Nb repas     : " + nombreRepas + "\n"
                + "1ère classe  : " + (premiereClasseDisponible ? "Disponible" : "Non disponible") + "\n"
                + "Divertissem. : " + (divertissementABord ? "Oui" : "Non");
    }

    // ==================== Getters & Setters ====================

    public String getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(String distanceKm) {
        this.distanceKm = distanceKm;
    }

    public String getNombreRepas() {
        return nombreRepas;
    }

    public void setNombreRepas(String nombreRepas) {
        this.nombreRepas = nombreRepas;
    }

    public boolean isPremiereClasseDisponible() {
        return premiereClasseDisponible;
    }

    public void setPremiereClasseDisponible(boolean premiereClasseDisponible) {
        this.premiereClasseDisponible = premiereClasseDisponible;
    }

    public boolean isDivertissementABord() {
        return divertissementABord;
    }

    public void setDivertissementABord(boolean divertissementABord) {
        this.divertissementABord = divertissementABord;
    }
}
