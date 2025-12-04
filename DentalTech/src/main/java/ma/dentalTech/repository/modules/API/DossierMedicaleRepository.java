package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des dossiers médicaux
 * Author : Walid
 */
public interface DossierMedicaleRepository extends CrudRepository<DossierMedicale, Long> {

    /**
     * Trouve un dossier médical par patient (relation 1:1)
     */
    Optional<DossierMedicale> findByPatientId(Long patientId);

    /**
     * Trouve les dossiers par localité
     */
    List<DossierMedicale> findByLocalite(String localite);

    /**
     * Trouve les dossiers créés à une date donnée
     */
    List<DossierMedicale> findByDateCreation(LocalDate date);

    /**
     * Trouve les dossiers créés dans une plage de dates
     */
    List<DossierMedicale> findByDateCreationRange(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Recherche des dossiers par localité (LIKE)
     */
    List<DossierMedicale> searchByLocalite(String keyword);

    /**
     * Compte les dossiers par localité
     */
    long countByLocalite(String localite);

    /**
     * Récupère les dossiers les plus récents
     */
    List<DossierMedicale> findRecent(int limit);

    /**
     * Vérifie si un dossier existe pour un patient
     */
    boolean existsByPatientId(Long patientId);

    /**
     * Vérifie si un dossier existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de dossiers
     */
    long count();

    /**
     * Récupère une page de dossiers
     */
    List<DossierMedicale> findPage(int limit, int offset);
}