package com.isep.airline;

import com.isep.airline.model.CompagnieAerienne;
import com.isep.airline.model.Vol;
import com.isep.airline.model.Reservation;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire pour la generation de graphiques statistiques
 * en utilisant JFreeChart (BONUS).
 *
 * Relation : association avec CompagnieAerienne (utilise ses donnees).
 */
public class GraphiqueStatistiques {

    private final CompagnieAerienne compagnie;

    public GraphiqueStatistiques(CompagnieAerienne compagnie) {
        this.compagnie = compagnie;
    }

    /**
     * Affiche un diagramme circulaire (Pie Chart) de la repartition
     * des types de vols (Court / Moyen / Long Courrier).
     */
    public void afficherRepartitionTypesVols() {
        Map<String, Integer> stats = compagnie.getStatistiquesTypesVols();

        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Repartition des types de vols - " + compagnie.getNom(),
                dataset,
                true,  // legende
                true,  // tooltips
                false  // urls
        );

        afficherDansFenetre(chart, "Types de Vols", 600, 400);
    }

    /**
     * Affiche un diagramme en barres du nombre de vols par statut
     * (Planifie, Confirme, Annule, Termine).
     */
    public void afficherVolsParStatut() {
        List<Vol> vols = compagnie.listerVols();
        Map<String, Integer> statutCount = new HashMap<>();

        for (Vol v : vols) {
            String statut = v.getStatut();
            statutCount.put(statut, statutCount.getOrDefault(statut, 0) + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : statutCount.entrySet()) {
            dataset.addValue(entry.getValue(), "Vols", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Vols par Statut - " + compagnie.getNom(),
                "Statut",
                "Nombre de vols",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        afficherDansFenetre(chart, "Vols par Statut", 600, 400);
    }

    /**
     * Affiche un diagramme en barres des destinations les plus populaires
     * (basé sur le nombre de reservations par aeroport d'arrivee).
     */
    public void afficherDestinationsPopulaires() {
        Map<String, Integer> destinations = compagnie.getDestinationsPopulaires();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : destinations.entrySet()) {
            dataset.addValue(entry.getValue(), "Reservations", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Destinations Populaires - " + compagnie.getNom(),
                "Aeroport d'arrivee",
                "Nombre de reservations",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        afficherDansFenetre(chart, "Destinations Populaires", 700, 400);
    }

    /**
     * Affiche un diagramme en barres des revenus par type de vol.
     */
    public void afficherRevenusParTypeVol() {
        List<Vol> vols = compagnie.listerVols();
        Map<String, Double> revenus = new HashMap<>();

        for (Vol v : vols) {
            String type = v.getTypeVol();
            double prix = 0;
            try {
                prix = Double.parseDouble(v.getPrix());
            } catch (NumberFormatException ignored) {
            }
            // Compter le nombre de reservations confirmees pour ce vol
            long nbRes = compagnie.listerReservations().stream()
                    .filter(r -> r.getVolsReserves().contains(v) && "CONFIRMEE".equals(r.getStatut()))
                    .count();
            revenus.put(type, revenus.getOrDefault(type, 0.0) + (prix * nbRes));
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : revenus.entrySet()) {
            dataset.addValue(entry.getValue(), "Revenus (EUR)", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Revenus par Type de Vol - " + compagnie.getNom(),
                "Type de Vol",
                "Revenus (EUR)",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        afficherDansFenetre(chart, "Revenus par Type", 600, 400);
    }

    /**
     * Affiche un diagramme en barres du nombre de reservations par statut.
     */
    public void afficherReservationsParStatut() {
        List<Reservation> reservations = compagnie.listerReservations();
        Map<String, Integer> statutCount = new HashMap<>();

        for (Reservation r : reservations) {
            String statut = r.getStatut();
            statutCount.put(statut, statutCount.getOrDefault(statut, 0) + 1);
        }

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : statutCount.entrySet()) {
            dataset.addValue(entry.getValue(), "Reservations", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Reservations par Statut - " + compagnie.getNom(),
                "Statut",
                "Nombre",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        afficherDansFenetre(chart, "Reservations par Statut", 600, 400);
    }

    /**
     * Affiche tous les graphiques dans une seule fenetre a onglets.
     */
    public void afficherTousLesGraphiques() {
        JFrame frame = new JFrame("Statistiques - " + compagnie.getNom());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Onglet 1 : Types de vols (pie)
        Map<String, Integer> statsTypes = compagnie.getStatistiquesTypesVols();
        DefaultPieDataset<String> pieDataset = new DefaultPieDataset<>();
        for (Map.Entry<String, Integer> entry : statsTypes.entrySet()) {
            pieDataset.setValue(entry.getKey(), entry.getValue());
        }
        JFreeChart pieChart = ChartFactory.createPieChart("Repartition Types de Vols", pieDataset, true, true, false);
        tabbedPane.addTab("Types de Vols", new ChartPanel(pieChart));

        // Onglet 2 : Statuts des vols
        List<Vol> vols = compagnie.listerVols();
        Map<String, Integer> statutVols = new HashMap<>();
        for (Vol v : vols) {
            statutVols.put(v.getStatut(), statutVols.getOrDefault(v.getStatut(), 0) + 1);
        }
        DefaultCategoryDataset barDataset1 = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : statutVols.entrySet()) {
            barDataset1.addValue(entry.getValue(), "Vols", entry.getKey());
        }
        JFreeChart barChart1 = ChartFactory.createBarChart("Vols par Statut", "Statut", "Nombre", barDataset1, PlotOrientation.VERTICAL, true, true, false);
        tabbedPane.addTab("Statuts Vols", new ChartPanel(barChart1));

        // Onglet 3 : Destinations populaires
        Map<String, Integer> destinations = compagnie.getDestinationsPopulaires();
        DefaultCategoryDataset barDataset2 = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : destinations.entrySet()) {
            barDataset2.addValue(entry.getValue(), "Reservations", entry.getKey());
        }
        JFreeChart barChart2 = ChartFactory.createBarChart("Destinations Populaires", "Destination", "Reservations", barDataset2, PlotOrientation.VERTICAL, true, true, false);
        tabbedPane.addTab("Destinations", new ChartPanel(barChart2));

        frame.add(tabbedPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ======================== UTILITAIRE ========================

    private void afficherDansFenetre(JFreeChart chart, String titre, int largeur, int hauteur) {
        JFrame frame = new JFrame(titre);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(largeur, hauteur);
        frame.add(new ChartPanel(chart));
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
