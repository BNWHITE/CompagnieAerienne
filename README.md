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
│   │   │       ├── model/
│   │   │       │   ├── ObtenirInformation.java
│   │   │       │   ├── Personne.java
│   │   │       │   ├── Passager.java
│   │   │       │   ├── Employe.java
│   │   │       │   ├── Pilote.java
│   │   │       │   ├── PersonnelCabine.java
│   │   │       │   ├── Aeroport.java
│   │   │       │   ├── Vol.java
│   │   │       │   ├── CourtCourrier.java
│   │   │       │   ├── MoyenCourrier.java
│   │   │       │   ├── LongCourrier.java
│   │   │       │   ├── Avion.java
│   │   │       │   ├── Equipage.java
│   │   │       │   ├── Reservation.java
│   │   │       │   └── CompagnieAerienne.java
│   │   │       └── service/
│   │   │           ├── FichierService.java
│   │   │           └── DatabaseService.java
│   │   └── resources/
│   └── test/
│       └── java/
│           └── com/isep/airline/
│               ├── PassagerTest.java
│               ├── VolTest.java
│               ├── CompagnieAerienneTest.java
│               ├── FichierServiceTest.java
│               └── DatabaseServiceTest.java
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
| **JUnit Jupiter** | 5.10.2 | Tests unitaires (JUnit 5 API) | [junit.org](https://junit.org/junit5/) |
| **H2 Database** | 2.2.224 | Base de données embarquée (JDBC, bonus) | [h2database.com](https://www.h2database.com/) |

> JFreeChart inclut automatiquement **JCommon** comme dépendance transitive.

### Plugins Maven

| Plugin | Version | Rôle |
|---|---|---|
| `maven-compiler-plugin` | 3.11.0 | Compilation Java 17 |
| `maven-jar-plugin` | 3.3.0 | Création du JAR exécutable |
| `maven-surefire-plugin` | 3.2.5 | Exécution des tests JUnit 5 |
| `maven-javadoc-plugin` | 3.6.3 | Génération de la Javadoc |

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

### 5. Générer la Javadoc

```bash
mvn javadoc:javadoc
```

La documentation sera générée dans `target/site/apidocs/`.

---

## 🆕 Fonctionnalités ajoutées (Séance 2)

### 1. 📁 Fichiers externes (Lecture / Écriture)

Service `FichierService` utilisant **Java I/O API** (`java.io` + `java.nio.file`) :

- **Export CSV** : passagers, vols, réservations
- **Import CSV** : lecture et création d'objets depuis fichiers CSV
- **Utilitaires** : lecture/écriture texte, copie, suppression, vérification d'existence
- Packages utilisés : `BufferedReader`, `BufferedWriter`, `Files`, `Path`, `StandardOpenOption`

### 2. 🧪 Tests unitaires — JUnit 5

5 classes de tests couvrant l'ensemble du projet :

| Classe de test | Couverture |
|---|---|
| `PassagerTest` | Constructeurs, setters, réservation/annulation |
| `VolTest` | Types de vols, polymorphisme, associations |
| `CompagnieAerienneTest` | CRUD aéroports, passagers, employés, avions |
| `FichierServiceTest` | Export/import CSV, opérations fichiers |
| `DatabaseServiceTest` | CRUD base de données H2 |

### 3. 🗄️ Base de données — H2 + JDBC (Bonus)

Service `DatabaseService` avec base de données embarquée H2 :

- **Tables** : `PASSAGERS`, `VOLS`, `RESERVATIONS`
- **Opérations** : insertion, lecture, recherche, comptage, mise à jour, suppression
- **API** : `java.sql` (Connection, PreparedStatement, ResultSet, DriverManager)

### 4. 📖 Javadoc complète

Toutes les classes du projet sont documentées avec :

- `@author`, `@version`, `@since`, `@see`
- `@param`, `@return` sur toutes les méthodes publiques
- Descriptions HTML avec listes `<ul><li>` pour les détails

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
