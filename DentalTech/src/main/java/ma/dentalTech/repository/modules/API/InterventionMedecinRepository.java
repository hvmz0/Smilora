package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.InterventionMedecin.InterventionMedecin;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

/**
 * Repository pour la gestion des interventions médicales
 * Author : Walid
 */
public interface InterventionMedecinRepository extends CrudRepository<InterventionMedecin, Long> {

    /**
     * Trouve les interventions par consultation
     */
    List<InterventionMedecin> findByConsultationId(Long consultationId);

    /**
     * Trouve les interventions par médecin
     */
    List<InterventionMedecin> findByMedecinId(Long medecinId);

    /**
     * Trouve les interventions par acte
     */
    List<InterventionMedecin> findByActeId(Long acteId);

    /**
     * Trouve les interventions par dent
     */
    List<InterventionMedecin> findByDent(String dentInt);

    /**
     * Trouve les interventions avec prix non couvert minimal
     */
    List<InterventionMedecin> findByPrixNonCouvertMin(Double prixMin);

    /**
     * Calcule le total des prix non couverts pour une consultation
     */
    Double calculateTotalNonCouvertByConsultation(Long consultationId);

    /**
     * Calcule le total des interventions pour un médecin
     */
    Double calculateTotalByMedecin(Long medecinId);

    /**
     * Compte les interventions par médecin
     */
    long countByMedecin(Long medecinId);

    /**
     * Compte les interventions par acte
     */
    long countByActe(Long acteId);

    /**
     * Trouve les interventions les plus récentes
     */
    List<InterventionMedecin> findRecent(int limit);

    /**
     * Vérifie si une intervention existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total d'interventions
     */
    long count();

    /**
     * Récupère une page d'interventions
     */
    List<InterventionMedecin> findPage(int limit, int offset);
}