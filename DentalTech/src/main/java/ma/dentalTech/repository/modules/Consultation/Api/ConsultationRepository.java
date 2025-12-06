package ma.dentalTech.repository.modules.Consultation.Api;

import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ConsultationRepository extends CrudRepository<Consultation, Long> {

    // Recherche par entité
    List<Consultation> findByDossierMedicaleId(Long dossierId);
    List<Consultation> findByPatientId(Long patientId);
    List<Consultation> findByMedecinId(Long medecinId);

    // Recherche par date
    List<Consultation> findByDate(LocalDate date);
    List<Consultation> findByDateRange(LocalDate debut, LocalDate fin);

    // Recherche par statut
    List<Consultation> findByStatut(StatutEnum statut);

    // Comptage
    long countByPatientId(Long patientId);
}