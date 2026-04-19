package com.isep.airline.service;

import com.isep.airline.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service de calcul de statistiques sur les données de la compagnie aérienne.
 *
 * <p>Fournit des méthodes d'analyse : moyennes, top destinations, taux
 * d'occupation, répartition par statut, revenus estimés, etc.</p>
 *
 * @author Kahina Medjkoune
 * @version 1.0
 * @since 2025
 */
public class StatistiquesService {

    // ========================= STATISTIQUES VOLS =========================

    /**
     * Compte le nombre de vols par statut (PLANIFIE, EN_COURS, TERMINE, ANNULE).
     *
     * @param vols la liste des vols
     * @return une map statut → nombre de vols
     */
    public Map<String, Long> compterVolsParStatut(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return Collections.emptyMap();
        return vols.stream()
                .filter(v -> v.getStatut() != null)
                .collect(Collectors.groupingBy(Vol::getStatut, Collectors.counting()));
    }

    /**
     * Calcule le taux d'annulation des vols (en pourcentage).
     *
     * @param vols la liste des vols
     * @return le taux d'annulation (0.0 à 100.0), ou 0 si la liste est vide
     */
    public double calculerTauxAnnulation(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return 0.0;
        long annules = vols.stream()
                .filter(v -> "ANNULE".equals(v.getStatut()))
                .count();
        return (annules * 100.0) / vols.size();
    }

    /**
     * Trouve les N destinations les plus desservies (par nombre de vols à l'arrivée).
     *
     * @param vols la liste des vols
     * @param top  le nombre de destinations à retourner
     * @return liste ordonnée des villes les plus desservies avec leur nombre de vols
     */
    public List<Map.Entry<String, Long>> topDestinations(List<Vol> vols, int top) {
        if (vols == null || vols.isEmpty() || top <= 0) return Collections.emptyList();
        Map<String, Long> parDestination = vols.stream()
                .filter(v -> v.getAeroportArrivee() != null && v.getAeroportArrivee().getVille() != null)
                .collect(Collectors.groupingBy(
                        v -> v.getAeroportArrivee().getVille(),
                        Collectors.counting()));
        return parDestination.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(top)
                .collect(Collectors.toList());
    }

    /**
     * Trouve les N villes de départ les plus fréquentes.
     *
     * @param vols la liste des vols
     * @param top  le nombre de villes à retourner
     * @return liste ordonnée des villes de départ avec leur nombre de vols
     */
    public List<Map.Entry<String, Long>> topOrigines(List<Vol> vols, int top) {
        if (vols == null || vols.isEmpty() || top <= 0) return Collections.emptyList();
        Map<String, Long> parOrigine = vols.stream()
                .filter(v -> v.getAeroportDepart() != null && v.getAeroportDepart().getVille() != null)
                .collect(Collectors.groupingBy(
                        v -> v.getAeroportDepart().getVille(),
                        Collectors.counting()));
        return parOrigine.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(top)
                .collect(Collectors.toList());
    }

