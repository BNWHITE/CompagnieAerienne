package com.isep.airline.model;

/**
 * Classe représentant un vol long courrier (&gt; 4000 km).
 * Hérite de {@link Vol} et implémente {@link ObtenirInformation}.
 *
 * <p>Caractéristiques spécifiques :</p>
 * <ul>
 *   <li>Service repas complet (plusieurs services)</li>
 *   <li>Classe affaires et première classe disponibles</li>
 *   <li>Divertissement à bord (écrans individuels)</li>
 *   <li>Durée typique : &gt; 6h</li>
 * </ul>
 *
 * @author Compagnie Aérienne ISEP
 * @version 1.0
 * @since 2025
 * @see Vol
 * @see CourtCourrier
 * @see MoyenCourrier
 */
public class LongCourrier extends Vol {
    /** Distance du vol en kilomètres. */
    private String distanceKm;
    /** Nombre de repas servis pendant le vol. */
    private String nombreRepas;
    /** Indique si la première classe est disponible. */
    private boolean premiereClasseDisponible;
    /** Indique si le divertissement à bord est disponible. */
    private boolean divertissementABord;

    /**
     * Constructeur par défaut.
     */
    public LongCourrier() {
        super();
    }

    /**
     * Constructeur complet d'un vol long courrier.
     *
     * @param numeroVol         numéro unique du vol
     * @param aeroportDepart    aéroport de départ
     * @param aeroportArrivee   aéroport d'arrivée
     * @param dateDepart        date de départ
     * @param dateArrivee       date d'arrivée
     * @param heureDepart       heure de départ
     * @param heureArrivee      heure d'arrivée
     * @param prix              prix du billet
     * @param distanceKm        distance en kilomètres
     */
    public LongCourrier(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
                        String dateDepart, String dateArrivee, String heureDepart,
                        String heureArrivee, String prix, String distanceKm) {
        super(numeroVol, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
        this.distanceKm = distanceKm;
        this.nombreRepas = "2";          // Par défaut 2 repas pour un long courrier
        this.premiereClasseDisponible = true;
        this.divertissementABord = true;
    }

    /**
     * {@inheritDoc}
     * @return {@code "Long Courrier"}
     */
    @Override
    public String getTypeVol() {
        return "Long Courrier";
    }

    // ==================== Interface ObtenirInformation ====================

    /**
     * {@inheritDoc}
     * Inclut la distance, le nombre de repas, la première classe et le divertissement.
     */
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
