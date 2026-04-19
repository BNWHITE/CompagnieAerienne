package com.isep.airline.service;

import com.isep.airline.model.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service de validation des données métier de la compagnie aérienne.
 *
 * <p>Vérifie la conformité des entrées utilisateur avant insertion :
 * emails, téléphones, numéros de vol, dates, codes IATA, passeports, etc.</p>
 *
 * @author Kahina Medjkoune
 * @version 1.0
 * @since 2025
 */
public class ValidationService {

    // ========================= PATTERNS REGEX =========================

    private static final Pattern PATTERN_EMAIL =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private static final Pattern PATTERN_TELEPHONE =
            Pattern.compile("^\\+?[0-9]{8,15}$");

    private static final Pattern PATTERN_CODE_IATA_AEROPORT =
            Pattern.compile("^[A-Z]{3}$");

    private static final Pattern PATTERN_CODE_IATA_COMPAGNIE =
            Pattern.compile("^[A-Z]{2}$");

    private static final Pattern PATTERN_NUMERO_VOL =
            Pattern.compile("^[A-Z]{2}[0-9]{1,4}$");

    private static final Pattern PATTERN_PASSEPORT =
            Pattern.compile("^[A-Z0-9]{6,12}$");

    private static final Pattern PATTERN_IMMATRICULATION =
            Pattern.compile("^[A-Z]-[A-Z]{4}$");

    private static final DateTimeFormatter FORMAT_DATE =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static final Pattern PATTERN_HEURE =
            Pattern.compile("^([01]\\d|2[0-3]):[0-5]\\d$");

    // ========================= VALIDATIONS UNITAIRES =========================

    /**
     * Vérifie qu'une chaîne n'est ni null ni vide (après trim).
     *
     * @param valeur la chaîne à vérifier
     * @return {@code true} si la chaîne contient du texte
     */
    public boolean estNonVide(String valeur) {
        return valeur != null && !valeur.trim().isEmpty();
    }

    /**
     * Valide le format d'une adresse e-mail.
     *
     * @param email l'adresse à valider
     * @return {@code true} si le format est valide
     */
    public boolean validerEmail(String email) {
        if (email == null) return false;
        return PATTERN_EMAIL.matcher(email.trim()).matches();
    }

    /**
     * Valide le format d'un numéro de téléphone (8 à 15 chiffres, + optionnel).
     *
     * @param telephone le numéro à valider
     * @return {@code true} si le format est valide
     */
    public boolean validerTelephone(String telephone) {
        if (telephone == null) return false;
        return PATTERN_TELEPHONE.matcher(telephone.trim().replaceAll("[\\s.-]", "")).matches();
    }

    /**
     * Valide un code IATA d'aéroport (3 lettres majuscules).
     *
     * @param code le code à valider
     * @return {@code true} si le code est conforme
     */
    public boolean validerCodeIATAAeroport(String code) {
        if (code == null) return false;
        return PATTERN_CODE_IATA_AEROPORT.matcher(code.trim().toUpperCase()).matches();
    }

    /**
     * Valide un code IATA de compagnie (2 lettres majuscules).
     *
     * @param code le code à valider
     * @return {@code true} si le code est conforme
     */
    public boolean validerCodeIATACompagnie(String code) {
        if (code == null) return false;
        return PATTERN_CODE_IATA_COMPAGNIE.matcher(code.trim().toUpperCase()).matches();
    }

    /**
     * Valide un numéro de vol (ex: AF123).
     *
     * @param numero le numéro de vol
     * @return {@code true} si le format est valide
     */
    public boolean validerNumeroVol(String numero) {
        if (numero == null) return false;
        return PATTERN_NUMERO_VOL.matcher(numero.trim().toUpperCase()).matches();
    }

    /**
     * Valide un numéro de passeport (6 à 12 caractères alphanumériques majuscules).
     *
     * @param passeport le numéro de passeport
     * @return {@code true} si le format est valide
     */
    public boolean validerPasseport(String passeport) {
        if (passeport == null) return false;
        return PATTERN_PASSEPORT.matcher(passeport.trim().toUpperCase()).matches();
    }

    /**
     * Valide une immatriculation d'avion (ex: F-GKXA).
     *
     * @param immatriculation l'immatriculation à valider
     * @return {@code true} si le format est valide
     */
    public boolean validerImmatriculation(String immatriculation) {
        if (immatriculation == null) return false;
        return PATTERN_IMMATRICULATION.matcher(immatriculation.trim().toUpperCase()).matches();
    }

