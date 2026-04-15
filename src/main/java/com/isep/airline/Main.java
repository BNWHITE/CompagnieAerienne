package com.isep.airline;

import com.isep.airline.model.*;

import java.util.List;
import java.util.Scanner;

/**
 * Classe principale de l'application.
 * Interface console interactive pour la gestion de la compagnie aerienne.
 */
public class Main {

    private static CompagnieAerienne compagnie;
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        compagnie = new CompagnieAerienne("SkyISEP Airlines", "SI");

        // Charger des donnees de demonstration
        chargerDonneesDemonstration();

        System.out.println("==================================================");
        System.out.println("  Bienvenue dans le systeme de reservation de");
        System.out.println("          " + compagnie.getNom());
        System.out.println("==================================================");

        boolean quitter = false;
        while (!quitter) {
            afficherMenuPrincipal();
            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> menuAeroports();
                case 2 -> menuPassagers();
                case 3 -> menuEmployes();
                case 4 -> menuVols();
                case 5 -> menuReservations();
                case 6 -> menuAvions();
                case 7 -> menuEquipages();
                case 8 -> compagnie.afficherRapport();
                case 9 -> demonstrationPolymorphisme();
                case 0 -> {
                    quitter = true;
                    System.out.println("Merci d'avoir utilise notre systeme. Au revoir !");
                }
                default -> System.out.println("Choix invalide. Veuillez reessayer.");
            }
        }
        scanner.close();
    }

    // ======================== MENUS ========================

    private static void afficherMenuPrincipal() {
        System.out.println("\n========== MENU PRINCIPAL ==========");
        System.out.println("1. Gestion des Aeroports");
        System.out.println("2. Gestion des Passagers");
        System.out.println("3. Gestion des Employes");
        System.out.println("4. Gestion des Vols");
        System.out.println("5. Gestion des Reservations");
        System.out.println("6. Gestion des Avions");
        System.out.println("7. Gestion des Equipages");
        System.out.println("8. Statistiques et Rapports");
        System.out.println("9. Demonstration Polymorphisme");
        System.out.println("0. Quitter");
        System.out.println("====================================");
    }

    // ======================== MENU AEROPORTS ========================

    private static void menuAeroports() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- Gestion des Aeroports ---");
            System.out.println("1. Ajouter un aeroport");
            System.out.println("2. Rechercher un aeroport");
            System.out.println("3. Modifier un aeroport");
            System.out.println("4. Supprimer un aeroport");
            System.out.println("5. Lister tous les aeroports");
            System.out.println("6. Afficher les infos d'un aeroport");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String code = lireChaine("Code IATA (ex: CDG) : ");
                    String nom = lireChaine("Nom de l'aeroport : ");
                    String ville = lireChaine("Ville : ");
                    String pays = lireChaine("Pays : ");
                    Aeroport a = new Aeroport(code, nom, ville, pays);
                    compagnie.ajouterAeroport(a);
                }
                case 2 -> {
                    String code = lireChaine("Code IATA : ");
                    Aeroport a = compagnie.rechercherAeroport(code);
                    if (a != null) {
                        System.out.println(a.obtenirInformation());
                    } else {
                        System.out.println("Aeroport introuvable.");
                    }
                }
                case 3 -> {
                    String code = lireChaine("Code IATA de l'aeroport a modifier : ");
                    String nom = lireChaine("Nouveau nom (vide pour ne pas modifier) : ");
                    String ville = lireChaine("Nouvelle ville (vide pour ne pas modifier) : ");
                    String pays = lireChaine("Nouveau pays (vide pour ne pas modifier) : ");
                    compagnie.modifierAeroport(code, nom, ville, pays);
                }
                case 4 -> {
                    String code = lireChaine("Code IATA de l'aeroport a supprimer : ");
                    compagnie.supprimerAeroport(code);
                }
                case 5 -> {
                    List<Aeroport> aeroports = compagnie.listerAeroports();
                    if (aeroports.isEmpty()) {
                        System.out.println("Aucun aeroport enregistre.");
                    } else {
                        System.out.println("Liste des aeroports (" + aeroports.size() + ") :");
                        for (Aeroport a : aeroports) {
                            System.out.println("  " + a.getCodeIATA() + " - " + a.getNom() + " (" + a.getVille() + ", " + a.getPays() + ")");
                        }
                    }
                }
                case 6 -> {
                    String code = lireChaine("Code IATA : ");
                    Aeroport a = compagnie.rechercherAeroport(code);
                    if (a != null) {
                        System.out.println(a.obtenirInformation());
                    } else {
                        System.out.println("Aeroport introuvable.");
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
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
                    String prenom = lireChaine("Prenom : ");
                    String email = lireChaine("Email : ");
                    String tel = lireChaine("Telephone : ");
                    String passeport = lireChaine("N Passeport : ");
                    Passager p = new Passager(id, nom, prenom, email, tel, passeport);
                    compagnie.ajouterPassager(p);
                }
                case 2 -> {
                    String id = lireChaine("ID du passager : ");
                    Passager p = compagnie.rechercherPassager(id);
                    if (p != null) {
                        System.out.println("Trouve : " + p.getNom() + " " + p.getPrenom());
                    } else {
                        System.out.println("Passager introuvable.");
                    }
                }
                case 3 -> {
                    String id = lireChaine("ID du passager a modifier : ");
                    String nom = lireChaine("Nouveau nom (vide pour ne pas modifier) : ");
                    String prenom = lireChaine("Nouveau prenom (vide pour ne pas modifier) : ");
                    String email = lireChaine("Nouvel email (vide pour ne pas modifier) : ");
                    String tel = lireChaine("Nouveau telephone (vide pour ne pas modifier) : ");
                    String passeport = lireChaine("Nouveau passeport (vide pour ne pas modifier) : ");
                    compagnie.modifierPassager(id, nom, prenom, email, tel, passeport);
                }
                case 4 -> {
                    String id = lireChaine("ID du passager a supprimer : ");
                    compagnie.supprimerPassager(id);
                }
                case 5 -> {
                    List<Passager> passagers = compagnie.listerPassagers();
                    if (passagers.isEmpty()) {
                        System.out.println("Aucun passager enregistre.");
                    } else {
                        System.out.println("Liste des passagers (" + passagers.size() + ") :");
                        for (Passager p : passagers) {
                            System.out.println("  " + p.getId() + " - " + p.getNom() + " " + p.getPrenom());
                        }
                    }
                }
                case 6 -> {
                    String id = lireChaine("ID du passager : ");
                    Passager p = compagnie.rechercherPassager(id);
                    if (p != null) {
                        System.out.println(p.obtenirInformation());
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
            System.out.println("\n--- Gestion des Employes ---");
            System.out.println("1. Ajouter un pilote");
            System.out.println("2. Ajouter un personnel de cabine");
            System.out.println("3. Rechercher un employe");
            System.out.println("4. Obtenir le role d'un employe");
            System.out.println("5. Modifier un employe");
            System.out.println("6. Supprimer un employe");
            System.out.println("7. Lister tous les employes");
            System.out.println("8. Afficher les infos d'un employe");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String id = lireChaine("ID : ");
                    String nom = lireChaine("Nom : ");
                    String prenom = lireChaine("Prenom : ");
                    String email = lireChaine("Email : ");
                    String tel = lireChaine("Telephone : ");
                    String numEmp = lireChaine("N Employe : ");
                    String salaire = lireChaine("Salaire : ");
                    String licence = lireChaine("Licence : ");
                    String heuresVol = lireChaine("Heures de vol : ");
                    Pilote pilote = new Pilote(id, nom, prenom, email, tel, numEmp, salaire, licence, heuresVol);
                    compagnie.ajouterEmploye(pilote);
                }
                case 2 -> {
                    String id = lireChaine("ID : ");
                    String nom = lireChaine("Nom : ");
                    String prenom = lireChaine("Prenom : ");
                    String email = lireChaine("Email : ");
                    String tel = lireChaine("Telephone : ");
                    String numEmp = lireChaine("N Employe : ");
                    String salaire = lireChaine("Salaire : ");
                    String qualification = lireChaine("Qualification : ");
                    String experience = lireChaine("Annees d'experience : ");
                    PersonnelCabine pc = new PersonnelCabine(id, nom, prenom, email, tel, numEmp, salaire, qualification, experience);
                    compagnie.ajouterEmploye(pc);
                }
                case 3 -> {
                    String id = lireChaine("ID de l'employe : ");
                    Employe e = compagnie.rechercherEmploye(id);
                    if (e != null) {
                        System.out.println("Trouve : " + e.getNom() + " " + e.getPrenom());
                    } else {
                        System.out.println("Employe introuvable.");
                    }
                }
                case 4 -> {
                    String id = lireChaine("ID de l'employe : ");
                    String role = compagnie.obtenirRoleEmploye(id);
                    if (role != null) {
                        System.out.println("Role : " + role);
                    }
                }
                case 5 -> {
                    String id = lireChaine("ID de l'employe a modifier : ");
                    String nom = lireChaine("Nouveau nom (vide pour ne pas modifier) : ");
                    String prenom = lireChaine("Nouveau prenom (vide pour ne pas modifier) : ");
                    String email = lireChaine("Nouvel email (vide pour ne pas modifier) : ");
                    String tel = lireChaine("Nouveau telephone (vide pour ne pas modifier) : ");
                    compagnie.modifierEmploye(id, nom, prenom, email, tel);
                }
                case 6 -> {
                    String id = lireChaine("ID de l'employe a supprimer : ");
                    compagnie.supprimerEmploye(id);
                }
                case 7 -> {
                    List<Employe> employes = compagnie.listerEmployes();
                    if (employes.isEmpty()) {
                        System.out.println("Aucun employe enregistre.");
                    } else {
                        System.out.println("Liste des employes (" + employes.size() + ") :");
                        for (Employe e : employes) {
                            System.out.println("  " + e.getId() + " - " + e.getNom() + " " + e.getPrenom() + " (" + e.obtenirRole() + ")");
                        }
                    }
                }
                case 8 -> {
                    String id = lireChaine("ID de l'employe : ");
                    Employe e = compagnie.rechercherEmploye(id);
                    if (e != null) {
                        System.out.println(e.obtenirInformation());
                    } else {
                        System.out.println("Employe introuvable.");
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
            System.out.println("4. Annuler un vol");
            System.out.println("5. Affecter un equipage a un vol");
            System.out.println("6. Affecter un avion a un vol");
            System.out.println("7. Lister tous les vols");
            System.out.println("8. Lister les vols planifies");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String numero = lireChaine("N du vol : ");

                    // Choix du type de vol
                    System.out.println("Type de vol :");
                    System.out.println("  1. Court Courrier (< 1500 km)");
                    System.out.println("  2. Moyen Courrier (1500 - 4000 km)");
                    System.out.println("  3. Long Courrier (> 4000 km)");
                    int typeVol = lireEntier("Choix du type : ");

                    // Choix des aeroports
                    System.out.println("Aeroports disponibles :");
                    List<Aeroport> aeroports = compagnie.listerAeroports();
                    for (Aeroport a : aeroports) {
                        System.out.println("  " + a.getCodeIATA() + " - " + a.getNom());
                    }
                    String codeDepart = lireChaine("Code IATA depart : ");
                    String codeArrivee = lireChaine("Code IATA arrivee : ");
                    Aeroport aeroportDepart = compagnie.rechercherAeroport(codeDepart);
                    Aeroport aeroportArrivee = compagnie.rechercherAeroport(codeArrivee);

                    if (aeroportDepart == null || aeroportArrivee == null) {
                        System.out.println("Erreur : aeroport introuvable. Creez d'abord les aeroports.");
                        break;
                    }

                    String dateDepart = lireChaine("Date de depart (JJ/MM/AAAA) : ");
                    String dateArrivee = lireChaine("Date d'arrivee (JJ/MM/AAAA) : ");
                    String heureDepart = lireChaine("Heure de depart (HH:MM) : ");
                    String heureArrivee = lireChaine("Heure d'arrivee (HH:MM) : ");
                    String prix = lireChaine("Prix du billet (EUR) : ");
                    String distance = lireChaine("Distance (km) : ");

                    Vol vol;
                    switch (typeVol) {
                        case 1 -> vol = new CourtCourrier(numero, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix, distance);
                        case 2 -> vol = new MoyenCourrier(numero, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix, distance);
                        case 3 -> vol = new LongCourrier(numero, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix, distance);
                        default -> {
                            System.out.println("Type invalide, vol cree en Court Courrier par defaut.");
                            vol = new CourtCourrier(numero, aeroportDepart, aeroportArrivee, dateDepart, dateArrivee, heureDepart, heureArrivee, prix, distance);
                        }
                    }
                    compagnie.planifierVol(vol);
                }
                case 2 -> {
                    String numero = lireChaine("N du vol : ");
                    Vol v = compagnie.rechercherVol(numero);
                    if (v != null) {
                        System.out.println("Trouve : " + v.getNumeroVol() + " (" + v.getTypeVol() + ")");
                    } else {
                        System.out.println("Vol introuvable.");
                    }
                }
                case 3 -> {
                    String numero = lireChaine("N du vol : ");
                    compagnie.obtenirVol(numero);
                }
                case 4 -> {
                    String numero = lireChaine("N du vol a annuler : ");
                    compagnie.annulerVol(numero);
                }
                case 5 -> {
                    String numero = lireChaine("N du vol : ");
                    String idEquipage = lireChaine("ID de l'equipage : ");
                    compagnie.affecterEquipageAuVol(numero, idEquipage);
                }
                case 6 -> {
                    String numero = lireChaine("N du vol : ");
                    String immat = lireChaine("Immatriculation de l'avion : ");
                    compagnie.affecterAvionAuVol(numero, immat);
                }
                case 7 -> {
                    List<Vol> volsList = compagnie.listerVols();
                    if (volsList.isEmpty()) {
                        System.out.println("Aucun vol enregistre.");
                    } else {
                        System.out.println("Liste des vols (" + volsList.size() + ") :");
                        for (Vol v : volsList) {
                            String dep = (v.getAeroportDepart() != null) ? v.getAeroportDepart().getCodeIATA() : "?";
                            String arr = (v.getAeroportArrivee() != null) ? v.getAeroportArrivee().getCodeIATA() : "?";
                            System.out.println("  " + v.getNumeroVol() + " | " + dep + " -> " + arr + " | " + v.getTypeVol() + " | " + v.getStatut());
                        }
                    }
                }
                case 8 -> {
                    List<Vol> volsPlanifies = compagnie.listerVolsPlanifies();
                    if (volsPlanifies.isEmpty()) {
                        System.out.println("Aucun vol planifie.");
                    } else {
                        System.out.println("Vols planifies (" + volsPlanifies.size() + ") :");
                        for (Vol v : volsPlanifies) {
                            String dep = (v.getAeroportDepart() != null) ? v.getAeroportDepart().getCodeIATA() : "?";
                            String arr = (v.getAeroportArrivee() != null) ? v.getAeroportArrivee().getCodeIATA() : "?";
                            System.out.println("  " + v.getNumeroVol() + " | " + dep + " -> " + arr + " | " + v.getTypeVol());
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
            System.out.println("\n--- Gestion des Reservations ---");
            System.out.println("1. Reserver un vol");
            System.out.println("2. Annuler une reservation");
            System.out.println("3. Obtenir les infos d'une reservation");
            System.out.println("4. Lister les reservations d'un passager");
            System.out.println("5. Lister toutes les reservations");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String idPassager = lireChaine("ID du passager : ");
                    String numVol = lireChaine("N du vol : ");
                    compagnie.reserverVol(idPassager, numVol);
                }
                case 2 -> {
                    String numRes = lireChaine("N de la reservation : ");
                    compagnie.annulerReservation(numRes);
                }
                case 3 -> {
                    String numRes = lireChaine("N de la reservation : ");
                    compagnie.obtenirReservation(numRes);
                }
                case 4 -> {
                    String idPassager = lireChaine("ID du passager : ");
                    Passager p = compagnie.rechercherPassager(idPassager);
                    if (p != null) {
                        List<Reservation> reservations = p.obtenirReservations();
                        if (reservations.isEmpty()) {
                            System.out.println("Aucune reservation pour ce passager.");
                        } else {
                            System.out.println("Reservations de " + p.getNom() + " " + p.getPrenom() + " :");
                            for (Reservation r : reservations) {
                                System.out.println("  " + r.getNumeroReservation() + " - " + r.getStatut());
                            }
                        }
                    } else {
                        System.out.println("Passager introuvable.");
                    }
                }
                case 5 -> {
                    List<Reservation> reservations = compagnie.listerReservations();
                    if (reservations.isEmpty()) {
                        System.out.println("Aucune reservation.");
                    } else {
                        System.out.println("Liste des reservations (" + reservations.size() + ") :");
                        for (Reservation r : reservations) {
                            System.out.println("  " + r.getNumeroReservation() + " | " + r.getPassager().getNom() + " | " + r.getStatut());
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
            System.out.println("5. Lister tous les avions");
            System.out.println("6. Lister les avions disponibles");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String immat = lireChaine("Immatriculation : ");
                    String modele = lireChaine("Modele : ");
                    int capacite = lireEntier("Capacite (places) : ");
                    Avion avion = new Avion(immat, modele, capacite);
                    compagnie.ajouterAvion(avion);
                }
                case 2 -> {
                    String immat = lireChaine("Immatriculation : ");
                    Avion a = compagnie.rechercherAvion(immat);
                    if (a != null) {
                        System.out.println(a.obtenirInformation());
                    } else {
                        System.out.println("Avion introuvable.");
                    }
                }
                case 3 -> {
                    String immat = lireChaine("Immatriculation de l'avion a modifier : ");
                    String modele = lireChaine("Nouveau modele (vide pour ne pas modifier) : ");
                    int capacite = lireEntier("Nouvelle capacite (0 pour ne pas modifier) : ");
                    compagnie.modifierAvion(immat, modele, capacite);
                }
                case 4 -> {
                    String immat = lireChaine("Immatriculation de l'avion a supprimer : ");
                    compagnie.supprimerAvion(immat);
                }
                case 5 -> {
                    List<Avion> avionsList = compagnie.listerAvions();
                    if (avionsList.isEmpty()) {
                        System.out.println("Aucun avion enregistre.");
                    } else {
                        System.out.println("Liste des avions (" + avionsList.size() + ") :");
                        for (Avion a : avionsList) {
                            System.out.println("  " + a.getImmatriculation() + " - " + a.getModele() + " (" + a.getCapacite() + " places)");
                        }
                    }
                }
                case 6 -> {
                    List<Avion> disponibles = compagnie.listerAvionsDisponibles();
                    if (disponibles.isEmpty()) {
                        System.out.println("Aucun avion disponible.");
                    } else {
                        System.out.println("Avions disponibles (" + disponibles.size() + ") :");
                        for (Avion a : disponibles) {
                            System.out.println("  " + a.getImmatriculation() + " - " + a.getModele());
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
            System.out.println("\n--- Gestion des Equipages ---");
            System.out.println("1. Creer un equipage");
            System.out.println("2. Ajouter du personnel cabine a un equipage");
            System.out.println("3. Afficher les infos d'un equipage");
            System.out.println("4. Supprimer un equipage");
            System.out.println("5. Lister tous les equipages");
            System.out.println("0. Retour");

            int choix = lireEntier("Votre choix : ");
            switch (choix) {
                case 1 -> {
                    String idEquipage = lireChaine("ID de l'equipage : ");
                    String idPilote = lireChaine("ID du pilote : ");
                    Employe emp = compagnie.rechercherEmploye(idPilote);
                    if (emp instanceof Pilote pilote) {
                        Equipage equipage = new Equipage(idEquipage, pilote);
                        compagnie.ajouterEquipage(equipage);
                    } else {
                        System.out.println("Erreur : pilote introuvable ou l'employe n'est pas un pilote.");
                    }
                }
                case 2 -> {
                    String idEquipage = lireChaine("ID de l'equipage : ");
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
                        System.out.println("Equipage introuvable.");
                    }
                }
                case 3 -> {
                    String idEquipage = lireChaine("ID de l'equipage : ");
                    Equipage equipage = compagnie.rechercherEquipage(idEquipage);
                    if (equipage != null) {
                        System.out.println(equipage.obtenirInformation());
                    } else {
                        System.out.println("Equipage introuvable.");
                    }
                }
                case 4 -> {
                    String idEquipage = lireChaine("ID de l'equipage a supprimer : ");
                    compagnie.supprimerEquipage(idEquipage);
                }
                case 5 -> {
                    List<Equipage> equipagesList = compagnie.listerEquipages();
                    if (equipagesList.isEmpty()) {
                        System.out.println("Aucun equipage enregistre.");
                    } else {
                        System.out.println("Liste des equipages (" + equipagesList.size() + ") :");
                        for (Equipage eq : equipagesList) {
                            System.out.println("  " + eq.getIdEquipage() + " - Pilote: " + (eq.getPilote() != null ? eq.getPilote().getNom() : "N/A") + " - PC: " + eq.getPersonnelCabine().size());
                        }
                    }
                }
                case 0 -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    // ======================== DEMONSTRATION POLYMORPHISME ========================

    /**
     * Demonstration du polymorphisme entre Pilote et PersonnelCabine.
     * Les deux heritent de Employe et surchargent obtenirRole() et obtenirInformation().
     */
    private static void demonstrationPolymorphisme() {
        System.out.println("\n========== DEMONSTRATION POLYMORPHISME ==========");
        System.out.println("Pilote et PersonnelCabine heritent tous deux de Employe.");
        System.out.println("Ils surchargent obtenirRole() et obtenirInformation().\n");

        List<Employe> employes = compagnie.listerEmployes();
        for (Employe employe : employes) {
            // Appel polymorphe : obtenirRole() retourne un resultat different
            // selon que l'objet est un Pilote ou un PersonnelCabine
            System.out.println("Employe : " + employe.getNom() + " " + employe.getPrenom());
            System.out.println("  -> Role (polymorphe) : " + employe.obtenirRole());
            System.out.println("  -> Type reel         : " + employe.getClass().getSimpleName());
            System.out.println();
        }
        System.out.println("================================================\n");
    }

    // ======================== DONNEES DE DEMONSTRATION ========================

    private static void chargerDonneesDemonstration() {
        // Aeroports
        Aeroport cdg = new Aeroport("CDG", "Charles de Gaulle", "Paris", "France");
        Aeroport jfk = new Aeroport("JFK", "John F. Kennedy", "New York", "USA");
        Aeroport nrt = new Aeroport("NRT", "Narita", "Tokyo", "Japon");
        Aeroport lhr = new Aeroport("LHR", "Heathrow", "Londres", "Royaume-Uni");
        Aeroport bcn = new Aeroport("BCN", "El Prat", "Barcelone", "Espagne");
        compagnie.ajouterAeroport(cdg);
        compagnie.ajouterAeroport(jfk);
        compagnie.ajouterAeroport(nrt);
        compagnie.ajouterAeroport(lhr);
        compagnie.ajouterAeroport(bcn);

        // Avions
        compagnie.ajouterAvion(new Avion("F-GKXA", "Airbus A320", 180));
        compagnie.ajouterAvion(new Avion("F-GKXB", "Boeing 737", 160));
        compagnie.ajouterAvion(new Avion("F-GKXC", "Airbus A350", 300));

        // Pilotes (salaire et heures en String)
        Pilote pilote1 = new Pilote("E001", "Dupont", "Jean", "j.dupont@skyisep.com", "0601020304", "EMP001", "8000", "ATPL-001", "5000");
        Pilote pilote2 = new Pilote("E002", "Martin", "Pierre", "p.martin@skyisep.com", "0605060708", "EMP002", "7500", "ATPL-002", "3000");
        compagnie.ajouterEmploye(pilote1);
        compagnie.ajouterEmploye(pilote2);

        // Personnel de cabine (salaire et experience en String)
        PersonnelCabine pc1 = new PersonnelCabine("E003", "Lemoine", "Sophie", "s.lemoine@skyisep.com", "0611121314", "EMP003", "3500", "Chef de cabine", "10");
        PersonnelCabine pc2 = new PersonnelCabine("E004", "Bernard", "Marie", "m.bernard@skyisep.com", "0615161718", "EMP004", "3000", "Hotesse", "5");
        PersonnelCabine pc3 = new PersonnelCabine("E005", "Petit", "Luc", "l.petit@skyisep.com", "0619202122", "EMP005", "3000", "Steward", "3");
        compagnie.ajouterEmploye(pc1);
        compagnie.ajouterEmploye(pc2);
        compagnie.ajouterEmploye(pc3);

        // Equipages
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

        // Vols (avec sous-classes et Aeroport)
        Vol vol1 = new LongCourrier("SI101", cdg, jfk, "20/04/2025", "20/04/2025", "08:00", "12:00", "450", "5800");
        Vol vol2 = new LongCourrier("SI102", cdg, nrt, "21/04/2025", "22/04/2025", "10:30", "06:30", "800", "9700");
        Vol vol3 = new CourtCourrier("SI103", cdg, lhr, "22/04/2025", "22/04/2025", "14:00", "15:30", "120", "350");
        Vol vol4 = new MoyenCourrier("SI104", cdg, bcn, "23/04/2025", "23/04/2025", "09:00", "11:00", "180", "1100");
        compagnie.planifierVol(vol1);
        compagnie.planifierVol(vol2);
        compagnie.planifierVol(vol3);
        compagnie.planifierVol(vol4);

        // Affectation avions et equipages aux vols
        compagnie.affecterAvionAuVol("SI101", "F-GKXA");
        compagnie.affecterAvionAuVol("SI102", "F-GKXC");
        compagnie.affecterAvionAuVol("SI103", "F-GKXB");
        compagnie.affecterEquipageAuVol("SI101", "EQ001");
        compagnie.affecterEquipageAuVol("SI102", "EQ002");

        // Quelques reservations
        compagnie.reserverVol("P001", "SI101");
        compagnie.reserverVol("P002", "SI101");
        compagnie.reserverVol("P003", "SI102");

        System.out.println("\n[OK] Donnees de demonstration chargees avec succes !\n");
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
        scanner.nextLine(); // consomme le retour a la ligne
        return val;
    }

    private static double lireDouble(String message) {
        System.out.print(message);
        while (!scanner.hasNextDouble()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        double val = scanner.nextDouble();
        scanner.nextLine();
        return val;
    }
}
