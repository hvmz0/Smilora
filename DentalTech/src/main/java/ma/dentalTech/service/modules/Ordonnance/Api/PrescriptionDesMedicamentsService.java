package ma.dentalTech.service.modules.Ordonnance.Api;

import ma.dentalTech.entities.Ordonnance.PrescriptionDesMedicaments;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface PrescriptionDesMedicamentsService {

    // CRUD Operations
    List<PrescriptionDesMedicaments> getAllPrescriptions() throws ServiceException;
    Optional<PrescriptionDesMedicaments> getPrescriptionById(Long id) throws ServiceException;
    PrescriptionDesMedicaments createPrescription(PrescriptionDesMedicaments prescription) throws ServiceException, ValidationException;
    PrescriptionDesMedicaments updatePrescription(PrescriptionDesMedicaments prescription) throws ServiceException, ValidationException;
    void deletePrescription(Long id) throws ServiceException;

    // Recherche
    List<PrescriptionDesMedicaments> getPrescriptionsByOrdonnance(Long ordonnanceId) throws ServiceException;
    List<PrescriptionDesMedicaments> getPrescriptionsByMedicament(Long medicamentId) throws ServiceException;

    // Comptage
    long countPrescriptionsByOrdonnance(Long ordonnanceId) throws ServiceException;
}
