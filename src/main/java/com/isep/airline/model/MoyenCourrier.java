package com.isep.airline.model;

/**
 * Classe représentant un vol moyen courrier (1500 - 4000 km).
 * Hérite de {@link Vol} et implémente {@link ObtenirInformation}.
 *
 * <p>
 * Caractéristiques spécifiques :
 * </p>
 * <ul>
 * <li>Service repas léger inclus</li>
 * <li>Possibilité de classe affaires (limité)</li>
 * <li>Durée typique : 3h - 6h</li>
 * </ul>
 *
 * @author Compagnie Aérienne ISEP
 * @version 1.0
 * @since 2025
 * @see Vol
 * @see CourtCourrier
 * @see LongCourrier
 */
public class MoyenCourrier extends Vol {
    /** Distance du vol en kilomètres. */
    private String distanceKm;
    /** Indique si un repas léger est inclus dans le vol. */
    private boolean repasInclus;
    /** Indique si la classe affaires est disponible. */
    private boolean classeAffairesDisponible;

    /**
     * Constructeur par défaut.
     */
    public MoyenCourrier() {
        super();
    }

    /**
     * Constructeur complet d'un vol moyen courrier.
     *
     * @param numeroVol       numéro unique du vol
     * @param aeroportDepart  aéroport de départ
     * @param aeroportArrivee aéroport d'arrivée
     * @param dateDepart      date de départ
     * @param dateArrivee     date d'arrivée
     * @param heureDepart     heure de départ
     * @param heureArrivee    heure d'arrivée
     * @param prix            prix du billet
     * @param distanceKm      distance en kilomètres
     */
    public MoyenCourrier(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
            String dateDepart, String dateArrivee, String heureDepart,
            String heureArrivee, String prix, String distanceKm) {
        super(numeroVol, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
        this.distanceKm = distanceKm;
        this.repasInclus = true;
        this.classeAffairesDisponible = true;
    }

    /**
     * {@inheritDoc}
     * 
     * @return {@code "Moyen Courrier"}
     */
    @Override
    public String getTypeVol() {
        return "Moyen Courrier";
    }

    // ==================== Interface ObtenirInformation ====================

    /**
     * {@inheritDoc}
     * Inclut la distance, le repas et la disponibilité de la classe affaires.
     */
    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Distance     : " + distanceKm + " km\n"
                + "Repas inclus : " + (repasInclus ? "Oui" : "Non") + "\n"
                + "Classe Aff.  : " + (classeAffairesDisponible ? "Disponible" : "Non disponible");
    }

    // ==================== Getters & Setters ====================

    /**
     * Retourne la distance du vol en km.
     * 
     * @return distance en km
     */
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
