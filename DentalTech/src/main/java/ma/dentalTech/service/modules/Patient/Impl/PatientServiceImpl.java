package ma.dentalTech.service.modules.Patient.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Patient.Patient;
import ma.dentalTech.repository.modules.Patient.Api.PatientRepository;
import ma.dentalTech.service.modules.Patient.Api.PatientService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public List<Patient> getAllPatients() throws ServiceException {
        try {
            return patientRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des patients", e);
        }
    }

    @Override
    public Optional<Patient> getPatientById(Long id) throws ServiceException {
        try {
            Patient patient = patientRepository.findById(id);
            return Optional.ofNullable(patient);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du patient avec l'ID: " + id, e);
        }
    }

    @Override
    public Patient createPatient(Patient patient) throws ServiceException, ValidationException {
        try {
            // Validation
            validatePatient(patient);

            // Vérifier l'unicité de l'email si présent
            if (patient.getEmail() != null && !patient.getEmail().isEmpty()) {
                Optional<Patient> existing = patientRepository.findByEmail(patient.getEmail());
                if (existing.isPresent()) {
                    throw new ValidationException("Un patient avec cet email existe déjà");
                }
            }

            // Création
            patientRepository.create(user);
            return patient;

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du patient", e);
        }
    }

    @Override
    public Patient updatePatient(Patient patient) throws ServiceException, ValidationException {
        try {
            // Validation
            if (patient.getId() == null) {
                throw new ValidationException("L'ID du patient est requis pour la mise à jour");
            }

            validatePatient(patient);

            // Vérifier que le patient existe
            if (!patientRepository.existsById(patient.getId())) {
                throw new ServiceException("Patient non trouvé avec l'ID: " + patient.getId());
            }

            // Mise à jour
            patientRepository.update(patient);
            return patient;

        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du patient", e);
        }
    }

    @Override
    public void deletePatient(Long id) throws ServiceException {
        try {
            if (!patientRepository.existsById(id)) {
                throw new ServiceException("Patient non trouvé avec l'ID: " + id);
            }
            patientRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du patient", e);
        }
    }

    @Override
    public Optional<Patient> findPatientByEmail(String email) throws ServiceException {
        try {
            return patientRepository.findByEmail(email);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche du patient par email", e);
        }
    }

    @Override
    public List<Patient> searchPatients(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllPatients();
            }
            return patientRepository.searchByNomPrenom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de patients", e);
        }
    }

    @Override
    public List<Patient> getPatientsByPage(int page, int size) throws ServiceException {
        try {
            int offset = page * size;
            return patientRepository.findPage(size, offset);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la page de patients", e);
        }
    }

    @Override
    public long getTotalPatients() throws ServiceException {
        try {
            return patientRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des patients", e);
        }
    }

    @Override
    public boolean patientExists(Long id) throws ServiceException {
        try {
            return patientRepository.existsById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la vérification de l'existence du patient", e);
        }
    }

    @Override
    public Patient getPatientWithAntecedents(Long patientId) throws ServiceException {
        try {
            Patient patient = patientRepository.findById(patientId);
            if (patient == null) {
                throw new ServiceException("Patient non trouvé avec l'ID: " + patientId);
            }
            patient.setAntecedents(patientRepository.getAntecedentsOfPatient(patientId));
            return patient;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du patient avec antécédents", e);
        }
    }

    @Override
    public void addAntecedentToPatient(Long patientId, Long antecedentId) throws ServiceException {
        try {
            if (!patientRepository.existsById(patientId)) {
                throw new ServiceException("Patient non trouvé avec l'ID: " + patientId);
            }
            patientRepository.addAntecedentToPatient(patientId, antecedentId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de l'ajout de l'antécédent au patient", e);
        }
    }

    @Override
    public void removeAntecedentFromPatient(Long patientId, Long antecedentId) throws ServiceException {
        try {
            patientRepository.removeAntecedentFromPatient(patientId, antecedentId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'antécédent du patient", e);
        }
    }

    @Override
    public Long getPatientDossierMedicaleId(Long patientId) throws ServiceException {
        try {
            return patientRepository.getDossierMedicaleId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du dossier médical", e);
        }
    }

    @Override
    public Long getPatientSituationFinanciereId(Long patientId) throws ServiceException {
        try {
            return patientRepository.getSituationFinanciereId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la situation financière", e);
        }
    }

    // Méthode privée de validation
    private void validatePatient(Patient patient) throws ValidationException {
        Validators.notBlank(patient.getNom(), "Le nom");

        if (patient.getTel() != null && !patient.getTel().isEmpty()) {
            Validators.phone(patient.getTel());
        }

        if (patient.getEmail() != null && !patient.getEmail().isEmpty()) {
            Validators.email(patient.getEmail());
        }

        if (patient.getSexe() == null) {
            throw new ValidationException("Le sexe est obligatoire");
        }

        if (patient.getAssurance() == null) {
            throw new ValidationException("Le type d'assurance est obligatoire");
        }
    }
}