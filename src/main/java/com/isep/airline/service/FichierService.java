package com.isep.airline.service;

import com.isep.airline.model.Passager;
import com.isep.airline.model.Reservation;
import com.isep.airline.model.Vol;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de communication avec des fichiers externes.
 *
 * <p>Permet de <b>sauvegarder</b> et de <b>charger</b> les données de l'application
 * dans des fichiers au format <b>CSV</b> (Comma-Separated Values).</p>
 *
 * <h2>Java I/O utilisée</h2>
 * <ul>
 *   <li>{@link java.io.BufferedWriter} / {@link java.io.FileWriter} — écriture ligne par ligne</li>
 *   <li>{@link java.io.BufferedReader} / {@link java.io.FileReader} — lecture ligne par ligne</li>
 *   <li>{@link java.nio.file.Files} — opérations sur les fichiers (création, existence, copie)</li>
 *   <li>{@link java.nio.file.Path} / {@link java.nio.file.Paths} — manipulation de chemins</li>
 *   <li>{@link java.nio.charset.StandardCharsets} — encodage UTF-8</li>
 * </ul>
 *
 * <h2>Format CSV</h2>
 * <pre>
 *   # Passagers : id;nom;prenom;email;telephone;numeroPasseport
 *   # Vols      : numeroVol;typeVol;dateDepart;dateArrivee;heureDepart;heureArrivee;prix;statut
 * </pre>
 *
 * @author  Équipe SkyISEP
 * @author  Kahina Medjkoune
 * @version 1.0
 * @since   2025
 */
public class FichierService {

    // ── Séparateur CSV ──────────────────────────────────────────────────────
    private static final String SEPARATEUR = ";";

    // ── Nom des fichiers par défaut ─────────────────────────────────────────
    private static final String FICHIER_PASSAGERS    = "data/passagers.csv";
    private static final String FICHIER_VOLS         = "data/vols.csv";
    private static final String FICHIER_RESERVATIONS = "data/reservations.csv";

