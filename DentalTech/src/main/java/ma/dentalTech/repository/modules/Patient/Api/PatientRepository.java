package ma.dentalTech.repository.modules.Patient.Api;

import ma.dentalTech.entities.Patient.Antecedent;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.repository.common.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient, Long> {

    // Recherche
    Optional<Patient> findByEmail(String email);
    List<Patient> searchByNomPrenom(String keyword);

    // Pagination
    List<Patient> findPage(int pageSize, int offset);
    long count();
    boolean existsById(Long id);

    // Relations Many-to-Many avec Antecedents
    List<Antecedent> getAntecedentsOfPatient(Long patientId);
    List<Patient> getPatientsByAntecedent(Long antecedentId);
    void addAntecedentToPatient(Long patientId, Long antecedentId);
    void removeAntecedentFromPatient(Long patientId, Long antecedentId);
    void removeAllAntecedentsFromPatient(Long patientId);

    // Relations avec autres entités
    Long getDossierMedicaleId(Long patientId);
    Long getSituationFinanciereId(Long patientId);
}