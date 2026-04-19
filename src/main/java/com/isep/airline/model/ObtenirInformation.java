package com.isep.airline.model;

/**
 * Interface définissant le contrat pour obtenir les informations d'un objet.
 * Toutes les classes métier du système implémentent cette interface.
 *
 * <p>
 * C'est le polymorphisme d'interface : chaque classe fournit sa propre
 * implémentation de {@link #obtenirInformation()}.
 * </p>
 *
 * <p>
 * <b>Exemple d'utilisation :</b>
 * </p>
 * 
 * <pre>
 *   ObtenirInformation obj = new Passager("P001", "Dupont", "Jean", ...);
 *   System.out.println(obj.obtenirInformation());
 * </pre>
 *
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 */
public interface ObtenirInformation {

    /**
     * Retourne une chaîne de caractères contenant toutes les informations de
     * l'objet.
     * Cette méthode est utilisée par {@link Object#toString()} pour l'affichage.
     *
     * @return une {@link String} non nulle décrivant l'état complet de l'objet
     */
    String obtenirInformation();
}
