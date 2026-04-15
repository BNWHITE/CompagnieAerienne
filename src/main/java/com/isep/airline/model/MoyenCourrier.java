package com.isep.airline.model;

/**
 * Classe représentant un vol moyen courrier (1500 - 4000 km).
 * Hérite de Vol.
 *
 * Différence de structure par rapport aux autres types de vol :
 * - Service repas léger inclus
 * - Possibilité de classe affaires (limité)
 * - Durée typique : 3h - 6h
 */
public class MoyenCourrier extends Vol {
    private String distanceKm;          // Distance en km (String)
    private boolean repasInclus;
    private boolean classeAffairesDisponible;

    public MoyenCourrier() {
        super();
    }

    public MoyenCourrier(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
                         String dateDepart, String dateArrivee, String heureDepart,
                         String heureArrivee, String prix, String distanceKm) {
        super(numeroVol, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
        this.distanceKm = distanceKm;
        this.repasInclus = true;
        this.classeAffairesDisponible = true;
    }

    @Override
    public String getTypeVol() {
        return "Moyen Courrier";
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Distance     : " + distanceKm + " km\n"
                + "Repas inclus : " + (repasInclus ? "Oui" : "Non") + "\n"
                + "Classe Aff.  : " + (classeAffairesDisponible ? "Disponible" : "Non disponible");
    }

    // ==================== Getters & Setters ====================

    public String getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(String distanceKm) {
        this.distanceKm = distanceKm;
    }

    public boolean isRepasInclus() {
        return repasInclus;
    }

    public void setRepasInclus(boolean repasInclus) {
        this.repasInclus = repasInclus;
    }

    public boolean isClasseAffairesDisponible() {
        return classeAffairesDisponible;
    }

    public void setClasseAffairesDisponible(boolean classeAffairesDisponible) {
        this.classeAffairesDisponible = classeAffairesDisponible;
    }
}
