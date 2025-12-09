package ma.dentalTech.service.modules.Consultation.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.repository.modules.Consultation.Api.ConsultationRepository;
import ma.dentalTech.service.modules.Consultation.Api.ConsultationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;

    @Override
    public List<Consultation> getAllConsultations() throws ServiceException {
        try {
            return consultationRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations", e);
        }
    }

    @Override
    public Optional<Consultation> getConsultationById(Long id) throws ServiceException {
        try {
            Consultation consultation = consultationRepository.findById(id);
            return Optional.ofNullable(consultation);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la consultation avec l'ID: " + id, e);
        }
    }

    @Override
    public Consultation createConsultation(Consultation consultation) throws ServiceException, ValidationException {
        try {
            validateConsultation(consultation);
            consultationRepository.create(user);
            return consultation;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la consultation", e);
        }
    }

    @Override
    public Consultation updateConsultation(Consultation consultation) throws ServiceException, ValidationException {
        try {
            if (consultation.getId() == null) {
                throw new ValidationException("L'ID de la consultation est requis pour la mise à jour");
            }
            validateConsultation(consultation);
            consultationRepository.update(consultation);
            return consultation;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la consultation", e);
        }
    }

    @Override
    public void deleteConsultation(Long id) throws ServiceException {
        try {
            Consultation consultation = consultationRepository.findById(id);
            if (consultation == null) {
                throw new ServiceException("Consultation non trouvée avec l'ID: " + id);
            }
            consultationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la consultation", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByDossierMedicale(Long dossierId) throws ServiceException {
        try {
            return consultationRepository.findByDossierMedicaleId(dossierId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations du dossier", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByPatient(Long patientId) throws ServiceException {
        try {
            return consultationRepository.findByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations du patient", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByMedecin(Long medecinId) throws ServiceException {
        try {
            return consultationRepository.findByMedecinId(medecinId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations du médecin", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByDate(LocalDate date) throws ServiceException {
        try {
            return consultationRepository.findByDate(date);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations par date", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByDateRange(LocalDate debut, LocalDate fin) throws ServiceException {
        try {
            return consultationRepository.findByDateRange(debut, fin);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations par plage de date", e);
        }
    }

    @Override
    public List<Consultation> getConsultationsByStatut(StatutEnum statut) throws ServiceException {
        try {
            return consultationRepository.findByStatut(statut);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des consultations par statut", e);
        }
    }

    @Override
    public long countConsultationsByPatient(Long patientId) throws ServiceException {
        try {
            return consultationRepository.countByPatientId(patientId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des consultations du patient", e);
        }
    }

    private void validateConsultation(Consultation consultation) throws ValidationException {
        if (consultation.getDateCons() == null) {
            throw new ValidationException("La date de consultation est obligatoire");
        }
        if (consultation.getPatientId() == null) {
            throw new ValidationException("L'ID du patient est obligatoire");
        }
        if (consultation.getMedecinId() == null) {
            throw new ValidationException("L'ID du médecin est obligatoire");
        }
    }
}
