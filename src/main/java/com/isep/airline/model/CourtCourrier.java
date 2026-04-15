package com.isep.airline.model;

/**
 * Classe représentant un vol court courrier (< 1500 km).
 * Hérite de Vol.
 *
 * Différence de structure par rapport aux autres types de vol :
 * - Pas de service repas (collation uniquement)
 * - Pas de classe affaires
 * - Durée typique : < 3h
 */
public class CourtCourrier extends Vol {
    private String distanceKm;       // Distance en km (String)
    private boolean collationIncluse;

    public CourtCourrier() {
        super();
    }

    public CourtCourrier(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
                         String dateDepart, String dateArrivee, String heureDepart,
                         String heureArrivee, String prix, String distanceKm) {
        super(numeroVol, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
        this.distanceKm = distanceKm;
        this.collationIncluse = true; // Par défaut une collation est incluse
    }

    @Override
    public String getTypeVol() {
        return "Court Courrier";
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Distance     : " + distanceKm + " km\n"
                + "Collation    : " + (collationIncluse ? "Oui" : "Non");
    }

    // ==================== Getters & Setters ====================

    public String getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(String distanceKm) {
        this.distanceKm = distanceKm;
    }

    public boolean isCollationIncluse() {
        return collationIncluse;
    }

    public void setCollationIncluse(boolean collationIncluse) {
        this.collationIncluse = collationIncluse;
    }
}
