package ma.dentalTech.service.modules.Referentiel.Impl;

import lombok.AllArgsConstructor;
import ma.dentalTech.common.exceptions.ServiceException;
import ma.dentalTech.common.exceptions.ValidationException;
import ma.dentalTech.common.validation.Validators;
import ma.dentalTech.entities.Referentiel.CabinetMedicale;
import ma.dentalTech.repository.modules.Referentiel.Api.CabinetMedicaleRepository;
import ma.dentalTech.service.modules.Referentiel.Api.CabinetMedicaleService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class CabinetMedicaleServiceImpl implements CabinetMedicaleService {

    private final CabinetMedicaleRepository cabinetRepository;

    @Override
    public List<CabinetMedicale> getAllCabinets() throws ServiceException {
        try {
            return cabinetRepository.findAll();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération des cabinets médicaux", e);
        }
    }

    @Override
    public Optional<CabinetMedicale> getCabinetById(Long id) throws ServiceException {
        try {
            CabinetMedicale cabinet = cabinetRepository.findById(id);
            return Optional.ofNullable(cabinet);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la récupération du cabinet avec l'ID: " + id, e);
        }
    }

    @Override
    public CabinetMedicale createCabinet(CabinetMedicale cabinet) throws ServiceException, ValidationException {
        try {
            validateCabinet(cabinet);
            cabinetRepository.create(user);
            return cabinet;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la création du cabinet médical", e);
        }
    }

    @Override
    public CabinetMedicale updateCabinet(CabinetMedicale cabinet) throws ServiceException, ValidationException {
        try {
            if (cabinet.getId() == null) {
                throw new ValidationException("L'ID du cabinet est requis pour la mise à jour");
            }
            validateCabinet(cabinet);
            cabinetRepository.update(cabinet);
            return cabinet;
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la mise à jour du cabinet médical", e);
        }
    }

    @Override
    public void deleteCabinet(Long id) throws ServiceException {
        try {
            CabinetMedicale cabinet = cabinetRepository.findById(id);
            if (cabinet == null) {
                throw new ServiceException("Cabinet non trouvé avec l'ID: " + id);
            }
            cabinetRepository.deleteById(id);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la suppression du cabinet médical", e);
        }
    }

    @Override
    public List<CabinetMedicale> searchCabinets(String keyword) throws ServiceException {
        try {
            if (keyword == null || keyword.trim().isEmpty()) {
                return getAllCabinets();
            }
            return cabinetRepository.searchByNom(keyword);
        } catch (Exception e) {
            throw new ServiceException("Erreur lors de la recherche de cabinets", e);
        }
    }

    @Override
    public long getTotalCabinets() throws ServiceException {
        try {
            return cabinetRepository.count();
        } catch (Exception e) {
            throw new ServiceException("Erreur lors du comptage des cabinets", e);
        }
    }

    private void validateCabinet(CabinetMedicale cabinet) throws ValidationException {
        Validators.notBlank(cabinet.getNom(), "Le nom");
    }
}
