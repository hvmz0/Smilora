package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Ordonnance.Ordonnance;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des ordonnances
 * Author : Hamza
 */
public interface OrdonnanceRepository extends CrudRepository<Ordonnance, Long> {

    /**
     * Trouve une ordonnance par rendez-vous (relation 1:1)
     */
    Optional<Ordonnance> findByRendezVousId(Long rdvId);

    /**
     * Trouve les ordonnances par date
     */
    List<Ordonnance> findByDate(LocalDate date);

    /**
     * Trouve les ordonnances dans une plage de dates
     */
    List<Ordonnance> findByDateRange(LocalDate dateDebut, LocalDate dateFin);

    /**
     * Trouve les ordonnances par patient (via RDV)
     */
    List<Ordonnance> findByPatientId(Long patientId);

    /**
     * Trouve les ordonnances par médecin (via RDV)
     */
    List<Ordonnance> findByMedecinId(Long medecinId);

    /**
     * Trouve les ordonnances récentes
     */
    List<Ordonnance> findRecent(int limit);

    /**
     * Compte les ordonnances pour un patient
     */
    long countByPatient(Long patientId);

    /**
     * Compte les ordonnances pour un médecin
     */
    long countByMedecin(Long medecinId);

    /**
     * Vérifie si une ordonnance existe pour un rendez-vous
     */
    boolean existsByRendezVousId(Long rdvId);

    /**
     * Vérifie si une ordonnance existe
     */
    boolean existsById(Long id);

    /**
     * Compte le nombre total d'ordonnances
     */
    long count();

    /**
     * Récupère une page d'ordonnances
     */
    List<Ordonnance> findPage(int limit, int offset);
}