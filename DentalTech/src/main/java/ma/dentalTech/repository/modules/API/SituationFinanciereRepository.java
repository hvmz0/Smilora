package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.SituationFinanciere.SituationFinanciere;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion de la situation financière des patients
 * Author : Ayoub
 */
public interface SituationFinanciereRepository extends CrudRepository<SituationFinanciere, Long> {

    /**
     * Trouve la situation financière par patient (relation 1:1)
     */
    Optional<SituationFinanciere> findByPatientId(Long patientId);

    /**
     * Trouve les situations par statut
     */
    List<SituationFinanciere> findByStatut(String statut);

    /**
     * Trouve les situations par état
     */
    List<SituationFinanciere> findByEtat(String etat);

    /**
     * Trouve les situations avec reste dû supérieur à un montant
     */
    List<SituationFinanciere> findByResteDuMin(Double resteDuMin);

    /**
     * Trouve les situations avec crédit
     */
    List<SituationFinanciere> findWithCredit();

    /**
     * Trouve les situations débitrices (resteDu > 0)
     */
    List<SituationFinanciere> findDebitrices();

    /**
     * Trouve les situations avec crédit disponible
     */
    List<SituationFinanciere> findWithAvailableCredit();

    /**
     * Calcule le total du reste dû pour toutes les situations
     */
    Double calculateTotalResteDu();

    /**
     * Calcule le total des crédits disponibles
     */
    Double calculateTotalCredit();

    /**
     * Met à jour le statut d'une situation
     */
    void updateStatut(Long id, String newStatut);

    /**
     * Met à jour l'état d'une situation
     */
    void updateEtat(Long id, String newEtat);

    /**
     * Met à jour les montants d'une situation
     */
    void updateMontants(Long id, Double totalFacture, Double totalPaye, Double resteDu);

    /**
     * Vérifie si une situation existe pour un patient
     */
    boolean existsByPatientId(Long patientId);

    /**
     * Vérifie si une situation existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de situations
     */
    long count();

    /**
     * Compte les situations par statut
     */
    long countByStatut(String statut);

    /**
     * Récupère une page de situations
     */
    List<SituationFinanciere> findPage(int limit, int offset);
}