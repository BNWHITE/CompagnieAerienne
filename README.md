# ✈️ SkyISEP Airlines — Système de Réservation de Compagnie Aérienne

> **Projet Java — ISEP 2025/2026**
> Système complet de gestion d'une compagnie aérienne : vols, passagers, employés, avions, équipages, aéroports et réservations.

---

## 📑 Table des matières

1. [Présentation du projet](#-présentation-du-projet)
2. [Architecture et structure du projet](#-architecture-et-structure-du-projet)
3. [Diagramme UML des classes](#-diagramme-uml-des-classes)
4. [Concepts Java utilisés — Cours complet](#-concepts-java-utilisés--cours-complet)
   - [Classe et Objet](#1-classe-et-objet)
   - [Encapsulation (private, Getters, Setters)](#2-encapsulation-private-getters-setters)
   - [Constructeurs](#3-constructeurs)
   - [Héritage (extends)](#4-héritage-extends)
   - [Classe abstraite (abstract)](#5-classe-abstraite-abstract)
   - [Polymorphisme](#6-polymorphisme)
   - [Interface (implements)](#7-interface-implements)
   - [toString()](#8-tostring)
   - [String en Java](#9-string-en-java)
   - [Association, Agrégation, Composition](#10-association-agrégation-composition)
   - [Les Collections : List, ArrayList, Deque, ArrayDeque](#11-les-collections--list-arraylist-deque-arraydeque)
   - [Map et HashMap](#12-map-et-hashmap)
   - [Le mot-clé this](#13-le-mot-clé-this)
   - [Le mot-clé super](#14-le-mot-clé-super)
   - [Le mot-clé instanceof](#15-le-mot-clé-instanceof)
   - [StringBuilder](#16-stringbuilder)
   - [Try/Catch](#17-trycatch)
5. [Choix de conception et justifications](#-choix-de-conception-et-justifications)
6. [Relations entre les classes](#-relations-entre-les-classes)
7. [Questions du prof et réponses à préparer](#-questions-du-prof-et-réponses-à-préparer)
8. [Comment compiler et exécuter](#-comment-compiler-et-exécuter)
9. [Bonus : JFreeChart](#-bonus--jfreechart)
10. [Arborescence complète du projet](#-arborescence-complète-du-projet)

---

## 🎯 Présentation du projet

Ce projet implémente un **système de réservation pour une compagnie aérienne** en Java 17.
Il couvre toutes les opérations CRUD (Create, Read, Update, Delete) pour :

| Entité | Description |
|--------|-------------|
| **Aéroport** | Gestion des aéroports (code IATA, ville, pays) avec une file de vols (Deque) |
| **Vol** | Classe abstraite avec 3 sous-classes : CourtCourrier, MoyenCourrier, LongCourrier |
| **Passager** | Personnes qui réservent des vols |
| **Employé** | Pilotes et Personnel de cabine (polymorphisme) |
| **Avion** | Flotte d'avions avec gestion de disponibilité |
| **Équipage** | Composition d'un pilote + personnel de cabine |
| **Réservation** | Lien entre passager et vol(s) réservé(s) |
| **CompagnieAérienne** | Classe centrale qui orchestre tout |

---

## 🏗 Architecture et structure du projet

```
CompagnieAerienne/
├── pom.xml                          # Configuration Maven (Java 17, JFreeChart)
├── README.md                        # Ce fichier
└── src/
    └── main/
        └── java/
            └── com/
                └── isep/
                    └── airline/
                        ├── Main.java                    # Point d'entrée (menu console interactif)
                        ├── GraphiqueStatistiques.java   # BONUS : graphiques JFreeChart
                        └── model/
                            ├── ObtenirInformation.java  # Interface
                            ├── Personne.java            # Classe abstraite (mère)
                            ├── Passager.java            # Hérite de Personne
                            ├── Employe.java             # Hérite de Personne
                            ├── Pilote.java              # Hérite de Employe
                            ├── PersonnelCabine.java     # Hérite de Employe
                            ├── Aeroport.java            # Avec Deque<Vol>
                            ├── Vol.java                 # Classe abstraite
                            ├── CourtCourrier.java       # Hérite de Vol
                            ├── MoyenCourrier.java       # Hérite de Vol
                            ├── LongCourrier.java        # Hérite de Vol
                            ├── Avion.java               # Gestion de la flotte
                            ├── Equipage.java            # Pilote + PersonnelCabine
                            ├── Reservation.java         # Passager + Vol(s)
                            └── CompagnieAerienne.java   # Classe principale de gestion
```

---

## 📊 Diagramme UML des classes

```
                        ┌───────────────────────┐
                        │  «interface»           │
                        │  ObtenirInformation    │
                        ├───────────────────────┤
                        │ +obtenirInformation() │
                        │       : String         │
                        └───────────┬───────────┘
                                    │ implements
          ┌─────────────────────────┼─────────────────────────┐
          │                         │                         │
┌─────────┴──────────┐  ┌──────────┴──────────┐   ┌─────────┴─────────┐
│  «abstract»        │  │     Aeroport        │   │      Avion        │
│  Personne          │  ├─────────────────────┤   ├───────────────────┤
├────────────────────┤  │ -codeIATA: String   │   │ -immatriculation  │
│ -id: String        │  │ -nom: String        │   │ -modele: String   │
│ -nom: String       │  │ -ville: String      │   │ -capacite: int    │
│ -prenom: String    │  │ -pays: String       │   │ -disponible: bool │
│ -email: String     │  │ -volsDepart: Deque  │   │ -volsAffectes:List│
│ -telephone: String │  │ -volsArrivee: Deque │   └───────────────────┘
└────────┬───────────┘  └─────────────────────┘
         │ extends
    ┌────┴──────────────────┐
    │                       │
┌───┴──────────┐    ┌──────┴───────────┐
│  Passager    │    │    Employe       │
├──────────────┤    ├──────────────────┤
│ -passeport   │    │ -numeroEmploye   │
│ -reservations│    │ -role: String    │
│  : List      │    │ -salaire: String │
└──────────────┘    │ -vols: List<Vol> │
                    │ +obtenirRole()   │    ← polymorphisme
                    └────────┬─────────┘
                             │ extends
                    ┌────────┴─────────┐
                    │                  │
            ┌───────┴──────┐  ┌───────┴──────────────┐
            │   Pilote     │  │  PersonnelCabine     │
            ├──────────────┤  ├──────────────────────┤
            │ -licence     │  │ -qualification       │
            │ -heuresDeVol │  │ -anneesExperience    │
            │ +obtenirRole │  │ +obtenirRole()       │
            └──────────────┘  └──────────────────────┘

┌──────────────────────────┐
│  «abstract» Vol          │ ◄── implements ObtenirInformation
├──────────────────────────┤
│ -numeroVol: String       │
│ -aeroportDepart: Aeroport│ ←── agrégation avec Aeroport
│ -aeroportArrivee:Aeroport│
│ -prix: String            │
│ -statut: String          │
│ -avion: Avion            │ ←── composition
│ -equipage: Equipage      │ ←── composition
│ -passagers: List         │
│ -employes: List<Employe> │ ←── association bidirectionnelle
│ +getTypeVol(): abstract  │
└────────────┬─────────────┘
             │ extends
    ┌────────┼────────────┐
    │        │            │
┌───┴────┐ ┌┴────────┐ ┌─┴──────────┐
│ Court  │ │ Moyen   │ │   Long     │
│Courrier│ │Courrier │ │  Courrier  │
├────────┤ ├─────────┤ ├────────────┤
│distance│ │distance │ │ distance   │
│collat. │ │repas    │ │ nombreRepas│
│        │ │classeAff│ │ 1èreClasse │
│        │ │         │ │ divertiss. │
└────────┘ └─────────┘ └────────────┘
```

---

## 📘 Concepts Java utilisés — Cours complet

### 1. Classe et Objet

**Une classe** est un modèle (un plan) qui décrit les attributs (données) et les méthodes (comportements) d'un type d'objet.

**Un objet** est une instance concrète d'une classe, créée avec `new`.

```java
// La classe est le modèle :
public class Avion {
    private String immatriculation;
    private String modele;
    private int capacite;
}

// L'objet est l'instance concrète :
Avion monAvion = new Avion("F-GKXA", "Airbus A320", 180);
```

**Dans notre projet :**
- `Avion`, `Passager`, `Vol`, `Aeroport`… sont des **classes**
- Quand on écrit `new Avion("F-GKXA", "Airbus A320", 180)`, on crée un **objet**

---

### 2. Encapsulation (private, Getters, Setters)

L'**encapsulation** est le principe de cacher les données internes d'un objet et de ne les exposer qu'à travers des méthodes contrôlées.

#### Pourquoi ?
- **Protéger les données** : empêcher qu'on modifie un attribut n'importe comment
- **Contrôler l'accès** : on peut ajouter des validations dans les setters
- **Cacher l'implémentation** : on peut changer la structure interne sans casser le code qui utilise la classe

#### Comment ?
- Les attributs sont déclarés **`private`** → inaccessibles directement depuis l'extérieur
- On fournit des **getters** (pour lire) et des **setters** (pour modifier)

```java
public class Personne {
    private String nom;     // ❌ On ne peut PAS faire personne.nom = "Dupont" depuis l'extérieur
    private String email;

    // GETTER : permet de LIRE l'attribut
    public String getNom() {
        return nom;
    }

    // SETTER : permet de MODIFIER l'attribut (avec contrôle possible)
    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        // On pourrait ajouter une validation ici :
        // if (!email.contains("@")) throw new IllegalArgumentException("Email invalide");
        this.email = email;
    }
}
```

**Convention de nommage :**
- Getter : `get` + nom de l'attribut avec majuscule → `getNom()`, `getEmail()`
- Setter : `set` + nom de l'attribut avec majuscule → `setNom()`, `setEmail()`
- Pour les booléens : `is` au lieu de `get` → `isDisponible()`, `isCollationIncluse()`

**Dans notre projet :** Toutes les classes utilisent l'encapsulation. Tous les attributs sont `private`, tous ont des getters/setters.

---

### 3. Constructeurs

Un **constructeur** est une méthode spéciale appelée automatiquement lors de la création d'un objet avec `new`. Il porte le même nom que la classe et n'a pas de type de retour.

```java
public class Pilote extends Employe {
    private String licence;
    private String heuresDeVol;

    // Constructeur par défaut (sans paramètres)
    public Pilote() {
        super();             // appelle le constructeur de Employe
        setRole("Pilote");
    }

    // Constructeur paramétré (avec arguments)
    public Pilote(String id, String nom, String prenom, String email, String telephone,
                  String numeroEmploye, String salaire, String licence, String heuresDeVol) {
        super(id, nom, prenom, email, telephone, numeroEmploye, "Pilote", salaire);
        this.licence = licence;
        this.heuresDeVol = heuresDeVol;
    }
}
```

**Points clés :**
- Si on ne définit aucun constructeur, Java en crée un par défaut (vide)
- `super(...)` appelle le constructeur de la classe mère
- `this.attribut = parametre` distingue l'attribut de l'objet du paramètre de la méthode

---

### 4. Héritage (extends)

L'**héritage** permet à une classe (classe fille) de récupérer tous les attributs et méthodes d'une autre classe (classe mère).

```
Personne (classe mère)
   ├── Passager (classe fille)
   └── Employe (classe fille)
          ├── Pilote (sous-classe de Employe)
          └── PersonnelCabine (sous-classe de Employe)
```

```java
public abstract class Personne { ... }         // Classe mère
public class Passager extends Personne { ... } // Hérite de Personne
public class Employe extends Personne { ... }  // Hérite de Personne
public class Pilote extends Employe { ... }    // Hérite de Employe → qui hérite de Personne
```

**Ce que la classe fille récupère :**
- Tous les attributs (même privés, mais accessibles via getters/setters)
- Toutes les méthodes publiques et protégées
- Les constructeurs ne sont PAS hérités (on utilise `super(...)`)

**Pourquoi hériter ?**
- Éviter la duplication de code (DRY — Don't Repeat Yourself)
- `Pilote` et `PersonnelCabine` partagent les attributs de `Employe` (numéroEmployé, salaire…) et de `Personne` (nom, prénom, email…)

---

### 5. Classe abstraite (abstract)

Une **classe abstraite** est une classe qu'on **ne peut pas instancier directement**. Elle sert de modèle pour ses sous-classes.

```java
// ❌ IMPOSSIBLE : new Personne(...) → erreur de compilation
public abstract class Personne implements ObtenirInformation { ... }

// ❌ IMPOSSIBLE : new Vol(...) → erreur de compilation
public abstract class Vol implements ObtenirInformation {
    public abstract String getTypeVol();  // Méthode abstraite = pas de corps
}

// ✅ OK : on instancie les sous-classes concrètes
Passager p = new Passager("P001", "Moreau", "Alice", ...);
Vol v = new LongCourrier("SI101", cdg, jfk, ...);
```

**Méthode abstraite :** Une méthode déclarée sans corps (`abstract`). Les sous-classes **doivent** l'implémenter.

```java
// Dans Vol.java (abstraite) :
public abstract String getTypeVol();

// Dans CourtCourrier.java (concrète) :
@Override
public String getTypeVol() {
    return "Court Courrier";
}

// Dans LongCourrier.java (concrète) :
@Override
public String getTypeVol() {
    return "Long Courrier";
}
```

**Dans notre projet :**
- `Personne` est abstraite → on crée des `Passager` ou `Employe`
- `Vol` est abstraite → on crée des `CourtCourrier`, `MoyenCourrier` ou `LongCourrier`

---

### 6. Polymorphisme

Le **polymorphisme** signifie "plusieurs formes". Un même appel de méthode peut avoir un comportement différent selon le type réel de l'objet.

**C'est LE concept clé demandé par le prof :**
> *"Il y a un seul polymorphisme : juste entre pilote et personnel cabine"*

```java
// Même type déclaré : Employe
// Mais type réel différent : Pilote ou PersonnelCabine
List<Employe> employes = compagnie.listerEmployes();

for (Employe employe : employes) {
    // Le MÊME appel, mais résultat DIFFÉRENT selon le type réel
    System.out.println(employe.obtenirRole());
    // Si c'est un Pilote → "Pilote (Licence: ATPL-001, Heures de vol: 5000)"
    // Si c'est un PersonnelCabine → "Personnel Cabine (Qualification: Chef de cabine, Expérience: 10 ans)"
}
```

**Comment ça marche ?**
1. `Employe` définit `obtenirRole()` qui retourne `this.role`
2. `Pilote` **surcharge** (`@Override`) `obtenirRole()` pour retourner licence + heures
3. `PersonnelCabine` **surcharge** `obtenirRole()` pour retourner qualification + expérience
4. À l'exécution, Java appelle la bonne version selon le **type réel** de l'objet

**La démonstration est dans le menu 9** de l'application : elle itère sur tous les employés et montre que `obtenirRole()` retourne un résultat différent selon que l'objet est un Pilote ou un PersonnelCabine.

---

### 7. Interface (implements)

Une **interface** définit un **contrat** : elle impose des méthodes que les classes doivent implémenter, sans fournir d'implémentation.

```java
// L'interface définit le contrat
public interface ObtenirInformation {
    String obtenirInformation();  // Pas de corps — juste la signature
}

// Les classes signent le contrat avec "implements"
public class Avion implements ObtenirInformation {
    @Override
    public String obtenirInformation() {
        return "Avion " + immatriculation + " - " + modele;
    }
}

public abstract class Personne implements ObtenirInformation {
    @Override
    public String obtenirInformation() {
        return "Personne " + nom + " " + prenom;
    }
}
```

**Différence interface vs classe abstraite :**

| | Interface | Classe abstraite |
|---|---|---|
| Instanciation | ❌ Non | ❌ Non |
| Attributs | Pas d'attributs d'instance | ✅ Oui |
| Méthodes | Signatures uniquement (par défaut) | Peut avoir des méthodes avec corps |
| Héritage multiple | ✅ Une classe peut implémenter plusieurs interfaces | ❌ Une classe ne peut étendre qu'une seule classe |
| Utilisation | Définir un **contrat** | Définir un **modèle commun** avec du code partagé |

**Dans notre projet :** Toutes les classes implémentent `ObtenirInformation` → elles ont toutes une méthode `obtenirInformation()` qui retourne leurs infos sous forme de String.

---

### 8. toString()

`toString()` est une méthode héritée de `Object` (la classe mère de toutes les classes Java). Elle est appelée automatiquement quand on essaie d'afficher un objet.

```java
Avion avion = new Avion("F-GKXA", "Airbus A320", 180);
System.out.println(avion);  // Appelle automatiquement avion.toString()
```

**Remarque du prof :**
> *"Obtenir des infos : toString (afficher les infos du proprio)"*

Dans notre projet, `toString()` **délègue** à `obtenirInformation()` :

```java
@Override
public String toString() {
    return obtenirInformation();  // toString() appelle notre méthode de l'interface
}
```

**Pourquoi ?** Parce que le prof veut que :
1. L'interface `ObtenirInformation` impose `obtenirInformation()`
2. `toString()` utilise `obtenirInformation()` pour l'affichage
3. Quand on fait `System.out.println(objet)`, ça affiche les infos complètes

---

### 9. String en Java

En Java, `String` est une **classe** (pas un type primitif). C'est une chaîne de caractères **immuable** (immutable) : une fois créée, elle ne peut pas être modifiée.

```java
String nom = "Dupont";        // Création par littéral
String prenom = new String("Jean"); // Création par constructeur (moins courant)
```

**Remarque du prof :**
> *"Les attributs sont en String"*

C'est pourquoi dans notre projet, même des valeurs numériques comme le salaire, les heures de vol ou le prix sont stockées en `String` :

```java
private String salaire;        // "8000" au lieu de double salaire = 8000.0
private String heuresDeVol;    // "5000" au lieu de int heuresDeVol = 5000
private String prix;           // "450"  au lieu de double prix = 450.0
```

**Méthodes utiles de String :**
- `equals()` — comparer deux strings (⚠️ jamais `==` pour comparer des strings !)
- `isEmpty()` — vérifier si vide
- `trim()` — supprimer les espaces autour
- `contains()` — vérifier si contient une sous-chaîne

---

### 10. Association, Agrégation, Composition

Ce sont les **3 types de relations** entre classes en POO :

#### Association (relation simple "utilise")
Deux classes sont liées, mais peuvent exister indépendamment.

```java
public class Vol {
    private List<Passager> passagers;   // Un vol a des passagers
    private List<Employe> employes;     // Un vol a des employés
}
```

#### Agrégation (relation "a un", faible)
Un objet "contient" un autre, mais l'objet contenu **peut exister** sans le conteneur. Si on supprime le conteneur, l'objet contenu survit.

```java
public class Vol {
    private Aeroport aeroportDepart;    // Le vol utilise un aéroport,
    private Aeroport aeroportArrivee;   // mais l'aéroport existe sans le vol
}
```

> *Le prof dit : "Vol, Airport : on peut faire une agrégation avec 2 associations"*

#### Composition (relation "a un", forte)
Un objet fait **partie intégrante** d'un autre. Si on supprime le conteneur, l'objet contenu est détruit aussi.

```java
public class Vol {
    private Equipage equipage;  // L'équipage est spécifique à ce vol
    private Avion avion;        // L'avion est affecté à ce vol
}
```

**Résumé visuel :**

| Relation | Force | Symbole UML | Exemple |
|----------|-------|-------------|---------|
| Association | Faible | `———>` | Vol → Passager |
| Agrégation | Moyenne | `◇———>` | Vol ◇→ Aeroport |
| Composition | Forte | `◆———>` | Vol ◆→ Equipage |

---

### 11. Les Collections : List, ArrayList, Deque, ArrayDeque

#### List (interface) vs ArrayList (implémentation)

`List` est une **interface** qui définit les opérations d'une liste ordonnée.
`ArrayList` est une **classe concrète** qui implémente `List` en utilisant un tableau dynamique.

**Remarque du prof :**
> *"Relation entre Vol et Pilote : c'est List et pas ArrayList"*

```java
// ✅ CORRECT — déclaré avec List (interface)
private List<Vol> vols;
vols = new ArrayList<>();

// ❌ INCORRECT pour le prof — déclaré avec ArrayList (implémentation)
private ArrayList<Vol> vols;
```

**Pourquoi déclarer avec `List` ?**
- **Flexibilité** : on peut changer l'implémentation (`LinkedList`, `Vector`…) sans modifier le reste du code
- **Programmation par contrat** : on programme contre l'interface, pas contre l'implémentation
- C'est une **bonne pratique** Java universellement reconnue

**Opérations principales de List :**

| Méthode | Description |
|---------|-------------|
| `add(element)` | Ajouter un élément à la fin |
| `get(index)` | Accéder par index |
| `remove(index)` ou `remove(element)` | Supprimer |
| `size()` | Nombre d'éléments |
| `isEmpty()` | Vérifier si vide |
| `contains(element)` | Vérifier si contient |

#### Deque (interface) vs ArrayDeque (implémentation)

`Deque` (**D**ouble **E**nded **Que**ue) est une file à double entrée : on peut ajouter/retirer des éléments aux deux extrémités.

**Remarque du prof :**
> *"Entre Vol et Airport, on peut faire un Deque"*

```java
public class Aeroport {
    private Deque<Vol> volsDepart;    // File des vols au départ
    private Deque<Vol> volsArrivee;   // File des vols à l'arrivée

    public Aeroport() {
        this.volsDepart = new ArrayDeque<>();   // ArrayDeque = implémentation de Deque
        this.volsArrivee = new ArrayDeque<>();
    }
}
```

**Pourquoi un Deque pour les vols d'un aéroport ?**
- Les vols arrivent dans un **ordre** (file d'attente → FIFO)
- On peut consulter le **prochain** vol au départ (`peekFirst()`)
- On peut ajouter un vol en fin de file (`addLast()`)
- C'est plus **réaliste** qu'une simple liste pour modéliser un tableau de vols

**Opérations principales de Deque :**

| Méthode | Description |
|---------|-------------|
| `addFirst(e)` | Ajouter en tête |
| `addLast(e)` | Ajouter en queue |
| `peekFirst()` | Consulter le premier (sans retirer) |
| `peekLast()` | Consulter le dernier (sans retirer) |
| `pollFirst()` | Retirer le premier |
| `pollLast()` | Retirer le dernier |
| `size()` | Taille |
| `remove(e)` | Supprimer un élément |

#### Comparaison List vs Deque

| Critère | List (ArrayList) | Deque (ArrayDeque) |
|---------|------------------|--------------------|
| Accès par index | ✅ `O(1)` | ❌ Pas d'accès par index |
| Ajout en fin | ✅ `O(1)` | ✅ `O(1)` |
| Ajout en début | ❌ `O(n)` | ✅ `O(1)` |
| Retrait en début | ❌ `O(n)` | ✅ `O(1)` |
| Utilisation idéale | Collection indexée | File d'attente (FIFO/LIFO) |

---

### 12. Map et HashMap

Une `Map` est une structure clé → valeur. `HashMap` est l'implémentation la plus courante.

```java
// Compter les vols par type
Map<String, Integer> stats = new HashMap<>();
for (Vol v : vols) {
    stats.merge(v.getTypeVol(), 1, Integer::sum);
}
// Résultat : {"Court Courrier": 1, "Moyen Courrier": 1, "Long Courrier": 2}
```

**Dans notre projet :** Utilisée dans `CompagnieAerienne` pour les statistiques (`getStatistiquesTypesVols()`, `getDestinationsPopulaires()`).

---

### 13. Le mot-clé `this`

`this` fait référence à **l'instance courante** de l'objet. Il est utilisé pour :

```java
public class Avion {
    private String modele;

    public Avion(String modele) {
        this.modele = modele;  // this.modele = l'attribut de l'objet
                                // modele = le paramètre du constructeur
    }
}
```

---

### 14. Le mot-clé `super`

`super` fait référence à **la classe mère**. Il est utilisé pour :

```java
public class Pilote extends Employe {
    public Pilote(String id, String nom, ...) {
        super(id, nom, ...);    // Appelle le constructeur de Employe
    }

    @Override
    public String obtenirInformation() {
        return super.obtenirInformation() + "\n"  // Appelle obtenirInformation() de Employe
                + "Licence: " + licence;
    }
}
```

**Chaîne d'appels dans notre projet :**
`Pilote.obtenirInformation()` → appelle `super` → `Employe.obtenirInformation()` → appelle `super` → `Personne.obtenirInformation()`

---

### 15. Le mot-clé `instanceof`

`instanceof` vérifie le **type réel** d'un objet à l'exécution.

```java
Employe emp = compagnie.rechercherEmploye("E001");

if (emp instanceof Pilote pilote) {
    // "pilote" est automatiquement casté en Pilote (pattern matching Java 16+)
    System.out.println("Licence : " + pilote.getLicence());
} else if (emp instanceof PersonnelCabine pc) {
    System.out.println("Qualification : " + pc.getQualification());
}
```

**Dans notre projet :** Utilisé dans les menus pour vérifier si un employé est un Pilote ou un PersonnelCabine.

---

### 16. StringBuilder

`StringBuilder` permet de construire des chaînes de caractères de manière **efficace**, sans créer plein d'objets String intermédiaires.

```java
// ❌ Moins efficace (crée un nouvel objet String à chaque +)
String result = "A" + "B" + "C" + "D";

// ✅ Plus efficace avec StringBuilder
StringBuilder sb = new StringBuilder();
sb.append("A").append("B").append("C").append("D");
String result = sb.toString();
```

**Dans notre projet :** Utilisé dans `Equipage.obtenirInformation()` et `Reservation.obtenirInformation()` pour construire des strings complexes.

---

### 17. Try/Catch

`try/catch` permet de **gérer les erreurs** (exceptions) sans que le programme ne crash.

```java
public double getRevenusGeneres() {
    double revenus = 0;
    for (Reservation r : reservations) {
        try {
            revenus += Double.parseDouble(r.getMontantTotal());  // Peut échouer si c'est pas un nombre
        } catch (NumberFormatException e) {
            // On ignore les montants non numériques au lieu de crasher
        }
    }
    return revenus;
}
```

**Pourquoi c'est nécessaire ici ?** Parce que le prix est un `String` (demandé par le prof). Quand on veut calculer des revenus, on doit convertir avec `Double.parseDouble()`, qui peut lancer une `NumberFormatException` si le String n'est pas un nombre valide.

---

## 🎯 Choix de conception et justifications

| Choix | Justification | Demandé par le prof ? |
|-------|--------------|----------------------|
| `Vol` est `abstract` | On ne crée jamais un "Vol" générique, toujours un Court/Moyen/Long Courrier | ✅ *"Entre court, moyen et long courrier il y a une différence de structure"* |
| `Personne` est `abstract` | On ne crée jamais une "Personne" générique, toujours un Passager ou Employe | ✅ bonne pratique |
| Tous les attributs en `String` | Même salaire, heures de vol, prix… | ✅ *"Les attributs sont en String"* |
| `List` au lieu de `ArrayList` comme type déclaré | Programmer contre l'interface, pas l'implémentation | ✅ *"C'est List et pas ArrayList"* |
| `Deque<Vol>` dans Aeroport | File de vols plus réaliste qu'une liste | ✅ *"Entre Vol et Airport, on peut faire un Deque"* |
| Interface `ObtenirInformation` | Contrat commun pour toutes les classes | ✅ *"Obtenir-information : interface"* |
| `toString()` → `obtenirInformation()` | Affichage automatique via l'interface | ✅ *"Obtenir des infos : toString"* |
| Association bidirectionnelle Vol ↔ Employe | Relation directe dans les deux sens | ✅ *"Ajouter des relations entre Vol et Employé"* |
| Association bidirectionnelle Vol ↔ Aeroport | Le Vol connaît ses aéroports, l'Aéroport connaît ses vols | ✅ *"Il faut dans la class Vol avoir un objet Airport"* |
| Polymorphisme Pilote/PersonnelCabine | `obtenirRole()` surchargé dans les deux | ✅ *"Un seul polymorphisme : juste entre pilote et personnel cabine"* |

---

## 🔗 Relations entre les classes

| Classe A | Relation | Classe B | Type | Explication |
|----------|----------|----------|------|-------------|
| `Personne` | héritage | `Passager` | extends | Passager EST une Personne |
| `Personne` | héritage | `Employe` | extends | Employé EST une Personne |
| `Employe` | héritage | `Pilote` | extends | Pilote EST un Employé |
| `Employe` | héritage | `PersonnelCabine` | extends | PC EST un Employé |
| `Vol` | héritage | `CourtCourrier` | extends | CC EST un Vol |
| `Vol` | héritage | `MoyenCourrier` | extends | MC EST un Vol |
| `Vol` | héritage | `LongCourrier` | extends | LC EST un Vol |
| `Vol` | agrégation | `Aeroport` | `aeroportDepart`, `aeroportArrivee` | Vol A UN aéroport (qui existe indépendamment) |
| `Aeroport` | agrégation | `Vol` | `Deque<Vol> volsDepart/Arrivee` | Aéroport A DES vols (bidirectionnel) |
| `Vol` | composition | `Avion` | `avion` | Vol A UN avion (affecté spécifiquement) |
| `Vol` | composition | `Equipage` | `equipage` | Vol A UN équipage |
| `Vol` | association | `Passager` | `List<Passager>` | Vol A DES passagers |
| `Vol` | association | `Employe` | `List<Employe>` | Vol A DES employés (bidirectionnel) |
| `Employe` | association | `Vol` | `List<Vol>` | Employé A DES vols (bidirectionnel) |
| `Equipage` | composition | `Pilote` + `PersonnelCabine` | attributs | Équipage composé d'un pilote et de PC |
| `Reservation` | association | `Passager` + `Vol` | attributs | Réservation lie un passager à des vols |
| `CompagnieAerienne` | composition | toutes les listes | `List<...>` | La compagnie gère tout |
| Toutes | interface | `ObtenirInformation` | implements | Contrat commun |

---

## ❓ Questions du prof et réponses à préparer

### Questions sur l'architecture

**Q : Pourquoi Vol est une classe abstraite ?**
> Parce qu'on ne crée jamais un "Vol" générique. Un vol est toujours court, moyen ou long courrier, et chacun a une structure différente (attributs spécifiques). Le mot-clé `abstract` empêche d'instancier Vol directement et force à utiliser une sous-classe.

**Q : Quelle est la différence entre CourtCourrier, MoyenCourrier et LongCourrier ?**
> Ce sont des sous-classes de Vol avec des **structures différentes** :
> - CourtCourrier : `distanceKm`, `collationIncluse` (pas de repas, pas de classe affaires)
> - MoyenCourrier : `distanceKm`, `repasInclus`, `classeAffairesDisponible`
> - LongCourrier : `distanceKm`, `nombreRepas`, `premiereClasseDisponible`, `divertissementABord`

**Q : Pourquoi utiliser une interface ObtenirInformation ?**
> Pour définir un **contrat** : toutes les classes métier doivent fournir une méthode `obtenirInformation()`. Cela garantit un comportement uniforme. De plus, `toString()` délègue à cette méthode pour que `System.out.println(objet)` affiche les infos.

**Q : Où est le polymorphisme ?**
> Entre `Pilote` et `PersonnelCabine`. Les deux héritent de `Employe` et surchargent `obtenirRole()`. Quand on appelle `employe.obtenirRole()` sur une `List<Employe>`, le résultat dépend du type réel (Pilote ou PersonnelCabine). C'est démontré dans le menu 9.

**Q : Pourquoi `List` et pas `ArrayList` ?**
> On déclare avec `List` (l'interface) pour la **flexibilité** : on pourrait remplacer `ArrayList` par `LinkedList` sans modifier le reste du code. C'est le principe de "programmer contre l'interface". L'instanciation se fait avec `new ArrayList<>()`.

**Q : Pourquoi un `Deque` entre Aeroport et Vol ?**
> Un `Deque` (Double-Ended Queue) modélise mieux une **file de vols** qu'une simple liste :
> - `addLast()` pour ajouter un vol à la file
> - `peekFirst()` pour voir le prochain vol au départ
> - Insertion/retrait en O(1) aux deux extrémités

### Questions sur les relations

**Q : Quelle est la relation entre Vol et Aeroport ?**
> C'est une **agrégation bidirectionnelle** : Vol a des références vers ses aéroports (départ et arrivée), et chaque Aéroport maintient une Deque de ses vols. L'aéroport existe indépendamment du vol.

**Q : Quelle est la relation entre Vol et Employe ?**
> C'est une **association bidirectionnelle** : Vol a une `List<Employe>` et Employe a une `List<Vol>`. Quand on ajoute un employé à un vol, le vol est aussi ajouté à la liste des vols de l'employé.

**Q : Quelle est la différence entre agrégation et composition ici ?**
> - **Agrégation** (Vol → Aeroport) : l'aéroport existe même si le vol est supprimé
> - **Composition** (Vol → Equipage) : l'équipage est créé pour un vol spécifique, il est lié au cycle de vie du vol

### Questions sur le code

**Q : Pourquoi tous les attributs sont en String ?**
> C'est une demande du prof. Même les valeurs numériques (salaire, heures de vol, prix) sont en String. Quand on a besoin de faire des calculs, on utilise `Double.parseDouble()` avec un `try/catch`.

**Q : Que fait `@Override` ?**
> L'annotation `@Override` indique qu'on **surcharge** une méthode de la classe mère ou de l'interface. Le compilateur vérifie qu'on surcharge bien une méthode existante (protection contre les fautes de frappe).

**Q : Comment fonctionne la chaîne d'héritage de obtenirInformation() ?**
> `Pilote.obtenirInformation()` appelle `super.obtenirInformation()` (Employe), qui appelle `super.obtenirInformation()` (Personne). Chaque niveau ajoute ses propres infos. Le résultat final contient : infos personne + infos employé + infos pilote.

**Q : Pourquoi toString() appelle obtenirInformation() ?**
> Pour que `System.out.println(objet)` affiche automatiquement les infos via l'interface. `toString()` est appelé implicitement par Java, et il délègue à notre méthode d'interface.

---

## ⚙️ Comment compiler et exécuter

### Avec Maven (recommandé pour IntelliJ)
```bash
# Compiler
mvn compile

# Exécuter
mvn exec:java -Dexec.mainClass="com.isep.airline.Main"

# Ou packager en JAR
mvn package
java -jar target/compagnie-aerienne-1.0-SNAPSHOT.jar
```

### Avec javac (sans Maven)
```bash
cd CompagnieAerienne

# Compiler tous les fichiers (sauf GraphiqueStatistiques qui nécessite JFreeChart)
mkdir -p target/classes
javac -d target/classes --source 17 $(find src/main/java -name "*.java" ! -name "GraphiqueStatistiques.java")

# Exécuter
java -cp target/classes com.isep.airline.Main
```

### Avec IntelliJ IDEA
1. Ouvrir le dossier `CompagnieAerienne/` comme projet Maven
2. IntelliJ détecte automatiquement le `pom.xml`
3. Clic droit sur `Main.java` → Run 'Main.main()'

---

## 📈 Bonus : JFreeChart

La classe `GraphiqueStatistiques.java` utilise **JFreeChart 1.5.4** pour générer des graphiques :

- **Pie Chart** : Répartition des types de vols (Court/Moyen/Long Courrier)
- **Bar Chart** : Vols par statut (Planifié, En cours, Terminé, Annulé)
- **Bar Chart** : Destinations les plus populaires
- **Bar Chart** : Revenus par type de vol
- **Bar Chart** : Réservations par statut

La dépendance est déclarée dans le `pom.xml` :
```xml
<dependency>
    <groupId>org.jfree</groupId>
    <artifactId>jfreechart</artifactId>
    <version>1.5.4</version>
</dependency>
```

---

## 📁 Arborescence complète du projet

```
CompagnieAerienne/
├── pom.xml
├── README.md
└── src/
    └── main/
        └── java/
            └── com/isep/airline/
                ├── Main.java                        (763 lignes — menu console interactif)
                ├── GraphiqueStatistiques.java        (243 lignes — BONUS JFreeChart)
                └── model/
                    ├── ObtenirInformation.java       (18 lignes — interface)
                    ├── Personne.java                 (100 lignes — classe abstraite mère)
                    ├── Passager.java                 (126 lignes — hérite de Personne)
                    ├── Employe.java                  (150 lignes — hérite de Personne)
                    ├── Pilote.java                   (70 lignes — hérite de Employe)
                    ├── PersonnelCabine.java          (70 lignes — hérite de Employe)
                    ├── Aeroport.java                 (158 lignes — avec Deque<Vol>)
                    ├── Vol.java                      (317 lignes — classe abstraite)
                    ├── CourtCourrier.java             (65 lignes — hérite de Vol)
                    ├── MoyenCourrier.java             (80 lignes — hérite de Vol)
                    ├── LongCourrier.java              (95 lignes — hérite de Vol)
                    ├── Avion.java                    (140 lignes)
                    ├── Equipage.java                 (125 lignes)
                    ├── Reservation.java              (140 lignes)
                    └── CompagnieAerienne.java        (669 lignes — classe centrale)
```

**Total : ~3 330 lignes de Java** réparties sur 17 fichiers.

---

## 👨‍💻 Auteur

Projet réalisé dans le cadre du cours de Java — **ISEP 2025/2026**

---

*Ce README fait office de documentation complète et de support de révision pour la soutenance.*
