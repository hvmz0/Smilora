package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des consultations
 * Author : Walid
 */
public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

    /**
     * Trouve une consultation par rendez-vous
     */
    Optional<Consultation> findByRendezVousId(Long rdvId);

    /**
     * Trouve les consultations par dossier médical
     */
    List<Consultation> findByDossierMedicaleId(Long dossierId);

    /**
     * Trouve les consultations par médecin
     */
    List<Consultation> findByMedecinId(Long medecinId);

    /**
     * Trouve les consultations par statut
     */
    List<Consultation> findByStatut(String statut);

    /**
     * Trouve les consultations par date
     */
    List<Consultation> findByDate(LocalDate date);

    /**
     * Trouve les consultations dans une plage de dates
     */
    List<Consultation> findByDateRange(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Trouve les consultations par médecin et date
     */
    List<Consultation> findByMedecinIdAndDate(Long medecinId, LocalDate date);

    /**
     * Trouve les consultations par patient (via dossier médical)
     */
    List<Consultation> findByPatientId(Long patientId);

    /**
     * Compte les consultations par médecin
     */
    long countByMedecin(Long medecinId);

    /**
     * Compte les consultations par statut
     */
    long countByStatut(String statut);

    /**
     * Met à jour le statut d'une consultation
     */
    void updateStatut(Long id, String newStatut);

    /**
     * Récupère les dernières consultations
     */
    List<Consultation> findRecent(int limit);

    /**
     * Vérifie si une consultation existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de consultations
     */
    long count();

    /**
     * Récupère une page de consultations
     */
    List<Consultation> findPage(int limit, int offset);
}