    /**
     * Calcule le prix moyen des vols dont le prix est renseigné.
     *
     * @param vols la liste des vols
     * @return le prix moyen, ou 0.0 si aucun prix valide
     */
    public double calculerPrixMoyen(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return 0.0;
        List<Double> prixValides = new ArrayList<>();
        for (Vol v : vols) {
            if (v.getPrix() != null && !v.getPrix().trim().isEmpty()) {
                try {
                    double p = Double.parseDouble(v.getPrix().trim());
                    if (p > 0) prixValides.add(p);
                } catch (NumberFormatException ignored) {
                }
            }
        }
        if (prixValides.isEmpty()) return 0.0;
        return prixValides.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Calcule le revenu total estimé (somme des prix de tous les vols terminés).
     *
     * @param vols la liste des vols
     * @return le revenu total estimé
     */
    public double calculerRevenuTotal(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return 0.0;
        double total = 0.0;
        for (Vol v : vols) {
            if ("TERMINE".equals(v.getStatut()) && v.getPrix() != null) {
                try {
                    double p = Double.parseDouble(v.getPrix().trim());
                    if (p > 0 && v.getPassagers() != null) {
                        total += p * v.getPassagers().size();
                    }
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return total;
    }

    // ========================= STATISTIQUES PASSAGERS =========================

    /**
     * Compte le nombre total de passagers uniques dans tous les vols.
     *
     * @param vols la liste des vols
     * @return le nombre de passagers uniques
     */
    public int compterPassagersUniques(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return 0;
        Set<String> ids = new HashSet<>();
        for (Vol v : vols) {
            if (v.getPassagers() != null) {
                for (Passager p : v.getPassagers()) {
                    if (p.getId() != null) ids.add(p.getId());
                }
            }
        }
        return ids.size();
    }

    /**
     * Calcule le taux d'occupation moyen des vols (passagers / capacité avion).
     *
     * @param vols la liste des vols
     * @return le taux d'occupation moyen (0.0 à 100.0), ou 0 si non calculable
     */
    public double calculerTauxOccupationMoyen(List<Vol> vols) {
        if (vols == null || vols.isEmpty()) return 0.0;
        List<Double> taux = new ArrayList<>();
        for (Vol v : vols) {
            if (v.getAvion() != null && v.getAvion().getCapacite() > 0 && v.getPassagers() != null) {
                double t = (v.getPassagers().size() * 100.0) / v.getAvion().getCapacite();
                taux.add(t);
            }
        }
        if (taux.isEmpty()) return 0.0;
        return taux.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
    }

    /**
     * Identifie les passagers fréquents (ayant effectué au moins {@code seuil} réservations).
     *
     * @param passagers la liste des passagers
     * @param seuil     nombre minimum de réservations
     * @return la liste des passagers fréquents
     */
    public List<Passager> trouverPassagersFrequents(List<Passager> passagers, int seuil) {
        if (passagers == null || passagers.isEmpty() || seuil <= 0) return Collections.emptyList();
        return passagers.stream()
                .filter(p -> p.getReservations() != null && p.getReservations().size() >= seuil)
                .collect(Collectors.toList());
    }

    // ========================= STATISTIQUES RESERVATIONS =========================

    /**
     * Compte le nombre de réservations par statut (CONFIRMEE, ANNULEE, EN_ATTENTE).
     *
     * @param reservations la liste des réservations
     * @return une map statut → nombre de réservations
     */
    public Map<String, Long> compterReservationsParStatut(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) return Collections.emptyMap();
        return reservations.stream()
                .filter(r -> r.getStatut() != null)
                .collect(Collectors.groupingBy(Reservation::getStatut, Collectors.counting()));
    }

    /**
     * Calcule le montant total des réservations confirmées.
     *
     * @param reservations la liste des réservations
     * @return le montant total
     */
    public double calculerMontantTotalReservations(List<Reservation> reservations) {
        if (reservations == null || reservations.isEmpty()) return 0.0;
        double total = 0.0;
        for (Reservation r : reservations) {
            if ("CONFIRMEE".equals(r.getStatut()) && r.getMontantTotal() != null) {
                try {
                    total += Double.parseDouble(r.getMontantTotal().trim());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        return total;
    }

    // ========================= STATISTIQUES FLOTTE =========================

    /**
     * Compte le nombre d'avions disponibles vs indisponibles.
     *
     * @param avions la liste des avions
     * @return map avec les clés "Disponible" et "Indisponible"
     */
    public Map<String, Long> compterAvionsParDisponibilite(List<Avion> avions) {
        if (avions == null || avions.isEmpty()) return Collections.emptyMap();
        Map<String, Long> result = new HashMap<>();
        long dispo = avions.stream().filter(Avion::isDisponible).count();
        result.put("Disponible", dispo);
        result.put("Indisponible", avions.size() - dispo);
        return result;
    }

    /**
     * Calcule la capacité totale de la flotte.
     *
     * @param avions la liste des avions
     * @return la somme des capacités de tous les avions
     */
    public int calculerCapaciteTotaleFlotte(List<Avion> avions) {
        if (avions == null || avions.isEmpty()) return 0;
        return avions.stream().mapToInt(Avion::getCapacite).sum();
    }

    /**
     * Regroupe les avions par modèle et compte le nombre par modèle.
     *
     * @param avions la liste des avions
     * @return map modèle → nombre d'avions
     */
    public Map<String, Long> compterAvionsParModele(List<Avion> avions) {
        if (avions == null || avions.isEmpty()) return Collections.emptyMap();
        return avions.stream()
                .filter(a -> a.getModele() != null)
                .collect(Collectors.groupingBy(Avion::getModele, Collectors.counting()));
    }

    // ========================= RAPPORT GLOBAL =========================

    /**
     * Génère un rapport textuel résumant les statistiques principales.
     *
     * @param compagnie la compagnie aérienne
     * @return le rapport sous forme de chaîne de caractères
     */
    public String genererRapport(CompagnieAerienne compagnie) {
        StringBuilder sb = new StringBuilder();
        sb.append("═══════════════════════════════════════════════\n");
        sb.append("  RAPPORT STATISTIQUES — ").append(compagnie.getNom()).append("\n");
        sb.append("═══════════════════════════════════════════════\n\n");

        List<Vol> vols = compagnie.getVols();
        List<Passager> passagers = compagnie.getPassagers();
        List<Avion> avions = compagnie.getAvions();
        List<Reservation> reservations = compagnie.getReservations();

        // Vols
        sb.append("📊 VOLS\n");
        sb.append("  Total : ").append(vols != null ? vols.size() : 0).append("\n");
        if (vols != null && !vols.isEmpty()) {
            Map<String, Long> parStatut = compterVolsParStatut(vols);
            parStatut.forEach((statut, count) ->
                    sb.append("  ").append(statut).append(" : ").append(count).append("\n"));
            sb.append(String.format("  Taux d'annulation : %.1f%%\n", calculerTauxAnnulation(vols)));
            sb.append(String.format("  Prix moyen : %.2f €\n", calculerPrixMoyen(vols)));
        }

        // Passagers
        sb.append("\n👥 PASSAGERS\n");
        sb.append("  Total enregistrés : ").append(passagers != null ? passagers.size() : 0).append("\n");
        if (vols != null) {
            sb.append("  Passagers uniques sur vols : ").append(compterPassagersUniques(vols)).append("\n");
            sb.append(String.format("  Taux d'occupation moyen : %.1f%%\n", calculerTauxOccupationMoyen(vols)));
        }

        // Flotte
        sb.append("\n✈️ FLOTTE\n");
        sb.append("  Nombre d'avions : ").append(avions != null ? avions.size() : 0).append("\n");
        if (avions != null && !avions.isEmpty()) {
            sb.append("  Capacité totale : ").append(calculerCapaciteTotaleFlotte(avions)).append(" places\n");
            Map<String, Long> parDispo = compterAvionsParDisponibilite(avions);
            parDispo.forEach((k, v) ->
                    sb.append("  ").append(k).append(" : ").append(v).append("\n"));
        }

        // Réservations
        sb.append("\n📋 RÉSERVATIONS\n");
        sb.append("  Total : ").append(reservations != null ? reservations.size() : 0).append("\n");
        if (reservations != null && !reservations.isEmpty()) {
            Map<String, Long> parStatutRes = compterReservationsParStatut(reservations);
            parStatutRes.forEach((statut, count) ->
                    sb.append("  ").append(statut).append(" : ").append(count).append("\n"));
            sb.append(String.format("  Montant total confirmé : %.2f €\n",
                    calculerMontantTotalReservations(reservations)));
        }

        sb.append("\n═══════════════════════════════════════════════\n");
        return sb.toString();
    }
}
