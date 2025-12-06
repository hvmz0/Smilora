package ma.dentalTech.repository.modules.Ordonnance.Api;

import ma.dentalTech.entities.Ordonnance.PrescriptionDesMedicaments;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface PrescriptionDesMedicamentsRepository extends CrudRepository<PrescriptionDesMedicaments, Long> {

    List<PrescriptionDesMedicaments> findByOrdonnanceId(Long ordonnanceId);
    List<PrescriptionDesMedicaments> findByMedicamentId(Long medicamentId);
    long countByOrdonnanceId(Long ordonnanceId);
}