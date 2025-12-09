package ma.dentalTech.service.modules.Referentiel.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Referentiel.Actes;
import ma.dentalTech.repository.modules.Referentiel.Api.ActesRepository;
import ma.dentalTech.service.modules.Referentiel.Api.ActesService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ActesServiceImpl implements ActesService {

    private final ActesRepository actesRepository;

    @Override
    public List<Actes> getAllActes() throws ServiceException {
        try {
            return actesRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des actes", e);
        }
    }

    @Override
    public Optional<Actes> getActeById(Long id) throws ServiceException {
        try {
            Actes acte = actesRepository.findById(id);
            return Optional.ofNullable(acte);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération de l'acte avec l'ID: " + id, e);
        }
    }

    @Override
    public Actes createActe(Actes acte) throws ServiceException, ValidationException {
        try {
            validateActe(acte);
            actesRepository.create(user);
            return acte;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création de l'acte", e);
        }
    }

    @Override
    public Actes updateActe(Actes acte) throws ServiceException, ValidationException {
        try {
            if (acte.getId() == null) {
                throw new ValidationException("L'ID de l'acte est requis pour la mise à jour");
            }
            validateActe(acte);
            actesRepository.update(acte);
            return acte;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour de l'acte", e);
        }
    }

    @Override
    public void deleteActe(Long id) throws ServiceException {
        try {
            Actes acte = actesRepository.findById(id);
            if (acte == null) {
                throw new ServiceException("Acte non trouvé avec l'ID: " + id);
            }
            actesRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression de l'acte", e);
        }
    }

    @Override
    public List<Actes> searchActes(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllActes();
            }
            return actesRepository.searchByLibelle(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche d'actes", e);
        }
    }

    @Override
    public long getTotalActes() throws ServiceException {
        try {
            return actesRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des actes", e);
        }
    }

    private void validateActe(Actes acte) throws ValidationException {
        Validators.notBlank(acte.getLibelle(), "Le libellé");
    }
}
