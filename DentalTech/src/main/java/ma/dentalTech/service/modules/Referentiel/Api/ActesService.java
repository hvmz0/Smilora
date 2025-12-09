package ma.dentalTech.service.modules.Referentiel.Api;

import ma.dentalTech.entities.Referentiel.Actes;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface ActesService {

    // CRUD Operations
    List<Actes> getAllActes() throws ServiceException;
    Optional<Actes> getActeById(Long id) throws ServiceException;
    Actes createActe(Actes acte) throws ServiceException, ValidationException;
    Actes updateActe(Actes acte) throws ServiceException, ValidationException;
    void deleteActe(Long id) throws ServiceException;

    // Recherche
    List<Actes> searchActes(String keyword) throws ServiceException;

    // Comptage
    long getTotalActes() throws ServiceException;
}
