package ma.dentalTech.repository.modules.API;

import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.repository.common.CrudRepository;
import ma.dentalTech.entities.Patient.Antecedent;

import java.util.List;
import java.util.Optional;

/**
 * Author : Hamza
 */

public interface PatientRepository extends CrudRepository<Patient, Long> {

    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByTelephone(String telephone);
    List<Patient> searchByNomPrenom(String keyword); // LIKE %keyword%
    boolean existsById(Long id);
    long count();
    List<Patient> findPage(int limit, int offset);

    // ---- Liaison Many-to-Many ----
    void addAntecedentToPatient(Long patientId, Long antecedentId);
    void removeAntecedentFromPatient(Long patientId, Long antecedentId);
    void removeAllAntecedentsFromPatient(Long patientId);
    List<Antecedent> getAntecedentsOfPatient(Long patientId);
    List<Patient> getPatientsByAntecedent(Long antecedentId);

}
