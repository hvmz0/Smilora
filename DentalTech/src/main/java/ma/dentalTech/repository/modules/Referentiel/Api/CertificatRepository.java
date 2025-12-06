package ma.dentalTech.repository.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.Certificat;
import ma.dentalTech.repository.common.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface CertificatRepository extends CrudRepository<Certificat, Long> {

    List<Certificat> findByConsultationId(Long consultationId);
    List<Certificat> findByPatientId(Long patientId);
    List<Certificat> findByDateRange(LocalDate debut, LocalDate fin);
    List<Certificat> findActive();
    long countByPatientId(Long patientId);
}