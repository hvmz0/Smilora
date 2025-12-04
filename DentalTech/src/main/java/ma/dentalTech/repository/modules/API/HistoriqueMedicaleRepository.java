package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.HistoriqueMedicale.HistoriqueMedicale;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

/**
 * Repository pour la gestion de l'historique médical
 * Author : Walid
 */
public interface HistoriqueMedicaleRepository extends CrudRepository<HistoriqueMedicale, Long> {

    /**
     * Trouve l'historique médical par patient
     */
    List<HistoriqueMedicale> findByPatientId(Long patientId);

    /**
     * Recherche dans l'historique par libellé
     */
    List<HistoriqueMedicale> searchByLibelle(String keyword);

    /**
     * Compte le nombre d'entrées pour un patient
     */
    long countByPatient(Long patientId);

    /**
     * Récupère les dernières entrées pour un patient
     */
    List<HistoriqueMedicale> findRecentByPatient(Long patientId, int limit);

    /**
     * Vérifie si un historique existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total d'entrées
     */
    long count();

    /**
     * Récupère une page d'historiques
     */
    List<HistoriqueMedicale> findPage(int limit, int offset);

    /**
     * Supprime tout l'historique d'un patient
     */
    void deleteByPatientId(Long patientId);
}