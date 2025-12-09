package ma.dentalTech.service.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.CabinetMedicale;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface CabinetMedicaleService {

    // CRUD Operations
    List<CabinetMedicale> getAllCabinets() throws ServiceException;
    Optional<CabinetMedicale> getCabinetById(Long id) throws ServiceException;
    CabinetMedicale createCabinet(CabinetMedicale cabinet) throws ServiceException, ValidationException;
    CabinetMedicale updateCabinet(CabinetMedicale cabinet) throws ServiceException, ValidationException;
    void deleteCabinet(Long id) throws ServiceException;

    // Recherche
    List<CabinetMedicale> searchCabinets(String keyword) throws ServiceException;

    // Comptage
    long getTotalCabinets() throws ServiceException;
}
