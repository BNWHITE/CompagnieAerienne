package com.isep.airline.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principale représentant la compagnie aérienne.
 * Gère les vols, passagers, employés, avions, équipages et réservations.
 */
public class CompagnieAerienne {
    private String nom;
    private String codeIATA;
    private List<Vol> vols;
    private List<Passager> passagers;
    private List<Employe> employes;
    private List<Avion> avions;
    private List<Equipage> equipages;
    private List<Reservation> reservations;

    public CompagnieAerienne() {
        this.vols = new ArrayList<>();
        this.passagers = new ArrayList<>();
        this.employes = new ArrayList<>();
        this.avions = new ArrayList<>();
        this.equipages = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public CompagnieAerienne(String nom, String codeIATA) {
        this.nom = nom;
        this.codeIATA = codeIATA;
        this.vols = new ArrayList<>();
        this.passagers = new ArrayList<>();
        this.employes = new ArrayList<>();
        this.avions = new ArrayList<>();
        this.equipages = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    // ========================================================================
    // ==================== GESTION DES PASSAGERS (CRUD) ====================
    // ========================================================================

    /**
     * Ajouter un passager.
     */
    public void ajouterPassager(Passager passager) {
        if (passager != null && rechercherPassager(passager.getId()) == null) {
            passagers.add(passager);
            System.out.println("Passager " + passager.getNom() + " " + passager.getPrenom() + " ajouté.");
        } else {
            System.out.println("Erreur : passager null ou déjà existant.");
        }
    }

    /**
     * Rechercher un passager par son ID.
     */
    public Passager rechercherPassager(String id) {
        for (Passager p : passagers) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    /**
     * Modifier un passager.
     */
    public boolean modifierPassager(String id, String nom, String prenom, String email, String telephone, String passeport) {
        Passager passager = rechercherPassager(id);
        if (passager != null) {
            if (nom != null && !nom.isEmpty()) passager.setNom(nom);
            if (prenom != null && !prenom.isEmpty()) passager.setPrenom(prenom);
            if (email != null && !email.isEmpty()) passager.setEmail(email);
            if (telephone != null && !telephone.isEmpty()) passager.setTelephone(telephone);
            if (passeport != null && !passeport.isEmpty()) passager.setNumeroPasseport(passeport);
            System.out.println("Passager " + id + " modifié avec succès.");
            return true;
        }
        System.out.println("Erreur : passager " + id + " introuvable.");
        return false;
    }

    /**
     * Supprimer un passager par son ID.
     */
    public boolean supprimerPassager(String id) {
        Passager passager = rechercherPassager(id);
        if (passager != null) {
            passagers.remove(passager);
            System.out.println("Passager " + id + " supprimé.");
            return true;
        }
        System.out.println("Erreur : passager " + id + " introuvable.");
        return false;
    }

    /**
     * Lister tous les passagers.
     */
    public List<Passager> listerPassagers() {
        return new ArrayList<>(passagers);
    }

    // ========================================================================
    // ==================== GESTION DES EMPLOYES (CRUD) =====================
    // ========================================================================

    /**
     * Ajouter un employé.
     */
    public void ajouterEmploye(Employe employe) {
        if (employe != null && rechercherEmploye(employe.getId()) == null) {
            employes.add(employe);
            System.out.println("Employé " + employe.getNom() + " " + employe.getPrenom() + " ajouté (rôle: " + employe.obtenirRole() + ").");
        } else {
            System.out.println("Erreur : employé null ou déjà existant.");
        }
    }

    /**
     * Rechercher un employé par son ID.
     */
    public Employe rechercherEmploye(String id) {
        for (Employe e : employes) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    /**
     * Obtenir le rôle d'un employé par son identifiant.
     */
    public String obtenirRoleEmploye(String id) {
        Employe employe = rechercherEmploye(id);
        if (employe != null) {
            return employe.obtenirRole();
        }
        System.out.println("Erreur : employé " + id + " introuvable.");
        return null;
    }

    /**
     * Modifier un employé.
     */
    public boolean modifierEmploye(String id, String nom, String prenom, String email, String telephone) {
        Employe employe = rechercherEmploye(id);
        if (employe != null) {
            if (nom != null && !nom.isEmpty()) employe.setNom(nom);
            if (prenom != null && !prenom.isEmpty()) employe.setPrenom(prenom);
            if (email != null && !email.isEmpty()) employe.setEmail(email);
            if (telephone != null && !telephone.isEmpty()) employe.setTelephone(telephone);
            System.out.println("Employé " + id + " modifié avec succès.");
            return true;
        }
        System.out.println("Erreur : employé " + id + " introuvable.");
        return false;
    }

    /**
     * Supprimer un employé par son ID.
     */
    public boolean supprimerEmploye(String id) {
        Employe employe = rechercherEmploye(id);
        if (employe != null) {
            employes.remove(employe);
            System.out.println("Employé " + id + " supprimé.");
            return true;
        }
        System.out.println("Erreur : employé " + id + " introuvable.");
        return false;
    }

    /**
     * Lister tous les employés.
     */
    public List<Employe> listerEmployes() {
        return new ArrayList<>(employes);
    }

    /**
     * Lister les pilotes.
     */
    public List<Pilote> listerPilotes() {
        List<Pilote> pilotes = new ArrayList<>();
        for (Employe e : employes) {
            if (e instanceof Pilote) {
                pilotes.add((Pilote) e);
            }
        }
        return pilotes;
    }

    /**
     * Lister le personnel de cabine.
     */
    public List<PersonnelCabine> listerPersonnelCabine() {
        List<PersonnelCabine> personnelCabines = new ArrayList<>();
        for (Employe e : employes) {
            if (e instanceof PersonnelCabine) {
                personnelCabines.add((PersonnelCabine) e);
            }
        }
        return personnelCabines;
    }

    // ========================================================================
    // ==================== GESTION DES VOLS (CRUD) =========================
    // ========================================================================

    /**
     * Planifier un vol - ajouter un vol à la liste des vols planifiés.
     */
    public void planifierVol(Vol vol) {
        if (vol != null && rechercherVol(vol.getNumeroVol()) == null) {
            vol.setStatut("PLANIFIE");
            vols.add(vol);
            System.out.println("Vol " + vol.getNumeroVol() + " planifié : " + vol.getVilleDepart() + " -> " + vol.getVilleArrivee());
        } else {
            System.out.println("Erreur : vol null ou déjà existant.");
        }
    }

    /**
     * Rechercher un vol par son numéro.
     */
    public Vol rechercherVol(String numeroVol) {
        for (Vol v : vols) {
            if (v.getNumeroVol().equals(numeroVol)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Obtenir les informations d'un vol par son numéro.
     */
    public Vol obtenirVol(String numeroVol) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            vol.obtenirVol();
            return vol;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return null;
    }

    /**
     * Modifier un vol.
     */
    public boolean modifierVol(String numeroVol, String villeDepart, String villeArrivee,
                               String dateDepart, String dateArrivee, String heureDepart,
                               String heureArrivee, double prix) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            if (villeDepart != null && !villeDepart.isEmpty()) vol.setVilleDepart(villeDepart);
            if (villeArrivee != null && !villeArrivee.isEmpty()) vol.setVilleArrivee(villeArrivee);
            if (dateDepart != null && !dateDepart.isEmpty()) vol.setDateDepart(dateDepart);
            if (dateArrivee != null && !dateArrivee.isEmpty()) vol.setDateArrivee(dateArrivee);
            if (heureDepart != null && !heureDepart.isEmpty()) vol.setHeureDepart(heureDepart);
            if (heureArrivee != null && !heureArrivee.isEmpty()) vol.setHeureArrivee(heureArrivee);
            if (prix > 0) vol.setPrix(prix);
            System.out.println("Vol " + numeroVol + " modifié avec succès.");
            return true;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return false;
    }

    /**
     * Annuler un vol par son numéro.
     */
    public boolean annulerVol(String numeroVol) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            vol.annulerVol();
            return true;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return false;
    }

    /**
     * Supprimer un vol de la liste.
     */
    public boolean supprimerVol(String numeroVol) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            vols.remove(vol);
            System.out.println("Vol " + numeroVol + " supprimé.");
            return true;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return false;
    }

    /**
     * Affecter un équipage à un vol.
     */
    public boolean affecterEquipageAuVol(String numeroVol, String idEquipage) {
        Vol vol = rechercherVol(numeroVol);
        Equipage equipage = rechercherEquipage(idEquipage);
        if (vol == null) {
            System.out.println("Erreur : vol " + numeroVol + " introuvable.");
            return false;
        }
        if (equipage == null) {
            System.out.println("Erreur : équipage " + idEquipage + " introuvable.");
            return false;
        }
        return vol.affecterEquipage(equipage);
    }

    /**
     * Affecter un avion à un vol.
     */
    public boolean affecterAvionAuVol(String numeroVol, String immatriculation) {
        Vol vol = rechercherVol(numeroVol);
        Avion avion = rechercherAvion(immatriculation);
        if (vol == null) {
            System.out.println("Erreur : vol " + numeroVol + " introuvable.");
            return false;
        }
        if (avion == null) {
            System.out.println("Erreur : avion " + immatriculation + " introuvable.");
            return false;
        }
        return vol.affecterAvion(avion);
    }

    /**
     * Lister tous les vols.
     */
    public List<Vol> listerVols() {
        return new ArrayList<>(vols);
    }

    /**
     * Lister les vols planifiés.
     */
    public List<Vol> listerVolsPlanifies() {
        List<Vol> volsPlanifies = new ArrayList<>();
        for (Vol v : vols) {
            if (v.getStatut().equals("PLANIFIE")) {
                volsPlanifies.add(v);
            }
        }
        return volsPlanifies;
    }

    // ========================================================================
    // ==================== GESTION DES AVIONS (CRUD) =======================
    // ========================================================================

    /**
     * Ajouter un avion.
     */
    public void ajouterAvion(Avion avion) {
        if (avion != null && rechercherAvion(avion.getImmatriculation()) == null) {
            avions.add(avion);
            System.out.println("Avion " + avion.getImmatriculation() + " (" + avion.getModele() + ") ajouté.");
        } else {
            System.out.println("Erreur : avion null ou déjà existant.");
        }
    }

    /**
     * Rechercher un avion par son immatriculation.
     */
    public Avion rechercherAvion(String immatriculation) {
        for (Avion a : avions) {
            if (a.getImmatriculation().equals(immatriculation)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Modifier un avion.
     */
    public boolean modifierAvion(String immatriculation, String modele, int capacite) {
        Avion avion = rechercherAvion(immatriculation);
        if (avion != null) {
            if (modele != null && !modele.isEmpty()) avion.setModele(modele);
            if (capacite > 0) avion.setCapacite(capacite);
            System.out.println("Avion " + immatriculation + " modifié avec succès.");
            return true;
        }
        System.out.println("Erreur : avion " + immatriculation + " introuvable.");
        return false;
    }

    /**
     * Supprimer un avion.
     */
    public boolean supprimerAvion(String immatriculation) {
        Avion avion = rechercherAvion(immatriculation);
        if (avion != null) {
            avions.remove(avion);
            System.out.println("Avion " + immatriculation + " supprimé.");
            return true;
        }
        System.out.println("Erreur : avion " + immatriculation + " introuvable.");
        return false;
    }

    /**
     * Lister tous les avions.
     */
    public List<Avion> listerAvions() {
        return new ArrayList<>(avions);
    }

    /**
     * Lister les avions disponibles.
     */
    public List<Avion> listerAvionsDisponibles() {
        List<Avion> disponibles = new ArrayList<>();
        for (Avion a : avions) {
            if (a.isDisponible()) {
                disponibles.add(a);
            }
        }
        return disponibles;
    }

    // ========================================================================
    // ==================== GESTION DES EQUIPAGES (CRUD) ====================
    // ========================================================================

    /**
     * Ajouter un équipage.
     */
    public void ajouterEquipage(Equipage equipage) {
        if (equipage != null && rechercherEquipage(equipage.getIdEquipage()) == null) {
            equipages.add(equipage);
            System.out.println("Équipage " + equipage.getIdEquipage() + " ajouté.");
        } else {
            System.out.println("Erreur : équipage null ou déjà existant.");
        }
    }

    /**
     * Rechercher un équipage par son ID.
     */
    public Equipage rechercherEquipage(String idEquipage) {
        for (Equipage eq : equipages) {
            if (eq.getIdEquipage().equals(idEquipage)) {
                return eq;
            }
        }
        return null;
    }

    /**
     * Supprimer un équipage.
     */
    public boolean supprimerEquipage(String idEquipage) {
        Equipage equipage = rechercherEquipage(idEquipage);
        if (equipage != null) {
            equipages.remove(equipage);
            System.out.println("Équipage " + idEquipage + " supprimé.");
            return true;
        }
        System.out.println("Erreur : équipage " + idEquipage + " introuvable.");
        return false;
    }

    /**
     * Lister tous les équipages.
     */
    public List<Equipage> listerEquipages() {
        return new ArrayList<>(equipages);
    }

    // ========================================================================
    // ==================== GESTION DES RESERVATIONS (CRUD) =================
    // ========================================================================

    /**
     * Créer une réservation pour un passager sur un vol.
     */
    public Reservation reserverVol(String idPassager, String numeroVol) {
        Passager passager = rechercherPassager(idPassager);
        Vol vol = rechercherVol(numeroVol);
        if (passager == null) {
            System.out.println("Erreur : passager " + idPassager + " introuvable.");
            return null;
        }
        if (vol == null) {
            System.out.println("Erreur : vol " + numeroVol + " introuvable.");
            return null;
        }
        Reservation reservation = passager.reserverVol(vol);
        if (reservation != null) {
            reservations.add(reservation);
        }
        return reservation;
    }

    /**
     * Annuler une réservation.
     */
    public boolean annulerReservation(String numeroReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            if (r.getNumeroReservation().equals(numeroReservation)) {
                r.getPassager().annulerReservation(numeroReservation);
                reservations.remove(i);
                return true;
            }
        }
        System.out.println("Erreur : réservation " + numeroReservation + " introuvable.");
        return false;
    }

    /**
     * Rechercher une réservation par son numéro.
     */
    public Reservation rechercherReservation(String numeroReservation) {
        for (Reservation r : reservations) {
            if (r.getNumeroReservation().equals(numeroReservation)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Obtenir les informations d'une réservation par son numéro.
     */
    public Reservation obtenirReservation(String numeroReservation) {
        Reservation reservation = rechercherReservation(numeroReservation);
        if (reservation != null) {
            reservation.obtenirReservation();
            return reservation;
        }
        System.out.println("Erreur : réservation " + numeroReservation + " introuvable.");
        return null;
    }

    /**
     * Lister toutes les réservations.
     */
    public List<Reservation> listerReservations() {
        return new ArrayList<>(reservations);
    }

    // ========================================================================
    // ==================== STATISTIQUES ET RAPPORTS (BONUS) ================
    // ========================================================================

    /**
     * Génère un rapport sur le nombre total de vols.
     */
    public int getNombreVols() {
        return vols.size();
    }

    /**
     * Nombre de vols par statut.
     */
    public Map<String, Integer> getStatistiquesVols() {
        Map<String, Integer> stats = new HashMap<>();
        for (Vol v : vols) {
            stats.merge(v.getStatut(), 1, Integer::sum);
        }
        return stats;
    }

    /**
     * Nombre total de passagers transportés (sur les vols terminés).
     */
    public int getNombrePassagersTransportes() {
        int total = 0;
        for (Vol v : vols) {
            if (v.getStatut().equals("TERMINE")) {
                total += v.getPassagers().size();
            }
        }
        return total;
    }

    /**
     * Revenus générés (somme des montants des réservations confirmées).
     */
    public double getRevenusGeneres() {
        double revenus = 0;
        for (Reservation r : reservations) {
            if (r.getStatut().equals("CONFIRMEE")) {
                revenus += r.getMontantTotal();
            }
        }
        return revenus;
    }

    /**
     * Destinations les plus populaires (classées par nombre de réservations).
     */
    public Map<String, Integer> getDestinationsPopulaires() {
        Map<String, Integer> destinations = new HashMap<>();
        for (Reservation r : reservations) {
            if (r.getStatut().equals("CONFIRMEE")) {
                for (Vol v : r.getVolsReserves()) {
                    destinations.merge(v.getVilleArrivee(), 1, Integer::sum);
                }
            }
        }
        return destinations;
    }

    /**
     * Affiche un rapport complet.
     */
    public void afficherRapport() {
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║       RAPPORT - " + nom + " (" + codeIATA + ")");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ Nombre total de vols        : " + getNombreVols());
        System.out.println("║ Nombre de passagers         : " + passagers.size());
        System.out.println("║ Nombre d'employés           : " + employes.size());
        System.out.println("║ Nombre d'avions             : " + avions.size());
        System.out.println("║ Nombre de réservations      : " + reservations.size());
        System.out.println("║ Passagers transportés       : " + getNombrePassagersTransportes());
        System.out.println("║ Revenus générés             : " + String.format("%.2f", getRevenusGeneres()) + " €");
        System.out.println("╠══════════════════════════════════════════════════╣");

        System.out.println("║ Vols par statut :");
        Map<String, Integer> statsVols = getStatistiquesVols();
        for (Map.Entry<String, Integer> entry : statsVols.entrySet()) {
            System.out.println("║   - " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.println("║ Destinations populaires :");
        Map<String, Integer> destinations = getDestinationsPopulaires();
        destinations.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("║   - " + entry.getKey() + " : " + entry.getValue() + " réservation(s)"));

        System.out.println("╚══════════════════════════════════════════════════╝\n");
    }

    // ==================== Getters & Setters ====================

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodeIATA() {
        return codeIATA;
    }

    public void setCodeIATA(String codeIATA) {
        this.codeIATA = codeIATA;
    }

    public List<Vol> getVols() {
        return vols;
    }

    public List<Passager> getPassagers() {
        return passagers;
    }

    public List<Employe> getEmployes() {
        return employes;
    }

    public List<Avion> getAvions() {
        return avions;
    }

    public List<Equipage> getEquipages() {
        return equipages;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    @Override
    public String toString() {
        return "CompagnieAerienne{" +
                "nom='" + nom + '\'' +
                ", codeIATA='" + codeIATA + '\'' +
                ", vols=" + vols.size() +
                ", passagers=" + passagers.size() +
                ", employes=" + employes.size() +
                ", avions=" + avions.size() +
                '}';
    }
}
