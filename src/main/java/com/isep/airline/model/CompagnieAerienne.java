package com.isep.airline.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe principale representant la compagnie aerienne.
 * Gere les vols, passagers, employes, avions, equipages, reservations et aeroports.
 * Implemente ObtenirInformation.
 */
public class CompagnieAerienne implements ObtenirInformation {
    private String nom;
    private String codeIATA;
    private List<Vol> vols;
    private List<Passager> passagers;
    private List<Employe> employes;
    private List<Avion> avions;
    private List<Equipage> equipages;
    private List<Reservation> reservations;
    private List<Aeroport> aeroports; // Nouvelle liste d'aeroports

    public CompagnieAerienne() {
        this.vols = new ArrayList<>();
        this.passagers = new ArrayList<>();
        this.employes = new ArrayList<>();
        this.avions = new ArrayList<>();
        this.equipages = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.aeroports = new ArrayList<>();
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
        this.aeroports = new ArrayList<>();
    }

    // ========================================================================
    // ==================== GESTION DES AEROPORTS (CRUD) ====================
    // ========================================================================

    public void ajouterAeroport(Aeroport aeroport) {
        if (aeroport != null && rechercherAeroport(aeroport.getCodeIATA()) == null) {
            aeroports.add(aeroport);
            System.out.println("Aeroport " + aeroport.getCodeIATA() + " (" + aeroport.getNom() + ") ajoute.");
        } else {
            System.out.println("Erreur : aeroport null ou deja existant.");
        }
    }

    public Aeroport rechercherAeroport(String codeIATA) {
        for (Aeroport a : aeroports) {
            if (a.getCodeIATA().equals(codeIATA)) {
                return a;
            }
        }
        return null;
    }

    public boolean modifierAeroport(String codeIATA, String nom, String ville, String pays) {
        Aeroport aeroport = rechercherAeroport(codeIATA);
        if (aeroport != null) {
            if (nom != null && !nom.isEmpty()) aeroport.setNom(nom);
            if (ville != null && !ville.isEmpty()) aeroport.setVille(ville);
            if (pays != null && !pays.isEmpty()) aeroport.setPays(pays);
            System.out.println("Aeroport " + codeIATA + " modifie avec succes.");
            return true;
        }
        System.out.println("Erreur : aeroport " + codeIATA + " introuvable.");
        return false;
    }

    public boolean supprimerAeroport(String codeIATA) {
        Aeroport aeroport = rechercherAeroport(codeIATA);
        if (aeroport != null) {
            aeroports.remove(aeroport);
            System.out.println("Aeroport " + codeIATA + " supprime.");
            return true;
        }
        System.out.println("Erreur : aeroport " + codeIATA + " introuvable.");
        return false;
    }

    public List<Aeroport> listerAeroports() {
        return new ArrayList<>(aeroports);
    }

    // ========================================================================
    // ==================== GESTION DES PASSAGERS (CRUD) ====================
    // ========================================================================

    public void ajouterPassager(Passager passager) {
        if (passager != null && rechercherPassager(passager.getId()) == null) {
            passagers.add(passager);
            System.out.println("Passager " + passager.getNom() + " " + passager.getPrenom() + " ajoute.");
        } else {
            System.out.println("Erreur : passager null ou deja existant.");
        }
    }

