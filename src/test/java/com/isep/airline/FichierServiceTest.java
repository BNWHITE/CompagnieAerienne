package com.isep.airline;

import com.isep.airline.model.Passager;
import com.isep.airline.service.FichierService;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires pour le {@link FichierService}.
 *
 * <p>Vérifie les opérations de lecture/écriture CSV et les utilitaires fichiers.</p>
 *
 * @author  Équipe SkyISEP
 * @version 1.0
 * @since   2025
 */
@DisplayName("Tests FichierService (I/O fichiers)")
class FichierServiceTest {

    private FichierService fichierService;
    private static final String DOSSIER_TEST = "data/test_temp";
    private static final String FICHIER_PASSAGERS_TEST = DOSSIER_TEST + "/passagers_test.csv";
    private static final String FICHIER_TEXTE_TEST = DOSSIER_TEST + "/test.txt";

    @BeforeEach
    void setUp() {
        fichierService = new FichierService();
    }

    @AfterEach
    void tearDown() throws IOException {
        // Nettoyage des fichiers de test
        Path dossier = Paths.get(DOSSIER_TEST);
        if (Files.exists(dossier)) {
            Files.walk(dossier)
                    .sorted(java.util.Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(java.io.File::delete);
        }
    }

    // ── Export / Import Passagers CSV ────────────────────────────────────────

    @Test
    @DisplayName("Exporter puis importer des passagers en CSV conserve les données")
    void testExporterPuisImporterPassagers() throws IOException {
        // Préparer des données
        List<Passager> passagers = new ArrayList<>();
        passagers.add(new Passager("P001", "Dupont", "Jean", "jean@mail.com", "0600000000", "FR123"));
        passagers.add(new Passager("P002", "Martin", "Luc", "luc@mail.com", "0700000000", "FR456"));

        // Exporter
        fichierService.exporterPassagers(passagers, FICHIER_PASSAGERS_TEST);
        assertTrue(fichierService.fichierExiste(FICHIER_PASSAGERS_TEST), "Le fichier CSV doit exister après export");

        // Importer
        List<Passager> importes = fichierService.importerPassagers(FICHIER_PASSAGERS_TEST);
        assertEquals(2, importes.size(), "Doit retrouver 2 passagers");
        assertEquals("Dupont", importes.get(0).getNom());
        assertEquals("FR456", importes.get(1).getNumeroPasseport());
    }

    @Test
    @DisplayName("Importer depuis un fichier inexistant retourne une liste vide")
    void testImporterFichierInexistant() throws IOException {
        List<Passager> importes = fichierService.importerPassagers("data/inexistant.csv");
        assertNotNull(importes);
        assertTrue(importes.isEmpty());
    }

    // ── Lecture / Écriture texte brut ────────────────────────────────────────

    @Test
    @DisplayName("Écrire puis lire un fichier texte conserve le contenu")
    void testEcrirePuisLireTexte() throws IOException {
        String contenu = "Bonjour SkyISEP !\nLigne 2\nLigne 3";
        fichierService.ecrireFichierTexte(FICHIER_TEXTE_TEST, contenu);
        assertTrue(fichierService.fichierExiste(FICHIER_TEXTE_TEST));

        String lu = fichierService.lireFichierTexte(FICHIER_TEXTE_TEST);
        assertEquals(contenu, lu);
    }

    @Test
    @DisplayName("Lire un fichier inexistant retourne une chaîne vide")
    void testLireFichierInexistant() throws IOException {
        String resultat = fichierService.lireFichierTexte("data/nope.txt");
        assertEquals("", resultat);
    }

    // ── Utilitaires fichiers ────────────────────────────────────────────────

    @Test
    @DisplayName("fichierExiste() retourne false pour un chemin inexistant")
    void testFichierExisteFaux() {
        assertFalse(fichierService.fichierExiste("dossier_fantome/fichier_fantome.txt"));
    }

    @Test
    @DisplayName("tailleFichier() retourne la taille correcte")
    void testTailleFichier() throws IOException {
        fichierService.ecrireFichierTexte(FICHIER_TEXTE_TEST, "Hello");
        long taille = fichierService.tailleFichier(FICHIER_TEXTE_TEST);
        assertTrue(taille > 0, "La taille doit être > 0");
    }

    @Test
    @DisplayName("copierFichier() crée une copie identique")
    void testCopierFichier() throws IOException {
        fichierService.ecrireFichierTexte(FICHIER_TEXTE_TEST, "Contenu original");
        String copie = DOSSIER_TEST + "/copie.txt";
        fichierService.copierFichier(FICHIER_TEXTE_TEST, copie);

        assertTrue(fichierService.fichierExiste(copie));
        assertEquals("Contenu original", fichierService.lireFichierTexte(copie));
    }

    @Test
    @DisplayName("supprimerFichier() supprime le fichier et retourne true")
    void testSupprimerFichier() throws IOException {
        fichierService.ecrireFichierTexte(FICHIER_TEXTE_TEST, "Temporaire");
        assertTrue(fichierService.supprimerFichier(FICHIER_TEXTE_TEST));
        assertFalse(fichierService.fichierExiste(FICHIER_TEXTE_TEST));
    }

    @Test
    @DisplayName("supprimerFichier() retourne false si le fichier n'existe pas")
    void testSupprimerFichierInexistant() throws IOException {
        assertFalse(fichierService.supprimerFichier("data/fantome.txt"));
    }
}
