package com.isep.airline.model;

/**
 * Classe représentant un vol court-courrier (distance inférieure à 1 500 km).
 *
 * <p>Hérite de {@link Vol} (classe abstraite).</p>
 *
 * <p><b>Spécificités du court-courrier :</b>
 * <ul>
 *   <li>Pas de service repas complet — collation uniquement</li>
 *   <li>Pas de classe affaires</li>
 *   <li>Durée typique : moins de 3 heures</li>
 * </ul></p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 * @see     Vol
 * @see     MoyenCourrier
 * @see     LongCourrier
 */
public class CourtCourrier extends Vol {
    private String distanceKm;       // Distance en km (String)
    private boolean collationIncluse;

    /**
     * Constructeur par défaut.
     */
    public CourtCourrier() {
        super();
    }

    /**
     * Constructeur complet.
     *
     * @param numeroVol        numéro unique du vol (ex : {@code "SK101"})
     * @param aeroportDepart   aéroport de départ
     * @param aeroportArrivee  aéroport d'arrivée
     * @param dateDepart       date de départ (format {@code "yyyy-MM-dd"})
     * @param dateArrivee      date d'arrivée (format {@code "yyyy-MM-dd"})
     * @param heureDepart      heure de départ (format {@code "HH:mm"})
     * @param heureArrivee     heure d'arrivée (format {@code "HH:mm"})
     * @param prix             prix du billet (en String)
     * @param distanceKm       distance en kilomètres (en String, ex : {@code "850"})
     */
    public CourtCourrier(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
                         String dateDepart, String dateArrivee, String heureDepart,
                         String heureArrivee, String prix, String distanceKm) {
        super(numeroVol, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
        this.distanceKm = distanceKm;
        this.collationIncluse = true;
    }

    /**
     * Retourne le type de vol.
     *
     * @return {@code "Court Courrier"}
     */
    @Override
    public String getTypeVol() {
        return "Court Courrier";
    }

    // ==================== Interface ObtenirInformation ====================

    /**
     * Retourne les informations complètes du vol court-courrier.
     *
     * @return informations de {@link Vol#obtenirInformation()} enrichies de la distance et de la collation
     */
    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"
                + "Distance     : " + distanceKm + " km\n"
                + "Collation    : " + (collationIncluse ? "Oui" : "Non");
    }

    // ==================== Getters & Setters ====================

    /**
     * Retourne la distance du vol en kilomètres.
     *
     * @return la distance (en String)
     */
    public String getDistanceKm() {
        return distanceKm;
    }

    /**
     * Définit la distance du vol.
     *
     * @param distanceKm la nouvelle distance (en String)
     */
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