    // ── Entête CSV ──────────────────────────────────────────────────────────
    private static final String ENTETE_PASSAGERS    = "id;nom;prenom;email;telephone;numeroPasseport";
    private static final String ENTETE_VOLS         = "numeroVol;typeVol;dateDepart;dateArrivee;heureDepart;heureArrivee;prix;statut";
    private static final String ENTETE_RESERVATIONS = "numeroReservation;passagerId;passagerNom;numeroVol;dateReservation;statut;montantTotal";

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ ÉCRITURE — java.io ═════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Exporte la liste des passagers dans un fichier CSV.
     *
     * <p>Utilise {@link java.io.BufferedWriter} via {@link java.io.FileWriter}
     * pour une écriture efficace ligne par ligne.</p>
     *
     * @param  passagers  la liste des {@link Passager} à exporter (ne doit pas être {@code null})
     * @param  cheminFichier chemin du fichier de sortie (ex : {@code "data/passagers.csv"})
     * @throws IOException si une erreur d'écriture survient
     */
    public void exporterPassagers(List<Passager> passagers, String cheminFichier) throws IOException {
        // Créer le dossier parent si nécessaire (java.nio.file)
        Path chemin = Paths.get(cheminFichier);
        creerDossiersSiNecessaire(chemin);

        // Écriture avec BufferedWriter (java.io) — encodage UTF-8
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(cheminFichier), StandardCharsets.UTF_8))) {

            // Ligne d'entête
            writer.write(ENTETE_PASSAGERS);
            writer.newLine();

            // Une ligne par passager
            for (Passager p : passagers) {
                StringBuilder ligne = new StringBuilder();
                ligne.append(echapper(p.getId())).append(SEPARATEUR)
                     .append(echapper(p.getNom())).append(SEPARATEUR)
                     .append(echapper(p.getPrenom())).append(SEPARATEUR)
                     .append(echapper(p.getEmail())).append(SEPARATEUR)
                     .append(echapper(p.getTelephone())).append(SEPARATEUR)
                     .append(echapper(p.getNumeroPasseport()));
                writer.write(ligne.toString());
                writer.newLine();
            }
        }
        System.out.println("[FichierService] " + passagers.size() + " passager(s) exporté(s) → " + cheminFichier);
    }

    /**
     * Exporte la liste des vols dans un fichier CSV.
     *
     * @param  vols          la liste des {@link Vol} à exporter
     * @param  cheminFichier chemin du fichier de sortie
     * @throws IOException   si une erreur d'écriture survient
     */
    public void exporterVols(List<Vol> vols, String cheminFichier) throws IOException {
        Path chemin = Paths.get(cheminFichier);
        creerDossiersSiNecessaire(chemin);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(cheminFichier), StandardCharsets.UTF_8))) {

            writer.write(ENTETE_VOLS);
            writer.newLine();

            for (Vol v : vols) {
                String depCode = (v.getAeroportDepart() != null) ? v.getAeroportDepart().getCodeIATA() : "";
                String arrCode = (v.getAeroportArrivee() != null) ? v.getAeroportArrivee().getCodeIATA() : "";
                StringBuilder ligne = new StringBuilder();
                ligne.append(echapper(v.getNumeroVol())).append(SEPARATEUR)
                     .append(echapper(v.getTypeVol())).append(SEPARATEUR)
                     .append(echapper(depCode)).append(SEPARATEUR)
                     .append(echapper(arrCode)).append(SEPARATEUR)
                     .append(echapper(v.getDateDepart())).append(SEPARATEUR)
                     .append(echapper(v.getDateArrivee())).append(SEPARATEUR)
                     .append(echapper(v.getHeureDepart())).append(SEPARATEUR)
                     .append(echapper(v.getHeureArrivee())).append(SEPARATEUR)
                     .append(echapper(v.getPrix())).append(SEPARATEUR)
                     .append(echapper(v.getStatut()));
                writer.write(ligne.toString());
                writer.newLine();
            }
        }
        System.out.println("[FichierService] " + vols.size() + " vol(s) exporté(s) → " + cheminFichier);
    }

    /**
     * Exporte la liste des réservations dans un fichier CSV.
     *
     * @param  reservations  la liste des {@link Reservation} à exporter
     * @param  cheminFichier chemin du fichier de sortie
     * @throws IOException   si une erreur d'écriture survient
     */
    public void exporterReservations(List<Reservation> reservations, String cheminFichier) throws IOException {
        Path chemin = Paths.get(cheminFichier);
        creerDossiersSiNecessaire(chemin);

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(cheminFichier), StandardCharsets.UTF_8))) {

            writer.write(ENTETE_RESERVATIONS);
            writer.newLine();

            for (Reservation r : reservations) {
                String passagerId  = (r.getPassager() != null) ? r.getPassager().getId() : "";
                String passagerNom = (r.getPassager() != null)
                        ? r.getPassager().getNom() + " " + r.getPassager().getPrenom() : "";
                String numeroVol   = (r.getVol() != null) ? r.getVol().getNumeroVol() : "";
                StringBuilder ligne = new StringBuilder();
                ligne.append(echapper(r.getNumeroReservation())).append(SEPARATEUR)
                     .append(echapper(passagerId)).append(SEPARATEUR)
                     .append(echapper(passagerNom)).append(SEPARATEUR)
                     .append(echapper(numeroVol)).append(SEPARATEUR)
                     .append(echapper(r.getDateReservation())).append(SEPARATEUR)
                     .append(echapper(r.getStatut())).append(SEPARATEUR)
                     .append(echapper(r.getMontantTotal()));
                writer.write(ligne.toString());
                writer.newLine();
            }
        }
        System.out.println("[FichierService] " + reservations.size() + " réservation(s) exportée(s) → " + cheminFichier);
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ LECTURE — java.io ══════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Importe une liste de passagers depuis un fichier CSV.
     *
     * <p>Utilise {@link java.io.BufferedReader} via {@link java.io.FileReader}
     * pour une lecture efficace ligne par ligne. La première ligne (entête) est ignorée.</p>
     *
     * @param  cheminFichier chemin du fichier CSV source
     * @return une {@link List} de {@link Passager} reconstitués
     * @throws IOException           si le fichier est introuvable ou illisible
     * @throws IllegalStateException si une ligne CSV est malformée (nombre de colonnes incorrect)
     */
    public List<Passager> importerPassagers(String cheminFichier) throws IOException {
        List<Passager> passagers = new ArrayList<>();

        // Vérifier l'existence du fichier (java.nio.file)
        Path chemin = Paths.get(cheminFichier);
        if (!Files.exists(chemin)) {
            System.out.println("[FichierService] Fichier introuvable : " + cheminFichier);
            return passagers;
        }

        // Lecture avec BufferedReader (java.io)
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(cheminFichier), StandardCharsets.UTF_8))) {

            String ligne;
            boolean premiereEnLigne = true;

            while ((ligne = reader.readLine()) != null) {
                // Ignorer l'entête et les lignes vides / commentaires
                if (premiereEnLigne || ligne.isBlank() || ligne.startsWith("#")) {
                    premiereEnLigne = false;
                    continue;
                }
                String[] colonnes = ligne.split(SEPARATEUR, -1);
                if (colonnes.length < 6) {
                    System.out.println("[FichierService] Ligne ignorée (format invalide) : " + ligne);
                    continue;
                }
                Passager p = new Passager(
                        colonnes[0].trim(),  // id
                        colonnes[1].trim(),  // nom
                        colonnes[2].trim(),  // prenom
                        colonnes[3].trim(),  // email
                        colonnes[4].trim(),  // telephone
                        colonnes[5].trim()   // numeroPasseport
                );
                passagers.add(p);
            }
        }
        System.out.println("[FichierService] " + passagers.size() + " passager(s) importé(s) depuis " + cheminFichier);
        return passagers;
    }

    /**
     * Lit le contenu brut d'un fichier texte et le retourne sous forme de {@link String}.
     *
     * <p>Utilise {@link java.nio.file.Files#readString(Path)} — Java NIO.2.</p>
     *
     * @param  cheminFichier chemin du fichier à lire
     * @return le contenu complet du fichier en UTF-8, ou une chaîne vide si le fichier n'existe pas
     * @throws IOException si une erreur de lecture survient
     */
    public String lireFichierTexte(String cheminFichier) throws IOException {
        Path chemin = Paths.get(cheminFichier);
        if (!Files.exists(chemin)) {
            return "";
        }
        // java.nio.file.Files.readString — Java 11+
        return Files.readString(chemin, StandardCharsets.UTF_8);
    }

    /**
     * Écrit du texte dans un fichier (écrase le contenu existant).
     *
     * <p>Utilise {@link java.nio.file.Files#writeString(Path, CharSequence, java.nio.file.OpenOption...)}
     * — Java NIO.2.</p>
     *
     * @param  cheminFichier chemin du fichier de sortie
     * @param  contenu       texte à écrire
     * @throws IOException   si une erreur d'écriture survient
     */
    public void ecrireFichierTexte(String cheminFichier, String contenu) throws IOException {
        Path chemin = Paths.get(cheminFichier);
        creerDossiersSiNecessaire(chemin);
        // java.nio.file.Files.writeString — Java 11+
        Files.writeString(chemin, contenu, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.println("[FichierService] Fichier écrit : " + cheminFichier);
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ UTILITAIRES — java.nio ═════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Vérifie si un fichier existe.
     *
     * <p>Utilise {@link java.nio.file.Files#exists(Path, java.nio.file.LinkOption...)}.</p>
     *
     * @param  cheminFichier le chemin à vérifier
     * @return {@code true} si le fichier existe, {@code false} sinon
     */
    public boolean fichierExiste(String cheminFichier) {
        return Files.exists(Paths.get(cheminFichier));
    }

    /**
     * Retourne la taille d'un fichier en octets.
     *
     * @param  cheminFichier le chemin du fichier
     * @return la taille en octets, ou {@code -1} si le fichier n'existe pas
     * @throws IOException si une erreur survient lors de l'accès au fichier
     */
    public long tailleFichier(String cheminFichier) throws IOException {
        Path chemin = Paths.get(cheminFichier);
        if (!Files.exists(chemin)) return -1L;
        return Files.size(chemin);
    }

    /**
     * Copie un fichier vers une destination.
     *
     * <p>Utilise {@link java.nio.file.Files#copy(Path, Path, java.nio.file.CopyOption...)}.</p>
     *
     * @param  source      chemin du fichier source
     * @param  destination chemin de destination
     * @throws IOException si la copie échoue
     */
    public void copierFichier(String source, String destination) throws IOException {
        Path src  = Paths.get(source);
        Path dest = Paths.get(destination);
        creerDossiersSiNecessaire(dest);
        Files.copy(src, dest, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("[FichierService] Copie : " + source + " → " + destination);
    }

    /**
     * Supprime un fichier s'il existe.
     *
     * <p>Utilise {@link java.nio.file.Files#deleteIfExists(Path)}.</p>
     *
     * @param  cheminFichier le chemin du fichier à supprimer
     * @return {@code true} si le fichier a été supprimé, {@code false} s'il n'existait pas
     * @throws IOException si la suppression échoue
     */
    public boolean supprimerFichier(String cheminFichier) throws IOException {
        return Files.deleteIfExists(Paths.get(cheminFichier));
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ MÉTHODES PAR DÉFAUT (chemins standards) ════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Exporte les passagers vers le fichier par défaut ({@value #FICHIER_PASSAGERS}).
     *
     * @param  passagers la liste des passagers à exporter
     * @throws IOException si une erreur d'écriture survient
     */
    public void exporterPassagers(List<Passager> passagers) throws IOException {
        exporterPassagers(passagers, FICHIER_PASSAGERS);
    }

    /**
     * Exporte les vols vers le fichier par défaut ({@value #FICHIER_VOLS}).
     *
     * @param  vols la liste des vols à exporter
     * @throws IOException si une erreur d'écriture survient
     */
    public void exporterVols(List<Vol> vols) throws IOException {
        exporterVols(vols, FICHIER_VOLS);
    }

    /**
     * Exporte les réservations vers le fichier par défaut ({@value #FICHIER_RESERVATIONS}).
     *
     * @param  reservations la liste des réservations à exporter
     * @throws IOException  si une erreur d'écriture survient
     */
    public void exporterReservations(List<Reservation> reservations) throws IOException {
        exporterReservations(reservations, FICHIER_RESERVATIONS);
    }

    /**
     * Importe les passagers depuis le fichier par défaut ({@value #FICHIER_PASSAGERS}).
     *
     * @return la liste des passagers importés
     * @throws IOException si une erreur de lecture survient
     */
    public List<Passager> importerPassagers() throws IOException {
        return importerPassagers(FICHIER_PASSAGERS);
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ MÉTHODES PRIVÉES ═══════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Crée les dossiers parents d'un chemin s'ils n'existent pas encore.
     *
     * @param chemin le chemin dont il faut créer les parents
     * @throws IOException si la création du dossier échoue
     */
    private void creerDossiersSiNecessaire(Path chemin) throws IOException {
        Path parent = chemin.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
            System.out.println("[FichierService] Dossier créé : " + parent);
        }
    }

    /**
     * Échappe une valeur pour l'écriture CSV (remplace {@code null} par une chaîne vide,
     * supprime les retours à la ligne).
     *
     * @param  valeur la valeur à échapper
     * @return la valeur nettoyée, jamais {@code null}
     */
    private String echapper(String valeur) {
        if (valeur == null) return "";
        return valeur.replace("\n", " ").replace("\r", "");
    }
}
