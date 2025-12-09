package ma.dentalTech.service.modules.Consultation.Api;

import ma.dentalTech.entities.Consultation.Consultation;
import ma.dentalTech.entities.Enums.StatutEnum;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ConsultationService {

    // CRUD Operations
    List<Consultation> getAllConsultations() throws ServiceException;
    Optional<Consultation> getConsultationById(Long id) throws ServiceException;
    Consultation createConsultation(Consultation consultation) throws ServiceException, ValidationException;
    Consultation updateConsultation(Consultation consultation) throws ServiceException, ValidationException;
    void deleteConsultation(Long id) throws ServiceException;

    // Recherche par entité
    List<Consultation> getConsultationsByDossierMedicale(Long dossierId) throws ServiceException;
    List<Consultation> getConsultationsByPatient(Long patientId) throws ServiceException;
    List<Consultation> getConsultationsByMedecin(Long medecinId) throws ServiceException;

    // Recherche par date
    List<Consultation> getConsultationsByDate(LocalDate date) throws ServiceException;
    List<Consultation> getConsultationsByDateRange(LocalDate debut, LocalDate fin) throws ServiceException;

    // Recherche par statut
    List<Consultation> getConsultationsByStatut(StatutEnum statut) throws ServiceException;

    // Comptage
    long countConsultationsByPatient(Long patientId) throws ServiceException;
}