    public Passager rechercherPassager(String id) {
        for (Passager p : passagers) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public boolean modifierPassager(String id, String nom, String prenom, String email, String telephone, String passeport) {
        Passager passager = rechercherPassager(id);
        if (passager != null) {
            if (nom != null && !nom.isEmpty()) passager.setNom(nom);
            if (prenom != null && !prenom.isEmpty()) passager.setPrenom(prenom);
            if (email != null && !email.isEmpty()) passager.setEmail(email);
            if (telephone != null && !telephone.isEmpty()) passager.setTelephone(telephone);
            if (passeport != null && !passeport.isEmpty()) passager.setNumeroPasseport(passeport);
            System.out.println("Passager " + id + " modifie avec succes.");
            return true;
        }
        System.out.println("Erreur : passager " + id + " introuvable.");
        return false;
    }

    public boolean supprimerPassager(String id) {
        Passager passager = rechercherPassager(id);
        if (passager != null) {
            passagers.remove(passager);
            System.out.println("Passager " + id + " supprime.");
            return true;
        }
        System.out.println("Erreur : passager " + id + " introuvable.");
        return false;
    }

    public List<Passager> listerPassagers() {
        return new ArrayList<>(passagers);
    }

    // ========================================================================
    // ==================== GESTION DES EMPLOYES (CRUD) =====================
    // ========================================================================

    public void ajouterEmploye(Employe employe) {
        if (employe != null && rechercherEmploye(employe.getId()) == null) {
            employes.add(employe);
            System.out.println("Employe " + employe.getNom() + " " + employe.getPrenom() + " ajoute (role: " + employe.obtenirRole() + ").");
        } else {
            System.out.println("Erreur : employe null ou deja existant.");
        }
    }

    public Employe rechercherEmploye(String id) {
        for (Employe e : employes) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        return null;
    }

    public String obtenirRoleEmploye(String id) {
        Employe employe = rechercherEmploye(id);
        if (employe != null) {
            return employe.obtenirRole();
        }
        System.out.println("Erreur : employe " + id + " introuvable.");
        return null;
    }

    public boolean modifierEmploye(String id, String nom, String prenom, String email, String telephone) {
        Employe employe = rechercherEmploye(id);
        if (employe != null) {
            if (nom != null && !nom.isEmpty()) employe.setNom(nom);
            if (prenom != null && !prenom.isEmpty()) employe.setPrenom(prenom);
            if (email != null && !email.isEmpty()) employe.setEmail(email);
            if (telephone != null && !telephone.isEmpty()) employe.setTelephone(telephone);
            System.out.println("Employe " + id + " modifie avec succes.");
            return true;
        }
        System.out.println("Erreur : employe " + id + " introuvable.");
        return false;
    }

    public boolean supprimerEmploye(String id) {
        Employe employe = rechercherEmploye(id);
        if (employe != null) {
            employes.remove(employe);
            System.out.println("Employe " + id + " supprime.");
            return true;
        }
        System.out.println("Erreur : employe " + id + " introuvable.");
        return false;
    }

    public List<Employe> listerEmployes() {
        return new ArrayList<>(employes);
    }

    /**
     * Polymorphisme : lister les pilotes parmi les employes.
     * Utilise instanceof pour filtrer — demontre le polymorphisme.
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
     * Polymorphisme : lister le personnel de cabine parmi les employes.
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

    public void planifierVol(Vol vol) {
        if (vol != null && rechercherVol(vol.getNumeroVol()) == null) {
            vol.setStatut("PLANIFIE");
            vols.add(vol);
            String depart = (vol.getAeroportDepart() != null) ? vol.getAeroportDepart().getCodeIATA() : "?";
            String arrivee = (vol.getAeroportArrivee() != null) ? vol.getAeroportArrivee().getCodeIATA() : "?";
            System.out.println("Vol " + vol.getNumeroVol() + " planifie : " + depart + " -> " + arrivee + " (" + vol.getTypeVol() + ")");
        } else {
            System.out.println("Erreur : vol null ou deja existant.");
        }
    }

    public Vol rechercherVol(String numeroVol) {
        for (Vol v : vols) {
            if (v.getNumeroVol().equals(numeroVol)) {
                return v;
            }
        }
        return null;
    }

    public Vol obtenirVol(String numeroVol) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            System.out.println(vol.obtenirInformation());
            return vol;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return null;
    }

    public boolean modifierVol(String numeroVol, Aeroport aeroportDepart, Aeroport aeroportArrivee,
                               String dateDepart, String dateArrivee, String heureDepart,
                               String heureArrivee, String prix) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            if (aeroportDepart != null) vol.setAeroportDepart(aeroportDepart);
            if (aeroportArrivee != null) vol.setAeroportArrivee(aeroportArrivee);
            if (dateDepart != null && !dateDepart.isEmpty()) vol.setDateDepart(dateDepart);
            if (dateArrivee != null && !dateArrivee.isEmpty()) vol.setDateArrivee(dateArrivee);
            if (heureDepart != null && !heureDepart.isEmpty()) vol.setHeureDepart(heureDepart);
            if (heureArrivee != null && !heureArrivee.isEmpty()) vol.setHeureArrivee(heureArrivee);
            if (prix != null && !prix.isEmpty() && !prix.equals("0")) vol.setPrix(prix);
            System.out.println("Vol " + numeroVol + " modifie avec succes.");
            return true;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return false;
    }

    public boolean annulerVol(String numeroVol) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            vol.annulerVol();
            return true;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return false;
    }

    public boolean supprimerVol(String numeroVol) {
        Vol vol = rechercherVol(numeroVol);
        if (vol != null) {
            vols.remove(vol);
            System.out.println("Vol " + numeroVol + " supprime.");
            return true;
        }
        System.out.println("Erreur : vol " + numeroVol + " introuvable.");
        return false;
    }

    public boolean affecterEquipageAuVol(String numeroVol, String idEquipage) {
        Vol vol = rechercherVol(numeroVol);
        Equipage equipage = rechercherEquipage(idEquipage);
        if (vol == null) {
            System.out.println("Erreur : vol " + numeroVol + " introuvable.");
            return false;
        }
        if (equipage == null) {
            System.out.println("Erreur : equipage " + idEquipage + " introuvable.");
            return false;
        }
        return vol.affecterEquipage(equipage);
    }

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

    public List<Vol> listerVols() {
        return new ArrayList<>(vols);
    }

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

    public void ajouterAvion(Avion avion) {
        if (avion != null && rechercherAvion(avion.getImmatriculation()) == null) {
            avions.add(avion);
            System.out.println("Avion " + avion.getImmatriculation() + " (" + avion.getModele() + ") ajoute.");
        } else {
            System.out.println("Erreur : avion null ou deja existant.");
        }
    }

    public Avion rechercherAvion(String immatriculation) {
        for (Avion a : avions) {
            if (a.getImmatriculation().equals(immatriculation)) {
                return a;
            }
        }
        return null;
    }

    public boolean modifierAvion(String immatriculation, String modele, int capacite) {
        Avion avion = rechercherAvion(immatriculation);
        if (avion != null) {
            if (modele != null && !modele.isEmpty()) avion.setModele(modele);
            if (capacite > 0) avion.setCapacite(capacite);
            System.out.println("Avion " + immatriculation + " modifie avec succes.");
            return true;
        }
        System.out.println("Erreur : avion " + immatriculation + " introuvable.");
        return false;
    }

    public boolean supprimerAvion(String immatriculation) {
        Avion avion = rechercherAvion(immatriculation);
        if (avion != null) {
            avions.remove(avion);
            System.out.println("Avion " + immatriculation + " supprime.");
            return true;
        }
        System.out.println("Erreur : avion " + immatriculation + " introuvable.");
        return false;
    }

    public List<Avion> listerAvions() {
        return new ArrayList<>(avions);
    }

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

    public void ajouterEquipage(Equipage equipage) {
        if (equipage != null && rechercherEquipage(equipage.getIdEquipage()) == null) {
            equipages.add(equipage);
            System.out.println("Equipage " + equipage.getIdEquipage() + " ajoute.");
        } else {
            System.out.println("Erreur : equipage null ou deja existant.");
        }
    }

    public Equipage rechercherEquipage(String idEquipage) {
        for (Equipage eq : equipages) {
            if (eq.getIdEquipage().equals(idEquipage)) {
                return eq;
            }
        }
        return null;
    }

    public boolean supprimerEquipage(String idEquipage) {
        Equipage equipage = rechercherEquipage(idEquipage);
        if (equipage != null) {
            equipages.remove(equipage);
            System.out.println("Equipage " + idEquipage + " supprime.");
            return true;
        }
        System.out.println("Erreur : equipage " + idEquipage + " introuvable.");
        return false;
    }

    public List<Equipage> listerEquipages() {
        return new ArrayList<>(equipages);
    }

    // ========================================================================
    // ==================== GESTION DES RESERVATIONS (CRUD) =================
    // ========================================================================

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

    public boolean annulerReservation(String numeroReservation) {
        for (int i = 0; i < reservations.size(); i++) {
            Reservation r = reservations.get(i);
            if (r.getNumeroReservation().equals(numeroReservation)) {
                r.getPassager().annulerReservation(numeroReservation);
                reservations.remove(i);
                return true;
            }
        }
        System.out.println("Erreur : reservation " + numeroReservation + " introuvable.");
        return false;
    }

    public Reservation rechercherReservation(String numeroReservation) {
        for (Reservation r : reservations) {
            if (r.getNumeroReservation().equals(numeroReservation)) {
                return r;
            }
        }
        return null;
    }

    public Reservation obtenirReservation(String numeroReservation) {
        Reservation reservation = rechercherReservation(numeroReservation);
        if (reservation != null) {
            System.out.println(reservation.obtenirInformation());
            return reservation;
        }
        System.out.println("Erreur : reservation " + numeroReservation + " introuvable.");
        return null;
    }

    public List<Reservation> listerReservations() {
        return new ArrayList<>(reservations);
    }

    // ========================================================================
    // ==================== STATISTIQUES ET RAPPORTS ========================
    // ========================================================================

    public int getNombreVols() {
        return vols.size();
    }

    public Map<String, Integer> getStatistiquesVols() {
        Map<String, Integer> stats = new HashMap<>();
        for (Vol v : vols) {
            stats.merge(v.getStatut(), 1, Integer::sum);
        }
        return stats;
    }

    public Map<String, Integer> getStatistiquesTypesVols() {
        Map<String, Integer> stats = new HashMap<>();
        for (Vol v : vols) {
            stats.merge(v.getTypeVol(), 1, Integer::sum);
        }
        return stats;
    }

    public int getNombrePassagersTransportes() {
        int total = 0;
        for (Vol v : vols) {
            if (v.getStatut().equals("TERMINE")) {
                total += v.getPassagers().size();
            }
        }
        return total;
    }

    public double getRevenusGeneres() {
        double revenus = 0;
        for (Reservation r : reservations) {
            if (r.getStatut().equals("CONFIRMEE")) {
                try {
                    revenus += Double.parseDouble(r.getMontantTotal());
                } catch (NumberFormatException e) {
                    // ignorer les montants non numeriques
                }
            }
        }
        return revenus;
    }

    public Map<String, Integer> getDestinationsPopulaires() {
        Map<String, Integer> destinations = new HashMap<>();
        for (Reservation r : reservations) {
            if (r.getStatut().equals("CONFIRMEE")) {
                for (Vol v : r.getVolsReserves()) {
                    String dest = (v.getAeroportArrivee() != null) ? v.getAeroportArrivee().getCodeIATA() : "Inconnu";
                    destinations.merge(dest, 1, Integer::sum);
                }
            }
        }
        return destinations;
    }

    public void afficherRapport() {
        System.out.println("\n====================================================");
        System.out.println("       RAPPORT - " + nom + " (" + codeIATA + ")");
        System.out.println("====================================================");
        System.out.println(" Nombre total de vols        : " + getNombreVols());
        System.out.println(" Nombre de passagers         : " + passagers.size());
        System.out.println(" Nombre d employes           : " + employes.size());
        System.out.println(" Nombre d avions             : " + avions.size());
        System.out.println(" Nombre d aeroports          : " + aeroports.size());
        System.out.println(" Nombre de reservations      : " + reservations.size());
        System.out.println(" Passagers transportes       : " + getNombrePassagersTransportes());
        System.out.println(" Revenus generes             : " + String.format("%.2f", getRevenusGeneres()) + " EUR");
        System.out.println("----------------------------------------------------");

        System.out.println(" Vols par statut :");
        Map<String, Integer> statsVols = getStatistiquesVols();
        for (Map.Entry<String, Integer> entry : statsVols.entrySet()) {
            System.out.println("   - " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println(" Vols par type :");
        Map<String, Integer> statsTypes = getStatistiquesTypesVols();
        for (Map.Entry<String, Integer> entry : statsTypes.entrySet()) {
            System.out.println("   - " + entry.getKey() + " : " + entry.getValue());
        }

        System.out.println("----------------------------------------------------");
        System.out.println(" Destinations populaires :");
        Map<String, Integer> destinations = getDestinationsPopulaires();
        destinations.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(entry -> System.out.println("   - " + entry.getKey() + " : " + entry.getValue() + " reservation(s)"));

        System.out.println("====================================================\n");
    }

    // ==================== Interface ObtenirInformation ====================

    @Override
    public String obtenirInformation() {
        return "===== Compagnie Aerienne =====\n"
                + "Nom         : " + nom + "\n"
                + "Code IATA   : " + codeIATA + "\n"
                + "Vols        : " + vols.size() + "\n"
                + "Passagers   : " + passagers.size() + "\n"
                + "Employes    : " + employes.size() + "\n"
                + "Avions      : " + avions.size() + "\n"
                + "Aeroports   : " + aeroports.size() + "\n"
                + "Reservations: " + reservations.size();
    }

    @Override
    public String toString() {
        return obtenirInformation();
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

    public List<Aeroport> getAeroports() {
        return aeroports;
    }
}
