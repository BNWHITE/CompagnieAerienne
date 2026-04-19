package com.isep.airline.service;

import com.isep.airline.model.Passager;

/**
 * Script d'initialisation de la base de données MongoDB Atlas.
 *
 * <p>Insère un jeu de données réaliste : passagers, vols et réservations
 * pour démontrer le fonctionnement complet du système SkyISEP Airlines.</p>
 *
 * @author Kahina Medjkoune
 * @author Seydina SY
 * @version 1.0
 * @since 2025
 */
public class InitialisationMongoDB {

    public static void main(String[] args) {
        MongoDBService mongo = new MongoDBService();
        mongo.ouvrirConnexion();

        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  INITIALISATION BASE MongoDB Atlas");
        System.out.println("  Base : skyisep_airlines");
        System.out.println("═══════════════════════════════════════════════\n");

        // ═══════════════════ NETTOYAGE ═══════════════════
        System.out.println("🧹 Nettoyage des collections existantes...");
        mongo.viderToutesLesCollections();

        // ═══════════════════ PASSAGERS (10) ═══════════════════
        System.out.println("\n👥 Insertion des passagers...");

        mongo.insererPassager(new Passager("P001", "Moreau", "Alice",
                "alice.moreau@email.com", "+33631323334", "FR123456"));
        mongo.insererPassager(new Passager("P002", "Durand", "Bob",
                "bob.durand@email.com", "+33635363738", "FR654321"));
        mongo.insererPassager(new Passager("P003", "Garcia", "Carlos",
                "carlos.garcia@email.com", "+33639404142", "ES789012"));
        mongo.insererPassager(new Passager("P004", "Medjkoune", "Kahina",
                "kahina.medjkoune@isep.fr", "+33645678901", "DZ456789"));
        mongo.insererPassager(new Passager("P005", "SY", "Seydina",
                "seydina.sy@isep.fr", "+33656789012", "SN987654"));
        mongo.insererPassager(new Passager("P006", "Tanaka", "Yuki",
                "yuki.tanaka@email.jp", "+81312345678", "JP112233"));
        mongo.insererPassager(new Passager("P007", "Smith", "John",
                "john.smith@email.com", "+44712345678", "GB445566"));
        mongo.insererPassager(new Passager("P008", "Mueller", "Hans",
                "hans.mueller@email.de", "+49301234567", "DE778899"));
        mongo.insererPassager(new Passager("P009", "Rossi", "Maria",
                "maria.rossi@email.it", "+39021234567", "IT334455"));
        mongo.insererPassager(new Passager("P010", "Kim", "Soo-Jin",
                "soojin.kim@email.kr", "+82212345678", "KR667788"));

        // ═══════════════════ VOLS (8) ═══════════════════
        System.out.println("\n✈️  Insertion des vols...");

        // Long-courriers
        mongo.insererVol("SI101", "Long Courrier",
                "20/04/2026", "20/04/2026", "450");
        mongo.insererVol("SI102", "Long Courrier",
                "21/04/2026", "22/04/2026", "800");
        mongo.insererVol("SI105", "Long Courrier",
                "25/04/2026", "25/04/2026", "550");
        mongo.insererVol("SI108", "Long Courrier",
                "30/04/2026", "01/05/2026", "900");

        // Moyen-courriers
        mongo.insererVol("SI104", "Moyen Courrier",
                "23/04/2026", "23/04/2026", "180");
        mongo.insererVol("SI106", "Moyen Courrier",
                "26/04/2026", "26/04/2026", "200");

        // Court-courriers
        mongo.insererVol("SI103", "Court Courrier",
                "22/04/2026", "22/04/2026", "120");
        mongo.insererVol("SI107", "Court Courrier",
                "28/04/2026", "28/04/2026", "95");

        // ═══════════════════ RESERVATIONS (12) ═══════════════════
        System.out.println("\n📋 Insertion des réservations...");

        // Vol SI101 (CDG → JFK) — 3 passagers
        mongo.insererReservation("RES-001", "P001", "SI101", "15/04/2026", "450");
        mongo.insererReservation("RES-002", "P002", "SI101", "16/04/2026", "450");
        mongo.insererReservation("RES-003", "P007", "SI101", "17/04/2026", "450");

        // Vol SI102 (CDG → NRT) — 2 passagers
        mongo.insererReservation("RES-004", "P003", "SI102", "18/04/2026", "800");
        mongo.insererReservation("RES-005", "P006", "SI102", "18/04/2026", "800");

        // Vol SI103 (CDG → LHR) — 2 passagers
        mongo.insererReservation("RES-006", "P004", "SI103", "19/04/2026", "120");
        mongo.insererReservation("RES-007", "P005", "SI103", "19/04/2026", "120");

        // Vol SI104 (CDG → BCN) — 2 passagers
        mongo.insererReservation("RES-008", "P008", "SI104", "20/04/2026", "180");
        mongo.insererReservation("RES-009", "P009", "SI104", "20/04/2026", "180");

        // Vol SI105 (CDG → DXB) — 1 passager
        mongo.insererReservation("RES-010", "P010", "SI105", "21/04/2026", "550");

        // Vol SI106 (CDG → FCO) — 1 passager
        mongo.insererReservation("RES-011", "P009", "SI106", "22/04/2026", "200");

        // Vol SI107 (CDG → AMS) — 1 passager
        mongo.insererReservation("RES-012", "P005", "SI107", "23/04/2026", "95");

        // ═══════════════════ VÉRIFICATION ═══════════════════
        System.out.println("\n═══════════════════════════════════════════════");
        System.out.println("  ✅ RÉSUMÉ DE L'INITIALISATION");
        System.out.println("═══════════════════════════════════════════════");
        System.out.println("  Passagers  : " + mongo.compterPassagers());
        System.out.println("  Vols       : " + mongo.compterVols());
        System.out.println("═══════════════════════════════════════════════");

        System.out.println("\n📋 Liste des passagers :");
        mongo.listerPassagers().forEach(p ->
                System.out.println("  " + p.getId() + " | " + p.getNom() + " " + p.getPrenom()
                        + " | " + p.getEmail() + " | Passeport: " + p.getNumeroPasseport()));

        System.out.println("\n✈️  Liste des vols :");
        mongo.listerVols().forEach(v -> System.out.println("  " + v));

        mongo.fermerConnexion();
        System.out.println("\n🎉 Initialisation terminée avec succès !");
    }
}
