package com.isep.airline.service;

import com.isep.airline.model.Passager;

/**
 * Script d'initialisation de la base de données MongoDB Atlas.
 *
 * <p>Insère un jeu de données réaliste simulant une vraie compagnie aérienne :
 * 30 passagers internationaux, 20 vols (long/moyen/court courrier) et
 * 45 réservations couvrant plusieurs mois d'activité.</p>
 *
 * @author Kahina Medjkoune
 * @version 2.0
 * @since 2025
 */
public class InitialisationMongoDB {

    public static void main(String[] args) {
        MongoDBService mongo = new MongoDBService();
        mongo.ouvrirConnexion();

        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  ✈️  INITIALISATION BASE MongoDB Atlas");
        System.out.println("  Compagnie : SkyISEP Airlines (SI)");
        System.out.println("  Base      : skyisep_airlines");
        System.out.println("═══════════════════════════════════════════════════════\n");

        // ═══════════════════ NETTOYAGE ═══════════════════
        System.out.println("🧹 Nettoyage des collections existantes...");
        mongo.viderToutesLesCollections();

        // ═══════════════════════════════════════════════════════════════════
        //                     PASSAGERS (30)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n👥 Insertion de 30 passagers internationaux...");

        // --- Passagers français ---
        mongo.insererPassager(new Passager("P001", "Moreau", "Alice",
                "alice.moreau@gmail.com", "+33612345678", "FR1234567"));
        mongo.insererPassager(new Passager("P002", "Durand", "Bob",
                "bob.durand@orange.fr", "+33623456789", "FR2345678"));
        mongo.insererPassager(new Passager("P003", "Lefevre", "Claire",
                "claire.lefevre@hotmail.fr", "+33634567890", "FR3456789"));
        mongo.insererPassager(new Passager("P004", "Medjkoune", "Kahina",
                "kahina.medjkoune@isep.fr", "+33645678901", "DZ4567890"));
        mongo.insererPassager(new Passager("P005", "SY", "Seydina",
                "seydina.sy@isep.fr", "+33656789012", "SN5678901"));
        mongo.insererPassager(new Passager("P006", "Bernard", "Sophie",
                "sophie.bernard@yahoo.fr", "+33667890123", "FR6789012"));
        mongo.insererPassager(new Passager("P007", "Petit", "Lucas",
                "lucas.petit@gmail.com", "+33678901234", "FR7890123"));
        mongo.insererPassager(new Passager("P008", "Roux", "Emma",
                "emma.roux@laposte.net", "+33689012345", "FR8901234"));

        // --- Passagers européens ---
        mongo.insererPassager(new Passager("P009", "Rossi", "Maria",
                "maria.rossi@email.it", "+39021234567", "IT1122334"));
        mongo.insererPassager(new Passager("P010", "Mueller", "Hans",
                "hans.mueller@email.de", "+49301234567", "DE2233445"));
        mongo.insererPassager(new Passager("P011", "Lopez", "Carmen",
                "carmen.lopez@email.es", "+34912345678", "ES3344556"));
        mongo.insererPassager(new Passager("P012", "De Vries", "Jan",
                "jan.devries@email.nl", "+31201234567", "NL4455667"));
        mongo.insererPassager(new Passager("P013", "Andersson", "Erik",
                "erik.andersson@email.se", "+46812345678", "SE5566778"));
        mongo.insererPassager(new Passager("P014", "Silva", "Ana",
                "ana.silva@email.pt", "+35121234567", "PT6677889"));

        // --- Passagers nord-américains ---
        mongo.insererPassager(new Passager("P015", "Smith", "John",
                "john.smith@email.com", "+12125551234", "US7788990"));
        mongo.insererPassager(new Passager("P016", "Johnson", "Emily",
                "emily.johnson@email.com", "+13105552345", "US8899001"));
        mongo.insererPassager(new Passager("P017", "Williams", "Michael",
                "michael.williams@email.com", "+14165553456", "CA9900112"));
        mongo.insererPassager(new Passager("P018", "Brown", "Sarah",
                "sarah.brown@email.com", "+12025554567", "US0011223"));

        // --- Passagers asiatiques ---
        mongo.insererPassager(new Passager("P019", "Tanaka", "Yuki",
                "yuki.tanaka@email.jp", "+81312345678", "JP1122330"));
        mongo.insererPassager(new Passager("P020", "Kim", "Soo-Jin",
                "soojin.kim@email.kr", "+82212345678", "KR2233441"));
        mongo.insererPassager(new Passager("P021", "Wang", "Li",
                "li.wang@email.cn", "+86101234567", "CN3344552"));
        mongo.insererPassager(new Passager("P022", "Patel", "Raj",
                "raj.patel@email.in", "+91112345678", "IN4455663"));
        mongo.insererPassager(new Passager("P023", "Nguyen", "Linh",
                "linh.nguyen@email.vn", "+84901234567", "VN5566774"));

        // --- Passagers Moyen-Orient / Afrique ---
        mongo.insererPassager(new Passager("P024", "Al-Farsi", "Omar",
                "omar.alfarsi@email.ae", "+97141234567", "AE6677885"));
        mongo.insererPassager(new Passager("P025", "Diallo", "Fatou",
                "fatou.diallo@email.sn", "+221771234567", "SN7788996"));
        mongo.insererPassager(new Passager("P026", "Okafor", "Chidi",
                "chidi.okafor@email.ng", "+234801234567", "NG8899007"));

        // --- Passagers Amérique du Sud ---
        mongo.insererPassager(new Passager("P027", "Santos", "Pedro",
                "pedro.santos@email.br", "+55119012345", "BR9900118"));
        mongo.insererPassager(new Passager("P028", "Gonzalez", "Isabella",
                "isabella.gonzalez@email.ar", "+54111234567", "AR0011229"));
        mongo.insererPassager(new Passager("P029", "Torres", "Diego",
                "diego.torres@email.co", "+57311234567", "CO1122330"));

        // --- Passager Océanie ---
        mongo.insererPassager(new Passager("P030", "O'Brien", "Liam",
                "liam.obrien@email.au", "+61291234567", "AU2233441"));

        // ═══════════════════════════════════════════════════════════════════
        //                       VOLS (20)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n✈️  Insertion de 20 vols (avril – juin 2026)...");

        // ---- LONG COURRIER (8 vols) ----
        // Paris CDG → New York JFK
        mongo.insererVol("SI101", "Long Courrier",
                "22/04/2026", "22/04/2026", "480");
        // Paris CDG → Tokyo NRT
        mongo.insererVol("SI102", "Long Courrier",
                "24/04/2026", "25/04/2026", "850");
        // Paris CDG → Dubaï DXB
        mongo.insererVol("SI103", "Long Courrier",
                "28/04/2026", "28/04/2026", "520");
        // Paris CDG → São Paulo GRU
        mongo.insererVol("SI104", "Long Courrier",
                "02/05/2026", "03/05/2026", "720");
        // Paris CDG → Séoul ICN
        mongo.insererVol("SI115", "Long Courrier",
                "10/05/2026", "11/05/2026", "890");
        // Paris CDG → Mumbai BOM
        mongo.insererVol("SI116", "Long Courrier",
                "15/05/2026", "15/05/2026", "580");
        // Paris CDG → Sydney SYD
        mongo.insererVol("SI119", "Long Courrier",
                "01/06/2026", "02/06/2026", "1100");
        // Paris CDG → Lagos LOS
        mongo.insererVol("SI120", "Long Courrier",
                "05/06/2026", "05/06/2026", "490");

        // ---- MOYEN COURRIER (7 vols) ----
        // Paris CDG → Istanbul IST
        mongo.insererVol("SI105", "Moyen Courrier",
                "23/04/2026", "23/04/2026", "250");
        // Paris CDG → Marrakech RAK
        mongo.insererVol("SI106", "Moyen Courrier",
                "26/04/2026", "26/04/2026", "180");
        // Paris CDG → Athènes ATH
        mongo.insererVol("SI109", "Moyen Courrier",
                "01/05/2026", "01/05/2026", "220");
        // Paris CDG → Lisbonne LIS
        mongo.insererVol("SI112", "Moyen Courrier",
                "06/05/2026", "06/05/2026", "195");
        // Paris CDG → Stockholm ARN
        mongo.insererVol("SI113", "Moyen Courrier",
                "08/05/2026", "08/05/2026", "230");
        // Paris CDG → Le Caire CAI
        mongo.insererVol("SI117", "Moyen Courrier",
                "20/05/2026", "20/05/2026", "280");
        // Paris CDG → Dakar DSS
        mongo.insererVol("SI118", "Moyen Courrier",
                "25/05/2026", "25/05/2026", "350");

        // ---- COURT COURRIER (5 vols) ----
        // Paris CDG → Londres LHR
        mongo.insererVol("SI107", "Court Courrier",
                "25/04/2026", "25/04/2026", "120");
        // Paris CDG → Amsterdam AMS
        mongo.insererVol("SI108", "Court Courrier",
                "27/04/2026", "27/04/2026", "95");
        // Paris CDG → Barcelone BCN
        mongo.insererVol("SI110", "Court Courrier",
                "03/05/2026", "03/05/2026", "110");
        // Paris CDG → Rome FCO
        mongo.insererVol("SI111", "Court Courrier",
                "05/05/2026", "05/05/2026", "130");
        // Paris CDG → Berlin BER
        mongo.insererVol("SI114", "Court Courrier",
                "09/05/2026", "09/05/2026", "105");

        // ═══════════════════════════════════════════════════════════════════
        //                    RÉSERVATIONS (45)
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n📋 Insertion de 45 réservations...");

        // --- Vol SI101 CDG→JFK (480€) — 5 réservations ---
        mongo.insererReservation("RES-001", "P001", "SI101", "10/04/2026", "480");
        mongo.insererReservation("RES-002", "P015", "SI101", "11/04/2026", "480");
        mongo.insererReservation("RES-003", "P016", "SI101", "12/04/2026", "480");
        mongo.insererReservation("RES-004", "P018", "SI101", "13/04/2026", "480");
        mongo.insererReservation("RES-005", "P007", "SI101", "14/04/2026", "480");

        // --- Vol SI102 CDG→NRT (850€) — 3 réservations ---
        mongo.insererReservation("RES-006", "P019", "SI102", "15/04/2026", "850");
        mongo.insererReservation("RES-007", "P021", "SI102", "15/04/2026", "850");
        mongo.insererReservation("RES-008", "P003", "SI102", "16/04/2026", "850");

        // --- Vol SI103 CDG→DXB (520€) — 3 réservations ---
        mongo.insererReservation("RES-009", "P024", "SI103", "18/04/2026", "520");
        mongo.insererReservation("RES-010", "P022", "SI103", "19/04/2026", "520");
        mongo.insererReservation("RES-011", "P002", "SI103", "20/04/2026", "520");

        // --- Vol SI104 CDG→GRU (720€) — 2 réservations ---
        mongo.insererReservation("RES-012", "P027", "SI104", "22/04/2026", "720");
        mongo.insererReservation("RES-013", "P028", "SI104", "23/04/2026", "720");

        // --- Vol SI105 CDG→IST (250€) — 3 réservations ---
        mongo.insererReservation("RES-014", "P004", "SI105", "14/04/2026", "250");
        mongo.insererReservation("RES-015", "P005", "SI105", "14/04/2026", "250");
        mongo.insererReservation("RES-016", "P006", "SI105", "15/04/2026", "250");

        // --- Vol SI106 CDG→RAK (180€) — 2 réservations ---
        mongo.insererReservation("RES-017", "P008", "SI106", "17/04/2026", "180");
        mongo.insererReservation("RES-018", "P025", "SI106", "18/04/2026", "180");

        // --- Vol SI107 CDG→LHR (120€) — 4 réservations ---
        mongo.insererReservation("RES-019", "P010", "SI107", "16/04/2026", "120");
        mongo.insererReservation("RES-020", "P012", "SI107", "17/04/2026", "120");
        mongo.insererReservation("RES-021", "P017", "SI107", "18/04/2026", "120");
        mongo.insererReservation("RES-022", "P030", "SI107", "19/04/2026", "120");

        // --- Vol SI108 CDG→AMS (95€) — 2 réservations ---
        mongo.insererReservation("RES-023", "P012", "SI108", "20/04/2026", "95");
        mongo.insererReservation("RES-024", "P013", "SI108", "20/04/2026", "95");

        // --- Vol SI109 CDG→ATH (220€) — 2 réservations ---
        mongo.insererReservation("RES-025", "P009", "SI109", "22/04/2026", "220");
        mongo.insererReservation("RES-026", "P011", "SI109", "23/04/2026", "220");

        // --- Vol SI110 CDG→BCN (110€) — 3 réservations ---
        mongo.insererReservation("RES-027", "P011", "SI110", "25/04/2026", "110");
        mongo.insererReservation("RES-028", "P014", "SI110", "26/04/2026", "110");
        mongo.insererReservation("RES-029", "P029", "SI110", "26/04/2026", "110");

        // --- Vol SI111 CDG→FCO (130€) — 2 réservations ---
        mongo.insererReservation("RES-030", "P009", "SI111", "27/04/2026", "130");
        mongo.insererReservation("RES-031", "P006", "SI111", "28/04/2026", "130");

        // --- Vol SI112 CDG→LIS (195€) — 2 réservations ---
        mongo.insererReservation("RES-032", "P014", "SI112", "28/04/2026", "195");
        mongo.insererReservation("RES-033", "P001", "SI112", "29/04/2026", "195");

        // --- Vol SI113 CDG→ARN (230€) — 1 réservation ---
        mongo.insererReservation("RES-034", "P013", "SI113", "01/05/2026", "230");

        // --- Vol SI114 CDG→BER (105€) — 2 réservations ---
        mongo.insererReservation("RES-035", "P010", "SI114", "02/05/2026", "105");
        mongo.insererReservation("RES-036", "P008", "SI114", "03/05/2026", "105");

        // --- Vol SI115 CDG→ICN (890€) — 2 réservations ---
        mongo.insererReservation("RES-037", "P020", "SI115", "01/05/2026", "890");
        mongo.insererReservation("RES-038", "P023", "SI115", "02/05/2026", "890");

        // --- Vol SI116 CDG→BOM (580€) — 2 réservations ---
        mongo.insererReservation("RES-039", "P022", "SI116", "05/05/2026", "580");
        mongo.insererReservation("RES-040", "P021", "SI116", "06/05/2026", "580");

        // --- Vol SI117 CDG→CAI (280€) — 1 réservation ---
        mongo.insererReservation("RES-041", "P024", "SI117", "10/05/2026", "280");

        // --- Vol SI118 CDG→DSS (350€) — 2 réservations ---
        mongo.insererReservation("RES-042", "P025", "SI118", "15/05/2026", "350");
        mongo.insererReservation("RES-043", "P005", "SI118", "16/05/2026", "350");

        // --- Vol SI119 CDG→SYD (1100€) — 1 réservation ---
        mongo.insererReservation("RES-044", "P030", "SI119", "20/05/2026", "1100");

        // --- Vol SI120 CDG→LOS (490€) — 1 réservation ---
        mongo.insererReservation("RES-045", "P026", "SI120", "25/05/2026", "490");

        // ═══════════════════════════════════════════════════════════════════
        //                       VÉRIFICATION
        // ═══════════════════════════════════════════════════════════════════
        System.out.println("\n═══════════════════════════════════════════════════════");
        System.out.println("  ✅ RÉSUMÉ DE L'INITIALISATION");
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("  Passagers    : " + mongo.compterPassagers());
        System.out.println("  Vols         : " + mongo.compterVols());
        System.out.println("  Réservations : 45");
        System.out.println("═══════════════════════════════════════════════════════");

        System.out.println("\n📋 Liste des passagers :");
        mongo.listerPassagers().forEach(p ->
                System.out.println("  " + p.getId() + " | " + p.getNom() + " " + p.getPrenom()
                        + " | " + p.getEmail() + " | Passeport: " + p.getNumeroPasseport()));

        System.out.println("\n✈️  Liste des vols :");
        mongo.listerVols().forEach(v -> System.out.println("  " + v));

        mongo.fermerConnexion();
        System.out.println("\n🎉 Initialisation terminée — SkyISEP Airlines prête à voler !");
    }
}
