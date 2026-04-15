package com.isep.airline;

import com.isep.airline.model.*;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale de l'application.
 * Interface console interactive pour la gestion de la compagnie aérienne.
 */
public class Main {

    private static CompagnieAerienne compagnie;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        compagnie = new CompagnieAerienne("SkyISEP Airlines", "SI");

        // Charger des données de démonstration
        chargerDonneesDemonstration();

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║  Bienvenue dans le système de réservation de    ║");
        System.out.println("║          " + compagnie.getNom() + "              ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        boolean quitter = false;
        while (!quitter) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> menuPassagers();
                case 2 -> menuEmployes();
                case 3 -> menuVols();
                case 4 -> menuReservations();
                case 5 -> menuAvions();
                case 6 -> menuEquipages();
                case 7 -> compagnie.afficherRapport();
                case 0 -> {
                    quitter = true;
                    System.out.println("Merci d'avoir utilisé notre système. Au revoir !");
                }
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
        scanner.close();
    }

    // ======================== MENUS ========================

    private static void afficherMenuPrincipal() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Gestion des Passagers");
        System.out.println("2. Gestion des Employés");
        System.out.println("3. Gestion des Vols");
        System.out.println("4. Gestion des Réservations");
        System.out.println("5. Gestion des Avions");
        System.out.println("6. Gestion des Équipages");
        System.out.println("7. Statistiques et Rapports");
        System.out.println("0. Quitter");
        System.out.println("====================================");
    }

    // ======================== MENU PASSAGERS ========================

    private static void menuPassagers() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Passagers ---");
            System.out.println("1. Ajouter un passager");
            System.out.println("2. Rechercher un passager");
            System.out.println("3. Modifier un passager");
            System.out.println("4. Supprimer un passager");
            System.out.println("5. Lister tous les passagers");
            System.out.println("6. Afficher les infos d'un passager");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String id = lireChaine("ID du passager : ");
                    String nom = lireChaine("Nom : ");
                    String prenom = lireChaine("Prénom : ");
                    String email = lireChaine("Email : ");
                    String tel = lireChaine("Téléphone : ");
                    String passeport = lireChaine("N° Passeport : ");
                    Passager p = new Passager(id, nom, prenom, email, tel, passeport);
                    compagnie.ajouterPassager(p);
                }
                case 2 -> {
                    String id = lireChaine("ID du passager : ");
                    Passager p = compagnie.rechercherPassager(id);
                    if (p != null) {
                        System.out.println("Trouvé : " + p);
                    } else {
                        System.out.println("Passager introuvable.");
                    }
                }
                case 3 -> {
                    String id = lireChaine("ID du passager à modifier : ");
                    String nom = lireChaine("Nouveau nom (vide pour ne pas modifier) : ");
                    String prenom = lireChaine("Nouveau prénom (vide pour ne pas modifier) : ");
                    String email = lireChaine("Nouvel email (vide pour ne pas modifier) : ");
                    String tel = lireChaine("Nouveau téléphone (vide pour ne pas modifier) : ");
                    String passeport = lireChaine("Nouveau passeport (vide pour ne pas modifier) : ");
                    compagnie.modifierPassager(id, nom, prenom, email, tel, passeport);
                }
                case 4 -> {
                    String id = lireChaine("ID du passager à supprimer : ");
                    compagnie.supprimerPassager(id);
                }
                case 5 -> {
                    List<Passager> passagers = compagnie.listerPassagers();
                    if (passagers.isEmpty()) {
                        System.out.println("Aucun passager enregistré.");
                    } else {
                        System.out.println("Liste des passagers (" + passagers.size() + ") :");
                        for (Passager p : passagers) {
                            System.out.println("  " + p);
                        }
                    }
                }
                case 6 -> {
                    String id = lireChaine("ID du passager : ");
                    Passager p = compagnie.rechercherPassager(id);
                    if (p != null) {
                        p.obtenirInfos();
                    } else {
                        System.out.println("Passager introuvable.");
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== MENU EMPLOYES ========================

    private static void menuEmployes() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Employés ---");
            System.out.println("1. Ajouter un pilote");
            System.out.println("2. Ajouter un personnel de cabine");
            System.out.println("3. Rechercher un employé");
            System.out.println("4. Obtenir le rôle d'un employé");
            System.out.println("5. Modifier un employé");
            System.out.println("6. Supprimer un employé");
            System.out.println("7. Lister tous les employés");
            System.out.println("8. Afficher les infos d'un employé");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String id = lireChaine("ID : ");
                    String nom = lireChaine("Nom : ");
                    String prenom = lireChaine("Prénom : ");
                    String email = lireChaine("Email : ");
                    String tel = lireChaine("Téléphone : ");
                    String numEmp = lireChaine("N° Employé : ");
                    double salaire = lireDouble("Salaire : ");
                    String licence = lireChaine("Licence : ");
                    int heuresVol = lireEntier("Heures de vol : ");
                    Pilote pilote = new Pilote(id, nom, prenom, email, tel, numEmp, salaire, licence, heuresVol);
                    compagnie.ajouterEmploye(pilote);
                }
                case 2 -> {
                    String id = lireChaine("ID : ");
                    String nom = lireChaine("Nom : ");
                    String prenom = lireChaine("Prénom : ");
                    String email = lireChaine("Email : ");
                    String tel = lireChaine("Téléphone : ");
                    String numEmp = lireChaine("N° Employé : ");
                    double salaire = lireDouble("Salaire : ");
                    String qualification = lireChaine("Qualification : ");
                    int experience = lireEntier("Années d'expérience : ");
                    PersonnelCabine pc = new PersonnelCabine(id, nom, prenom, email, tel, numEmp, salaire, qualification, experience);
                    compagnie.ajouterEmploye(pc);
                }
                case 3 -> {
                    String id = lireChaine("ID de l'employé : ");
                    Employe e = compagnie.rechercherEmploye(id);
                    if (e != null) {
                        System.out.println("Trouvé : " + e);
                    } else {
                        System.out.println("Employé introuvable.");
                    }
                }
                case 4 -> {
                    String id = lireChaine("ID de l'employé : ");
                    String role = compagnie.obtenirRoleEmploye(id);
                    if (role != null) {
                        System.out.println("Rôle : " + role);
                    }
                }
                case 5 -> {
                    String id = lireChaine("ID de l'employé à modifier : ");
                    String nom = lireChaine("Nouveau nom (vide pour ne pas modifier) : ");
                    String prenom = lireChaine("Nouveau prénom (vide pour ne pas modifier) : ");
                    String email = lireChaine("Nouvel email (vide pour ne pas modifier) : ");
                    String tel = lireChaine("Nouveau téléphone (vide pour ne pas modifier) : ");
                    compagnie.modifierEmploye(id, nom, prenom, email, tel);
                }
                case 6 -> {
                    String id = lireChaine("ID de l'employé à supprimer : ");
                    compagnie.supprimerEmploye(id);
                }
                case 7 -> {
                    List<Employe> employes = compagnie.listerEmployes();
                    if (employes.isEmpty()) {
                        System.out.println("Aucun employé enregistré.");
                    } else {
                        System.out.println("Liste des employés (" + employes.size() + ") :");
                        for (Employe e : employes) {
                            System.out.println("  " + e);
                        }
                    }
                }
                case 8 -> {
                    String id = lireChaine("ID de l'employé : ");
                    Employe e = compagnie.rechercherEmploye(id);
                    if (e != null) {
                        e.obtenirInfos();
                    } else {
                        System.out.println("Employé introuvable.");
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== MENU VOLS ========================

    private static void menuVols() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Vols ---");
            System.out.println("1. Planifier un vol");
            System.out.println("2. Rechercher un vol");
            System.out.println("3. Obtenir les infos d'un vol");
            System.out.println("4. Modifier un vol");
            System.out.println("5. Annuler un vol");
            System.out.println("6. Affecter un équipage à un vol");
            System.out.println("7. Affecter un avion à un vol");
            System.out.println("8. Lister tous les vols");
            System.out.println("9. Lister les vols planifiés");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String numero = lireChaine("N° du vol : ");
                    String villeDepart = lireChaine("Ville de départ : ");
                    String villeArrivee = lireChaine("Ville d'arrivée : ");
                    String dateDepart = lireChaine("Date de départ (JJ/MM/AAAA) : ");
                    String dateArrivee = lireChaine("Date d'arrivée (JJ/MM/AAAA) : ");
                    String heureDepart = lireChaine("Heure de départ (HH:MM) : ");
                    String heureArrivee = lireChaine("Heure d'arrivée (HH:MM) : ");
                    double prix = lireDouble("Prix du billet (€) : ");
                    Vol vol = new Vol(numero, villeDepart, villeArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
                    compagnie.planifierVol(vol);
                }
                case 2 -> {
                    String numero = lireChaine("N° du vol : ");
                    Vol v = compagnie.rechercherVol(numero);
                    if (v != null) {
                        System.out.println("Trouvé : " + v);
                    } else {
                        System.out.println("Vol introuvable.");
                    }
                }
                case 3 -> {
                    String numero = lireChaine("N° du vol : ");
                    compagnie.obtenirVol(numero);
                }
                case 4 -> {
                    String numero = lireChaine("N° du vol à modifier : ");
                    String villeDepart = lireChaine("Nouvelle ville de départ (vide pour ne pas modifier) : ");
                    String villeArrivee = lireChaine("Nouvelle ville d'arrivée (vide pour ne pas modifier) : ");
                    String dateDepart = lireChaine("Nouvelle date de départ (vide pour ne pas modifier) : ");
                    String dateArrivee = lireChaine("Nouvelle date d'arrivée (vide pour ne pas modifier) : ");
                    String heureDepart = lireChaine("Nouvelle heure de départ (vide pour ne pas modifier) : ");
                    String heureArrivee = lireChaine("Nouvelle heure d'arrivée (vide pour ne pas modifier) : ");
                    double prix = lireDouble("Nouveau prix (0 pour ne pas modifier) : ");
                    compagnie.modifierVol(numero, villeDepart, villeArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix);
                }
                case 5 -> {
                    String numero = lireChaine("N° du vol à annuler : ");
                    compagnie.annulerVol(numero);
                }
                case 6 -> {
                    String numero = lireChaine("N° du vol : ");
                    String idEquipage = lireChaine("ID de l'équipage : ");
                    compagnie.affecterEquipageAuVol(numero, idEquipage);
                }
                case 7 -> {
                    String numero = lireChaine("N° du vol : ");
                    String immat = lireChaine("Immatriculation de l'avion : ");
                    compagnie.affecterAvionAuVol(numero, immat);
                }
                case 8 -> {
                    List<Vol> volsList = compagnie.listerVols();
                    if (volsList.isEmpty()) {
                        System.out.println("Aucun vol enregistré.");
                    } else {
                        System.out.println("Liste des vols (" + volsList.size() + ") :");
                        for (Vol v : volsList) {
                            System.out.println("  " + v);
                        }
                    }
                }
                case 9 -> {
                    List<Vol> volsPlanifies = compagnie.listerVolsPlanifies();
                    if (volsPlanifies.isEmpty()) {
                        System.out.println("Aucun vol planifié.");
                    } else {
                        System.out.println("Vols planifiés (" + volsPlanifies.size() + ") :");
                        for (Vol v : volsPlanifies) {
                            System.out.println("  " + v);
                        }
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== MENU RESERVATIONS ========================

    private static void menuReservations() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Réservations ---");
            System.out.println("1. Réserver un vol");
            System.out.println("2. Annuler une réservation");
            System.out.println("3. Obtenir les infos d'une réservation");
            System.out.println("4. Lister les réservations d'un passager");
            System.out.println("5. Lister toutes les réservations");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String idPassager = lireChaine("ID du passager : ");
                    String numVol = lireChaine("N° du vol : ");
                    compagnie.reserverVol(idPassager, numVol);
                }
                case 2 -> {
                    String numRes = lireChaine("N° de la réservation : ");
                    compagnie.annulerReservation(numRes);
                }
                case 3 -> {
                    String numRes = lireChaine("N° de la réservation : ");
                    compagnie.obtenirReservation(numRes);
                }
                case 4 -> {
                    String idPassager = lireChaine("ID du passager : ");
                    Passager p = compagnie.rechercherPassager(idPassager);
                    if (p != null) {
                        List<Reservation> reservations = p.obtenirReservations();
                        if (reservations.isEmpty()) {
                            System.out.println("Aucune réservation pour ce passager.");
                        } else {
                            System.out.println("Réservations de " + p.getNom() + " " + p.getPrenom() + " :");
                            for (Reservation r : reservations) {
                                System.out.println("  " + r);
                            }
                        }
                    } else {
                        System.out.println("Passager introuvable.");
                    }
                }
                case 5 -> {
                    List<Reservation> reservations = compagnie.listerReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("Aucune réservation.");
                    } else {
                        System.out.println("Liste des réservations (" + reservations.size() + ") :");
                        for (Reservation r : reservations) {
                            System.out.println("  " + r);
                        }
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== MENU AVIONS ========================

    private static void menuAvions() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Avions ---");
            System.out.println("1. Ajouter un avion");
            System.out.println("2. Rechercher un avion");
            System.out.println("3. Modifier un avion");
            System.out.println("4. Supprimer un avion");
            System.out.println("5. Vérifier la disponibilité d'un avion");
            System.out.println("6. Lister tous les avions");
            System.out.println("7. Lister les avions disponibles");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String immat = lireChaine("Immatriculation : ");
                    String modele = lireChaine("Modèle : ");
                    int capacite = lireEntier("Capacité (places) : ");
                    Avion avion = new Avion(immat, modele, capacite);
                    compagnie.ajouterAvion(avion);
                }
                case 2 -> {
                    String immat = lireChaine("Immatriculation : ");
                    Avion a = compagnie.rechercherAvion(immat);
                    if (a != null) {
                        a.obtenirInfos();
                    } else {
                        System.out.println("Avion introuvable.");
                    }
                }
                case 3 -> {
                    String immat = lireChaine("Immatriculation de l'avion à modifier : ");
                    String modele = lireChaine("Nouveau modèle (vide pour ne pas modifier) : ");
                    int capacite = lireEntier("Nouvelle capacité (0 pour ne pas modifier) : ");
                    compagnie.modifierAvion(immat, modele, capacite);
                }
                case 4 -> {
                    String immat = lireChaine("Immatriculation de l'avion à supprimer : ");
                    compagnie.supprimerAvion(immat);
                }
                case 5 -> {
                    String immat = lireChaine("Immatriculation : ");
                    Avion a = compagnie.rechercherAvion(immat);
                    if (a != null) {
                        String dateDepart = lireChaine("Date de départ prévue : ");
                        String dateArrivee = lireChaine("Date d'arrivée prévue : ");
                        boolean dispo = a.verifierDisponibilite(dateDepart, dateArrivee);
                        System.out.println("L'avion " + immat + " est " + (dispo ? "DISPONIBLE" : "INDISPONIBLE") + " pour ces dates.");
                    } else {
                        System.out.println("Avion introuvable.");
                    }
                }
                case 6 -> {
                    List<Avion> avionsList = compagnie.listerAvions();
                    if (avionsList.isEmpty()) {
                        System.out.println("Aucun avion enregistré.");
                    } else {
                        System.out.println("Liste des avions (" + avionsList.size() + ") :");
                        for (Avion a : avionsList) {
                            System.out.println("  " + a);
                        }
                    }
                }
                case 7 -> {
                    List<Avion> disponibles = compagnie.listerAvionsDisponibles();
                    if (disponibles.isEmpty()) {
                        System.out.println("Aucun avion disponible.");
                    } else {
                        System.out.println("Avions disponibles (" + disponibles.size() + ") :");
                        for (Avion a : disponibles) {
                            System.out.println("  " + a);
                        }
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== MENU EQUIPAGES ========================

    private static void menuEquipages() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Équipages ---");
            System.out.println("1. Créer un équipage");
            System.out.println("2. Ajouter du personnel cabine à un équipage");
            System.out.println("3. Afficher les infos d'un équipage");
            System.out.println("4. Supprimer un équipage");
            System.out.println("5. Lister tous les équipages");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String idEquipage = lireChaine("ID de l'équipage : ");
                    String idPilote = lireChaine("ID du pilote : ");
                    Employe emp = compagnie.rechercherEmploye(idPilote);
                    if (emp instanceof Pilote pilote) {
                        Equipage equipage = new Equipage(idEquipage, pilote);
                        compagnie.ajouterEquipage(equipage);
                    } else {
                        System.out.println("Erreur : pilote introuvable ou l'employé n'est pas un pilote.");
                    }
                }
                case 2 -> {
                    String idEquipage = lireChaine("ID de l'équipage : ");
                    Equipage equipage = compagnie.rechercherEquipage(idEquipage);
                    if (equipage != null) {
                        String idPersonnel = lireChaine("ID du personnel cabine : ");
                        Employe emp = compagnie.rechercherEmploye(idPersonnel);
                        if (emp instanceof PersonnelCabine pc) {
                            equipage.ajouterPersonnelCabine(pc);
                        } else {
                            System.out.println("Erreur : personnel cabine introuvable.");
                        }
                    } else {
                        System.out.println("Équipage introuvable.");
                    }
                }
                case 3 -> {
                    String idEquipage = lireChaine("ID de l'équipage : ");
                    Equipage equipage = compagnie.rechercherEquipage(idEquipage);
                    if (equipage != null) {
                        equipage.obtenirInfos();
                    } else {
                        System.out.println("Équipage introuvable.");
                    }
                }
                case 4 -> {
                    String idEquipage = lireChaine("ID de l'équipage à supprimer : ");
                    compagnie.supprimerEquipage(idEquipage);
                }
                case 5 -> {
                    List<Equipage> equipagesList = compagnie.listerEquipages();
                    if (equipagesList.isEmpty()) {
                        System.out.println("Aucun équipage enregistré.");
                    } else {
                        System.out.println("Liste des équipages (" + equipagesList.size() + ") :");
                        for (Equipage eq : equipagesList) {
                            System.out.println("  " + eq);
                        }
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== DONNEES DE DEMONSTRATION ========================

    /**
     * Charge des données de démonstration pour tester l'application.
     */
    private static void chargerDonneesDemonstration() {
        // Avions
        compagnie.ajouterAvion(new Avion("F-GKXA", "Airbus A320", 180));
        compagnie.ajouterAvion(new Avion("F-GKXB", "Boeing 737", 160));
        compagnie.ajouterAvion(new Avion("F-GKXC", "Airbus A350", 300));

        // Pilotes
        Pilote pilote1 = new Pilote("E001", "Dupont", "Jean", "j.dupont@skyisep.com", "0601020304", "EMP001", 8000, "ATPL-001", 5000);
        Pilote pilote2 = new Pilote("E002", "Martin", "Pierre", "p.martin@skyisep.com", "0605060708", "EMP002", 7500, "ATPL-002", 3000);
        compagnie.ajouterEmploye(pilote1);
        compagnie.ajouterEmploye(pilote2);

        // Personnel de cabine
        PersonnelCabine pc1 = new PersonnelCabine("E003", "Lemoine", "Sophie", "s.lemoine@skyisep.com", "0611121314", "EMP003", 3500, "Chef de cabine", 10);
        PersonnelCabine pc2 = new PersonnelCabine("E004", "Bernard", "Marie", "m.bernard@skyisep.com", "0615161718", "EMP004", 3000, "Hôtesse", 5);
        PersonnelCabine pc3 = new PersonnelCabine("E005", "Petit", "Luc", "l.petit@skyisep.com", "0619202122", "EMP005", 3000, "Steward", 3);
        compagnie.ajouterEmploye(pc1);
        compagnie.ajouterEmploye(pc2);
        compagnie.ajouterEmploye(pc3);

        // Équipages
        Equipage equipage1 = new Equipage("EQ001", pilote1);
        equipage1.ajouterPersonnelCabine(pc1);
        equipage1.ajouterPersonnelCabine(pc2);
        compagnie.ajouterEquipage(equipage1);

        Equipage equipage2 = new Equipage("EQ002", pilote2);
        equipage2.ajouterPersonnelCabine(pc3);
        compagnie.ajouterEquipage(equipage2);

        // Passagers
        compagnie.ajouterPassager(new Passager("P001", "Moreau", "Alice", "alice.moreau@email.com", "0631323334", "FR123456"));
        compagnie.ajouterPassager(new Passager("P002", "Durand", "Bob", "bob.durand@email.com", "0635363738", "FR654321"));
        compagnie.ajouterPassager(new Passager("P003", "Garcia", "Carlos", "carlos.garcia@email.com", "0639404142", "ES789012"));

        // Vols
        Vol vol1 = new Vol("SI101", "Paris", "New York", "20/04/2025", "20/04/2025", "08:00", "12:00", 450.0);
        Vol vol2 = new Vol("SI102", "Paris", "Tokyo", "21/04/2025", "22/04/2025", "10:30", "06:30", 800.0);
        Vol vol3 = new Vol("SI103", "Paris", "Londres", "22/04/2025", "22/04/2025", "14:00", "15:30", 120.0);
        Vol vol4 = new Vol("SI104", "Paris", "New York", "23/04/2025", "23/04/2025", "09:00", "13:00", 480.0);
        compagnie.planifierVol(vol1);
        compagnie.planifierVol(vol2);
        compagnie.planifierVol(vol3);
        compagnie.planifierVol(vol4);

        // Affectation avions et équipages aux vols
        compagnie.affecterAvionAuVol("SI101", "F-GKXA");
        compagnie.affecterAvionAuVol("SI102", "F-GKXC");
        compagnie.affecterAvionAuVol("SI103", "F-GKXB");
        compagnie.affecterEquipageAuVol("SI101", "EQ001");
        compagnie.affecterEquipageAuVol("SI102", "EQ002");

        // Quelques réservations
        compagnie.reserverVol("P001", "SI101");
        compagnie.reserverVol("P002", "SI101");
        compagnie.reserverVol("P003", "SI102");

        System.out.println("\n✓ Données de démonstration chargées avec succès !\n");
    }

    // ======================== UTILITAIRES ========================

    private static String lireChaine(String message) {
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    private static int lireEntier(String message) {
        System.out.print(message);
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        int val = scanner.nextInt();
        scanner.nextLine(); // consomme le retour à la ligne
        return val;
    }

    private static double lireDouble(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine(); // consomme le retour à la ligne
        return val;
    }
}
