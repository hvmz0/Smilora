package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.PrescriptionMedicaments.PrescriptionMedicaments;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

/**
 * Repository pour la gestion des prescriptions de médicaments
 * Author : Abdo
 */
public interface PrescriptionMedicamentsRepository extends CrudRepository<PrescriptionMedicaments, Long> {

    /**
     * Trouve les prescriptions par ordonnance
     */
    List<PrescriptionMedicaments> findByOrdonnanceId(Long ordonnanceId);

    /**
     * Trouve les prescriptions par médicament
     */
    List<PrescriptionMedicaments> findByMedicamentId(Long medicamentId);

    /**
     * Trouve les prescriptions par fréquence
     */
    List<PrescriptionMedicaments> findByFrequence(String frequence);

    /**
     * Trouve les prescriptions par durée
     */
    List<PrescriptionMedicaments> findByDuree(String duree);

    /**
     * Trouve les prescriptions par unité
     */
    List<PrescriptionMedicaments> findByUnite(String unite);

    /**
     * Compte les prescriptions pour une ordonnance
     */
    long countByOrdonnance(Long ordonnanceId);

    /**
     * Compte combien de fois un médicament a été prescrit
     */
    long countByMedicament(Long medicamentId);

    /**
     * Supprime toutes les prescriptions d'une ordonnance
     */
    void deleteByOrdonnanceId(Long ordonnanceId);

    /**
     * Vérifie si une prescription existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de prescriptions
     */
    long count();

    /**
     * Récupère une page de prescriptions
     */
    List<PrescriptionMedicaments> findPage(int limit, int offset);
}