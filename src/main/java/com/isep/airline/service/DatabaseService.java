package com.isep.airline.service;

import com.isep.airline.model.Passager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Service de persistance des données via une base de données relationnelle <b>H2</b>.
 *
 * <p>Utilise l'API <b>JDBC</b> ({@link java.sql}) pour communiquer avec une base
 * de données embarquée H2. La base est créée automatiquement si elle n'existe pas.</p>
 *
 * <h2>API Java utilisée</h2>
 * <ul>
 *   <li>{@link java.sql.DriverManager} — établir la connexion</li>
 *   <li>{@link java.sql.Connection} — session avec la BDD</li>
 *   <li>{@link java.sql.Statement} / {@link java.sql.PreparedStatement} — exécuter des requêtes SQL</li>
 *   <li>{@link java.sql.ResultSet} — parcourir les résultats d'une requête SELECT</li>
 * </ul>
 *
 * <h2>Fonctionnalités CRUD</h2>
 * <ul>
 *   <li><b>Create</b> — {@link #insererPassager(Passager)}, {@link #insererVol(String, String, String, String, String)}</li>
 *   <li><b>Read</b>   — {@link #listerPassagers()}, {@link #listerVols()}, {@link #rechercherPassager(String)}</li>
 *   <li><b>Update</b> — {@link #mettreAJourPassager(Passager)}</li>
 *   <li><b>Delete</b> — {@link #supprimerPassager(String)}</li>
 * </ul>
 *
 * <h2>Exemple d'utilisation</h2>
 * <pre>
 *   DatabaseService db = new DatabaseService();
 *   db.initialiserBase();
 *   db.insererPassager(new Passager("P001", "Dupont", "Jean", "j@mail.com", "06", "FR123"));
 *   List&lt;Passager&gt; liste = db.listerPassagers();
 *   db.fermerConnexion();
 * </pre>
 *
 * @author  Kahina Medjkoune
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 */
public class DatabaseService {

    // ── Paramètres de connexion H2 ──────────────────────────────────────────
    /** URL JDBC — base H2 embarquée stockée dans le dossier {@code data/} */
    private static final String URL  = "jdbc:h2:./data/skyisep_db";
    /** Utilisateur par défaut */
    private static final String USER = "sa";
    /** Mot de passe par défaut (vide) */
    private static final String PASS = "";

    /** Connexion JDBC active */
    private Connection connexion;

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ CONNEXION ══════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Ouvre une connexion JDBC vers la base H2.
     *
     * <p>Utilise {@link DriverManager#getConnection(String, String, String)}.</p>
     *
     * @throws SQLException si la connexion échoue
     */
    public void ouvrirConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            connexion = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("[DatabaseService] Connexion H2 ouverte.");
        }
    }

    /**
     * Ferme la connexion JDBC.
     *
     * @throws SQLException si la fermeture échoue
     */
    public void fermerConnexion() throws SQLException {
        if (connexion != null && !connexion.isClosed()) {
            connexion.close();
            System.out.println("[DatabaseService] Connexion H2 fermée.");
        }
    }

    /**
     * Retourne la connexion active, en l'ouvrant si nécessaire.
     *
     * @return la {@link Connection} JDBC active
     * @throws SQLException si la connexion ne peut être établie
     */
    private Connection getConnexion() throws SQLException {
        if (connexion == null || connexion.isClosed()) {
            ouvrirConnexion();
        }
        return connexion;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ INITIALISATION DES TABLES ══════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Crée les tables {@code PASSAGERS} et {@code VOLS} si elles n'existent pas.
     *
     * <p>Utilise {@link Statement#execute(String)} pour exécuter du DDL
     * (Data Definition Language).</p>
     *
     * @throws SQLException si la création des tables échoue
     */
    public void initialiserBase() throws SQLException {
        try (Statement stmt = getConnexion().createStatement()) {

            // Table PASSAGERS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS PASSAGERS (
                    id               VARCHAR(50) PRIMARY KEY,
                    nom              VARCHAR(100) NOT NULL,
                    prenom           VARCHAR(100) NOT NULL,
                    email            VARCHAR(150),
                    telephone        VARCHAR(50),
                    numero_passeport VARCHAR(50)
                )
            """);

            // Table VOLS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS VOLS (
                    numero_vol      VARCHAR(50) PRIMARY KEY,
                    type_vol        VARCHAR(50),
                    date_depart     VARCHAR(50),
                    date_arrivee    VARCHAR(50),
                    heure_depart    VARCHAR(20),
                    heure_arrivee   VARCHAR(20),
                    prix            VARCHAR(50),
                    statut          VARCHAR(30) DEFAULT 'PLANIFIE'
                )
            """);

            // Table RESERVATIONS
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS RESERVATIONS (
                    numero_reservation VARCHAR(100) PRIMARY KEY,
                    passager_id        VARCHAR(50),
                    numero_vol         VARCHAR(50),
                    date_reservation   VARCHAR(50),
                    statut             VARCHAR(30) DEFAULT 'CONFIRMEE',
                    montant_total      VARCHAR(50),
                    FOREIGN KEY (passager_id) REFERENCES PASSAGERS(id),
                    FOREIGN KEY (numero_vol)  REFERENCES VOLS(numero_vol)
                )
            """);

            System.out.println("[DatabaseService] Tables initialisées avec succès.");
        }
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ CREATE — INSERT ════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Insère un passager dans la base de données.
     *
     * <p>Utilise un {@link PreparedStatement} pour éviter les injections SQL.</p>
     *
     * @param  passager le {@link Passager} à insérer (ne doit pas être {@code null})
     * @return {@code true} si l'insertion a réussi, {@code false} sinon
     * @throws SQLException si une erreur SQL survient
     */
    public boolean insererPassager(Passager passager) throws SQLException {
        String sql = "MERGE INTO PASSAGERS (id, nom, prenom, email, telephone, numero_passeport) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnexion().prepareStatement(sql)) {
            ps.setString(1, passager.getId());
            ps.setString(2, passager.getNom());
            ps.setString(3, passager.getPrenom());
            ps.setString(4, passager.getEmail());
            ps.setString(5, passager.getTelephone());
            ps.setString(6, passager.getNumeroPasseport());
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("[DatabaseService] Passager " + passager.getId() + " inséré/mis à jour.");
                return true;
            }
        }
        return false;
    }

    /**
     * Insère un vol dans la base de données.
     *
     * @param  numeroVol   numéro unique du vol
     * @param  typeVol     type de vol ({@code "Court Courrier"}, {@code "Moyen Courrier"}, {@code "Long Courrier"})
     * @param  dateDepart  date de départ
     * @param  dateArrivee date d'arrivée
     * @param  prix        prix du billet
     * @return {@code true} si l'insertion a réussi
     * @throws SQLException si une erreur SQL survient
     */
    public boolean insererVol(String numeroVol, String typeVol, String dateDepart,
                              String dateArrivee, String prix) throws SQLException {
        String sql = "MERGE INTO VOLS (numero_vol, type_vol, date_depart, date_arrivee, prix) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = getConnexion().prepareStatement(sql)) {
            ps.setString(1, numeroVol);
            ps.setString(2, typeVol);
            ps.setString(3, dateDepart);
            ps.setString(4, dateArrivee);
            ps.setString(5, prix);
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("[DatabaseService] Vol " + numeroVol + " inséré/mis à jour.");
                return true;
            }
        }
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ READ — SELECT ══════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Récupère tous les passagers de la base de données.
     *
     * <p>Utilise {@link ResultSet} pour parcourir les lignes retournées.</p>
     *
     * @return une {@link List} de {@link Passager} (liste vide si aucun résultat)
     * @throws SQLException si une erreur SQL survient
     */
    public List<Passager> listerPassagers() throws SQLException {
        List<Passager> passagers = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, email, telephone, numero_passeport FROM PASSAGERS ORDER BY nom";
        try (Statement stmt = getConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Passager p = new Passager(
                        rs.getString("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("telephone"),
                        rs.getString("numero_passeport")
                );
                passagers.add(p);
            }
        }
        return passagers;
    }

    /**
     * Recherche un passager par son identifiant.
     *
     * @param  id l'identifiant du passager (ex : {@code "P001"})
     * @return le {@link Passager} trouvé, ou {@code null} s'il n'existe pas
     * @throws SQLException si une erreur SQL survient
     */
    public Passager rechercherPassager(String id) throws SQLException {
        String sql = "SELECT id, nom, prenom, email, telephone, numero_passeport FROM PASSAGERS WHERE id = ?";
        try (PreparedStatement ps = getConnexion().prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Passager(
                            rs.getString("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("telephone"),
                            rs.getString("numero_passeport")
                    );
                }
            }
        }
        return null;
    }

    /**
     * Récupère tous les vols de la base de données sous forme de texte résumé.
     *
     * @return une {@link List} de {@link String} décrivant chaque vol
     * @throws SQLException si une erreur SQL survient
     */
    public List<String> listerVols() throws SQLException {
        List<String> vols = new ArrayList<>();
        String sql = "SELECT numero_vol, type_vol, date_depart, date_arrivee, prix, statut FROM VOLS ORDER BY date_depart";
        try (Statement stmt = getConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String desc = rs.getString("numero_vol") + " | "
                        + rs.getString("type_vol") + " | "
                        + rs.getString("date_depart") + " → "
                        + rs.getString("date_arrivee") + " | "
                        + rs.getString("prix") + " € | "
                        + rs.getString("statut");
                vols.add(desc);
            }
        }
        return vols;
    }

    /**
     * Compte le nombre de passagers dans la base.
     *
     * @return le nombre total de passagers
     * @throws SQLException si une erreur SQL survient
     */
    public int compterPassagers() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM PASSAGERS";
        try (Statement stmt = getConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    /**
     * Compte le nombre de vols dans la base.
     *
     * @return le nombre total de vols
     * @throws SQLException si une erreur SQL survient
     */
    public int compterVols() throws SQLException {
        String sql = "SELECT COUNT(*) AS total FROM VOLS";
        try (Statement stmt = getConnexion().createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("total");
            }
        }
        return 0;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ UPDATE ═════════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Met à jour les informations d'un passager existant.
     *
     * @param  passager le {@link Passager} avec les nouvelles données
     * @return {@code true} si la mise à jour a affecté au moins une ligne
     * @throws SQLException si une erreur SQL survient
     */
    public boolean mettreAJourPassager(Passager passager) throws SQLException {
        String sql = "UPDATE PASSAGERS SET nom = ?, prenom = ?, email = ?, telephone = ?, numero_passeport = ? WHERE id = ?";
        try (PreparedStatement ps = getConnexion().prepareStatement(sql)) {
            ps.setString(1, passager.getNom());
            ps.setString(2, passager.getPrenom());
            ps.setString(3, passager.getEmail());
            ps.setString(4, passager.getTelephone());
            ps.setString(5, passager.getNumeroPasseport());
            ps.setString(6, passager.getId());
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("[DatabaseService] Passager " + passager.getId() + " mis à jour.");
                return true;
            }
        }
        System.out.println("[DatabaseService] Passager " + passager.getId() + " introuvable pour la mise à jour.");
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ DELETE ═════════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Supprime un passager de la base par son identifiant.
     *
     * @param  id l'identifiant du passager à supprimer
     * @return {@code true} si le passager a été supprimé
     * @throws SQLException si une erreur SQL survient
     */
    public boolean supprimerPassager(String id) throws SQLException {
        String sql = "DELETE FROM PASSAGERS WHERE id = ?";
        try (PreparedStatement ps = getConnexion().prepareStatement(sql)) {
            ps.setString(1, id);
            int lignes = ps.executeUpdate();
            if (lignes > 0) {
                System.out.println("[DatabaseService] Passager " + id + " supprimé.");
                return true;
            }
        }
        System.out.println("[DatabaseService] Passager " + id + " introuvable.");
        return false;
    }

    /**
     * Supprime toutes les données des tables (utile pour les tests).
     *
     * @throws SQLException si une erreur SQL survient
     */
    public void viderToutesLesTables() throws SQLException {
        try (Statement stmt = getConnexion().createStatement()) {
            stmt.execute("DELETE FROM RESERVATIONS");
            stmt.execute("DELETE FROM VOLS");
            stmt.execute("DELETE FROM PASSAGERS");
            System.out.println("[DatabaseService] Toutes les tables vidées.");
        }
    }
}
