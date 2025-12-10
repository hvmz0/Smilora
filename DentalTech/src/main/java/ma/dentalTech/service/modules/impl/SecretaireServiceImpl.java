package ma.dentalTech.service.modules.impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.entities.Users.Secretaire;
import ma.dentalTech.repository.modules.Users.Api.SecretaireRepository;
import ma.dentalTech.service.modules.Api.SecretaireService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class SecretaireServiceImpl implements SecretaireService {

    private final SecretaireRepository secretaireRepository;

    @Override
    public List<Secretaire> getAllSecretaires() throws ServiceException {
        try {
            return secretaireRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des secrétaires", e);
        }
    }

    @Override
    public Optional<Secretaire> getSecretaireById(Long id) throws ServiceException {
        try {
            return Optional.ofNullable(secretaireRepository.findById(id));
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de la secrétaire", e);
        }
    }

    @Override
    public Secretaire createSecretaire(Secretaire secretaire) throws ServiceException, ValidationException {
        try {
            validateSecretaire(secretaire);
            secretaireRepository.create(user);
            return secretaire;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de la secrétaire", e);
        }
    }

    @Override
    public Secretaire updateSecretaire(Secretaire secretaire) throws ServiceException, ValidationException {
        try {
            if (secretaire.getId() == null) {
                throw new ValidationException("L'ID de la secrétaire est requis");
            }
            validateSecretaire(secretaire);
            secretaireRepository.update(secretaire);
            return secretaire;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de la secrétaire", e);
        }
    }

    @Override
    public void deleteSecretaire(Long id) throws ServiceException {
        try {
            secretaireRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de la secrétaire", e);
        }
    }

    @Override
    public Optional<Secretaire> getSecretaireByUserId(Long userId) throws ServiceException {
        try {
            return secretaireRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la secrétaire par utilisateur", e);
        }
    }

    @Override
    public Optional<Secretaire> getSecretaireByNumCNSS(String numCNSS) throws ServiceException {
        try {
            return secretaireRepository.findByNumCNSS(numCNSS);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de la secrétaire par numéro CNSS", e);
        }
    }

    @Override
    public List<Secretaire> getSecretairesByAgendaMed(String agendaMed) throws ServiceException {
        try {
            return secretaireRepository.findByAgendaMed(agendaMed);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des secrétaires par agenda médecin", e);
        }
    }

    @Override
    public long countSecretaires() throws ServiceException {
        try {
            return secretaireRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des secrétaires", e);
        }
    }

    private void validateSecretaire(Secretaire secretaire) throws ValidationException {
        if (secretaire.getUserId() == null) {
            throw new ValidationException("L'utilisateur est obligatoire");
        }
    }
}
