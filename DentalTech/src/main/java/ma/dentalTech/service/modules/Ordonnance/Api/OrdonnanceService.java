package ma.dentalTech.service.modules.Ordonnance.Api;

import ma.dentalTech.entities.Ordonnance.Ordonnance;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OrdonnanceService {

    // CRUD Operations
    List<Ordonnance> getAllOrdonnances() throws ServiceException;
    Optional<Ordonnance> getOrdonnanceById(Long id) throws ServiceException;
    Ordonnance createOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException;
    Ordonnance updateOrdonnance(Ordonnance ordonnance) throws ServiceException, ValidationException;
    void deleteOrdonnance(Long id) throws ServiceException;

    // Recherche
    List<Ordonnance> getOrdonnancesByPatient(Long patientId) throws ServiceException;
    List<Ordonnance> getOrdonnancesByMedecin(Long medecinId) throws ServiceException;
    List<Ordonnance> getOrdonnancesByConsultation(Long consultationId) throws ServiceException;
    List<Ordonnance> getOrdonnancesByDate(LocalDate date) throws ServiceException;

    // Comptage
    long countOrdonnancesByPatient(Long patientId) throws ServiceException;
}
