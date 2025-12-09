package ma.dentalTech.repository.modules.Ordonnance.Api;

import ma.dentalTech.entities.Ordonnance.Ordonnance;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrdonnanceRepository extends CrudRepository<Ordonnance, Long> {

    Optional<Ordonnance> findByConsultationId(Long consultationId);
    List<Ordonnance> findByPatientId(Long patientId);

    // --- FIND BY MEDECIN ID (Méthode spécifique) ---
    List<Ordonnance> findByMedecinId(Long medecinId);

    List<Ordonnance> findByDate(LocalDate date);
    List<Ordonnance> findByDateRange(LocalDate debut, LocalDate fin);
    long countByPatientId(Long patientId);
}