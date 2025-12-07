package ma.dentalTech.repository.modules.Consultation.Api;
import ma.dentalTech.entities.Consultation.InterventionMedecin;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;

public interface InterventionMedecinRepository extends CrudRepository<InterventionMedecin, Long> {

    List<InterventionMedecin> findByConsultationId(Long consultationId);
    List<InterventionMedecin> findByActeId(Long acteId);
    Double calculateTotal(Long consultationId);
    long countByConsultationId(Long consultationId);
}