    /**
     * Valide une date au format JJ/MM/AAAA.
     *
     * @param date la chaîne de date
     * @return {@code true} si la date est valide et parseable
     */
    public boolean validerDate(String date) {
        if (date == null || date.trim().isEmpty()) return false;
        try {
            LocalDate.parse(date.trim(), FORMAT_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Valide une heure au format HH:MM (24h).
     *
     * @param heure la chaîne d'heure
     * @return {@code true} si l'heure est valide
     */
    public boolean validerHeure(String heure) {
        if (heure == null) return false;
        return PATTERN_HEURE.matcher(heure.trim()).matches();
    }

    /**
     * Vérifie que la date d'arrivée est postérieure ou égale à la date de départ.
     *
     * @param dateDepart  date de départ (JJ/MM/AAAA)
     * @param dateArrivee date d'arrivée (JJ/MM/AAAA)
     * @return {@code true} si les dates sont cohérentes
     */
    public boolean validerCoherenceDates(String dateDepart, String dateArrivee) {
        if (!validerDate(dateDepart) || !validerDate(dateArrivee)) return false;
        LocalDate depart = LocalDate.parse(dateDepart.trim(), FORMAT_DATE);
        LocalDate arrivee = LocalDate.parse(dateArrivee.trim(), FORMAT_DATE);
        return !arrivee.isBefore(depart);
    }

    /**
     * Vérifie qu'un prix est un nombre positif valide.
     *
     * @param prix le prix sous forme de chaîne
     * @return {@code true} si le prix est un nombre &gt; 0
     */
    public boolean validerPrix(String prix) {
        if (prix == null || prix.trim().isEmpty()) return false;
        try {
            double valeur = Double.parseDouble(prix.trim());
            return valeur > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Vérifie qu'une capacité est un entier positif.
     *
     * @param capacite la capacité
     * @return {@code true} si la capacité est &gt; 0
     */
    public boolean validerCapacite(int capacite) {
        return capacite > 0;
    }

    // ========================= VALIDATIONS COMPLÈTES =========================

    /**
     * Valide toutes les données d'un passager et retourne la liste des erreurs.
     *
     * @param passager le passager à valider
     * @return liste des messages d'erreur (vide si tout est valide)
     */
    public List<String> validerPassager(Passager passager) {
        List<String> erreurs = new ArrayList<>();
        if (passager == null) {
            erreurs.add("Le passager est null");
            return erreurs;
        }
        if (!estNonVide(passager.getId())) {
            erreurs.add("L'identifiant du passager est obligatoire");
        }
        if (!estNonVide(passager.getNom())) {
            erreurs.add("Le nom du passager est obligatoire");
        }
        if (!estNonVide(passager.getPrenom())) {
            erreurs.add("Le prénom du passager est obligatoire");
        }
        if (!validerEmail(passager.getEmail())) {
            erreurs.add("L'email du passager est invalide : " + passager.getEmail());
        }
        if (!validerTelephone(passager.getTelephone())) {
            erreurs.add("Le téléphone du passager est invalide : " + passager.getTelephone());
        }
        if (!validerPasseport(passager.getNumeroPasseport())) {
            erreurs.add("Le numéro de passeport est invalide : " + passager.getNumeroPasseport());
        }
        return erreurs;
    }

    /**
     * Valide toutes les données d'un aéroport et retourne la liste des erreurs.
     *
     * @param aeroport l'aéroport à valider
     * @return liste des messages d'erreur (vide si tout est valide)
     */
    public List<String> validerAeroport(Aeroport aeroport) {
        List<String> erreurs = new ArrayList<>();
        if (aeroport == null) {
            erreurs.add("L'aéroport est null");
            return erreurs;
        }
        if (!validerCodeIATAAeroport(aeroport.getCodeIATA())) {
            erreurs.add("Le code IATA de l'aéroport est invalide : " + aeroport.getCodeIATA());
        }
        if (!estNonVide(aeroport.getNom())) {
            erreurs.add("Le nom de l'aéroport est obligatoire");
        }
        if (!estNonVide(aeroport.getVille())) {
            erreurs.add("La ville de l'aéroport est obligatoire");
        }
        if (!estNonVide(aeroport.getPays())) {
            erreurs.add("Le pays de l'aéroport est obligatoire");
        }
        return erreurs;
    }

    /**
     * Valide les données d'un avion et retourne la liste des erreurs.
     *
     * @param avion l'avion à valider
     * @return liste des messages d'erreur (vide si tout est valide)
     */
    public List<String> validerAvion(Avion avion) {
        List<String> erreurs = new ArrayList<>();
        if (avion == null) {
            erreurs.add("L'avion est null");
            return erreurs;
        }
        if (!validerImmatriculation(avion.getImmatriculation())) {
            erreurs.add("L'immatriculation est invalide : " + avion.getImmatriculation());
        }
        if (!estNonVide(avion.getModele())) {
            erreurs.add("Le modèle de l'avion est obligatoire");
        }
        if (!validerCapacite(avion.getCapacite())) {
            erreurs.add("La capacité de l'avion doit être > 0");
        }
        return erreurs;
    }

    /**
     * Vérifie qu'un statut de vol est valide.
     *
     * @param statut le statut à vérifier
     * @return {@code true} si le statut est reconnu
     */
    public boolean validerStatutVol(String statut) {
        if (statut == null) return false;
        String s = statut.trim().toUpperCase();
        return s.equals("PLANIFIE") || s.equals("EN_COURS") || s.equals("TERMINE") || s.equals("ANNULE");
    }

    /**
     * Vérifie qu'un statut de réservation est valide.
     *
     * @param statut le statut à vérifier
     * @return {@code true} si le statut est reconnu
     */
    public boolean validerStatutReservation(String statut) {
        if (statut == null) return false;
        String s = statut.trim().toUpperCase();
        return s.equals("CONFIRMEE") || s.equals("ANNULEE") || s.equals("EN_ATTENTE");
    }
}
