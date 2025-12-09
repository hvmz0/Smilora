package ma.dentalTech.service.modules.Users.Api;

import ma.dentalTech.entities.Users.Secretaire;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;

import java.util.List;
import java.util.Optional;

public interface SecretaireService {

    List<Secretaire> getAllSecretaires() throws ServiceException;
    Optional<Secretaire> getSecretaireById(Long id) throws ServiceException;
    Secretaire createSecretaire(Secretaire secretaire) throws ServiceException, ValidationException;
    Secretaire updateSecretaire(Secretaire secretaire) throws ServiceException, ValidationException;
    void deleteSecretaire(Long id) throws ServiceException;

    Optional<Secretaire> getSecretaireByUserId(Long userId) throws ServiceException;
    Optional<Secretaire> getSecretaireByNumCNSS(String numCNSS) throws ServiceException;
    List<Secretaire> getSecretairesByAgendaMed(String agendaMed) throws ServiceException;
    long countSecretaires() throws ServiceException;
}
