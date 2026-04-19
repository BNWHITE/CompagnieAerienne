package com.isep.airline.service;

import com.isep.airline.model.Passager;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * Service de persistance des données via <b>MongoDB Atlas</b> (NoSQL).
 *
 * <p>
 * Utilise le driver officiel <b>mongodb-driver-sync</b> pour communiquer
 * avec un cluster MongoDB Atlas hébergé dans le cloud.
 * </p>
 *
 * <h2>API Java utilisée</h2>
 * <ul>
 * <li>{@code MongoClients} — créer une connexion au cluster</li>
 * <li>{@code MongoDatabase} — accéder à la base de données</li>
 * <li>{@code MongoCollection} — manipuler les collections (documents)</li>
 * <li>{@code Document} (BSON) — représenter les objets JSON</li>
 * </ul>
 *
 * <h2>Fonctionnalités CRUD</h2>
 * <ul>
 * <li><b>Create</b> — {@link #insererPassager(Passager)},
 * {@link #insererVol(String, String, String, String, String)}</li>
 * <li><b>Read</b> — {@link #listerPassagers()}, {@link #listerVols()},
 * {@link #rechercherPassager(String)}</li>
 * <li><b>Update</b> — {@link #mettreAJourPassager(Passager)}</li>
 * <li><b>Delete</b> — {@link #supprimerPassager(String)}</li>
 * </ul>
 *
 * <h2>Exemple d'utilisation</h2>
 * <pre>
 * MongoDBService mongo = new MongoDBService();
 * mongo.ouvrirConnexion();
 * mongo.insererPassager(new Passager("P001", "Dupont", "Jean", "j@mail.com", "06", "FR123"));
 * List&lt;Passager&gt; liste = mongo.listerPassagers();
 * mongo.fermerConnexion();
 * </pre>
 *
 * @author Kahina Medjkoune
 * @author Équipe SkyISEP
 * @version 1.0
 * @since 2025
 */
public class MongoDBService {

    // ── Paramètres de connexion MongoDB Atlas ───────────────────────────────
    /** URI de connexion au cluster MongoDB Atlas */
    private static final String CONNECTION_URI =
            "mongodb+srv://blacknwhitemanagement_db_user:LT0wsOEpG3LcZWUr@cluster0.rf2e05z.mongodb.net/";

    /** Nom de la base de données */
    private static final String DATABASE_NAME = "skyisep_airlines";

    /** Client MongoDB */
    private MongoClient mongoClient;

    /** Base de données active */
    private MongoDatabase database;

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ CONNEXION ══════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Ouvre une connexion vers le cluster MongoDB Atlas.
     *
     * <p>Utilise {@code MongoClients.create()} avec l'URI SRV.</p>
     */
    public void ouvrirConnexion() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(CONNECTION_URI);
            database = mongoClient.getDatabase(DATABASE_NAME);
            System.out.println("[MongoDBService] Connexion MongoDB Atlas ouverte.");
        }
    }

    /**
     * Ferme la connexion MongoDB.
     */
    public void fermerConnexion() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
            database = null;
            System.out.println("[MongoDBService] Connexion MongoDB Atlas fermée.");
        }
    }

    /**
     * Retourne la base de données active, en ouvrant la connexion si nécessaire.
     *
     * @return la {@link MongoDatabase} active
     */
    private MongoDatabase getDatabase() {
        if (database == null) {
            ouvrirConnexion();
        }
        return database;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ CREATE — INSERT ════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Insère ou met à jour un passager dans la collection {@code passagers}.
     *
     * <p>Utilise {@code replaceOne} avec {@code upsert=true} pour insérer
     * ou mettre à jour si le document existe déjà.</p>
     *
     * @param passager le {@link Passager} à insérer
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererPassager(Passager passager) {
        MongoCollection<Document> collection = getDatabase().getCollection("passagers");
        Document doc = new Document("_id", passager.getId())
                .append("nom", passager.getNom())
                .append("prenom", passager.getPrenom())
                .append("email", passager.getEmail())
                .append("telephone", passager.getTelephone())
                .append("numero_passeport", passager.getNumeroPasseport());

        collection.replaceOne(
                Filters.eq("_id", passager.getId()),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Passager " + passager.getId() + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère ou met à jour un vol dans la collection {@code vols}.
     *
     * @param numeroVol   numéro unique du vol
     * @param typeVol     type de vol
     * @param dateDepart  date de départ
     * @param dateArrivee date d'arrivée
     * @param prix        prix du billet
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererVol(String numeroVol, String typeVol, String dateDepart,
                              String dateArrivee, String prix) {
        MongoCollection<Document> collection = getDatabase().getCollection("vols");
        Document doc = new Document("_id", numeroVol)
                .append("type_vol", typeVol)
                .append("date_depart", dateDepart)
                .append("date_arrivee", dateArrivee)
                .append("prix", prix)
                .append("statut", "PLANIFIE");

        collection.replaceOne(
                Filters.eq("_id", numeroVol),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Vol " + numeroVol + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère ou met à jour un aéroport dans la collection {@code aeroports}.
     *
     * @param codeIATA code IATA de l'aéroport
     * @param nom      nom de l'aéroport
     * @param ville    ville
     * @param pays     pays
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererAeroport(String codeIATA, String nom, String ville, String pays) {
        MongoCollection<Document> collection = getDatabase().getCollection("aeroports");
        Document doc = new Document("_id", codeIATA)
                .append("nom", nom)
                .append("ville", ville)
                .append("pays", pays);
        collection.replaceOne(
                Filters.eq("_id", codeIATA),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Aéroport " + codeIATA + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère ou met à jour un avion dans la collection {@code avions}.
     *
     * @param immatriculation immatriculation de l'avion
     * @param modele          modèle de l'avion
     * @param capacite        capacité en passagers
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererAvion(String immatriculation, String modele, int capacite) {
        MongoCollection<Document> collection = getDatabase().getCollection("avions");
        Document doc = new Document("_id", immatriculation)
                .append("modele", modele)
                .append("capacite", capacite)
                .append("disponible", true);
        collection.replaceOne(
                Filters.eq("_id", immatriculation),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Avion " + immatriculation + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère ou met à jour un pilote dans la collection {@code pilotes}.
     *
     * @param id          identifiant du pilote
     * @param nom         nom
     * @param prenom      prénom
     * @param email       email
     * @param telephone   téléphone
     * @param numEmploye  numéro employé
     * @param salaire     salaire
     * @param licence     licence de pilote
     * @param heuresDeVol heures de vol
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererPilote(String id, String nom, String prenom, String email,
                                  String telephone, String numEmploye, String salaire,
                                  String licence, String heuresDeVol) {
        MongoCollection<Document> collection = getDatabase().getCollection("pilotes");
        Document doc = new Document("_id", id)
                .append("nom", nom)
                .append("prenom", prenom)
                .append("email", email)
                .append("telephone", telephone)
                .append("numero_employe", numEmploye)
                .append("salaire", salaire)
                .append("licence", licence)
                .append("heures_de_vol", heuresDeVol);
        collection.replaceOne(
                Filters.eq("_id", id),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Pilote " + id + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère ou met à jour un personnel de cabine dans la collection {@code personnel_cabine}.
     *
     * @param id               identifiant
     * @param nom              nom
     * @param prenom           prénom
     * @param email            email
     * @param telephone        téléphone
     * @param numEmploye       numéro employé
     * @param salaire          salaire
     * @param qualification    qualification
     * @param anneesExperience années d'expérience
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererPersonnelCabine(String id, String nom, String prenom, String email,
                                           String telephone, String numEmploye, String salaire,
                                           String qualification, String anneesExperience) {
        MongoCollection<Document> collection = getDatabase().getCollection("personnel_cabine");
        Document doc = new Document("_id", id)
                .append("nom", nom)
                .append("prenom", prenom)
                .append("email", email)
                .append("telephone", telephone)
                .append("numero_employe", numEmploye)
                .append("salaire", salaire)
                .append("qualification", qualification)
                .append("annees_experience", anneesExperience);
        collection.replaceOne(
                Filters.eq("_id", id),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Personnel cabine " + id + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère ou met à jour un équipage dans la collection {@code equipages}.
     *
     * @param idEquipage      identifiant de l'équipage
     * @param piloteId        identifiant du pilote
     * @param personnelCabine liste des identifiants du personnel de cabine
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererEquipage(String idEquipage, String piloteId, List<String> personnelCabine) {
        MongoCollection<Document> collection = getDatabase().getCollection("equipages");
        Document doc = new Document("_id", idEquipage)
                .append("pilote_id", piloteId)
                .append("personnel_cabine_ids", personnelCabine);
        collection.replaceOne(
                Filters.eq("_id", idEquipage),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Équipage " + idEquipage + " inséré/mis à jour.");
        return true;
    }

    /**
     * Insère une réservation dans la collection {@code reservations}.
     *
     * @param numeroReservation numéro unique de la réservation
     * @param passagerId        identifiant du passager
     * @param numeroVol         numéro du vol
     * @param dateReservation   date de la réservation
     * @param montantTotal      montant total
     * @return {@code true} si l'opération a réussi
     */
    public boolean insererReservation(String numeroReservation, String passagerId,
                                      String numeroVol, String dateReservation, String montantTotal) {
        MongoCollection<Document> collection = getDatabase().getCollection("reservations");
        Document doc = new Document("_id", numeroReservation)
                .append("passager_id", passagerId)
                .append("numero_vol", numeroVol)
                .append("date_reservation", dateReservation)
                .append("statut", "CONFIRMEE")
                .append("montant_total", montantTotal);

        collection.replaceOne(
                Filters.eq("_id", numeroReservation),
                doc,
                new ReplaceOptions().upsert(true));
        System.out.println("[MongoDBService] Réservation " + numeroReservation + " insérée.");
        return true;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ READ — FIND ════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Récupère tous les passagers de la collection {@code passagers}.
     *
     * @return une {@link List} de {@link Passager}
     */
    public List<Passager> listerPassagers() {
        List<Passager> passagers = new ArrayList<>();
        MongoCollection<Document> collection = getDatabase().getCollection("passagers");
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                Passager p = new Passager(
                        doc.getString("_id"),
                        doc.getString("nom"),
                        doc.getString("prenom"),
                        doc.getString("email"),
                        doc.getString("telephone"),
                        doc.getString("numero_passeport"));
                passagers.add(p);
            }
        }
        return passagers;
    }

    /**
     * Recherche un passager par son identifiant.
     *
     * @param id l'identifiant du passager
     * @return le {@link Passager} trouvé, ou {@code null} s'il n'existe pas
     */
    public Passager rechercherPassager(String id) {
        MongoCollection<Document> collection = getDatabase().getCollection("passagers");
        Document doc = collection.find(Filters.eq("_id", id)).first();
        if (doc != null) {
            return new Passager(
                    doc.getString("_id"),
                    doc.getString("nom"),
                    doc.getString("prenom"),
                    doc.getString("email"),
                    doc.getString("telephone"),
                    doc.getString("numero_passeport"));
        }
        return null;
    }

    /**
     * Récupère tous les vols sous forme de texte résumé.
     *
     * @return une {@link List} de {@link String} décrivant chaque vol
     */
    public List<String> listerVols() {
        List<String> vols = new ArrayList<>();
        MongoCollection<Document> collection = getDatabase().getCollection("vols");
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                Document doc = cursor.next();
                String desc = doc.getString("_id") + " | "
                        + doc.getString("type_vol") + " | "
                        + doc.getString("date_depart") + " → "
                        + doc.getString("date_arrivee") + " | "
                        + doc.getString("prix") + " € | "
                        + doc.getString("statut");
                vols.add(desc);
            }
        }
        return vols;
    }

    /**
     * Compte le nombre de passagers dans la collection.
     *
     * @return le nombre total de passagers
     */
    public long compterPassagers() {
        return getDatabase().getCollection("passagers").countDocuments();
    }

    /**
     * Compte le nombre de vols dans la collection.
     *
     * @return le nombre total de vols
     */
    public long compterVols() {
        return getDatabase().getCollection("vols").countDocuments();
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ UPDATE ═════════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Met à jour les informations d'un passager existant.
     *
     * @param passager le {@link Passager} avec les nouvelles données
     * @return {@code true} si la mise à jour a trouvé le document
     */
    public boolean mettreAJourPassager(Passager passager) {
        MongoCollection<Document> collection = getDatabase().getCollection("passagers");
        Document doc = new Document("_id", passager.getId())
                .append("nom", passager.getNom())
                .append("prenom", passager.getPrenom())
                .append("email", passager.getEmail())
                .append("telephone", passager.getTelephone())
                .append("numero_passeport", passager.getNumeroPasseport());

        long modified = collection.replaceOne(
                Filters.eq("_id", passager.getId()), doc).getModifiedCount();
        if (modified > 0) {
            System.out.println("[MongoDBService] Passager " + passager.getId() + " mis à jour.");
            return true;
        }
        System.out.println("[MongoDBService] Passager " + passager.getId() + " introuvable.");
        return false;
    }

    // ════════════════════════════════════════════════════════════════════════
    // ═══════════════════ DELETE ═════════════════════════════════════════════
    // ════════════════════════════════════════════════════════════════════════

    /**
     * Supprime un passager par son identifiant.
     *
     * @param id l'identifiant du passager à supprimer
     * @return {@code true} si le passager a été supprimé
     */
    public boolean supprimerPassager(String id) {
        MongoCollection<Document> collection = getDatabase().getCollection("passagers");
        long deleted = collection.deleteOne(Filters.eq("_id", id)).getDeletedCount();
        if (deleted > 0) {
            System.out.println("[MongoDBService] Passager " + id + " supprimé.");
            return true;
        }
        System.out.println("[MongoDBService] Passager " + id + " introuvable.");
        return false;
    }

    /**
     * Vide toutes les collections (utile pour les tests).
     */
    public void viderToutesLesCollections() {
        getDatabase().getCollection("reservations").deleteMany(new Document());
        getDatabase().getCollection("vols").deleteMany(new Document());
        getDatabase().getCollection("passagers").deleteMany(new Document());
        getDatabase().getCollection("aeroports").deleteMany(new Document());
        getDatabase().getCollection("avions").deleteMany(new Document());
        getDatabase().getCollection("pilotes").deleteMany(new Document());
        getDatabase().getCollection("personnel_cabine").deleteMany(new Document());
        getDatabase().getCollection("equipages").deleteMany(new Document());
        System.out.println("[MongoDBService] Toutes les collections vidées.");
    }
}
