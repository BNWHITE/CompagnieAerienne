# ✈️ SkyISEP Airlines — Système de Réservation

> Projet Java — ISEP 2025/2026

[![Java](https://img.shields.io/badge/Java-17-orange?logo=java)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Build-Maven_3.8%2B-blue?logo=apachemaven)](https://maven.apache.org/)
[![JFreeChart](https://img.shields.io/badge/JFreeChart-1.5.4-green)](http://www.jfree.org/jfreechart/)

---

## 📋 Description

Système complet de gestion d'une compagnie aérienne développé en **Java 17**.
Il couvre la gestion des vols, des passagers, des employés, des avions, des équipages, des aéroports et des réservations.

---

## 📁 Structure du projet

```
CompagnieAerienne/
├── documentation/
│   └── README.md                    # Documentation détaillée (UML, concepts Java, etc.)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/isep/airline/
│   │   │       ├── Main.java
│   │   │       ├── GraphiqueStatistiques.java
│   │   │       └── model/
│   │   │           ├── ObtenirInformation.java
│   │   │           ├── Personne.java
│   │   │           ├── Passager.java
│   │   │           ├── Employe.java
│   │   │           ├── Pilote.java
│   │   │           ├── PersonnelCabine.java
│   │   │           ├── Aeroport.java
│   │   │           ├── Vol.java
│   │   │           ├── CourtCourrier.java
│   │   │           ├── MoyenCourrier.java
│   │   │           ├── LongCourrier.java
│   │   │           ├── Avion.java
│   │   │           ├── Equipage.java
│   │   │           ├── Reservation.java
│   │   │           └── CompagnieAerienne.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/isep/airline/
├── pom.xml
└── README.md
```

---

## ⚙️ Prérequis

| Outil  | Version minimale | Lien |
|--------|-----------------|------|
| **JDK**   | 17  | [adoptium.net](https://adoptium.net/) |
| **Maven** | 3.8+ | [maven.apache.org](https://maven.apache.org/download.cgi) |
| **Git**   | 2.x | [git-scm.com](https://git-scm.com/) |

Vérifier les installations :

```bash
java -version
mvn -version
git --version
```

---

## 📦 Dépendances

Toutes les dépendances sont gérées par **Maven** via le fichier `pom.xml`.

### Dépendances principales

| Bibliothèque | Version | Utilisation | Lien |
|---|---|---|---|
| **JFreeChart** | 1.5.4 | Génération de graphiques statistiques (histogrammes, camemberts) | [jfree.org](http://www.jfree.org/jfreechart/) |

> JFreeChart inclut automatiquement **JCommon** comme dépendance transitive.

### Plugins Maven

| Plugin | Version | Rôle |
|---|---|---|
| `maven-compiler-plugin` | 3.11.0 | Compilation Java 17 |
| `maven-jar-plugin` | 3.3.0 | Création du JAR exécutable |

### Bibliothèques Java standard utilisées

| Package | Classes utilisées |
|---|---|
| `java.util` | `ArrayList`, `HashMap`, `List`, `Map`, `Deque`, `ArrayDeque` |
| `java.util.stream` | `Collectors`, `Stream` |
| `javax.swing` | `JFrame`, `JPanel` (affichage des graphiques) |

---

## 🚀 Installation et lancement

### 1. Cloner le dépôt

```bash
git clone https://github.com/BNWHITE/CompagnieAerienne.git
cd CompagnieAerienne
```

### 2. Compiler le projet

```bash
mvn clean compile
```

### 3. Lancer l'application

```bash
mvn exec:java -Dexec.mainClass="com.isep.airline.Main"
```

Ou via le JAR :

```bash
mvn clean package
java -jar target/compagnie-aerienne-1.0-SNAPSHOT.jar
```

### 4. Lancer les tests

```bash
mvn test
```

---

## 🧱 Architecture

Le projet suit une architecture orientée objet en couches :

```
┌──────────────────────────────────────┐
│            Main.java                 │  ← Interface console (menu interactif)
├──────────────────────────────────────┤
│      CompagnieAerienne.java          │  ← Orchestrateur / logique métier
├──────────────────────────────────────┤
│            model/                    │  ← Entités du domaine
│  Personne ← Employe ← Pilote         │
│                     ← PersonnelCabine│
│           ← Passager                 │
│  Vol (abstract) ← CourtCourrier      │
│                 ← MoyenCourrier      │
│                 ← LongCourrier       │
│  Aeroport, Avion, Equipage,          │
│  Reservation, ObtenirInformation     │
└──────────────────────────────────────┘
```

**Patrons appliqués :**

- **Héritage & Polymorphisme** — hiérarchie `Personne`, `Vol`
- **Interface** — `ObtenirInformation` implémentée par toutes les entités
- **Composition / Agrégation** — `Equipage` contient un `Pilote` et des `PersonnelCabine`

---

## 📚 Documentation

La documentation technique complète est disponible dans [`documentation/README.md`](documentation/README.md) :

- Diagramme UML complet des classes
- Explication de tous les concepts Java utilisés
- Justifications des choix de conception
- Questions/réponses de cours

---

## 👥 Auteurs

| Nom | École |
|-----|-------|
| Équipe SkyISEP | ISEP — Paris |

---

## 📝 Licence

Projet académique — ISEP 2025/2026.
