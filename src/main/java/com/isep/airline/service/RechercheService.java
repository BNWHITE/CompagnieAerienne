package com.isep.airline.service;

import com.isep.airline.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service de recherche avancée sur les données de la compagnie aérienne.
 *
 * <p>Permet de rechercher et filtrer vols, passagers, réservations et avions
 * selon différents critères : ville, date, statut, prix, disponibilité, etc.</p>
 *
 * @author Kahina Medjkoune
 * @version 1.0
 * @since 2025
 */
public class RechercheService {

    // ========================= RECHERCHE DE VOLS =========================

    /**
     * Recherche les vols entre deux villes.
     *
     * @param vols         la liste de tous les vols
     * @param villeDepart  ville de départ (insensible à la casse)
     * @param villeArrivee ville d'arrivée (insensible à la casse)
     * @return les vols correspondants
     */
    public List<Vol> rechercherVolsParTrajet(List<Vol> vols, String villeDepart, String villeArrivee) {
        if (vols == null || villeDepart == null || villeArrivee == null) return Collections.emptyList();
        String dep = villeDepart.trim().toLowerCase();
        String arr = villeArrivee.trim().toLowerCase();
        return vols.stream()
                .filter(v -> v.getAeroportDepart() != null && v.getAeroportArrivee() != null)
                .filter(v -> v.getAeroportDepart().getVille() != null && v.getAeroportArrivee().getVille() != null)
                .filter(v -> v.getAeroportDepart().getVille().toLowerCase().equals(dep))
                .filter(v -> v.getAeroportArrivee().getVille().toLowerCase().equals(arr))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les vols par date de départ.
     *
     * @param vols       la liste de tous les vols
     * @param dateDepart la date de départ recherchée (format JJ/MM/AAAA)
     * @return les vols correspondants
     */
    public List<Vol> rechercherVolsParDate(List<Vol> vols, String dateDepart) {
        if (vols == null || dateDepart == null) return Collections.emptyList();
        String date = dateDepart.trim();
        return vols.stream()
                .filter(v -> date.equals(v.getDateDepart()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les vols par statut.
     *
     * @param vols   la liste de tous les vols
     * @param statut le statut recherché (PLANIFIE, EN_COURS, TERMINE, ANNULE)
     * @return les vols correspondants
     */
    public List<Vol> rechercherVolsParStatut(List<Vol> vols, String statut) {
        if (vols == null || statut == null) return Collections.emptyList();
        String s = statut.trim().toUpperCase();
        return vols.stream()
                .filter(v -> s.equals(v.getStatut()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les vols dans une fourchette de prix.
     *
     * @param vols    la liste de tous les vols
     * @param prixMin prix minimum (inclus)
     * @param prixMax prix maximum (inclus)
     * @return les vols dont le prix est dans la fourchette
     */
    public List<Vol> rechercherVolsParPrix(List<Vol> vols, double prixMin, double prixMax) {
        if (vols == null || prixMin < 0 || prixMax < prixMin) return Collections.emptyList();
        List<Vol> resultats = new ArrayList<>();
        for (Vol v : vols) {
            if (v.getPrix() != null) {
                try {
                    double prix = Double.parseDouble(v.getPrix().trim());
                    if (prix >= prixMin && prix <= prixMax) {
                        resultats.add(v);
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return resultats;
    }

    /**
     * Recherche les vols ayant des places disponibles.
     *
     * @param vols la liste de tous les vols
     * @return les vols qui ne sont pas complets
     */
    public List<Vol> rechercherVolsDisponibles(List<Vol> vols) {
        if (vols == null) return Collections.emptyList();
        return vols.stream()
                .filter(v -> !"ANNULE".equals(v.getStatut()))
                .filter(v -> v.getAvion() != null && v.getPassagers() != null)
                .filter(v -> v.getPassagers().size() < v.getAvion().getCapacite())
                .collect(Collectors.toList());
    }

    /**
     * Trie les vols par prix croissant.
     *
     * @param vols la liste des vols à trier
     * @return une nouvelle liste triée par prix
     */
    public List<Vol> trierVolsParPrix(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return Collections.emptyList();
        return vols.stream()
                .filter(v -> v.getPrix() != null)
                .sorted((v1, v2) -> {
                    try {
                        double p1 = Double.parseDouble(v1.getPrix().trim());
                        double p2 = Double.parseDouble(v2.getPrix().trim());
                        return Double.compare(p1, p2);
                    } catch (NumberFormatException e) {
                        return 0;
                    }
                })
                .collect(Collectors.toList());
    }

    // ========================= RECHERCHE DE PASSAGERS =========================

    /**
     * Recherche un passager par son numéro de passeport.
     *
     * @param passagers la liste de tous les passagers
     * @param passeport le numéro de passeport recherché
     * @return le passager trouvé, ou {@code null} si aucun ne correspond
     */
    public Passager rechercherParPasseport(List<Passager> passagers, String passeport) {
        if (passagers == null || passeport == null) return null;
        String p = passeport.trim().toUpperCase();
        return passagers.stream()
                .filter(pass -> p.equals(pass.getNumeroPasseport()))
                .findFirst()
                .orElse(null);
    }

    /**
     * Recherche des passagers par nom (correspondance partielle, insensible à la casse).
     *
     * @param passagers la liste de tous les passagers
     * @param nom       le nom recherché (peut être partiel)
     * @return les passagers correspondants
     */
    public List<Passager> rechercherPassagersParNom(List<Passager> passagers, String nom) {
        if (passagers == null || nom == null) return Collections.emptyList();
        String n = nom.trim().toLowerCase();
        return passagers.stream()
                .filter(p -> p.getNom() != null && p.getNom().toLowerCase().contains(n))
                .collect(Collectors.toList());
    }

    /**
     * Recherche des passagers par email (correspondance exacte).
     *
     * @param passagers la liste de tous les passagers
     * @param email     l'email recherché
     * @return le passager correspondant ou {@code null}
     */
    public Passager rechercherPassagerParEmail(List<Passager> passagers, String email) {
        if (passagers == null || email == null) return null;
        String e = email.trim().toLowerCase();
        return passagers.stream()
                .filter(p -> p.getEmail() != null && p.getEmail().toLowerCase().equals(e))
                .findFirst()
                .orElse(null);
    }

    /**
     * Recherche les passagers sur un vol spécifique.
     *
     * @param vol le vol concerné
     * @return la liste des passagers de ce vol, ou une liste vide
     */
    public List<Passager> rechercherPassagersDuVol(Vol vol) {
        if (vol == null || vol.getPassagers() == null) return Collections.emptyList();
        return new ArrayList<>(vol.getPassagers());
    }

    // ========================= RECHERCHE DE RESERVATIONS =========================

    /**
     * Recherche les réservations d'un passager donné.
     *
     * @param reservations la liste de toutes les réservations
     * @param passagerId   l'identifiant du passager
     * @return les réservations de ce passager
     */
    public List<Reservation> rechercherReservationsParPassager(List<Reservation> reservations, String passagerId) {
        if (reservations == null || passagerId == null) return Collections.emptyList();
        String id = passagerId.trim();
        return reservations.stream()
                .filter(r -> r.getPassager() != null && id.equals(r.getPassager().getId()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les réservations par statut.
     *
     * @param reservations la liste de toutes les réservations
     * @param statut       le statut recherché (CONFIRMEE, ANNULEE, EN_ATTENTE)
     * @return les réservations correspondantes
     */
    public List<Reservation> rechercherReservationsParStatut(List<Reservation> reservations, String statut) {
        if (reservations == null || statut == null) return Collections.emptyList();
        String s = statut.trim().toUpperCase();
        return reservations.stream()
                .filter(r -> s.equals(r.getStatut()))
                .collect(Collectors.toList());
    }

    /**
     * Recherche une réservation par son numéro.
     *
     * @param reservations      la liste de toutes les réservations
     * @param numeroReservation le numéro de réservation recherché
     * @return la réservation trouvée, ou {@code null}
     */
    public Reservation rechercherReservationParNumero(List<Reservation> reservations, String numeroReservation) {
        if (reservations == null || numeroReservation == null) return null;
        String num = numeroReservation.trim();
        return reservations.stream()
                .filter(r -> num.equals(r.getNumeroReservation()))
                .findFirst()
                .orElse(null);
    }

    // ========================= RECHERCHE D'AVIONS =========================

    /**
     * Recherche les avions disponibles.
     *
     * @param avions la liste de tous les avions
     * @return les avions disponibles
     */
    public List<Avion> rechercherAvionsDisponibles(List<Avion> avions) {
        if (avions == null) return Collections.emptyList();
        return avions.stream()
                .filter(Avion::isDisponible)
                .collect(Collectors.toList());
    }

    /**
     * Recherche les avions par modèle (correspondance partielle).
     *
     * @param avions la liste de tous les avions
     * @param modele le modèle recherché
     * @return les avions correspondants
     */
    public List<Avion> rechercherAvionsParModele(List<Avion> avions, String modele) {
        if (avions == null || modele == null) return Collections.emptyList();
        String m = modele.trim().toLowerCase();
        return avions.stream()
                .filter(a -> a.getModele() != null && a.getModele().toLowerCase().contains(m))
                .collect(Collectors.toList());
    }

    /**
     * Recherche les avions avec une capacité minimale.
     *
     * @param avions      la liste de tous les avions
     * @param capaciteMin la capacité minimale requise
     * @return les avions dont la capacité est ≥ capaciteMin
     */
    public List<Avion> rechercherAvionsParCapaciteMin(List<Avion> avions, int capaciteMin) {
        if (avions == null || capaciteMin <= 0) return Collections.emptyList();
        return avions.stream()
                .filter(a -> a.getCapacite() >= capaciteMin)
                .collect(Collectors.toList());
    }

    // ========================= RECHERCHE GLOBALE =========================

    /**
     * Effectue une recherche textuelle globale sur les passagers (nom, prénom, email, passeport).
     *
     * @param passagers la liste de tous les passagers
     * @param terme     le terme de recherche
     * @return les passagers correspondants
     */
    public List<Passager> rechercheGlobalePassagers(List<Passager> passagers, String terme) {
        if (passagers == null || terme == null || terme.trim().isEmpty()) return Collections.emptyList();
        String t = terme.trim().toLowerCase();
        return passagers.stream()
                .filter(p -> contientTerme(p.getNom(), t)
                        || contientTerme(p.getPrenom(), t)
                        || contientTerme(p.getEmail(), t)
                        || contientTerme(p.getNumeroPasseport(), t)
                        || contientTerme(p.getId(), t))
                .collect(Collectors.toList());
    }

    /**
     * Vérifie si une chaîne contient un terme (insensible à la casse, null-safe).
     */
    private boolean contientTerme(String champ, String terme) {
        return champ != null && champ.toLowerCase().contains(terme);
    }
}
