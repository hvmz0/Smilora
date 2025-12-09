package ma.dentalTech.service.modules.DossierMedicale.Api;

import ma.dentalTech.entities.DossierMedicale.DossierMedicale;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface DossierMedicaleService {

    // CRUD Operations
    List<DossierMedicale> getAllDossiers() throws ServiceException;
    Optional<DossierMedicale> getDossierById(Long id) throws ServiceException;
    DossierMedicale createDossier(DossierMedicale dossier) throws ServiceException, ValidationException;
    DossierMedicale updateDossier(DossierMedicale dossier) throws ServiceException, ValidationException;
    void deleteDossier(Long id) throws ServiceException;

    // Recherche
    Optional<Optional<DossierMedicale>> getDossierByPatient(Long patientId) throws ServiceException;
    List<DossierMedicale> searchDossiers(String keyword) throws ServiceException;

    // Comptage
    long getTotalDossiers() throws ServiceException;
}
