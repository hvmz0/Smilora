package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.RDV.RDV;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des rendez-vous
 * Author : Abdo
 */
public interface RDVRepository extends CrudRepository<RDV, Long> {

    /**
     * Trouve les rendez-vous par patient
     */
    List<RDV> findByPatientId(Long patientId);

    /**
     * Trouve les rendez-vous par agenda médecin
     */
    List<RDV> findByAgendaId(Long agendaId);

    /**
     * Trouve les rendez-vous par statut
     */
    List<RDV> findByStatut(String statut);

    /**
     * Trouve les rendez-vous par date
     */
    List<RDV> findByDate(LocalDateTime date);

    /**
     * Trouve les rendez-vous dans une plage de dates
     */
    List<RDV> findByDateRange(LocalDateTime dateDebut, LocalDateTime dateFin);

    /**
     * Trouve les rendez-vous à venir pour un patient
     */
    List<RDV> findUpcomingByPatient(Long patientId);

    /**
     * Trouve les rendez-vous passés pour un patient
     */
    List<RDV> findPastByPatient(Long patientId);

    /**
     * Trouve les rendez-vous du jour
     */
    List<RDV> findToday();

    /**
     * Trouve les rendez-vous de la semaine
     */
    List<RDV> findThisWeek();

    /**
     * Recherche des rendez-vous par motif
     */
    List<RDV> searchByMotif(String keyword);

    /**
     * Trouve un rendez-vous lié à une consultation
     */
    Optional<RDV> findByConsultationId(Long consultationId);

    /**
     * Met à jour le statut d'un rendez-vous
     */
    void updateStatut(Long id, String newStatut);

    /**
     * Compte les rendez-vous par patient
     */
    long countByPatient(Long patientId);

    /**
     * Compte les rendez-vous par statut
     */
    long countByStatut(String statut);

    /**
     * Compte les rendez-vous du jour
     */
    long countToday();

    /**
     * Vérifie si un rendez-vous existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total de rendez-vous
     */
    long count();

    /**
     * Récupère une page de rendez-vous
     */
    List<RDV> findPage(int limit, int offset);
}