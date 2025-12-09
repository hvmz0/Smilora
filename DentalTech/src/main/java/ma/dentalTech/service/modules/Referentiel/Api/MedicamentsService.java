package ma.dentalTech.service.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.Medicaments;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface MedicamentsService {

    // CRUD Operations
    List<Medicaments> getAllMedicaments() throws ServiceException;
    Optional<Medicaments> getMedicamentById(Long id) throws ServiceException;
    Medicaments createMedicament(Medicaments medicament) throws ServiceException, ValidationException;
    Medicaments updateMedicament(Medicaments medicament) throws ServiceException, ValidationException;
    void deleteMedicament(Long id) throws ServiceException;

    // Recherche
    List<Medicaments> searchMedicaments(String keyword) throws ServiceException;

    // Comptage
    long getTotalMedicaments() throws ServiceException;
}
