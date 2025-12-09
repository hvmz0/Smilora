package ma.dentalTech.service.modules.Patient.api;

import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    // CRUD Operations
    List<Patient> getAllPatients() throws ServiceException;
    Optional<Patient> getPatientById(Long id) throws ServiceException;
    Patient createPatient(Patient patient) throws ServiceException, ValidationException;
    Patient updatePatient(Patient patient) throws ServiceException, ValidationException;
    void deletePatient(Long id) throws ServiceException;

    // Business Operations
    Optional<Patient> findPatientByEmail(String email) throws ServiceException;
    List<Patient> searchPatients(String keyword) throws ServiceException;
    List<Patient> getPatientsByPage(int page, int size) throws ServiceException;
    long getTotalPatients() throws ServiceException;
    boolean patientExists(Long id) throws ServiceException;

    // Antecedents Management
    Patient getPatientWithAntecedents(Long patientId) throws ServiceException;
    void addAntecedentToPatient(Long patientId, Long antecedentId) throws ServiceException;
    void removeAntecedentFromPatient(Long patientId, Long antecedentId) throws ServiceException;

    // Relations
    Long getPatientDossierMedicaleId(Long patientId) throws ServiceException;
    Long getPatientSituationFinanciereId(Long patientId) throws ServiceException;
}