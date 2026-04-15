package com.isep.airline.model;

/**
 * Interface définissant le contrat pour obtenir les informations d'un objet.
 * Toutes les classes métier du système implémentent cette interface.
 * C'est le polymorphisme d'interface : chaque classe fournit sa propre implémentation.
 */
public interface ObtenirInformation {

    /**
     * Retourne une chaîne de caractères contenant toutes les informations de l'objet.
     * Cette méthode est utilisée par toString() pour l'affichage.
     *
     * @return les informations détaillées de l'objet
     */
    String obtenirInformation();
}
